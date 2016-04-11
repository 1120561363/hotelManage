package com.hotel.repository;

import com.hotel.domain.HotelOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HotelOrder entity.
 */
public interface HotelOrderRepository extends JpaRepository<HotelOrder,Long> {

}
