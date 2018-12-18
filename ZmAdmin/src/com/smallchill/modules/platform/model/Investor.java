package com.smallchill.modules.platform.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Generated by Blade.
 * 2017-09-26 18:12:48
 */
@Table(name = "tb_investor")
@SuppressWarnings("serial")
public class Investor extends BaseModel {

	private Integer id;
    @JSONField(name = "InvestorName")
	private String InvestorName; //投资人姓名
    @JSONField(name = "InvestorMoney")
    private Integer InvestorMoney;//投资人投资金额
    @JSONField(name = "InvestorPercent")
	private BigDecimal InvestorPercent; //投资人投资比例
    @JSONField(name = "InvestorPhone")
	private String InvestorPhone; //投资人手机
    @JSONField(name = "StationAbbreviate")
	private String StationAbbreviate; //加油站名称
    @JSONField(name = "StationNo")
	private String StationNo; //加油站编号
    @JSONField(name = "CreateTime")
	private Date CreateTime; //创建时间
    @JSONField(name = "IsDeleted")
    private Integer IsDeleted; //是否已删除
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getInvestorName() {
		return InvestorName;
	}
	
	public void setInvestorName(String InvestorName) {
		this.InvestorName = InvestorName;
	}

    public Integer getInvestorMoney() {
        return InvestorMoney;
    }

    public void setInvestorMoney(Integer investorMoney) {
        InvestorMoney = investorMoney;
    }

    public BigDecimal getInvestorPercent() {
        return InvestorPercent;
    }

    public void setInvestorPercent(BigDecimal investorPercent) {
        InvestorPercent = investorPercent;
    }

    public String getInvestorPhone() {
		return InvestorPhone;
	}
	
	public void setInvestorPhone(String InvestorPhone) {
		this.InvestorPhone = InvestorPhone;
	}
	
	public String getStationAbbreviate() {
		return StationAbbreviate;
	}
	
	public void setStationAbbreviate(String StationAbbreviate) {
		this.StationAbbreviate = StationAbbreviate;
	}
	
	public String getStationNo() {
		return StationNo;
	}
	
	public void setStationNo(String StationNo) {
		this.StationNo = StationNo;
	}
	
	public Date getCreateTime() {
		return CreateTime;
	}
	
	public void setCreateTime(Date CreateTime) {
		this.CreateTime = CreateTime;
	}

    public Integer getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        IsDeleted = isDeleted;
    }
}