package com.smallchill.common.task;

import com.smallchill.api.model.Order;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.support.SpringAcaHolder;
import com.smallchill.modules.app.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhuangqian on 2018/1/24.
 */
public class CloseTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CloseTask.class);

    public TaskService service = null;

    public TaskService getService() {
        if (null == service) {
            synchronized (CloseTask.class) {
                if (null == service) {
                    service = SpringAcaHolder.getBean(TaskService.class);
                }
            }
        }
        return service;
    }

    @Override
    public void run() {
//        List<Order> list = Blade.create(Order.class).findBy("status = 1 and date_sub(create_time,interval -1 hour) < NOW()", null);
//        for (Order o : list){
//            getService().closeAndAddStock(o.getId());
//            log.info("超出一小时未支付,订单自动关闭,订单id:" + o.getId());
//        }
    }

}
