package com.smallchill.modules.platform.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.modules.platform.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuangqian on 2017/9/9.
 */
@Controller
@RequestMapping("sync")
public class SyncController extends BaseController{

    @Autowired
    SyncService service;

    /**
     * 同步接口数据
     * @return
     */
    @Json
    @RequestMapping
    public ApiResult sync() {
        service.saveSync();
        return success("同步成功");
    }

    /**
     * 同步全部接口数据
     * @return
     */
    @Json
    @RequestMapping("all")
    public ApiResult syncAll() {
        service.saveSyncAll();
        return success("同步成功");
    }

    /**
     * 同步卡号
     * @return
     */
    @Json
    @RequestMapping("card")
    public ApiResult syncCard() {
        service.saveCard();
        return success("同步成功");
    }

    /**
     * 同步卡号
     * @return
     */
    @Json
    @RequestMapping("oil")
    public ApiResult syncOil() {
        service.saveOil();
        return success("同步成功");
    }

}
