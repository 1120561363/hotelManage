package com.hotel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.domain.HotelUser;
import com.hotel.service.HotelUserService;
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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HotelUser.
 */
@RestController
@RequestMapping("/api")
public class HotelUserResource {

    private final Logger log = LoggerFactory.getLogger(HotelUserResource.class);

    @Inject
    private HotelUserService hotelUserService;


   /* *//**
     * POST  /hotelUsers -> Create a new hotelUser.
     *//*
    @RequestMapping(value = "/login",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelUser> login(@RequestBody HotelUser hotelUser) throws URISyntaxException {
        HotelUser result = hotelUserService.findOneByNumber(hotelUser.getNumber());
        if(hotelUser.getPassword().equals(result.getPassword())){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.ok(null);
        }
    }*/

    /**
     * POST  /hotelUsers -> Create a new hotelUser.
     */
    @RequestMapping(value = "/hotelUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelUser> createHotelUser(@RequestBody HotelUser hotelUser) throws URISyntaxException {
        log.debug("REST request to save HotelUser : {}", hotelUser);
        if (hotelUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hotelUser", "idexists", "A new hotelUser cannot already have an ID")).body(null);
        }
        HotelUser result = hotelUserService.save(hotelUser);
        return ResponseEntity.created(new URI("/api/hotelUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hotelUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotelUsers -> Updates an existing hotelUser.
     */
    @RequestMapping(value = "/hotelUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelUser> updateHotelUser(@RequestBody HotelUser hotelUser) throws URISyntaxException {
        log.debug("REST request to update HotelUser : {}", hotelUser);
        if (hotelUser.getId() == null) {
            return createHotelUser(hotelUser);
        }
        HotelUser result = hotelUserService.save(hotelUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hotelUser", hotelUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotelUsers -> get all the hotelUsers.
     */
    @RequestMapping(value = "/hotelUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HotelUser>> getAllHotelUsers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HotelUsers");
        Page<HotelUser> page = hotelUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hotelUsers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hotelUsers/:id -> get the "id" hotelUser.
     */
    @RequestMapping(value = "/hotelUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelUser> getHotelUser(@PathVariable Long id) {
        log.debug("REST request to get HotelUser : {}", id);
        HotelUser hotelUser = hotelUserService.findOne(id);
        return Optional.ofNullable(hotelUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hotelUsers/:id -> delete the "id" hotelUser.
     */
    @RequestMapping(value = "/hotelUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHotelUser(@PathVariable Long id) {
        log.debug("REST request to delete HotelUser : {}", id);
        hotelUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hotelUser", id.toString())).build();
    }
}
