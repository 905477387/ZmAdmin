package com.smallchill.common.tool;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.smallchill.api.model.Order;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PingppKit {

	private static final Logger log = LoggerFactory.getLogger(PingppKit.class);
	
	private static String appId = "app_CGSyX5XjjPO4XT8O";
	//private static String appKey = "sk_test_PCCibHefTWf9LWDKq5Gur1aH";
	private static String appKey = "sk_live_0Su9S8fbPmHSGeLmj9zf1Oe5";

	private static String privateKey = "MIICXQIBAAKBgQDhWlqBNZ/tb4Ku/F4n/yWPAjbPqBqjCJc27mBEq6v5aAq2pjWE9IbVtFGuqOUAM5dT4E9x87ejwDzLfLoAyoQQLojIl+4658xJOFVEEmMw+jc7zLzmJsemCtczmLvzl1Y6RN4ttesyQkMLCa54YdyIwOHaisLx7JzC7AWYNUKpLQIDAQABAoGACfmq6e9KvgLioQZQ0Kptqi/p5zVEwJS/xNG2dzO2MBsX5k/jLJh6Y3lQ6E9Xm7Os7iyOt7eXGGuJBWTnAuP0BoIUqgccl13xR46d2JFzbxHYKXoEatKHSWsCNX0g5ogWE9hsbpAF2iUaXWhhBK6yeyvJHbrRGiHAfp76HFf/IM0CQQDzhP6aXwcJlhFW/SQ/uhXXz/PTAZOeBLlcVNq+OPag2VOCxDh4z0NesYPIl5MfpjWE2PLmiV8UOi0m/NbXYdLjAkEA7OcC7bV1ebs/kRf0HX4Vo3xuhd/1WhHorz+AW5SUN3iVWgkas/Bp0ZwSncw8B20jHgGI9cdM4SeEFDbKiOaArwJBANgnKMdlqh18Mw//ynk2d8UENO0K7TjKAK5QM4m9jkzgGxxrh6TUdHxNHSikQldJdR/iYlj3fom5yfJdY2Xy0vkCQQCJ5Z5qx+ltxnUl/A9461+lHlUnUEn899e9yvToWgA8mdA17H7CpvqxrGuZtrxqhjieyr3ycuRpWxsiuRIDxki5AkAOE5ZA9Bmc7rUyDmBU9zODTLMmu74BeQ4z8SHuRQMGLj7B4rcpTyQxK4DSyYOzozCCGM5YKQlK5eCpMLRitWDn";
	
	static {
		Pingpp.apiKey = appKey;
		System.setProperty("https.protocols", "TLSv1.2");
		Pingpp.privateKey = privateKey;
	}
	
	/**   
	 * ping++获取charge对象
	 * @param orderNo	订单编号
	 * @param amount	订单总金额
	 * @param channel	支付渠道
	 * @param clientIp	用户ip
	 * @return Charge
	*/
	public static Charge charge(String orderNo, float amount, String channel, String clientIp, String subject) {
		Charge charge = null;
		Order o = Blade.create(Order.class).findFirstBy("order_no = #{order_no}", CMap.init().set("order_no", orderNo));

		if (null == o) {
		    throw new RuntimeException("未找到对应订单");
        }

		if (StrKit.notBlank(o.getCharge_id())) {
			return chargeById(o.getCharge_id());
		} else {
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("order_no", orderNo);
			chargeParams.put("amount", amount * 100);// 订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填  100）
			Map<String, String> app = new HashMap<String, String>();
			app.put("id", appId);
			chargeParams.put("app", app);
			chargeParams.put("channel", channel);
			chargeParams.put("currency", "cny");
			chargeParams.put("client_ip", clientIp);
			chargeParams.put("subject", subject);
			chargeParams.put("body", subject);
			try {
				charge = Charge.create(chargeParams);
				if (StrKit.notBlank(orderNo)) {
				    String pay_type;
				    if (channel.equals("alipay")) {
                        pay_type = "支付宝";
                    } else if (channel.equals("wx")) {
                        pay_type = "微信";
                    } else {
				        pay_type = "其他";
                    }
					Blade.create(Order.class).updateBy("charge_id = #{charge_id}, pay_type = #{pay_type}", "order_no = #{order_no}", CMap.init().set("charge_id", charge.getId()).set("pay_type", pay_type).set("order_no", orderNo));
				}
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException | ChannelException e) {
				log.error(e.getMessage(), e);
			}
		}
		return charge;
	}
	
	/**   
	 * 根据chargeId获取charge对象
	 * @param chargeId
	 * @return Charge
	*/
	public static Charge chargeById(String chargeId) {
		Charge charge = null;
		try {
			charge = Charge.retrieve(chargeId);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException | ChannelException e) {
			e.printStackTrace();
		}
		return charge;
	}
	


}
