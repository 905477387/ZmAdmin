package com.smallchill.modules.platform.model;

import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_investor_percent")
public class InvestorPercent extends BaseModel {

    private Integer id ;
    //投资人id
    private Integer InvestorId ;
    //投资比例
    private BigDecimal InvestorPercent ;
    //创建时间
    private Date CreateTime ;

    public Integer getId(){
        return  id;
    }
    public void setId(Integer id ){
        this.id = id;
    }

    public Integer getInvestorId(){
        return  InvestorId;
    }
    public void setInvestorId(Integer InvestorId ){
        this.InvestorId = InvestorId;
    }

    public BigDecimal getInvestorPercent(){
        return  InvestorPercent;
    }
    public void setInvestorPercent(BigDecimal InvestorPercent ){
        this.InvestorPercent = InvestorPercent;
    }

    public Date getCreateTime(){
        return  CreateTime;
    }
    public void setCreateTime(Date CreateTime ){
        this.CreateTime = CreateTime;
    }

}
