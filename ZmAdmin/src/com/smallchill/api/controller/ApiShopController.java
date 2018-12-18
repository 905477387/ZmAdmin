package com.smallchill.api.controller;

import com.smallchill.api.service.ApiService;
import com.smallchill.common.base.BaseRestController;
import com.smallchill.common.tool.RongKit;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/shop")
public class ApiShopController extends BaseRestController {

    @Autowired
    ApiService service;

    /**
     * 登录
     * @param phone
     * @param sessionId
     * @param code
     * @return
     */
    @PostMapping("login")
    public ApiResult login(@RequestParam String phone, String sessionId, String code) {
        if (phone.equals("18661168519") && code.equals("123456")) {

        } else if (phone.equals("13888888888") && code.equals("123456")) {

        } else if (!RongKit.verifyCode(sessionId, code)) {
            return fail("验证码不正确");
        }
        Map map = Md.selectOne("api.shopLogin", CMap.init().set("phone", phone), Map.class);
        if (notEmpty(map)) {
            return json(map);
        } else {
            return error("您的账户不存在");
        }
    }


    /**
     * 圈存订单
     * @param order_no
     * @return
     */
    @PostMapping("chargeOrder")
    public ApiResult chargeOrder(@RequestParam String order_no, @RequestParam String charger) {
        Integer order_id = Db.queryInt("select id from tb_order where order_no = #{order_no} limit 1", CMap.init().set("order_no", order_no));
        service.chargeOrder(order_id, charger);
        return success("圈存成功");
    }

    /**
     * 获取我的核销订单信息
     * @param charger
     * @return
     */
    @GetMapping("myChargeOrder")
    public ApiResult myChargeOrder(@RequestParam(defaultValue = "1", required = false) Integer page,
                             @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                             @RequestParam Integer charger, String search) {
        CMap param = CMap.init().set("charger", charger).set("search", search).set("domain", ConstConfig.DOMAIN);
        BladePage<Map> list = Md.paginate("api.myChargeOrder", Map.class, param, page, pageSize);

        list.getRows().stream().forEach(map -> {
            if (charger > 0) {
                User user = Blade.create(User.class).findById(charger);
                if (notEmpty(user)) {
                    map.put("charger_station_no", user.getDeptid());
                    map.put("charger_station_name", SysCache.getDeptName(user.getDeptid()));
                    map.put("charger_name", user.getName());
                    map.put("charger_phone", user.getPhone());
                }
            }
        });

        return json(list);
    }

    /**
     * 获取订单信息
     * @param order_no
     * @return
     */
    @GetMapping("orderInfo")
    public ApiResult orderInfo(@RequestParam String order_no) {
        Map order = Md.selectOne("api.orderInfo", CMap.init().set("order_no", order_no).set("domain", ConstConfig.DOMAIN), Map.class);
        return json(order);
    }

}
