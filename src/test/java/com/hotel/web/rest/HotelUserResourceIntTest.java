package com.hotel.web.rest;

import com.hotel.Application;
import com.hotel.domain.HotelUser;
import com.hotel.repository.HotelUserRepository;
import com.hotel.service.HotelUserService;

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
 * Test class for the HotelUserResource REST controller.
 *
 * @see HotelUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HotelUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";

    private static final Integer DEFAULT_AUTH = 1;
    private static final Integer UPDATED_AUTH = 2;
    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_DATE_IN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_IN = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SEX = "AAAAA";
    private static final String UPDATED_SEX = "BBBBB";

    @Inject
    private HotelUserRepository hotelUserRepository;

    @Inject
    private HotelUserService hotelUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHotelUserMockMvc;

    private HotelUser hotelUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HotelUserResource hotelUserResource = new HotelUserResource();
        ReflectionTestUtils.setField(hotelUserResource, "hotelUserService", hotelUserService);
        this.restHotelUserMockMvc = MockMvcBuilders.standaloneSetup(hotelUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hotelUser = new HotelUser();
        hotelUser.setName(DEFAULT_NAME);
        hotelUser.setPassword(DEFAULT_PASSWORD);
        hotelUser.setAuth(DEFAULT_AUTH);
        hotelUser.setNumber(DEFAULT_NUMBER);
        hotelUser.setDateIn(DEFAULT_DATE_IN);
        hotelUser.setSex(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void createHotelUser() throws Exception {
        int databaseSizeBeforeCreate = hotelUserRepository.findAll().size();

        // Create the HotelUser

        restHotelUserMockMvc.perform(post("/api/hotelUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotelUser)))
                .andExpect(status().isCreated());

        // Validate the HotelUser in the database
        List<HotelUser> hotelUsers = hotelUserRepository.findAll();
        assertThat(hotelUsers).hasSize(databaseSizeBeforeCreate + 1);
        HotelUser testHotelUser = hotelUsers.get(hotelUsers.size() - 1);
        assertThat(testHotelUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotelUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testHotelUser.getAuth()).isEqualTo(DEFAULT_AUTH);
        assertThat(testHotelUser.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testHotelUser.getDateIn()).isEqualTo(DEFAULT_DATE_IN);
        assertThat(testHotelUser.getSex()).isEqualTo(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void getAllHotelUsers() throws Exception {
        // Initialize the database
        hotelUserRepository.saveAndFlush(hotelUser);

        // Get all the hotelUsers
        restHotelUserMockMvc.perform(get("/api/hotelUsers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hotelUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].auth").value(hasItem(DEFAULT_AUTH)))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].dateIn").value(hasItem(DEFAULT_DATE_IN.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())));
    }

    @Test
    @Transactional
    public void getHotelUser() throws Exception {
        // Initialize the database
        hotelUserRepository.saveAndFlush(hotelUser);

        // Get the hotelUser
        restHotelUserMockMvc.perform(get("/api/hotelUsers/{id}", hotelUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hotelUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.auth").value(DEFAULT_AUTH))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.dateIn").value(DEFAULT_DATE_IN.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotelUser() throws Exception {
        // Get the hotelUser
        restHotelUserMockMvc.perform(get("/api/hotelUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotelUser() throws Exception {
        // Initialize the database
        hotelUserRepository.saveAndFlush(hotelUser);

		int databaseSizeBeforeUpdate = hotelUserRepository.findAll().size();

        // Update the hotelUser
        hotelUser.setName(UPDATED_NAME);
        hotelUser.setPassword(UPDATED_PASSWORD);
        hotelUser.setAuth(UPDATED_AUTH);
        hotelUser.setNumber(UPDATED_NUMBER);
        hotelUser.setDateIn(UPDATED_DATE_IN);
        hotelUser.setSex(UPDATED_SEX);

        restHotelUserMockMvc.perform(put("/api/hotelUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hotelUser)))
                .andExpect(status().isOk());

        // Validate the HotelUser in the database
        List<HotelUser> hotelUsers = hotelUserRepository.findAll();
        assertThat(hotelUsers).hasSize(databaseSizeBeforeUpdate);
        HotelUser testHotelUser = hotelUsers.get(hotelUsers.size() - 1);
        assertThat(testHotelUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotelUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testHotelUser.getAuth()).isEqualTo(UPDATED_AUTH);
        assertThat(testHotelUser.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testHotelUser.getDateIn()).isEqualTo(UPDATED_DATE_IN);
        assertThat(testHotelUser.getSex()).isEqualTo(UPDATED_SEX);
    }

    @Test
    @Transactional
    public void deleteHotelUser() throws Exception {
        // Initialize the database
        hotelUserRepository.saveAndFlush(hotelUser);

		int databaseSizeBeforeDelete = hotelUserRepository.findAll().size();

        // Get the hotelUser
        restHotelUserMockMvc.perform(delete("/api/hotelUsers/{id}", hotelUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HotelUser> hotelUsers = hotelUserRepository.findAll();
        assertThat(hotelUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
