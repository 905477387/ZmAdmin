package com.smallchill.modules.app.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.modules.app.model.Shop;

import java.util.Map;

/**
 * Generated by Blade.
 * 2017-11-09 18:24:22
 */
public interface ShopService extends IService<Shop> {
	
	boolean updateStatus(String ids, Integer status);
	
	Map<String, Object> findOne(Object id);

}
