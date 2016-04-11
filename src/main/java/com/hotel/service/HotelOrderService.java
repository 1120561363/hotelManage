package com.hotel.service;

import com.hotel.domain.HotelOrder;
import com.hotel.repository.HotelOrderDAO;
import com.hotel.repository.HotelOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing HotelOrder.
 */
@Service
@Transactional
public class HotelOrderService {

    private final Logger log = LoggerFactory.getLogger(HotelOrderService.class);

    @Inject
    private HotelOrderRepository hotelOrderRepository;
    @Inject
    private HotelOrderDAO hotelOrderDAO;

    public List<Double> getReportByYear(int year){
        List<Double> reportList = hotelOrderDAO.getReportByYear(year);
        return  reportList;
     }

    /**
     * Save a hotelOrder.
     * @return the persisted entity
     */
    public HotelOrder save(HotelOrder hotelOrder) {
        log.debug("Request to save HotelOrder : {}", hotelOrder);
        HotelOrder result = hotelOrderRepository.save(hotelOrder);
        return result;
    }

    /**
     *  get all the hotelOrders.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HotelOrder> findAll(Pageable pageable) {
        log.debug("Request to get all HotelOrders");
        Page<HotelOrder> result = hotelOrderRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one hotelOrder by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HotelOrder findOne(Long id) {
        log.debug("Request to get HotelOrder : {}", id);
        HotelOrder hotelOrder = hotelOrderRepository.findOne(id);
        return hotelOrder;
    }

    /**
     *  delete the  hotelOrder by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelOrder : {}", id);
        hotelOrderRepository.delete(id);
    }
}
