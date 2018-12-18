/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.core.toolbox.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.constant.Const;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.ArrayKit;
import com.smallchill.core.toolbox.kit.BeanKit;
import com.smallchill.core.toolbox.kit.StrKit;

/**
 * javabean 、 paras映射
 */
public class BeanInjector {

	public static final <T> T inject(Class<T> beanClass, HttpServletRequest request) {
		try {
			return BeanKit.mapToBeanIgnoreCase(getParameterMap(request), beanClass);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static final <T> T inject(Class<T> beanClass, String prefix, HttpServletRequest request) {
		try {
			Map<String, Object> map = injectPara(prefix, request);
			return BeanKit.mapToBeanIgnoreCase(map, beanClass);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static final CMap injectMaps(HttpServletRequest request) {
		return CMap.parse(getParameterMap(request));
	}

	public static final CMap injectMaps(String prefix, HttpServletRequest request) {
		Map<String, Object> map = injectPara(prefix, request);
		return CMap.parse(map);
	}

	private static final Map<String, Object> injectPara(String prefix, HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		String start = prefix.toLowerCase() + ".";
		String[] value = null;
		for (Entry<String, String[]> param : paramMap.entrySet()) {
			if (!param.getKey().toLowerCase().startsWith(start)) {
				continue;
			}
			value = param.getValue();
			Object o = null;
			if (ArrayKit.isNotEmpty(value)) {
				if (value.length > 1) {
					o = ArrayKit.join(value, ",");
				} else {
					o = value[0];					
				}
			}
			map.put(StrKit.removePrefixIgnoreCase(param.getKey(), start).toLowerCase(), o);
		}
		String versionL = request.getParameter(Const.OPTIMISTIC_LOCK.toLowerCase());
		String versionU = request.getParameter(Const.OPTIMISTIC_LOCK);
	    if (StrKit.notBlank(versionL)){
			map.put(Const.OPTIMISTIC_LOCK.toLowerCase(), Func.toInt(versionL) + 1);
		} else if(StrKit.notBlank(versionU)){
			map.put(Const.OPTIMISTIC_LOCK.toLowerCase(), Func.toInt(versionU) + 1);
		}
		return map;
	}
	
	private static final Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		String[] value = null;
		for (Entry<String, String[]> param : paramMap.entrySet()) {
			value = param.getValue();
			Object o = null;
			if (ArrayKit.isNotEmpty(value)) {
				if (value.length > 1) {
					o = ArrayKit.join(value, ",");
				} else {
					o = value[0];				
				}
			}
			map.put(param.getKey(), o);
		}
		return map;
	}
	
}
