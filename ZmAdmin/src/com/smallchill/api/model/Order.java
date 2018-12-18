package com.smallchill.api.model;

import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Table(name = "tb_order")
public class Order extends BaseModel {

    //主键
    private Integer id;
    //订单编号
    private String order_no;
    //充值油卡id
    private Integer card_id;
    //充值数量
    private Integer charge_number;
    //订单状态
    private Integer status;
    //顾客卡号
    private String customer_card_no;
    //顾客姓名
    private String customer_name;
    //顾客手机
    private String customer_phone;
    //订单金额
    private Integer order_price;
    //圈存人
    private String charger;
    //圈存时间
    private Date charge_time;
    //订单创建时间
    private Date create_time;
    //ping++充值id
    private String charge_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Integer getCharge_number() {
        return charge_number;
    }

    public void setCharge_number(Integer charge_number) {
        this.charge_number = charge_number;
    }

    public Integer getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Integer order_price) {
        this.order_price = order_price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomer_card_no() {
        return customer_card_no;
    }

    public void setCustomer_card_no(String customer_card_no) {
        this.customer_card_no = customer_card_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public Date getCharge_time() {
        return charge_time;
    }

    public void setCharge_time(Date charge_time) {
        this.charge_time = charge_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCharge_id() {
        return charge_id;
    }

    public void setCharge_id(String charge_id) {
        this.charge_id = charge_id;
    }
}
