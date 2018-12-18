package com.smallchill.modules.charge.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.modules.charge.model.Charge;
import com.smallchill.modules.charge.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Generated by Blade.
 * 2017-09-10 01:09:32
 */
@Controller
@RequestMapping("/mycharge")
public class MyChargeController extends BaseController {
	private static String CODE = "mycharge";
	private static String PREFIX = "tb_charge";
	private static String LIST_SOURCE = "mycharge.list";
    private static String TRANS_LIST_SOURCE = "mycharge.transList";
	private static String BASE_PATH = "/modules/charge/mycharge/";
	
	@Autowired
    ChargeService service;
	
	@RequestMapping(KEY_MAIN)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "charge.html";
	}

    @RequestMapping("/trans")
    public String indexTrans(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "charge_trans.html";
    }

	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
        Object grid = paginate(LIST_SOURCE, new PageIntercept(){

            @Override
            public void queryBefore(AopContext ac) {
                /*if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)) {
                    sb.append("and CustNo = #{CustNo}");
                    ac.getParam().put("CustNo", ShiroKit.getUser().getDeptId());
                }*/
                StringBuilder sb = new StringBuilder();
                Map<String, Object> param = ac.getParam();

                if (param.get("month_skip_true") != null) {
                    sb.append(" and MONTH(CreateTime) = #{month_skip_true} ");
                }
                if (param.get("year_skip_true") != null) {
                    sb.append(" and YEAR(CreateTime) = #{year_skip_true} ");
                }
                ac.setCondition(sb.toString());
            }

            @Override
            public void queryAfter(AopContext ac) {
                BladePage<Map<String, Object>> page = (BladePage<Map<String, Object>>) ac.getObject();
                List<Map<String, Object>> list = page.getRows();
                for (Map<String, Object> map : list) {
                    map.put("StatusName", SysCache.getDictName(905, map.get("Status")));
                }
            }
        });
        return grid;
	}

    @Json
    @RequestMapping("transList")
    public Object transList() {
        Object grid = paginate(TRANS_LIST_SOURCE, new PageIntercept(){

            @Override
            public void queryBefore(AopContext ac) {
                /*if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)) {
                    ac.getParam().put("CustNo", ShiroKit.getUser().getDeptId());
                    ac.setCondition("and CustNo = #{CustNo}");
                }*/
                Map<String, Object> param = ac.getParam();
                StringBuilder sb = new StringBuilder();

                if (param.get("month_skip_true") != null) {
                    sb.append(" and MONTH(CreateTime) = #{month_skip_true} ");
                }
                if (param.get("year_skip_true") != null) {
                    sb.append(" and YEAR(CreateTime) = #{year_skip_true} ");
                }
                ac.setCondition(sb.toString());
            }

            @Override
            public void queryAfter(AopContext ac) {
                BladePage<Map<String, Object>> page = (BladePage<Map<String, Object>>) ac.getObject();
                List<Map<String, Object>> list = page.getRows();
                for (Map<String, Object> map : list) {
                    map.put("StatusName", SysCache.getDictName(905, map.get("Status")));
                }
            }
        });
        return grid;
    }


    @Json
	@RequestMapping(KEY_UPDATE)
	public ApiResult update() {
		Charge charge = mapping(Charge.class);
        charge.setBankName(SysCache.getBankName(charge.getBankNo()));
		boolean temp = service.updateCharge(charge);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

    @RequestMapping("charge")
    public String charge(String OrderNo, ModelMap mm) {
        //List<ChargeList> list = Blade.create(ChargeList.class).findBy("OrderNo = #{OrderNo}", CMap.init().set("OrderNo", OrderNo));
        List<Map> list = Db.selectList("SELECT cl.*,bd.name as StatusName FROM tb_charge_list cl LEFT JOIN (SELECT * FROM blade_dict WHERE `code` = 905) bd ON cl.Status = bd.num WHERE cl.OrderNo = #{OrderNo}", CMap.init().set("OrderNo", OrderNo));
        Charge charge = Blade.create(Charge.class).findFirstBy("OrderNo = #{OrderNo}", CMap.init().set("OrderNo", OrderNo));
        mm.put("charge", charge);
        mm.put("statusName", SysCache.getDictName(905, charge.getStatus()));
        mm.put("list", list);
        return BASE_PATH + "charge_view.html";
    }
}