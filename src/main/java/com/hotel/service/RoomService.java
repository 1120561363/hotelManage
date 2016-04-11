package com.hotel.service;

import com.hotel.domain.Authority;
import com.hotel.domain.Room;
import com.hotel.domain.RoomDTO;
import com.hotel.domain.User;
import com.hotel.repository.*;
import com.hotel.security.SecurityUtils;
import com.hotel.service.util.RandomUtil;
import com.hotel.web.rest.dto.ManagedUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service class for managing rooms.
 */
@Service
public class RoomService {

    @Inject
    private RoomRepository roomRepository;
    @Inject
    private RoomDAO roomDAO;

    private final Logger log = LoggerFactory.getLogger(RoomService.class);

    public List<Room> getAllRooms(Pageable pageable) {
        return  roomRepository.findAll();
    }

    public List<RoomDTO> getAllRoomDTOs(Pageable pageable) {
        List<Room> roomList = roomRepository.findAll();
        List<RoomDTO> roomDROList = new ArrayList<RoomDTO>();
        for (Room room: roomList) {
            RoomDTO roomDTO = new RoomDTO();
            String status = roomDAO.getStatusByID(room.getStatus());
            String type = roomDAO.getTypeByID(room.getType());
            roomDTO.setStatus(status);
            roomDTO.setType(type);
            roomDTO.setCustomerName(room.getCustomerName());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setPrice(room.getPrice());
            roomDROList.add(roomDTO);
        }
        return roomDROList;
    }
    public void updateRoom(Room roomIn){
        Room room= roomRepository.findByNumber(roomIn.getNumber());
        room.setPrice(roomIn.getPrice());
        room.setNumber(roomIn.getNumber());
        room.setType(roomIn.getType());
        int result =roomDAO.updateRoom(room);
    }
}
