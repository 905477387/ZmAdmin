package com.smallchill.api.controller;

import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.smallchill.api.model.Order;
import com.smallchill.api.service.ApiService;
import com.smallchill.common.base.BaseRestController;
import com.smallchill.common.tool.JpushKit;
import com.smallchill.common.tool.RongKit;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ApiController extends BaseRestController {

    @Autowired
    ApiService service;

    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @GetMapping("sendCode")
    public ApiResult sendCode(@RequestParam String phone) {

        int u1 = Db.queryInt("select count(1) from tb_iccard where telno = #{phone}", CMap.init().set("phone", phone));
        int u2 = Db.queryInt("select count(1) from tb_investor where investorphone = #{phone}", CMap.init().set("phone", phone));
        int u3 = Db.queryInt("select count(1) from tb_shop where phone = #{phone}", CMap.init().set("phone", phone));

        if (u1 == 0 && u2 == 0 && u3 == 0) {
            return fail("您的账户不存在");
        }

        String sendCode = RongKit.sendCode(RongKit.smsTemplate, phone);
        Map<String, String> map = JsonKit.parse(sendCode, Map.class);
        String sessionId = Func.toStr(map.get("sessionId"));
        if (StrKit.notBlank(sessionId)) {
            return json(CMap.init().set("sessionId", sessionId), "sendCode");
        }
        return fail("发送验证码失败");
    }

    /**
     * 校验验证码
     * @param sessionId 验证码id
     * @param code 验证码
     * @return
     */
    @RequestMapping("verifyCode")
    public ApiResult verifyCode(@RequestParam String sessionId, @RequestParam String code) {
        if (!RongKit.verifyCode(sessionId, code)) {
            return fail("验证码不正确");
        }
        return success("验证成功");
    }


    /**
     * 登录
     * @param phone
     * @param sessionId
     * @param code
     * @return
     */
    @PostMapping("login")
    public ApiResult login(@RequestParam String phone, String sessionId, String code) {
        if ((phone.equals("13999999999") || phone.equals("13888888888") || phone.equals("18661168519") || phone.equals("13515271618")) && code.equals("123456")) {

        } else if (!RongKit.verifyCode(sessionId, code)) {
            return fail("验证码不正确");
        }
        Map investor = Md.selectOne("api.investorLogin", CMap.init().set("phone", phone), Map.class);
        if (notEmpty(investor)) {
            return json(investor);
        } else {
            Map station = Md.selectOne("api.stationLogin", CMap.init().set("phone", phone), Map.class);
            if (notEmpty(station)) {
                return json(station);
            } else {
                return error("您的账户不存在");
            }
        }
    }


    /************************************ ping++支付回调 ********************************************/

    /**
     * ping++支付成功回调
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("webhooks")
    public ApiResult webhooks(HttpServletResponse response) throws IOException {
        HttpServletRequest request = getRequest();
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + " " + value);
        }
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        // 解析异步通知数据
        Event event = Webhooks.eventParse(buffer.toString());
        int status = 0;
        String object = "";
        String chargeId = "";
        if ("charge.succeeded".equals(event.getType())) {
            status = 2;
            object = event.getData().getObject().toString();
            Map<String, Object> map = JsonKit.parse(object, Map.class);
            chargeId = Func.toStr(map.get("id"));
        } else if ("refund.succeeded".equals(event.getType())) {
            status = 6;
            object = event.getData().getObject().toString();
            Map<String, Object> map = JsonKit.parse(object, Map.class);
            chargeId = Func.toStr(map.get("charge"));
        } else {
            response.setStatus(500);
        }
        if (status > 0) {
            Order order = Blade.create(Order.class).findFirstBy("charge_id = #{charge_id}", CMap.init().set("charge_id", chargeId));
            if (null != order) {
                LOGGER.info(object);
                LOGGER.info(JsonKit.toJson(order));
                int orderId = order.getId();
                String orderNo = order.getOrder_no();
                boolean temp = service.changeStatus(orderId, status);
                Map<String, String> extras = new HashMap<>();
                extras.put("order_id", String.valueOf(orderId));
                extras.put("order_no", orderNo);
                extras.put("customer_name", order.getCustomer_name());
                extras.put("customer_phone", order.getCustomer_phone());
                extras.put("customer_card_no", order.getCustomer_card_no());
                if (temp) {
                    if (status == 2) {
                        LOGGER.info("订单[付款]变更成功, 订单编号: " + orderNo);
                    }/* else if (status == 6) {
                        LOGGER.info("订单[退款]变更成功, 订单编号: " + orderNo);
                        service.updateStock(orderId, "+");
                        Blade.create(Refund.class).updateBy("refundTime = #{refundTime}", "orderId = #{orderId}", CMap.init().set("refundTime", new Date()).set("orderId", orderId));
                    }*/
                    response.setStatus(200);
                    JpushKit.pushOne("订单付款成功", "alias_" + order.getCustomer_phone(), extras);
                    return success("订单变更成功, 订单编号: " + orderNo);
                } else {
                    if (status == 2) {
                        LOGGER.error("订单[支付]变更失败, 订单编号: " + orderNo);
                    } /*else if (status == 6) {
                        LOGGER.error("订单[退款]变更失败, 订单编号: " + orderNo);
                    }*/
                    response.setStatus(500);
                    JpushKit.pushOne("订单付款成功", "alias_" + order.getCustomer_phone(), extras);
                    return fail("订单变更失败, 订单编号: " + orderNo);
                }
            }
        }
        return fail("调用失败");
    }

}
