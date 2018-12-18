package com.smallchill.modules.charge.model;

import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Created by zhuangqian on 2017/9/10.
 */
@Table(name = "tb_charge_list")
public class ChargeList {

    private Integer id;
    //当时余额
    private String Balance;
    //车牌号
    private String CarNo;
    //会员卡号
    private String CardNo;
    //印刷号
    private String CardSerialNo;
    //充值金额
    private String ChargeBalance;
    //客户名称
    private String CustName;
    //客户代码
    private String CustNo;
    //站点代码
    private String StationNo;
    //持有人
    private String HolderName;
    //流水号
    private String OrderNo;
    //提交时间
    private Date CreateTime;
    //状态
    private Integer Status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String CarNo) {
        this.CarNo = CarNo;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getChargeBalance() {
        return ChargeBalance;
    }

    public void setChargeBalance(String ChargeBalance) {
        this.ChargeBalance = ChargeBalance;
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

    public String getHolderName() {
        return HolderName;
    }

    public void setHolderName(String HolderName) {
        this.HolderName = HolderName;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getStationNo() {
        return StationNo;
    }

    public void setStationNo(String stationNo) {
        StationNo = stationNo;
    }

    public String getCardSerialNo() {
        return CardSerialNo;
    }

    public void setCardSerialNo(String cardSerialNo) {
        CardSerialNo = cardSerialNo;
    }
}
