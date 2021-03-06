package com.smallchill.modules.platform.service.impl;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.modules.charge.model.Charge;
import com.smallchill.modules.charge.model.ChargeList;
import com.smallchill.modules.platform.model.ICCard;
import com.smallchill.modules.platform.service.ICCardService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generated by Blade.
 * 2017-09-09 15:42:39
 */
@Service
public class ICCardServiceImpl extends BaseService<ICCard> implements ICCardService {

	@Override
	public boolean updateStatus(String ids, Integer status) {
		StringBuilder sb = new StringBuilder(" status = #{status} ");
		CMap map = CMap.init().set("status", status).set("ids", Convert.toIntArray(ids));
		boolean temp = updateBy(sb.toString(), "id in (#{join(ids)})", map);
		return temp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> findOne(Object id) {
		return Md.selectOne("iCCard.findOne", CMap.init().set("id", id), Map.class);
	}

    @Override
    public boolean saveCharge(Charge charge, List<ChargeList> chargeList) {
	    double total = 0.0;
        String orderNo = charge.getOrderNo();
        for (ChargeList cl : chargeList) {
            cl.setOrderNo(orderNo);
            cl.setCreateTime(new Date());
            cl.setStatus(1);
            cl.setStationNo(SysCache.getDept(charge.getCustNo()).getPid());
            cl.setCardSerialNo(SysCache.getCard(cl.getCardNo()).getCardSerialNo());
            total += Convert.toDouble(cl.getChargeBalance(), 0.0);
        }
        charge.setTotal(total + "");
        charge.setStationNo(SysCache.getDept(charge.getCustNo()).getPid());
        Blade.create(Charge.class).save(charge);
        Blade.create(ChargeList.class).saveBatch(chargeList);
        return true;
    }

}
