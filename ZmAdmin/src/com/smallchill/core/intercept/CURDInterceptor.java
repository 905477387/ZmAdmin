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
package com.smallchill.core.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ICURD;
import com.smallchill.core.toolbox.ApiResult;

/**
 * 增删改查拦截器工厂类
 */
public class CURDInterceptor implements ICURD{

	@Override
	public void saveBefore(AopContext ac) {
		
	}

	@Override
	public boolean saveAfter(AopContext ac) {
		
		return true;
	}

	@Override
	public ApiResult saveSucceed(AopContext ac) {
		
		return null;
	}

	@Override
	public void updateBefore(AopContext ac) {
		
	}

	@Override
	public boolean updateAfter(AopContext ac) {
		
		return true;
	}

	@Override
	public ApiResult updateSucceed(AopContext ac) {
		
		return null;
	}

	@Override
	public void removeBefore(AopContext ac) {
		
	}

	@Override
	public boolean removeAfter(AopContext ac) {
		
		return true;
	}

	@Override
	public ApiResult removeSucceed(AopContext ac) {
		
		return null;
	}

	@Override
	public void delBefore(AopContext ac) {
		
	}

	@Override
	public boolean delAfter(AopContext ac) {
		
		return true;
	}

	@Override
	public ApiResult delSucceed(AopContext ac) {
		
		return null;
	}

	@Override
	public void restoreBefore(AopContext ac) {
		
	}

	@Override
	public boolean restoreAfter(AopContext ac) {

		return true;
	}

	@Override
	public ApiResult restoreSucceed(AopContext ac) {

		return null;
	}

}
