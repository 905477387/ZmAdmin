package com.smallchill.api.model;

import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Table(name = "tb_my_card")
public class MyCard {

    //主键
    private Integer id;
    //是否默认
    private Integer is_default;
    //手机号
    private String phone;
    //卡号
    private String card_no;
    //姓名
    private String name;
    //创建时间
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


}
