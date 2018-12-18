package com.smallchill.modules.app.service.impl;

import com.smallchill.api.model.Order;
import com.smallchill.api.model.OrderStatus;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.modules.app.model.RechargeCard;
import com.smallchill.modules.app.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhuangqian on 2017/4/25.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public void closeAndAddStock(int orderId) {
        changeStatus(orderId, 4);
        addStock(orderId);
    }


    public boolean changeStatus(Integer orderId, Integer status) {
        //更新主订单
        Blade.create(Order.class).updateBy("status = #{status}", "id = #{orderId}", CMap.init().set("status", status).set("orderId", orderId));
        //更新订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrder_id(orderId);
        orderStatus.setChange_time(new Date());
        orderStatus.setOrder_status(status);
        Blade.create(OrderStatus.class).save(orderStatus);
        return true;
    }

    public boolean addStock(int orderId) {
        Order order = Blade.create(Order.class).findById(orderId);
        Integer card_id = order.getCard_id();
        Integer charge_number = order.getCharge_number();
        RechargeCard rechargeCard = Blade.create(RechargeCard.class).findById(card_id);
        rechargeCard.setCard_number(rechargeCard.getCard_number() + charge_number);
        Blade.create(RechargeCard.class).update(rechargeCard);
        return true;
    }


}
