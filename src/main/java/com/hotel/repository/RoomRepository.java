package com.hotel.repository;

import com.hotel.domain.Room;
import com.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Room entity.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByStatus(int status);

    Optional<Room> findByType(int type);

    Room findByNumber(int number);
}
