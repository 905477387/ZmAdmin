package com.smallchill.modules.platform.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by zhuangqian on 2017/9/21.
 */
@Table(name = "tb_oil_trade")
public class OilTrade extends BaseModel{

    //主键
    private Integer id;
    //流水号
    @JSONField(name = "FlowNo")
    private String FlowNo;
    //站点编号
    @JSONField(name = "StationNo")
    private String StationNo;
    //站点简称
    @JSONField(name = "StationAbbreviate")
    private String StationAbbreviate;
    //营业日期
    @JSONField(name = "SaleDate")
    private String SaleDate;
    //班次
    @JSONField(name = "ShiftNo")
    private String ShiftNo;
    //交易类型(0:加油,1:逃卡,3:补扣,4:补充,7:非卡,20:强制解灰)
    @JSONField(name = "TradeType")
    private String TradeType;
    //交易类型名称
    @JSONField(name = "TradeTypeName")
    private String TradeTypeName;
    //加油时间
    @JSONField(name = "OilTime")
    private String OilTime;
    //加油员编号
    @JSONField(name = "OperatorNo")
    private String OperatorNo;
    //加油员名称
    @JSONField(name = "OperatorName")
    private String OperatorName;
    //卡号
    @JSONField(name = "CardNo")
    private String CardNo;
    //持卡人姓名
    @JSONField(name = "HolderName")
    private String HolderName;
    //卡类型(0:非卡,1:司机卡,2:管理卡,4:员工卡,5:验泵卡,6:维修卡)
    @JSONField(name = "CardType")
    private String CardType;
    //卡类型名称
    @JSONField(name = "CardTypeName")
    private String CardTypeName;
    //油品代码
    @JSONField(name = "OilNo")
    private String OilNo;
    //油品简称
    @JSONField(name = "OilAbbreviate")
    private String OilAbbreviate;
    //单价
    @JSONField(name = "Price")
    private String Price;
    //枪号
    @JSONField(name = "GunNo")
    private String GunNo;
    //加油金额
    @JSONField(name = "TradeMoney")
    private String TradeMoney;
    //加油量(升)
    @JSONField(name = "Qty")
    private String Qty;
    //卡余额
    @JSONField(name = "Balance")
    private String Balance;
    //结算价
    @JSONField(name = "PayPrice")
    private String PayPrice;
    //结算金额
    @JSONField(name = "Settlement")
    private String Settlement;
    //结算单位(0:元,1:升)
    @JSONField(name = "SettlementUnite")
    private String SettlementUnite;
    //结算单位名称
    @JSONField(name = "SettlementUniteName")
    private String SettlementUniteName;
    //支付方式
    @JSONField(name = "PayWay")
    private String PayWay;
    //支付方式名称(0:现金,1:油票,2:记账,3:银行卡,4:其他,5:支票,6:IC卡,7:回罐,8:投币,12:银行转账)
    @JSONField(name = "PayWayName")
    private String PayWayName;
    //结算时间
    @JSONField(name = "PayTime")
    private String PayTime;
    //油站上传时间
    @JSONField(name = "UploadTime")
    private String UploadTime;
    //客户编号
    @JSONField(name = "CustNo")
    private String CustNo;
    //客户名称
    @JSONField(name = "CustName")
    private String CustName;
    //客户结算类型(0;预收,1:应收)
    @JSONField(name = "SettlementType")
    private String SettlementType;
    //客户证件类型(0:身份证,1:军官证,2:护照,3:入境证,4:临时身份证,5:驾驶证,6:组织机构代码,7:其它)
    @JSONField(name = "CustHolderIDType")
    private String CustHolderIDType;
    //客户证件类型名称
    @JSONField(name = "CustHolderIDTypeName")
    private String CustHolderIDTypeName;
    //客户证件号
    @JSONField(name = "CustHolderID")
    private String CustHolderID;
    //第三方系统客户编号
    @JSONField(name = "ThirdpartyCustNo")
    private String ThirdpartyCustNo;
    //会员卡号(用于积分加油时)
    @JSONField(name = "PointsCardNo")
    private String PointsCardNo;
    //积分
    @JSONField(name = "Points")
    private String Points;
    //积分余额
    @JSONField(name = "PointsBalance")
    private String PointsBalance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowNo() {
        return FlowNo;
    }

