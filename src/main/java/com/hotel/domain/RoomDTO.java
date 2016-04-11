package com.hotel.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yuxin on 2016/3/27.
 */

public class RoomDTO implements Serializable{


    private int number;

    private double price;

    private String status;

    private String type;

    private String customerName;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
            ", number=" + number +
            ", price=" + price +
            ", status=" + status +
            ", type=" + type +
            ", customerName='" + customerName + '\'' +
            '}';
    }
}
