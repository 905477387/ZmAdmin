package com.smallchill.modules.platform.model;

import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "tb_oil_price")
public class OilPrice extends BaseModel {

    private Integer id;
    //站点编号
    private String StationNo;
    //柴油
    private String DieselOil;
    //汽油
    private String GasOil0;
    //汽油
    private String GasOil92;
    //汽油
    private String GasOil95;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationNo() {
        return StationNo;
    }

    public void setStationNo(String StationNo) {
        this.StationNo = StationNo;
    }

    public String getDieselOil() {
        return DieselOil;
    }

    public void setDieselOil(String DieselOil) {
        this.DieselOil = DieselOil;
    }

    public String getGasOil0() {
        return GasOil0;
    }

    public void setGasOil0(String GasOil0) {
        this.GasOil0 = GasOil0;
    }

    public String getGasOil92() {
        return GasOil92;
    }

    public void setGasOil92(String GasOil92) {
        this.GasOil92 = GasOil92;
    }

    public String getGasOil95() {
        return GasOil95;
    }

    public void setGasOil95(String GasOil95) {
        this.GasOil95 = GasOil95;
    }


}
