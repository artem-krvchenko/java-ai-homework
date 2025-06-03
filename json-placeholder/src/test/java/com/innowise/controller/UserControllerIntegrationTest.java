package com.innowise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.dto.AddressDto;
import com.innowise.dto.CompanyDto;
import com.innowise.dto.GeoDto;
import com.innowise.dto.UserDto;
import com.innowise.model.Address;
import com.innowise.model.Company;
import com.innowise.model.Geo;
import com.innowise.model.User;
import com.innowise.repository.UserRepository;
import com.innowise.security.JwtService;
import com.innowise.model.AuthUser;
import com.innowise.repository.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_USER_EMAIL = "test@example.com";
    private static final String TEST_USER_PASSWORD = "password123";
    private static final String TEST_USER_ROLE = "ROLE_USER";
    private static final String TEST_GEO_LAT = "-37.3159";
    private static final String TEST_GEO_LNG = "81.1496";
    private static final String TEST_ADDRESS_STREET = "Kulas Light";
    private static final String TEST_ADDRESS_SUITE = "Apt. 556";
    private static final String TEST_ADDRESS_CITY = "Gwenborough";
    private static final String TEST_ADDRESS_ZIPCODE = "92998-3874";
    private static final String TEST_COMPANY_NAME = "Romaguera-Crona";
    private static final String TEST_COMPANY_CATCH_PHRASE = "Multi-layered client-server neural-net";
    private static final String TEST_COMPANY_BS = "harness real-time e-markets";
    private static final String TEST_USER_PHONE = "123-456-7890";
    private static final String TEST_USER_WEBSITE = "hildegard.org";
    private static final String TEST_USER_USERNAME = "Bret";
    private static final String TEST_USER_FULL_NAME = "Leanne Graham";
    private static final String TEST_USER_EMAIL_2 = "Sincere@april.biz";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final long NON_EXISTENT_ID = 999L;
    private static final String UPDATED_NAME = "Updated Name";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String authToken;
    private User testUser;
    private UserDto testUserDto;
    private Address testAddress;
    private AddressDto testAddressDto;
    private Company testCompany;
    private CompanyDto testCompanyDto;
    private Geo testGeo;
    private GeoDto testGeoDto;

    @BeforeEach
    void setUp() {
        setupTestAuthUser();
        setupTestData();
    }

    private void setupTestAuthUser() {
        AuthUser authUser = new AuthUser();
        authUser.setName(TEST_USER_NAME);
        authUser.setEmail(TEST_USER_EMAIL);
        authUser.setPasswordHash(passwordEncoder.encode(TEST_USER_PASSWORD));
        authUserRepository.save(authUser);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(authUser.getEmail())
            .password(authUser.getPasswordHash())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(TEST_USER_ROLE)))
            .build();

        authToken = jwtService.generateToken(userDetails);
    }

    private void setupTestData() {
        setupGeo();
        setupAddress();
        setupCompany();
        setupUser();
        setupUserDto();
    }

    private void setupGeo() {
        testGeo = new Geo();
        testGeo.setLat(TEST_GEO_LAT);
        testGeo.setLng(TEST_GEO_LNG);
        testGeoDto = new GeoDto(TEST_GEO_LAT, TEST_GEO_LNG);
    }

    private void setupAddress() {
        testAddress = new Address();
        testAddress.setStreet(TEST_ADDRESS_STREET);
        testAddress.setSuite(TEST_ADDRESS_SUITE);
        testAddress.setCity(TEST_ADDRESS_CITY);
        testAddress.setZipcode(TEST_ADDRESS_ZIPCODE);
        testAddress.setGeo(testGeo);
        
        testAddressDto = new AddressDto();
        testAddressDto.setStreet(TEST_ADDRESS_STREET);
        testAddressDto.setSuite(TEST_ADDRESS_SUITE);
        testAddressDto.setCity(TEST_ADDRESS_CITY);
        testAddressDto.setZipcode(TEST_ADDRESS_ZIPCODE);
        testAddressDto.setGeo(testGeoDto);
    }

    private void setupCompany() {
        testCompany = new Company();
        testCompany.setName(TEST_COMPANY_NAME);
        testCompany.setCatchPhrase(TEST_COMPANY_CATCH_PHRASE);
        testCompany.setBs(TEST_COMPANY_BS);
        
        testCompanyDto = new CompanyDto();
        testCompanyDto.setName(TEST_COMPANY_NAME);
        testCompanyDto.setCatchPhrase(TEST_COMPANY_CATCH_PHRASE);
        testCompanyDto.setBs(TEST_COMPANY_BS);
    }

    private void setupUser() {
        testUser = new User();
        testUser.setName(TEST_USER_FULL_NAME);
        testUser.setUsername(TEST_USER_USERNAME);
        testUser.setEmail(TEST_USER_EMAIL_2);
        testUser.setAddress(testAddress);
        testUser.setPhone(TEST_USER_PHONE);
        testUser.setWebsite(TEST_USER_WEBSITE);
        testUser.setCompany(testCompany);
    }

    private void setupUserDto() {
        testUserDto = new UserDto();
        testUserDto.setName(TEST_USER_FULL_NAME);
        testUserDto.setUsername(TEST_USER_USERNAME);
        testUserDto.setEmail(TEST_USER_EMAIL_2);
        testUserDto.setAddress(testAddressDto);
        testUserDto.setPhone(TEST_USER_PHONE);
        testUserDto.setWebsite(TEST_USER_WEBSITE);
        testUserDto.setCompany(testCompanyDto);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", BEARER_PREFIX + authToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(savedUser.getId()))
                .andExpect(jsonPath("$[0].name").value(savedUser.getName()))
                .andExpect(jsonPath("$[0].username").value(savedUser.getUsername()))
                .andExpect(jsonPath("$[0].email").value(savedUser.getEmail()));
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(get("/api/v1/users/{id}", savedUser.getId())
                        .header("Authorization", BEARER_PREFIX + authToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.name").value(savedUser.getName()))
                .andExpect(jsonPath("$.username").value(savedUser.getUsername()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/{id}", NON_EXISTENT_ID)
                        .header("Authorization", BEARER_PREFIX + authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_WhenUserIsValid_ShouldCreateUser() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", BEARER_PREFIX + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testUserDto.getName()))
                .andExpect(jsonPath("$.username").value(testUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(testUserDto.getEmail()));
    }

    @Test
    void createUser_WhenUserIsInvalid_ShouldReturnBadRequest() throws Exception {
        testUserDto.setEmail(INVALID_EMAIL);

        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", BEARER_PREFIX + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() throws Exception {
        User savedUser = userRepository.save(testUser);

        testUserDto.setName(UPDATED_NAME);

        mockMvc.perform(put("/api/v1/users/{id}", savedUser.getId())
                        .header("Authorization", BEARER_PREFIX + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(UPDATED_NAME));
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/users/{id}", NON_EXISTENT_ID)
                        .header("Authorization", BEARER_PREFIX + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(delete("/api/v1/users/{id}", savedUser.getId())
                        .header("Authorization", BEARER_PREFIX + authToken))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(savedUser.getId()));
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", NON_EXISTENT_ID)
                        .header("Authorization", BEARER_PREFIX + authToken))
                .andExpect(status().isNotFound());
    }
} 