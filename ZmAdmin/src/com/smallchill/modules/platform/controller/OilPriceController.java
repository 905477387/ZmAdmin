package com.smallchill.modules.platform.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.modules.platform.model.OilPrice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("oilPrice")
public class OilPriceController extends BaseController {

    private static String CODE = "oilPrice";
    private static String BASE_PATH = "/modules/platform/oilPrice/";


    @RequestMapping("/")
    public String index(ModelMap mm) {
        OilPrice info = Blade.create(OilPrice.class).findFirstBy("StationNo = #{StationNo}", CMap.init().set("StationNo", ShiroKit.getUser().getDeptId()));
        mm.put("code", CODE);
        mm.put("info", info);
        return BASE_PATH + "oilPrice.html";
    }

    @ResponseBody
    @RequestMapping("update")
    public ApiResult update() {
        OilPrice oilPrice = mapping(OilPrice.class);
        Object id = oilPrice.getId();
        boolean temp;
        if (Func.isEmpty(id)) {
            temp = Blade.create(OilPrice.class).save(oilPrice);
        } else {
            temp = Blade.create(OilPrice.class).update(oilPrice);
        }
        if (temp) {
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

}
