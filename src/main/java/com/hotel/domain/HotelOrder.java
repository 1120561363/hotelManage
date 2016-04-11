package com.hotel.domain;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HotelOrder.
 */
@Entity
@Table(name = "hotel_order")
public class HotelOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "customer_mobile")
    private String customerMobile;
    
    @Column(name = "room_number")
    private Integer roomNumber;
    
    @Column(name = "period")
    private Integer period;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "created_date")
    private LocalDate createdDate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }
    
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getPeriod() {
        return period;
    }
    
    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelOrder hotelOrder = (HotelOrder) o;
        if(hotelOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hotelOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HotelOrder{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", customerName='" + customerName + "'" +
            ", customerMobile='" + customerMobile + "'" +
            ", roomNumber='" + roomNumber + "'" +
            ", period='" + period + "'" +
            ", amount='" + amount + "'" +
            ", createdDate='" + createdDate + "'" +
            '}';
    }
}
