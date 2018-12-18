package com.smallchill.modules.platform.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.modules.platform.model.Custom;
import com.smallchill.modules.platform.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Generated by Blade.
 * 2017-09-09 15:42:39
 */
@Controller
@RequestMapping("/custom")
public class CustomController extends BaseController {
	private static String CODE = "custom";
	private static String PREFIX = "tb_custom";
	private static String LIST_SOURCE = "custom.list";
	private static String BASE_PATH = "/modules/platform/custom/";
	
	@Autowired
    CustomService service;
	
	@RequestMapping(KEY_MAIN)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "custom.html";
	}

	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "custom_add.html";
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	public String edit(@PathVariable String id, ModelMap mm) {
		Custom custom = service.findById(id);
		mm.put("model", JsonKit.toJson(custom));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "custom_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable String id, ModelMap mm) {
		Custom custom = service.findById(id);
		mm.put("model", JsonKit.toJson(custom));
		mm.put("id", id);
		mm.put("code", CODE);
		return BASE_PATH + "custom_view.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
        Object grid = paginate(LIST_SOURCE, new PageIntercept(){
            @Override
            public void queryBefore(AopContext ac) {
                if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)) {
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
		Custom custom = mapping(PREFIX, Custom.class);
		boolean temp = service.save(custom);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_UPDATE)
	public ApiResult update() {
		Custom custom = mapping(PREFIX, Custom.class);
		boolean temp = service.update(custom);
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