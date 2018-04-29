package com.werun.db;


import org.litepal.crud.DataSupport;


/**
 * Created by winta on 2018/4/3.
 */

public class User_Data extends DataSupport {

    private String userId;
    private boolean sex; // false is boy,true is girl
    private double height;
    private double weight;
    private String icon_address;
    private double target_weight;
    private String motto;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setIcon_address(String icon_address) {
        this.icon_address = icon_address;
    }

    public void setTarget_weight(double target_weight) {
        this.target_weight = target_weight;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getSex() {
        return sex;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getTarget_weight() {
        return target_weight;
    }

    public String getIcon_address() {
        return icon_address;
    }

    public String getMotto() {
        return motto;
    }
}
