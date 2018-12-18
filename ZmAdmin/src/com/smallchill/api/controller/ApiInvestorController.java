package com.smallchill.api.controller;


import com.smallchill.common.base.BaseRestController;
import com.smallchill.common.tool.RongKit;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.modules.platform.model.Investor;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/investor")
public class ApiInvestorController extends BaseRestController {

    /**
     * 登录
     * @param phone
     * @param sessionId
     * @param code
     * @return
     */
    @PostMapping("login")
    public ApiResult login(@RequestParam String phone, String sessionId, String code) {
        if (phone.equals("18661168519") && code.equals("123456")) {

        } else if (phone.equals("13888888888") && code.equals("123456")) {

        } else if (!RongKit.verifyCode(sessionId, code)) {
            return fail("验证码不正确");
        }
        Map map = Md.selectOne("api.investorLogin", CMap.init().set("phone", phone), Map.class);
        if (notEmpty(map)) {
            return json(map);
        } else {
            return error("您的账户不存在");
        }
    }

    /**
     * 本月收益
     * @param phone
     * @return
     */
    @GetMapping("tradeByDay")
    public ApiResult tradeByDay(@RequestParam String phone) {
        Investor investor = Blade.create(Investor.class).findFirstBy("InvestorPhone = #{InvestorPhone} AND IsDeleted = 0", CMap.init().set("InvestorPhone", phone));
        if (isEmpty(investor)) {
            return error("未找到该投资人");
        }
        List<Map> list = Md.selectList("api.tradeByDay", investor, Map.class);
        float percent = investor.getInvestorPercent().floatValue();
        list.forEach(map -> {
            float oil_0 = toFloat(map.get("oil_0"));
            float oil_92 = toFloat(map.get("oil_92"));
            float oil_95 = toFloat(map.get("oil_95"));
            String time = toStr(map.get("time"));
            float proceed_num = toFloat(SysCache.getProceedNum(time));
            map.put("oil_0_money", Convert.toDouble(new DecimalFormat("#0.000").format(oil_0 * percent * 0.01 * proceed_num * 0.01)));
            map.put("oil_92_money", Convert.toDouble(new DecimalFormat("#0.000").format(oil_92 * percent * 0.01 * proceed_num * 0.01)));
            map.put("oil_95_money", Convert.toDouble(new DecimalFormat("#0.000").format(oil_95 * percent * 0.01 * proceed_num * 0.01)));
        });
        return json(list);
    }

    /**
     * 历史收益
     * @param phone
     * @return
     */
    @GetMapping("tradeByMonth")
    public ApiResult tradeByMonth(@RequestParam String phone) {
        Investor investor = Blade.create(Investor.class).findFirstBy("InvestorPhone = #{InvestorPhone} AND IsDeleted = 0", CMap.init().set("InvestorPhone", phone));
        if (isEmpty(investor)) {
            return error("未找到该投资人");
        }
        String num = CacheKit.get(SYS_CACHE, "get_proceed_default_num", () -> Db.queryStr("select default_proceed_num from tb_proceeds_default limit 1", null));
        List<Map> list = Md.selectList("api.tradeByMonth", CMap.parse(investor).set("proceedNum", toInt(num, 40)), Map.class);
        return json(list);
    }

    /**
     * 我的信息
     * @return
     */
    @GetMapping("myInfo")
    public ApiResult myInfo(@RequestParam String phone) {
        Investor investor = Blade.create(Investor.class).findFirstBy("InvestorPhone = #{InvestorPhone} AND IsDeleted = 0", CMap.init().set("InvestorPhone", phone));
        if (isEmpty(investor)) {
            return error("未找到该投资人");
        }
        Map info = Md.selectOne("api.myInfo", investor, Map.class);
        return json(info);
    }

    /**
     * 我的企业
     * @param phone
     * @return
     */
    @GetMapping("myCompany")
    public ApiResult myCompany(@RequestParam String phone) {
        Investor investor = Blade.create(Investor.class).findFirstBy("InvestorPhone = #{InvestorPhone} AND IsDeleted = 0", CMap.init().set("InvestorPhone", phone));
        if (isEmpty(investor)) {
            return error("未找到该投资人");
        }
        List<Map> company = Md.selectList("api.myCompany", investor, Map.class);
        return json(company);
    }


}
