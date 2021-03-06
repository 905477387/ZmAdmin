package com.smallchill.modules.charge.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.modules.charge.model.Bank;
import com.smallchill.modules.charge.service.BankService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Generated by Blade.
 * 2017-09-13 20:35:54
 */
@Service
public class BankServiceImpl extends BaseService<Bank> implements BankService {

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
		return Md.selectOne("bank.findOne", CMap.init().set("id", id), Map.class);
	}

}