    public void setFlowNo(String flowNo) {
        FlowNo = flowNo;
    }

    public String getStationNo() {
        return StationNo;
    }

    public void setStationNo(String stationNo) {
        StationNo = stationNo;
    }

    public String getStationAbbreviate() {
        return StationAbbreviate;
    }

    public void setStationAbbreviate(String stationAbbreviate) {
        StationAbbreviate = stationAbbreviate;
    }

    public String getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(String saleDate) {
        SaleDate = saleDate;
    }

    public String getShiftNo() {
        return ShiftNo;
    }

    public void setShiftNo(String shiftNo) {
        ShiftNo = shiftNo;
    }

    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String tradeType) {
        TradeType = tradeType;
    }

    public String getTradeTypeName() {
        return TradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        TradeTypeName = tradeTypeName;
    }

    public String getOilTime() {
        return OilTime;
    }

    public void setOilTime(String oilTime) {
        OilTime = oilTime;
    }

    public String getOperatorNo() {
        return OperatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        OperatorNo = operatorNo;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getHolderName() {
        return HolderName;
    }

    public void setHolderName(String holderName) {
        HolderName = holderName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardTypeName() {
        return CardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        CardTypeName = cardTypeName;
    }

    public String getOilNo() {
        return OilNo;
    }

    public void setOilNo(String oilNo) {
        OilNo = oilNo;
    }

    public String getOilAbbreviate() {
        return OilAbbreviate;
    }

    public void setOilAbbreviate(String oilAbbreviate) {
        OilAbbreviate = oilAbbreviate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getGunNo() {
        return GunNo;
    }

    public void setGunNo(String gunNo) {
        GunNo = gunNo;
    }

    public String getTradeMoney() {
        return TradeMoney;
    }

    public void setTradeMoney(String tradeMoney) {
        TradeMoney = tradeMoney;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getPayPrice() {
        return PayPrice;
    }

    public void setPayPrice(String payPrice) {
        PayPrice = payPrice;
    }

    public String getSettlement() {
        return Settlement;
    }

    public void setSettlement(String settlement) {
        Settlement = settlement;
    }

    public String getSettlementUnite() {
        return SettlementUnite;
    }

    public void setSettlementUnite(String settlementUnite) {
        SettlementUnite = settlementUnite;
    }

    public String getSettlementUniteName() {
        return SettlementUniteName;
    }

    public void setSettlementUniteName(String settlementUniteName) {
        SettlementUniteName = settlementUniteName;
    }

    public String getPayWay() {
        return PayWay;
    }

    public void setPayWay(String payWay) {
        PayWay = payWay;
    }

    public String getPayWayName() {
        return PayWayName;
    }

    public void setPayWayName(String payWayName) {
        PayWayName = payWayName;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }

    public String getCustNo() {
        return CustNo;
    }

    public void setCustNo(String custNo) {
        CustNo = custNo;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getSettlementType() {
        return SettlementType;
    }

    public void setSettlementType(String settlementType) {
        SettlementType = settlementType;
    }

    public String getCustHolderIDType() {
        return CustHolderIDType;
    }

    public void setCustHolderIDType(String custHolderIDType) {
        CustHolderIDType = custHolderIDType;
    }

    public String getCustHolderIDTypeName() {
        return CustHolderIDTypeName;
    }

    public void setCustHolderIDTypeName(String custHolderIDTypeName) {
        CustHolderIDTypeName = custHolderIDTypeName;
    }

    public String getCustHolderID() {
        return CustHolderID;
    }

    public void setCustHolderID(String custHolderID) {
        CustHolderID = custHolderID;
    }

    public String getThirdpartyCustNo() {
        return ThirdpartyCustNo;
    }

    public void setThirdpartyCustNo(String thirdpartyCustNo) {
        ThirdpartyCustNo = thirdpartyCustNo;
    }

    public String getPointsCardNo() {
        return PointsCardNo;
    }

    public void setPointsCardNo(String pointsCardNo) {
        PointsCardNo = pointsCardNo;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getPointsBalance() {
        return PointsBalance;
    }

    public void setPointsBalance(String pointsBalance) {
        PointsBalance = pointsBalance;
    }
}
