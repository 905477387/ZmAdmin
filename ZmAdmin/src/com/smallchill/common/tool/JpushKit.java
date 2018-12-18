package com.smallchill.common.tool;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * Created by zhuangqian on 2017/4/25.
 */
public class JpushKit {

    private static Logger LOG = LogManager.getLogger(JpushKit.class);

    private static String APP_KEY = "78e75da9e4f82f107150bc67";
    private static String MASTER_SECRET = "9fc7f5fdf0987b502e92dfcc";

    public static void main(String[] args) {
        List<String> aliases = new ArrayList<>();
        aliases.add("alias1");

        Map<String, String> extras = new HashMap<>();
        extras.put("order_id", String.valueOf(1));
        extras.put("order_no", "ZM001");
        extras.put("customer_name", "庄");
        extras.put("customer_phone", "12345678910");
        extras.put("customer_card_no", "233");

        pushOne("付款成功", "alias_12345678910", extras);

    }


    public static void pushOne(String alert, String alias, Map<String, String> extras) {
        List<String> aliases = new ArrayList<>();
        aliases.add(alias);
        push(alert, aliases, extras);
    }

    public static void push(String alert, Collection<String> aliases, Map<String, String> extras) {

        final String f_alert = alert;
        final Collection<String> f_aliases = aliases;
        final Map<String, String> f_extras = extras;

        //Thread t = new Thread(() -> {
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(f_aliases))
                    .setNotification(Notification.newBuilder()
                            .setAlert(f_alert)
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setTitle("您有一条新消息")
                                    .addExtras(f_extras)
                                    .build())
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setContentAvailable(true)
                                    .incrBadge(1)
                                    .addExtras(f_extras)
                                    .build())
                            .build()).setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();


            send(payload);
        //});
        //t.start();

    }

    public static void pushAll(String alert, Map<String, String> extras) {

        final String f_alert = alert;
        final Map<String, String> f_extras = extras;

        //Thread t = new Thread(() -> {
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .setAlert(f_alert)
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setTitle("您有一条新消息")
                                    .addExtras(f_extras)
                                    .build())
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setContentAvailable(true)
                                    .incrBadge(1)
                                    .addExtras(f_extras)
                                    .build())
                            .build()).setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();

            send(payload);
        //});
        //t.start();
    }

    private static void send(PushPayload payload) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
        }
    }

}
