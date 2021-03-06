package com.smallchill.modules.bill.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.modules.bill.model.Bill;
import com.smallchill.modules.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Generated by Blade.
 * 2017-10-18 16:49:02
 */
@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {
	private static String CODE = "bill";
	private static String PREFIX = "tb_bill";
	private static String LIST_SOURCE = "bill.list";
	private static String BASE_PATH = "/modules/bill/bill/";
	
	@Autowired
    BillService service;
	
	@RequestMapping(KEY_MAIN)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "bill.html";
	}

	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "bill_add.html";
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Bill bill = service.findById(id);
		mm.put("model", JsonKit.toJson(bill));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "bill_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable Integer id, ModelMap mm) {
		Bill bill = service.findById(id);
		mm.put("model", JsonKit.toJson(bill));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "bill_view.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object grid = paginate(LIST_SOURCE, new PageIntercept(){
            @Override
            public void queryBefore(AopContext ac) {

                if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR) && ShiroKit.lacksRole(ConstShiro.FINANCE)) {
                    ac.getParam().put("CustNo", ShiroKit.getUser().getDeptId());
                    ac.setCondition("and CustNo = #{CustNo}");
                }

            }
        });
		return grid;
	}

	@Json
	@RequestMapping(KEY_SAVE)
	public ApiResult save() {
		Bill bill = mapping(PREFIX, Bill.class);
		bill.setCustName(SysCache.getCustName(bill.getCustNo()));
		boolean temp = service.save(bill);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_UPDATE)
	public ApiResult update() {
		Bill bill = mapping(PREFIX, Bill.class);
		if (notEmpty(bill.getCustNo())) {
            bill.setCustName(SysCache.getCustName(bill.getCustNo()));
        }
		boolean temp = service.update(bill);
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
