package com.smallchill.modules.bill.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.modules.bill.model.Bill;

import java.util.Map;

/**
 * Generated by Blade.
 * 2017-10-18 16:49:02
 */
public interface BillService extends IService<Bill> {
	
	boolean updateStatus(String ids, Integer status);
	
	Map<String, Object> findOne(Object id);

}
