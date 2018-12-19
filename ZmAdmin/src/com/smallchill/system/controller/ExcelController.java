package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.core.toolbox.support.SqlKeyword;
import org.apache.poi.hssf.util.HSSFColor;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	private static String cacheName = ConstCache.SYS_CACHE;

	@Json
	@RequestMapping("/preExport")
	@SuppressWarnings("unchecked")
	public ApiResult preExport(@RequestParam String postdata, @RequestParam String colnames, @RequestParam String colmodel, @RequestParam String source, @RequestParam String code) {
		Map<String, Object> _postdata = JsonKit.parse(postdata, HashMap.class);
		String[] _colname = colnames.replace("[", "").replace("]", "").split(",");
		List<Map<String, String>> _colmodel = JsonKit.parse(colmodel, ArrayList.class);

        //针对消费记录模块的特殊处理
        if (code.equalsIgnoreCase("oilTrade") || code.equalsIgnoreCase("oilTradeCust")) {
		    source = "oilTrade.listExcel";
        }

		String md_source = Md.getSql(source);
		String menu_source = getInfoByCode(code, "SOURCE");

		String _source = (StrKit.notBlank(menu_source)) ? menu_source : md_source;

		if (StrKit.isBlank(_source)) {
			return error("未找到与该模块匹配的数据源！");
		}

		String where = Func.toStr(_postdata.get("where"));
		Object sidx = _postdata.get("sidx");
		Object sord = _postdata.get("sord");
		Object sort = _postdata.get("sort");
		Object order = _postdata.get("order");
		if (!Func.isEmpty(sidx)) {
			sort = sidx + " " + sord + (Func.isEmpty(sort) ? ("," + sort) : "");
		}
		String orderby = (Func.isOneEmpty(sort, order)) ? (" order by " + sort + " " + order) : "";
		Map<String, Object> para = JsonKit.parse(Func.isEmpty(Func.decodeUrl(where)) ? null : Func.decodeUrl(where), HashMap.class);
		if (Func.isEmpty(para)) {
			para = new HashMap<>();
		}

        //针对消费记录模块的特殊处理
		String where_ext = "";
        if (code.equalsIgnoreCase("oilTrade") || code.equalsIgnoreCase("oilTradeCust")) {
            StringBuilder sb = new StringBuilder();
            if (para.get("OilTime_start_skip_true") != null) {
                sb.append(" and OilTime >= #{OilTime_start_skip_true} ");
            }
            if (para.get("OilTime_end_skip_true") != null) {
                sb.append(" and OilTime <= #{OilTime_end_skip_true} ");
            }
            where_ext = sb.toString();
        }
        
        //针对车辆管理模块的特殊处理
        if(code.equalsIgnoreCase("iccard")) {
        	where_ext += " and CardNo not in (SELECT CardNo from tb_charge_list where CustNo = #{CustNo} and (status = 1 or status = 3)) ";
        }

        if (ShiroKit.hasRole(ConstShiro.COMPANY)) {
        	where_ext += " and CustNo = #{CustNo} ";
        }
        if (ShiroKit.hasRole(ConstShiro.STATION)) {
        	where_ext += " and StationNo = #{StationNo} ";
        }
        
        
		String sql = "select {} from (" + _source + ") a " + SqlKeyword.getWhere(where) + where_ext + orderby;

		CacheKit.remove(cacheName, EXCEL_SQL + code);
        CacheKit.remove(cacheName, EXCEL_SQL_PARA + code);
		CacheKit.remove(cacheName, EXCEL_COL_NAME + code);
		CacheKit.remove(cacheName, EXCEL_COL_MODEL + code);
		CacheKit.put(cacheName, EXCEL_SQL + code, sql);
		CacheKit.put(cacheName, EXCEL_SQL_PARA + code, para);
		CacheKit.put(cacheName, EXCEL_COL_NAME + code, _colname);
		CacheKit.put(cacheName, EXCEL_COL_MODEL + code, _colmodel);

		return json(code);
	}

	/**
	 * excel视图方式
	 */
	@RequestMapping("/export")
	public String export(ModelMap modelMap, HttpServletResponse response, @RequestParam String code) {
		String sql = CacheKit.get(cacheName, EXCEL_SQL + code);
		Map<String, Object> para = CacheKit.get(cacheName, EXCEL_SQL_PARA + code);
		String[] _colname = CacheKit.get(cacheName, EXCEL_COL_NAME + code);
		List<Map<String, String>> _colmodel = CacheKit.get(cacheName, EXCEL_COL_MODEL + code);

		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		for (Map<String, String> m : _colmodel) {
			if (cnt > 1) {
				if (Func.toStr(m.get("hidden")).equals("true") || Func.toStr(m.get("skip")).equals("true") ) {
					cnt++;
					continue;
				}
				String name = m.get("name");
				entityList.add(new ExcelExportEntity(_colname[cnt], name, Func.toInt(m.get("widthOrg"), 70) / 4));
				sb.append(name).append(",");
			}
			cnt++;
		}

		String menu_name = getInfoByCode(code, "NAME");
		if (Func.isPostgresql()) {
			//postgresql8.3+版本 字段类型敏感,如果是int型需要做强制类型转换,mysql和oracle可以无视
			for (String key : para.keySet()) {
				if (key.startsWith(SqlKeyword.TOINT) || key.startsWith(SqlKeyword.IT) || key.startsWith(SqlKeyword.F_IT)) {
					para.put(key, Convert.toInt(para.get(key)));
				}
			}
		}
		para.put("CustNo", ShiroKit.getUser().getDeptId());
		@SuppressWarnings("rawtypes")
		List<Map> dataResult = Db.selectList(Func.format(sql, StrKit.removeSuffix(sb.toString(), ",")), para);

        if (code.equalsIgnoreCase("oilTrade") || code.equalsIgnoreCase("oilTradeCust")) {
            double qty = 0;
            double trade = 0;
            for (Map map : dataResult) {
                double q = Convert.toDouble(map.get("Qty"), 0.0);
                double t = Convert.toDouble(map.get("TradeMoney"), 0.0);
                qty = qty + q;
                trade = trade + t;
            }

            Map sum = new HashMap();
            sum.put("Price", "加油量总计");
            sum.put("Qty",  new BigDecimal(qty).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            sum.put("Balance", "加油金额总计");
            sum.put("TradeMoney", new BigDecimal(trade).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            sum.put("CardNo", "");
            sum.put("CustName", "");
            sum.put("CardSerialNo", "");
            sum.put("CarNo", "");
            sum.put("Balance1", "");
            sum.put("ReserveFund", "");
            sum.put("CustNo", "");
            sum.put("HolderName", "");
            sum.put("CustHolderIDTypeName", "");
            sum.put("CustHolderID", "");
            sum.put("FlowNo", "");
            sum.put("StationNo", "");
            sum.put("StationAbbreviate", "");
            sum.put("CustNo", "");
            sum.put("CardTypeName", "");
            sum.put("GunNo", "");
            sum.put("OilAbbreviate", "");
            sum.put("OilNo", "");
            sum.put("OilTime", "");
            sum.put("PayPrice", "");
            sum.put("PayTime", "");
            sum.put("PayWayName", "");
            sum.put("Points", "");
            sum.put("PointsBalance", "");
            sum.put("PointsCardNo", "");
            sum.put("SaleDate", "");
            sum.put("Settlement", "");
            sum.put("SettlementType", "");
            sum.put("SettlementUniteName", "");
            sum.put("TradeTypeName", "");
            sum.put("UploadTime", "");
            sum.put("OperatorName", "");
            sum.put("OperatorNo", "");
            sum.put("ThirdpartyCustNo", "");
            sum.put("ShiftNo", "");
            sum.put("id", "");
            dataResult.add(sum);

        }

        StringBuilder paramSb = new StringBuilder();
        if (code.equals("order")) {
            for (String key : para.keySet()) {

                if (key.equals("create_time_lt") || key.equals("charge_time_lt")) {
                    continue;
                }

                if (key.equals("create_time_gt") && para.containsKey("create_time_gt") && para.containsKey("create_time_lt")) {
                    paramSb.append(orderSearch.get("create_time_gt") + "：" + para.get("create_time_gt") + " ");
                    paramSb.append(orderSearch.get("create_time_lt") + " " + para.get("create_time_lt") + "; ");
                } else if (key.equals("charge_time_gt") && para.containsKey("charge_time_gt") && para.containsKey("charge_time_lt")) {
                    paramSb.append(orderSearch.get("charge_time_gt") + "：" + para.get("charge_time_gt") + " ");
                    paramSb.append(orderSearch.get("charge_time_lt") + " " + para.get("charge_time_lt") + "; ");
                } else {
                    paramSb.append(orderSearch.get(key) + "：" + para.get(key) + "; ");
                }

            }
        }

		ExportParams exportParams = new ExportParams(menu_name + " 数据导出表", code);
		exportParams.setColor(HSSFColor.GREY_50_PERCENT.index);
		exportParams.setAddIndex(true);
		exportParams.setIndexName("序号");
		if (notBlank(paramSb.toString())) {
	    	exportParams.setSecondTitle("筛选条件[  " + paramSb.toString() + "  ]");
        }
		//exportParams.setSecondTitleHeight(new Short("16"));

		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
		modelMap.put(MapExcelConstants.FILE_NAME, menu_name + DateKit.getAllTime() + "("+ShiroKit.getUser().getLoginName()+")");
		modelMap.put(NormalExcelConstants.PARAMS, exportParams);
		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}

	private String getInfoByCode(String code, String col) {
		List<Map<String, Object>> menu = CacheKit.get(SYS_CACHE, MENU_TABLE_ALL, new ILoader() {
					public Object load() {
						return Db.selectList("select CODE,PCODE,NAME,URL,SOURCE,PATH,TIPS,ISOPEN from BLADE_MENU order by levels asc,num asc");
					}
				});
		for (Map<String, Object> _menu : menu) {
			if (code.equals(Func.toStr(_menu.get("CODE")))) {
				String _info = Func.toStr(_menu.get(col));
				return _info;
			}
		}
		return "";
	}

	private static Map<String, String> orderSearch = new HashMap<>();

	static {
        orderSearch.put("create_time_gt", "创建订单时间");
        orderSearch.put("create_time_lt", "至");
        orderSearch.put("order_no", "订单编号");
        orderSearch.put("customer_card_no", "顾客卡号");
        orderSearch.put("charge_time_gt", "核销时间");
        orderSearch.put("charge_time_lt", "至");
        orderSearch.put("customer_name", "顾客姓名");
        orderSearch.put("customer_phone", "顾客手机");
        orderSearch.put("status", "订单状态");
    }

}
