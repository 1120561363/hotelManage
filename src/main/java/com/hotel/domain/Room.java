package com.hotel.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yuxin on 2016/3/27.
 */
@Entity
@Table(name = "hotel_room")
public class Room implements Serializable{
    /*room_number	int
room_price	double
room_status	int
room_type	int
room_customer——name	Varchar（128）*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    private int status;

    @Column(name = "type")
    private int type;

    @Column(name = "customer_name")
    private String customerName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
        return "Room{" +
            "id=" + id +
            ", number=" + number +
            ", price=" + price +
            ", status=" + status +
            ", type=" + type +
            ", customerName='" + customerName + '\'' +
            '}';
    }
}
