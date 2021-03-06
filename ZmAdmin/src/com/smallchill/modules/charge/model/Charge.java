package com.smallchill.modules.charge.model;

import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Generated by Blade.
 * 2017-09-10 01:09:32
 */
@Table(name = "tb_charge")
@SuppressWarnings("serial")
public class Charge extends BaseModel {

	private Integer id;
	private String Total; //充值总金额
    private String StationNo; //站点代码
	private String CustName; //客户名称
	private String CustNo; //客户代码
	private String OrderNo; //支付流水号
	private String BankName; //开户行
    private String BankNo; //开户行账号
    private Integer Status;//状态
	private Date CreateTime;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTotal() {
		return Total;
	}
	
	public void setTotal(String Total) {
		this.Total = Total;
	}

    public String getStationNo() {
        return StationNo;
    }

    public void setStationNo(String stationNo) {
        StationNo = stationNo;
    }

    public String getCustName() {
		return CustName;
	}
	
	public void setCustName(String CustName) {
		this.CustName = CustName;
	}
	
	public String getCustNo() {
		return CustNo;
	}
	
	public void setCustNo(String CustNo) {
		this.CustNo = CustNo;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String OrderNo) {
		this.OrderNo = OrderNo;
	}

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankNo() {
        return BankNo;
    }

    public void setBankNo(String bankNo) {
        BankNo = bankNo;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Date getCreateTime() {
		return CreateTime;
	}
	
	public void setCreateTime(Date CreateTime) {
		this.CreateTime = CreateTime;
	}
	

}
