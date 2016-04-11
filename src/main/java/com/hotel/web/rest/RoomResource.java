package com.hotel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.domain.Authority;
import com.hotel.domain.Room;
import com.hotel.domain.RoomDTO;
import com.hotel.domain.User;
import com.hotel.repository.AuthorityRepository;
import com.hotel.repository.UserRepository;
import com.hotel.security.AuthoritiesConstants;
import com.hotel.service.MailService;
import com.hotel.service.RoomService;
import com.hotel.service.UserService;
import com.hotel.web.rest.dto.ManagedUserDTO;
import com.hotel.web.rest.util.HeaderUtil;
import com.hotel.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class RoomResource {

    @Inject
    private RoomService roomService;

    private final Logger log = LoggerFactory.getLogger(RoomResource.class);


    /**
     * .
     */
    @RequestMapping(value = "/rooms/a",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<User> login(@RequestBody User user) throws URISyntaxException {
        return null;
    }


    /**
     * GET  /rooms -> get all rooms.
     */
    @RequestMapping(value = "/rooms/update",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateRoom(@RequestBody Room room)
        throws URISyntaxException {
        System.out.println(room);
        roomService.updateRoom(room);
        return ResponseEntity.ok(null);
    }

    /**
     * GET  /rooms -> get all rooms.
     */
    @RequestMapping(value = "/rooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<RoomDTO>> getAllRooms(Pageable pageable)
        throws URISyntaxException {
        return ResponseEntity.ok(roomService.getAllRoomDTOs(pageable));
    }
}
