package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.Const;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.modules.platform.model.OilPrice;
import com.smallchill.modules.platform.model.Station;
import com.smallchill.system.model.Dept;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	@GetMapping
	public String index(ModelMap mm) {
        Station station = Blade.create(Station.class).findById(ShiroKit.getUser().getDeptId());
        if (null == station) {
            Dept dept = Blade.create(Dept.class).findById(ShiroKit.getUser().getDeptId());
            station = Blade.create(Station.class).findById(dept.getPid());
            if (null == station) {
                station = new Station();
            }
        }
        int car_cnt = Db.queryInt("SELECT count(1) FROM tb_iccard where CustNo in (select CustNo from tb_custom where StationNo = #{StationNo}) and CardStatus = 2", station);
        if (ShiroKit.hasRole(ConstShiro.COMPANY)) {
            car_cnt = Db.queryInt("SELECT count(1) FROM tb_iccard where CustNo = #{CustNo}", CMap.init().set("CustNo", ShiroKit.getUser().getDeptId()));
        }
        List<Map> news = Db.selectList("SELECT notice.id,notice.title,dict.name as typename,notice.publishtime FROM blade_notice notice left join (select num,name from blade_dict where code = 102) dict on notice.type = dict.num where publishtime < now() order by publishtime DESC limit 5");
        mm.put("station_name", station.getStationAbbreviate());
        mm.put("car_cnt", car_cnt);
        mm.put("news", news);
        OilPrice oilPrice = Blade.create(OilPrice.class).findFirstBy("StationNo = #{StationNo}", station);
        mm.put("oilPrice", oilPrice);
        return Const.INDEX_MAIN_REALPATH;
	}
	
}
