package com.hotel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.domain.HotelOrder;
import com.hotel.repository.HotelOrderDAO;
import com.hotel.service.HotelOrderService;
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
 * REST controller for managing HotelOrder.
 */
@RestController
@RequestMapping("/api")
public class HotelOrderResource {

    private final Logger log = LoggerFactory.getLogger(HotelOrderResource.class);

    @Inject
    private HotelOrderService hotelOrderService;


    /**
     * GET  /hotelOrders/:id -> get report by year.
     */
    @RequestMapping(value = "/hotelOrders/report/{year}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Double>> getHotelOrderReport(@PathVariable int year) {
        log.debug("REST request to get HotelOrder report: {}", year);
        List<Double> reportList = hotelOrderService.getReportByYear(year);
        return ResponseEntity.ok(reportList);
    }


    /**
     * POST  /hotelOrders -> Create a new hotelOrder.
     */
    @RequestMapping(value = "/hotelOrders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelOrder> createHotelOrder(@RequestBody HotelOrder hotelOrder) throws URISyntaxException {
        log.debug("REST request to save HotelOrder : {}", hotelOrder);
        if (hotelOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hotelOrder", "idexists", "A new hotelOrder cannot already have an ID")).body(null);
        }
        HotelOrder result = hotelOrderService.save(hotelOrder);
        return ResponseEntity.created(new URI("/api/hotelOrders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hotelOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotelOrders -> Updates an existing hotelOrder.
     */
    @RequestMapping(value = "/hotelOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelOrder> updateHotelOrder(@RequestBody HotelOrder hotelOrder) throws URISyntaxException {
        log.debug("REST request to update HotelOrder : {}", hotelOrder);
        if (hotelOrder.getId() == null) {
            return createHotelOrder(hotelOrder);
        }
        HotelOrder result = hotelOrderService.save(hotelOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hotelOrder", hotelOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotelOrders -> get all the hotelOrders.
     */
    @RequestMapping(value = "/hotelOrders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HotelOrder>> getAllHotelOrders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HotelOrders");
        Page<HotelOrder> page = hotelOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hotelOrders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hotelOrders/:id -> get the "id" hotelOrder.
     */
    @RequestMapping(value = "/hotelOrders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HotelOrder> getHotelOrder(@PathVariable Long id) {
        log.debug("REST request to get HotelOrder : {}", id);
        HotelOrder hotelOrder = hotelOrderService.findOne(id);
        return Optional.ofNullable(hotelOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hotelOrders/:id -> delete the "id" hotelOrder.
     */
    @RequestMapping(value = "/hotelOrders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHotelOrder(@PathVariable Long id) {
        log.debug("REST request to delete HotelOrder : {}", id);
        hotelOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hotelOrder", id.toString())).build();
    }
}
