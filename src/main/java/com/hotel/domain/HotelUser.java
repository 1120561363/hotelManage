package com.hotel.domain;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HotelUser.
 */
@Entity
@Table(name = "hotel_user")
public class HotelUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "auth")
    private Integer auth;
    
    @Column(name = "number")
    private String number;
    
    @Column(name = "date_in")
    private LocalDate dateIn;
    
    @Column(name = "sex")
    private String sex;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAuth() {
        return auth;
    }
    
    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }
    
    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelUser hotelUser = (HotelUser) o;
        if(hotelUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hotelUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HotelUser{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", password='" + password + "'" +
            ", auth='" + auth + "'" +
            ", number='" + number + "'" +
            ", dateIn='" + dateIn + "'" +
            ", sex='" + sex + "'" +
            '}';
    }
}
