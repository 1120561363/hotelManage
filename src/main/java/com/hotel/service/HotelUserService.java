package com.hotel.service;

import com.hotel.domain.HotelUser;
import com.hotel.repository.HotelUserRepository;
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
 * Service Implementation for managing HotelUser.
 */
@Service
@Transactional
public class HotelUserService {

    private final Logger log = LoggerFactory.getLogger(HotelUserService.class);

    @Inject
    private HotelUserRepository hotelUserRepository;

    /**
     * Save a hotelUser.
     * @return the persisted entity
     */
    public HotelUser save(HotelUser hotelUser) {
        log.debug("Request to save HotelUser : {}", hotelUser);
        HotelUser result = hotelUserRepository.save(hotelUser);
        return result;
    }

    /**
     *  get all the hotelUsers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HotelUser> findAll(Pageable pageable) {
        log.debug("Request to get all HotelUsers");
        Page<HotelUser> result = hotelUserRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one hotelUser by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HotelUser findOne(Long id) {
        log.debug("Request to get HotelUser : {}", id);
        HotelUser hotelUser = hotelUserRepository.findOne(id);
        return hotelUser;
    }

    /**
     *  get one hotelUser by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HotelUser findOneByNumber(String number) {
        log.debug("Request to get HotelUser : {}", number);
        HotelUser hotelUser = hotelUserRepository.findByNumber(number);
        return hotelUser;
    }
    /**
     *  delete the  hotelUser by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelUser : {}", id);
        hotelUserRepository.delete(id);
    }
}
