package com.smallchill.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.modules.platform.service.NoticeService;

@RestController
@RequestMapping("/api/notice")
public class ApiNoticeController extends BaseController{

	@Autowired
	NoticeService service;
	
	@PostMapping("/list")
	public ApiResult noticeList(@RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "1", required = false) Integer type) {
		CMap param = CMap.init();
		param.set("type", type);
		BladePage<Map> list = Md.paginate("api.notice", Map.class, param, page, pageSize);
		return json(list);
	}
}
