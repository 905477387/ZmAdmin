package com.smallchill.modules.platform.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.base.model.BaseModel;
import com.smallchill.core.toolbox.kit.StrKit;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Generated by Blade.
 * 2017-09-09 15:42:39
 */
@Table(name = "tb_custom")
@SuppressWarnings("serial")
public class Custom extends BaseModel {
    @AssignID
    private String id;
    //账户状态(2:正常)
    @JSONField(name = "AccountStatus")
    private String AccountStatus;
    //账户状态名称
    @JSONField(name = "AccountStatusName")
    private String AccountStatusName;
    //地址
    @JSONField(name = "Address")
    private String Address;
    //是否积分(0:不积分、1:积分)
    @JSONField(name = "AllowPoints")
    private String AllowPoints;
    //银行账户
    @JSONField(name = "BankAccountNo")
    private String BankAccountNo;
    //开户行
    @JSONField(name = "BankName")
    private String BankName;
    //市
    @JSONField(name = "City")
    private String City;
    //县
    @JSONField(name = "County")
    private String County;
    //信用额度
    @JSONField(name = "Credit")
    private String Credit;
    //客户编号
    @JSONField(name = "CustNo")
    private String CustNo;
    //联系人证件号
    @JSONField(name = "CustomerID")
    private String CustomerID;
    //联系人证件类型(0:身份证、1:军官证、2:护照、3:入境证、4:临时身份证、5:驾驶证、6:组织机构代码、7:其他)
    @JSONField(name = "CustomerIDType")
    private String CustomerIDType;
    //证件类型名称
    @JSONField(name = "CustomerIDTypeName")
    private String CustomerIDTypeName;
    //客户等级
    @JSONField(name = "CustomerLevel")
    private String CustomerLevel;
    //客户名称
    @JSONField(name = "CustomerName")
    private String CustomerName;
    //客户类型(0:个人客户；1:集团客户)
    @JSONField(name = "CustomerType")
    private String CustomerType;
    //邮箱
    @JSONField(name = "Email")
    private String Email;
    //是否内部客户(0:是、1:不是)
    @JSONField(name = "InternalCus")
    private String InternalCus;
    //末次操作时间
    @JSONField(name = "LastOperateTime")
    private String LastOperateTime;
    //邮寄地址
    @JSONField(name = "MailAddress")
    private String MailAddress;
    //联系人
    @JSONField(name = "Master")
    private String Master;
    //备忘
    @JSONField(name = "Memo")
    private String Memo;
    //手机号
    @JSONField(name = "MobileNo")
    private String MobileNo;
    //开户时间
    @JSONField(name = "OpeningTime")
    private String OpeningTime;
    //邮编
    @JSONField(name = "PostNo")
    private String PostNo;
    //充值类型(0:备用金充值、1:IC卡充值)
    @JSONField(name = "PrepaidType")
    private String PrepaidType;
    //充值类型名称
    @JSONField(name = "PrepaidTypeName")
    private String PrepaidTypeName;
    //省
    @JSONField(name = "Province")
    private String Province;
    //客户结算类型(0:预收、1:应收)
    @JSONField(name = "SettlementType")
    private String SettlementType;
    //客户结算类型名称
    @JSONField(name = "SettlementTypeName")
    private String SettlementTypeName;
    //结算单位(0:元、1:升)
    @JSONField(name = "SettlementUnite")
    private String SettlementUnite;
    //结算单位名称
    @JSONField(name = "SettlementUniteName")
    private String SettlementUniteName;
    //站点简称
    @JSONField(name = "StationAbbreviate")
    private String StationAbbreviate;
    //开户站点
    @JSONField(name = "StationNo")
    private String StationNo;
    //税号
    @JSONField(name = "TaxCode")
    private String TaxCode;
    //电话
    @JSONField(name = "TelNo")
    private String TelNo;


    public String getId() {
        if (StrKit.isBlank(id))
            return CustNo;
        else
            return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(String AccountStatus) {
        this.AccountStatus = AccountStatus;
    }

    public String getAccountStatusName() {
        return AccountStatusName;
    }

    public void setAccountStatusName(String AccountStatusName) {
        this.AccountStatusName = AccountStatusName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAllowPoints() {
        return AllowPoints;
    }

    public void setAllowPoints(String AllowPoints) {
        this.AllowPoints = AllowPoints;
    }

    public String getBankAccountNo() {
        return BankAccountNo;
    }

    public void setBankAccountNo(String BankAccountNo) {
        this.BankAccountNo = BankAccountNo;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String County) {
        this.County = County;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String Credit) {
        this.Credit = Credit;
    }

    public String getCustNo() {
        return CustNo;
    }

    public void setCustNo(String CustNo) {
        this.CustNo = CustNo;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getCustomerIDType() {
        return CustomerIDType;
    }

    public void setCustomerIDType(String CustomerIDType) {
        this.CustomerIDType = CustomerIDType;
    }

    public String getCustomerIDTypeName() {
        return CustomerIDTypeName;
    }

    public void setCustomerIDTypeName(String CustomerIDTypeName) {
        this.CustomerIDTypeName = CustomerIDTypeName;
    }

    public String getCustomerLevel() {
        return CustomerLevel;
    }

    public void setCustomerLevel(String CustomerLevel) {
        this.CustomerLevel = CustomerLevel;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String CustomerType) {
        this.CustomerType = CustomerType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getInternalCus() {
        return InternalCus;
    }

    public void setInternalCus(String InternalCus) {
        this.InternalCus = InternalCus;
    }

    public String getLastOperateTime() {
        return LastOperateTime;
    }

    public void setLastOperateTime(String LastOperateTime) {
        this.LastOperateTime = LastOperateTime;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String MailAddress) {
        this.MailAddress = MailAddress;
    }

    public String getMaster() {
        return Master;
    }

    public void setMaster(String Master) {
        this.Master = Master;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(String OpeningTime) {
        this.OpeningTime = OpeningTime;
    }

    public String getPostNo() {
        return PostNo;
    }

    public void setPostNo(String PostNo) {
        this.PostNo = PostNo;
    }

    public String getPrepaidType() {
        return PrepaidType;
    }

    public void setPrepaidType(String PrepaidType) {
        this.PrepaidType = PrepaidType;
    }

    public String getPrepaidTypeName() {
        return PrepaidTypeName;
    }

    public void setPrepaidTypeName(String PrepaidTypeName) {
        this.PrepaidTypeName = PrepaidTypeName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getSettlementType() {
        return SettlementType;
    }

    public void setSettlementType(String SettlementType) {
        this.SettlementType = SettlementType;
    }

    public String getSettlementTypeName() {
        return SettlementTypeName;
    }

    public void setSettlementTypeName(String SettlementTypeName) {
        this.SettlementTypeName = SettlementTypeName;
    }

    public String getSettlementUnite() {
        return SettlementUnite;
    }

    public void setSettlementUnite(String SettlementUnite) {
        this.SettlementUnite = SettlementUnite;
    }

    public String getSettlementUniteName() {
        return SettlementUniteName;
    }

    public void setSettlementUniteName(String SettlementUniteName) {
        this.SettlementUniteName = SettlementUniteName;
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

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String TaxCode) {
        this.TaxCode = TaxCode;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String TelNo) {
        this.TelNo = TelNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Custom obj = (Custom) o;
        if (!CustNo.equals(obj.CustNo)) return false;
        return CustomerName.equals(obj.CustomerName);
    }

    @Override
    public int hashCode() {
        int result = StationNo.hashCode();
        result = 31 * result + StationNo.hashCode();
        return result;
    }

}
