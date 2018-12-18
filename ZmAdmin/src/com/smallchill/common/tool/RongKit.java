package com.smallchill.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.kit.HashKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.LogKit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 融云接口封装类
 */
public class RongKit {
	//开发环境
	public static String appKey = "mgb7ka1nmwdeg";
	public static String appSecret = "5GVOJLPgyfLWQ";
	
	//生产环境
	//public static String appKey = "cpj2xarlcs4tn";
	//public static String appSecret = "ahb49aKsSusnvu";
	
	private static String sendUrl = "http://api.sms.ronghub.com/sendCode.json";
	private static String verifyUrl = "http://api.sms.ronghub.com/verifyCode.json";
	private static String nonce = "233";
	private static String region = "86";
    private static String sendNotify = "http://api.sms.ronghub.com/sendNotify.json";

    //验证码模板
    public static String smsTemplate = "4QNrPyl8k889B1NpY7VXwH";

    /**
     * 发送验证码
     * @param templateId	短信模板id
     * @param code		变量
     * @return String
     */
    public static String send(String templateId, String mobile, String code) {
        Map<String, Object> param = new HashMap<>();
        param.put("templateId", templateId);
        param.put("mobile", mobile);
        param.put("region", region);
        param.put("p1", code);
        String str = JsonKit.toJson(new ApiResult().addFail("请求出错"));
        try {
            str = HttpKit.post(sendNotify, signature(), param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

	/**
	 * 发送验证码
	 * @param templateId	短信模板id
	 * @param mobile		发送手机
	 * @return String
	*/
	public static String sendCode(String templateId, String mobile) {
		Map<String, Object> param = new HashMap<>();
		param.put("templateId", templateId);
		param.put("mobile", mobile);
		param.put("region", region);
		String str = JsonKit.toJson(new ApiResult().addFail("请求出错"));
		try {
			str = HttpKit.post(sendUrl, signature(), param);
            LogKit.info(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

    /**
     * 校验验证码
     * @param sessionId		sessionId
     * @param code			短信验证码
     * @return String
     */
    public static boolean verifyCode(String sessionId, String code) {
        Map<String, Object> param = new HashMap<>();
        param.put("sessionId", sessionId);
        param.put("code", code);
        boolean temp = false;
        try {
            String str = HttpKit.post(verifyUrl, signature(), param);
            JSONObject json = JsonKit.parse(str);
            temp = json.getBoolean("success");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return temp;
    }



    private static Map<String, Object> signature() {
        Map<String, Object> headMap = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        String signature = HashKit.sha1(appSecret + nonce + timestamp);
        headMap.put("App-Key", appKey);
        headMap.put("Nonce", nonce);
        headMap.put("Timestamp", timestamp + "");
        headMap.put("Signature", signature);
        return headMap;
    }

}
