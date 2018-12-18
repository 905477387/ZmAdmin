package com.smallchill.modules.app.controller;

import com.smallchill.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("coordinate")
public class CoordinateController extends BaseController {

    @RequestMapping(KEY_MAIN)
    public String index() {
        return redirect("http://lbs.amap.com/console/show/picker");
    }

}
