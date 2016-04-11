package com.hotel.web.rest;

import com.hotel.Application;
import com.hotel.domain.HotelOrder;
import com.hotel.repository.HotelOrderRepository;
import com.hotel.service.HotelOrderService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HotelOrderResource REST controller.
 *
 * @see HotelOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HotelOrderResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";
    private static final String DEFAULT_CUSTOMER_NAME = "AAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBB";
    private static final String DEFAULT_CUSTOMER_MOBILE = "AAAAA";
    private static final String UPDATED_CUSTOMER_MOBILE = "BBBBB";

    private static final Integer DEFAULT_ROOM_NUMBER = 1;
    private static final Integer UPDATED_ROOM_NUMBER = 2;

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private HotelOrderRepository hotelOrderRepository;

    @Inject
    private HotelOrderService hotelOrderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHotelOrderMockMvc;

    private HotelOrder hotelOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HotelOrderResource hotelOrderResource = new HotelOrderResource();
        ReflectionTestUtils.setField(hotelOrderResource, "hotelOrderService", hotelOrderService);
        this.restHotelOrderMockMvc = MockMvcBuilders.standaloneSetup(hotelOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hotelOrder = new HotelOrder();
        hotelOrder.setNumber(DEFAULT_NUMBER);
        hotelOrder.setCustomerName(DEFAULT_CUSTOMER_NAME);
        hotelOrder.setCustomerMobile(DEFAULT_CUSTOMER_MOBILE);
        hotelOrder.setRoomNumber(DEFAULT_ROOM_NUMBER);
        hotelOrder.setPeriod(DEFAULT_PERIOD);
        hotelOrder.setAmount(DEFAULT_AMOUNT);
        hotelOrder.setCreatedDate(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createHotelOrder() throws Exception {
        int databaseSizeBeforeCreate = hotelOrderRepository.findAll().size();

        // Create the HotelOrder

        restHotelOrderMockMvc.perform(post("/api/hotelOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotelOrder)))
                .andExpect(status().isCreated());

        // Validate the HotelOrder in the database
        List<HotelOrder> hotelOrders = hotelOrderRepository.findAll();
        assertThat(hotelOrders).hasSize(databaseSizeBeforeCreate + 1);
        HotelOrder testHotelOrder = hotelOrders.get(hotelOrders.size() - 1);
        assertThat(testHotelOrder.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testHotelOrder.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testHotelOrder.getCustomerMobile()).isEqualTo(DEFAULT_CUSTOMER_MOBILE);
        assertThat(testHotelOrder.getRoomNumber()).isEqualTo(DEFAULT_ROOM_NUMBER);
        assertThat(testHotelOrder.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testHotelOrder.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testHotelOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllHotelOrders() throws Exception {
        // Initialize the database
        hotelOrderRepository.saveAndFlush(hotelOrder);

        // Get all the hotelOrders
        restHotelOrderMockMvc.perform(get("/api/hotelOrders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hotelOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
                .andExpect(jsonPath("$.[*].customerMobile").value(hasItem(DEFAULT_CUSTOMER_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER)))
                .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getHotelOrder() throws Exception {
        // Initialize the database
        hotelOrderRepository.saveAndFlush(hotelOrder);

        // Get the hotelOrder
        restHotelOrderMockMvc.perform(get("/api/hotelOrders/{id}", hotelOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hotelOrder.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.customerMobile").value(DEFAULT_CUSTOMER_MOBILE.toString()))
            .andExpect(jsonPath("$.roomNumber").value(DEFAULT_ROOM_NUMBER))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotelOrder() throws Exception {
        // Get the hotelOrder
        restHotelOrderMockMvc.perform(get("/api/hotelOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotelOrder() throws Exception {
        // Initialize the database
        hotelOrderRepository.saveAndFlush(hotelOrder);

		int databaseSizeBeforeUpdate = hotelOrderRepository.findAll().size();

        // Update the hotelOrder
        hotelOrder.setNumber(UPDATED_NUMBER);
        hotelOrder.setCustomerName(UPDATED_CUSTOMER_NAME);
        hotelOrder.setCustomerMobile(UPDATED_CUSTOMER_MOBILE);
        hotelOrder.setRoomNumber(UPDATED_ROOM_NUMBER);
        hotelOrder.setPeriod(UPDATED_PERIOD);
        hotelOrder.setAmount(UPDATED_AMOUNT);
        hotelOrder.setCreatedDate(UPDATED_CREATED_DATE);

        restHotelOrderMockMvc.perform(put("/api/hotelOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotelOrder)))
                .andExpect(status().isOk());

        // Validate the HotelOrder in the database
        List<HotelOrder> hotelOrders = hotelOrderRepository.findAll();
        assertThat(hotelOrders).hasSize(databaseSizeBeforeUpdate);
        HotelOrder testHotelOrder = hotelOrders.get(hotelOrders.size() - 1);
        assertThat(testHotelOrder.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testHotelOrder.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testHotelOrder.getCustomerMobile()).isEqualTo(UPDATED_CUSTOMER_MOBILE);
        assertThat(testHotelOrder.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testHotelOrder.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testHotelOrder.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testHotelOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void deleteHotelOrder() throws Exception {
        // Initialize the database
        hotelOrderRepository.saveAndFlush(hotelOrder);

		int databaseSizeBeforeDelete = hotelOrderRepository.findAll().size();

        // Get the hotelOrder
        restHotelOrderMockMvc.perform(delete("/api/hotelOrders/{id}", hotelOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HotelOrder> hotelOrders = hotelOrderRepository.findAll();
        assertThat(hotelOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
