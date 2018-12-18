package com.smallchill.modules.platform.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.modules.platform.model.Notice;
import com.smallchill.modules.platform.service.NoticeService;
import com.smallchill.system.model.User;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noticeDetails")
public class NoticeDetailsController extends BaseController {
	private static String BASE_PATH = "/modules/platform/notice/";
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping(DETAILS + "/{id}")
	public String details(@PathVariable Integer id,ModelMap mm) {
		Notice notice = noticeService.findById(id);
		mm.put("notice", notice);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mm.put("time", sdf.format(notice.getCreatetime()));
		User user = Blade.create(User.class).findById(notice.getCreater());
		mm.put("user", user);
		return BASE_PATH + "notice_details.html";
	}
}
