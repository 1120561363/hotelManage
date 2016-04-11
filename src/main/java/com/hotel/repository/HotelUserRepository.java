package com.hotel.repository;

import com.hotel.domain.HotelUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HotelUser entity.
 */
public interface HotelUserRepository extends JpaRepository<HotelUser,Long> {
        HotelUser findByNumber(String number);
}
