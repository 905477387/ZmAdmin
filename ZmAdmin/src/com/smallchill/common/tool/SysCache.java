package com.smallchill.common.tool;

import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.modules.app.model.Proceeds;
import com.smallchill.modules.app.model.Shop;
import com.smallchill.modules.charge.model.Bank;
import com.smallchill.modules.platform.model.Custom;
import com.smallchill.modules.platform.model.ICCard;
import com.smallchill.system.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SysCache implements ConstCache, ConstCacheKey{
	
	/**
	 * 获取字典表对应中文
	 * @param code 字典编号
	 * @param num  字典序号
	 * @return
	 */
	public static String getDictName(final Object code, final Object num) {
		Dict dict = CacheKit.get(SYS_CACHE, GET_DICT_NAME + code + "_" + num, new ILoader() {
			@Override
			public Object load() {
				return Blade.create(Dict.class).findFirstBy("code = #{code} and num = #{num}", CMap.init().set("code", Convert.toStr(code)).set("num", num));
			}
		});
		if(null == dict){
			return "";
		}
		return dict.getName();
	}
	
	/**
	 * 获取字典表
	 * @param code 字典编号
	 * @return
	 */
	public static List<Dict> getDict(final Object code) {
		List<Dict> list = CacheKit.get(SYS_CACHE, GET_DICT + code, new ILoader() {
			@Override
			public Object load() {
				return Blade.create(Dict.class).findBy("code = #{code} and num > 0", CMap.init().set("code", Convert.toStr(code)));
			}
		});
		return list;
	}
	
	/**
	 * 获取字典表
	 * @param code 字典编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> getSimpleDict(final Object code) {
		List<Map> list = CacheKit.get(SYS_CACHE, GET_DICT + "simple_" + code, new ILoader() {
			@Override
			public Object load() {
				return Db.selectList("select num, name, tips from blade_dict where code = #{code} and num > 0", CMap.init().set("code", Convert.toStr(code))); 
			}
		});
		return list;
	}

	/**
	 * 获取对应角色名
	 * @param roleIds 角色id
	 * @return
	 */
	public static String getRoleName(final Object roleIds) {
		if(Func.isEmpty(roleIds)){
			return "";
		}
		final Integer[] roleIdArr = Convert.toIntArray(roleIds.toString());
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < roleIdArr.length; i++){
			final Integer roleId = roleIdArr[i];
			Role role = CacheKit.get(SYS_CACHE, GET_ROLE_NAME + roleId, new ILoader() {
				@Override
				public Object load() {
					return Blade.create(Role.class).findById(roleId);
				}
			});
			if (null != role)
				sb.append(role.getName()).append(",");
		}
		return StrKit.removeSuffix(sb.toString(), ",");
	}

	/**
	 * 获取对应角色别名
	 * @param roleIds 角色id
	 * @return
	 */
	public static String getRoleAlias(final Object roleIds) {
		if(Func.isEmpty(roleIds)){
			return "";
		}
		final Integer[] roleIdArr = Convert.toIntArray(roleIds.toString());
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < roleIdArr.length; i++){
			final Integer roleId = roleIdArr[i];
			Role role = CacheKit.get(SYS_CACHE, GET_ROLE_ALIAS + roleId, new ILoader() {
				@Override
				public Object load() {
					return Blade.create(Role.class).findById(roleId);
				}
			});
			if (null != role)
				sb.append(role.getTips()).append(",");
		}
		return StrKit.removeSuffix(sb.toString(), ",");
	}

    /**
     * 获取对应用户
     * @param userId 用户id
     * @return
     */
    public static User getUser(final Object userId) {
        User user = CacheKit.get(SYS_CACHE, GET_USER_NAME + userId, new ILoader() {
            @Override
            public Object load() {
                return Blade.create(User.class).findById(Convert.toInt(userId));
            }
        });
        return user;
    }

	/**
	 * 获取对应用户名
	 * @param userId 用户id
	 * @return
	 */
	public static String getUserName(final Object userId) {
		User user = CacheKit.get(SYS_CACHE, GET_USER_NAME + userId, new ILoader() {
			@Override
			public Object load() {
				return Blade.create(User.class).findById(Convert.toInt(userId));
			}
		});
		if(null == user){
			return "";
		}
		return user.getName();
	}

	/**
	 * 获取对应部门名
	 * @param deptIds 部门id集合
	 * @return
	 */
	public static String getDeptName(final Object deptIds) {
		if(Func.isEmpty(deptIds)){
			return "";
		}
		final Integer[] deptIdArr = Convert.toIntArray(deptIds.toString());
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < deptIdArr.length; i++){
			final Integer deptId = deptIdArr[i];
			Dept dept = CacheKit.get(SYS_CACHE, GET_DEPT_NAME + deptId, () -> Blade.create(Dept.class).findById(deptId));
			if (null != dept)
				sb.append(dept.getSimplename()).append(",");
		}
		return StrKit.removeSuffix(sb.toString(), ",");
	}

	public static Dept getDept(final Object deptId) {
        Dept dept = CacheKit.get(SYS_CACHE, GET_DEPT + deptId, () -> Blade.create(Dept.class).findById(deptId));
        return dept;
    }

    public static String getBankName(final String BankNo) {
        Bank bank = CacheKit.get(SYS_CACHE, "get_bank_name" + BankNo, () -> Blade.create(Bank.class).findFirstBy("BankNo = #{BankNo}", CMap.init().set("BankNo", BankNo)));
        return Optional.ofNullable(bank).map(b -> b.getBankName()).orElse("未找到相关数据");
    }

    public static String getCustName(final Object CustNo) {
        Custom custom = CacheKit.get(SYS_CACHE, "get_cust_name" + CustNo, () -> Blade.create(Custom.class).findFirstBy("CustNo = #{CustNo}", CMap.init().set("CustNo", CustNo)));
        return Optional.ofNullable(custom).map(b -> b.getCustomerName()).orElse("未找到相关数据");
    }

    public static ICCard getCard(final Object CardNo) {
        ICCard icCard = CacheKit.get(SYS_CACHE, "get_card" + CardNo, () -> Blade.create(ICCard.class).findFirstBy("CardNo = #{CardNo}", CMap.init().set("CardNo", CardNo)));
        return icCard;
    }

    public static Shop getShop(final Object ShopId) {
        Shop shop = CacheKit.get(SYS_CACHE, "get_shop" + ShopId, () -> Blade.create(Shop.class).findById(ShopId));
        return shop;
    }

    public static String getCardsByCarNo(final Object CarNo) {
        List<ICCard> icCard = CacheKit.get(SYS_CACHE, "get_card_by_carno" + CarNo, () -> Blade.create(ICCard.class).findBy("CarNo like concat('%',#{CarNo},'%')", CMap.init().set("CarNo", CarNo)));
        StringBuilder sb = new StringBuilder();
        for (ICCard card : icCard) {
            sb.append(card.getCardNo()).append(",");
        }
        return sb.toString();
    }

    public static String getProceedNum(String proceed_time) {
        Proceeds proceeds =  CacheKit.get(SYS_CACHE, "get_proceed_num", () ->
            Blade.create(Proceeds.class).findFirstBy("proceed_time = #{proceed_time}", CMap.init().set("proceed_time", proceed_time))
        );
        if (Func.isEmpty(proceeds)) {
            String num = CacheKit.get(SYS_CACHE, "get_proceed_default_num", () -> Db.queryStr("select default_proceed_num from tb_proceeds_default limit 1", null));
            return Func.toStr(num, "40");
        } else {
            return proceeds.getProceed_num();
        }
    }

	/**   
	 * 获取参数表参数值
	 * @param code 参数编号
	 * @return String
	*/
	public static String getParamByCode(Object code){
		Parameter param = Blade.create(Parameter.class).findFirstBy("code = #{code} and status = 1", CMap.init().set("code", Convert.toInt(code)));
		return param.getPara();
	}
}
