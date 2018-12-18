package com.smallchill.modules.platform.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.modules.platform.model.ICCard;
import com.smallchill.modules.platform.model.OilTrade;
import com.smallchill.modules.platform.service.OilTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated by Blade.
 * 2017-09-22 11:11:39
 */
@Controller
@RequestMapping("/oilTrade")
public class OilTradeController extends BaseController {
	private static String CODE = "oilTrade";
	private static String PREFIX = "tb_oil_trade";
	private static String LIST_SOURCE = "oilTrade.list";
	private static String BASE_PATH = "/modules/platform/oilTrade/";
	
	@Autowired
    OilTradeService service;
	
	@RequestMapping(KEY_MAIN)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
        mm.put("cardNo", getParameter("cardNo", "x"));
		return BASE_PATH + "oilTrade.html";
	}

	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "oilTrade_add.html";
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	public String edit(@PathVariable Integer id, ModelMap mm) {
		OilTrade oilTrade = service.findById(id);
		mm.put("model", JsonKit.toJson(oilTrade));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "oilTrade_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable Integer id, ModelMap mm) {
		OilTrade oilTrade = service.findById(id);
		mm.put("model", JsonKit.toJson(oilTrade));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "oilTrade_view.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	public JqGrid<Map<String, Object>> list() {

        final Map[] sum = {new HashMap()};

        JqGrid<Map<String, Object>> grid = (JqGrid<Map<String, Object>>) paginate(LIST_SOURCE, new PageIntercept(){
            @Override
            public void queryBefore(AopContext ac) {
                Map<String, Object> param = ac.getParam();
                StringBuilder sb = new StringBuilder();

                if (ShiroKit.hasRole(ConstShiro.STATION)) {
                    sb.append("and StationNo = #{StationNo}");
                    ac.getParam().put("StationNo", ShiroKit.getUser().getDeptId());
                }

                if (param.get("OilTime_start_skip_true") != null) {
                    sb.append(" and OilTime >= #{OilTime_start_skip_true} ");
                }
                if (param.get("OilTime_end_skip_true") != null) {
                    sb.append(" and OilTime <= #{OilTime_end_skip_true} ");
                }
                if (param.get("CarNo_skip_true") != null) {
                    String cards = SysCache.getCardsByCarNo(param.get("CarNo_skip_true"));
                    param.put("CardNoExt", cards.split(","));
                    sb.append(" and CardNo in (#{join(CardNoExt)}) ");
                }

                ac.setCondition(sb.toString());

                ac.setSqlCount("select count(1) from tb_oil_trade" + ac.getSqlEx() + sb.toString());

                sum[0] = Db.selectOne("select sum(Qty) qty, SUM(TradeMoney) tra from tb_oil_trade" + ac.getSqlEx() + sb.toString(), param);

            }

            @Override
            public void queryAfter(AopContext ac) {
                BladePage<Map<String, Object>> page = (BladePage<Map<String, Object>>) ac.getObject();
                List<Map<String, Object>> list = page.getRows();
                for (Map<String, Object> map : list) {
                    ICCard card = SysCache.getCard(map.get("CardNo"));
                    if (null != card) {
                        map.put("CardSerialNo", card.getCardSerialNo());
                        map.put("CarNo", card.getCarNo());
                        map.put("ReserveFund", card.getReserveFund());
                    }
                }
            }
        });

        grid.setExt(sum[0]);

		return grid;
	}

    @Json
    @RequestMapping("listOne")
    public Object listOne() {
        Object grid = paginate(LIST_SOURCE, new PageIntercept(){
            @Override
            public void queryBefore(AopContext ac) {
                String cardNo = ac.getCtrl().getParameter("CardNo");
                ac.getParam().put("cardNo", cardNo);
                ac.setCondition("and CardNo = #{cardNo} ");
            }
        });
        return grid;
    }

	@Json
	@RequestMapping(KEY_SAVE)
	public ApiResult save() {
		OilTrade oilTrade = mapping(PREFIX, OilTrade.class);
		boolean temp = service.save(oilTrade);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_UPDATE)
	public ApiResult update() {
		OilTrade oilTrade = mapping(PREFIX, OilTrade.class);
		boolean temp = service.update(oilTrade);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_REMOVE)
	public ApiResult remove(@RequestParam String ids) {
		int cnt = service.deleteByIds(ids);
		if (cnt > 0) {
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
}