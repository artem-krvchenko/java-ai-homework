package com.innowise.mapper;

import com.innowise.dto.AddressDto;
import com.innowise.dto.CompanyDto;
import com.innowise.dto.GeoDto;
import com.innowise.dto.UserDto;
import com.innowise.model.Address;
import com.innowise.model.Company;
import com.innowise.model.Geo;
import com.innowise.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link UserMapper}.
 * Tests the mapping functionality between {@link User} entities and {@link UserDto} objects.
 */
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;
    private User testUser;
    private UserDto testUserDto;
    private Address testAddress;
    private AddressDto testAddressDto;
    private Company testCompany;
    private CompanyDto testCompanyDto;
    private Geo testGeo;
    private GeoDto testGeoDto;

    // Test data constants
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Leanne Graham";
    private static final String TEST_USERNAME = "Bret";
    private static final String TEST_EMAIL = "Sincere@april.biz";
    private static final String TEST_PHONE = "123-456-7890";
    private static final String TEST_WEBSITE = "hildegard.org";
    private static final String TEST_STREET = "Kulas Light";
    private static final String TEST_SUITE = "Apt. 556";
    private static final String TEST_CITY = "Gwenborough";
    private static final String TEST_ZIPCODE = "92998-3874";
    private static final String TEST_LAT = "-37.3159";
    private static final String TEST_LNG = "81.1496";
    private static final String TEST_COMPANY_NAME = "Romaguera-Crona";
    private static final String TEST_CATCH_PHRASE = "Multi-layered client-server neural-net";
    private static final String TEST_BS = "harness real-time e-markets";

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        setupTestData();
    }

    /**
     * Sets up test data for all test cases.
     * Creates instances of User, UserDto, Address, AddressDto, Company, CompanyDto, Geo, and GeoDto
     * with predefined test values.
     */
    private void setupTestData() {
        setupGeo();
        setupAddress();
        setupCompany();
        setupUser();
        setupUserDto();
    }

    private void setupGeo() {
        testGeo = new Geo(TEST_ID, TEST_LAT, TEST_LNG);
        testGeoDto = new GeoDto(TEST_LAT, TEST_LNG);
    }

    private void setupAddress() {
        testAddress = new Address(TEST_ID, TEST_STREET, TEST_SUITE, TEST_CITY, TEST_ZIPCODE, testGeo);
        testAddressDto = new AddressDto();
        testAddressDto.setStreet(TEST_STREET);
        testAddressDto.setSuite(TEST_SUITE);
        testAddressDto.setCity(TEST_CITY);
        testAddressDto.setZipcode(TEST_ZIPCODE);
        testAddressDto.setGeo(testGeoDto);
    }

    private void setupCompany() {
        testCompany = new Company(TEST_ID, TEST_COMPANY_NAME, TEST_CATCH_PHRASE, TEST_BS);
        testCompanyDto = new CompanyDto(TEST_COMPANY_NAME, TEST_CATCH_PHRASE, TEST_BS);
    }

    private void setupUser() {
        testUser = new User(TEST_ID, TEST_NAME, TEST_USERNAME, TEST_EMAIL, testAddress, TEST_PHONE, TEST_WEBSITE, testCompany);
    }

    private void setupUserDto() {
        testUserDto = new UserDto();
        testUserDto.setId(TEST_ID);
        testUserDto.setName(TEST_NAME);
        testUserDto.setUsername(TEST_USERNAME);
        testUserDto.setEmail(TEST_EMAIL);
        testUserDto.setAddress(testAddressDto);
        testUserDto.setPhone(TEST_PHONE);
        testUserDto.setWebsite(TEST_WEBSITE);
        testUserDto.setCompany(testCompanyDto);
    }

    @Nested
    @DisplayName("toDto() method tests")
    class ToDtoTests {

        @Test
        @DisplayName("Should map valid User to UserDto correctly")
        void toDto_WhenUserIsValid_ShouldMapCorrectly() {
            // Act
            UserDto result = userMapper.toDto(testUser);

            // Assert
            assertNotNull(result);
            assertBasicUserFields(result);
            assertAddressMapping(result.getAddress());
            assertCompanyMapping(result.getCompany());
        }

        @Test
        @DisplayName("Should return null when User is null")
        void toDto_WhenUserIsNull_ShouldReturnNull() {
            // Act
            UserDto result = userMapper.toDto(null);

            // Assert
            assertNull(result);
        }

        @Test
        @DisplayName("Should map User without Address correctly")
        void toDto_WhenUserHasNullAddress_ShouldMapWithoutAddress() {
            // Arrange
            testUser.setAddress(null);

            // Act
            UserDto result = userMapper.toDto(testUser);

            // Assert
            assertNotNull(result);
            assertNull(result.getAddress());
            assertBasicUserFields(result);
            assertNotNull(result.getCompany());
        }

        @Test
        @DisplayName("Should map User without Company correctly")
        void toDto_WhenUserHasNullCompany_ShouldMapWithoutCompany() {
            // Arrange
            testUser.setCompany(null);

            // Act
            UserDto result = userMapper.toDto(testUser);

            // Assert
            assertNotNull(result);
            assertNull(result.getCompany());
            assertBasicUserFields(result);
            assertNotNull(result.getAddress());
        }
    }

    @Nested
    @DisplayName("toEntity() method tests")
    class ToEntityTests {

        @Test
        @DisplayName("Should map valid UserDto to User correctly")
        void toEntity_WhenUserDtoIsValid_ShouldMapCorrectly() {
            // Act
            User result = userMapper.toEntity(testUserDto);

            // Assert
            assertNotNull(result);
            assertBasicUserFields(result);
            assertAddressMapping(result.getAddress());
            assertCompanyMapping(result.getCompany());
        }

        @Test
        @DisplayName("Should return null when UserDto is null")
        void toEntity_WhenUserDtoIsNull_ShouldReturnNull() {
            // Act
            User result = userMapper.toEntity(null);

            // Assert
            assertNull(result);
        }

        @Test
        @DisplayName("Should map UserDto without Address correctly")
        void toEntity_WhenUserDtoHasNullAddress_ShouldMapWithoutAddress() {
            // Arrange
            testUserDto.setAddress(null);

            // Act
            User result = userMapper.toEntity(testUserDto);

            // Assert
            assertNotNull(result);
            assertNull(result.getAddress());
            assertBasicUserFields(result);
            assertNotNull(result.getCompany());
        }

        @Test
        @DisplayName("Should map UserDto without Company correctly")
        void toEntity_WhenUserDtoHasNullCompany_ShouldMapWithoutCompany() {
            // Arrange
            testUserDto.setCompany(null);

            // Act
            User result = userMapper.toEntity(testUserDto);

            // Assert
            assertNotNull(result);
            assertNull(result.getCompany());
            assertBasicUserFields(result);
            assertNotNull(result.getAddress());
        }
    }

    /**
     * Asserts basic user fields in the result object.
     * @param result The object containing user fields to verify
     */
    private void assertBasicUserFields(Object result) {
        if (result instanceof UserDto dto) {
            assertEquals(TEST_ID, dto.getId());
            assertEquals(TEST_NAME, dto.getName());
            assertEquals(TEST_USERNAME, dto.getUsername());
            assertEquals(TEST_EMAIL, dto.getEmail());
            assertEquals(TEST_PHONE, dto.getPhone());
            assertEquals(TEST_WEBSITE, dto.getWebsite());
        } else if (result instanceof User user) {
            assertEquals(TEST_ID, user.getId());
            assertEquals(TEST_NAME, user.getName());
            assertEquals(TEST_USERNAME, user.getUsername());
            assertEquals(TEST_EMAIL, user.getEmail());
            assertEquals(TEST_PHONE, user.getPhone());
            assertEquals(TEST_WEBSITE, user.getWebsite());
        }
    }

    /**
     * Asserts address mapping in the result object.
     * @param address The address object to verify
     */
    private void assertAddressMapping(Object address) {
        if (address instanceof AddressDto dto) {
            assertNotNull(dto);
            assertEquals(TEST_STREET, dto.getStreet());
            assertEquals(TEST_SUITE, dto.getSuite());
            assertEquals(TEST_CITY, dto.getCity());
            assertEquals(TEST_ZIPCODE, dto.getZipcode());
            assertGeoMapping(dto.getGeo());
        } else if (address instanceof Address addr) {
            assertNotNull(addr);
            assertEquals(TEST_STREET, addr.getStreet());
            assertEquals(TEST_SUITE, addr.getSuite());
            assertEquals(TEST_CITY, addr.getCity());
            assertEquals(TEST_ZIPCODE, addr.getZipcode());
            assertGeoMapping(addr.getGeo());
        }
    }

    /**
     * Asserts geo mapping in the result object.
     * @param geo The geo object to verify
     */
    private void assertGeoMapping(Object geo) {
        if (geo instanceof GeoDto dto) {
            assertNotNull(dto);
            assertEquals(TEST_LAT, dto.getLat());
            assertEquals(TEST_LNG, dto.getLng());
        } else if (geo instanceof Geo g) {
            assertNotNull(g);
            assertEquals(TEST_LAT, g.getLat());
            assertEquals(TEST_LNG, g.getLng());
        }
    }

    /**
     * Asserts company mapping in the result object.
     * @param company The company object to verify
     */
    private void assertCompanyMapping(Object company) {
        if (company instanceof CompanyDto dto) {
            assertNotNull(dto);
            assertEquals(TEST_COMPANY_NAME, dto.getName());
            assertEquals(TEST_CATCH_PHRASE, dto.getCatchPhrase());
            assertEquals(TEST_BS, dto.getBs());
        } else if (company instanceof Company comp) {
            assertNotNull(comp);
            assertEquals(TEST_COMPANY_NAME, comp.getName());
            assertEquals(TEST_CATCH_PHRASE, comp.getCatchPhrase());
            assertEquals(TEST_BS, comp.getBs());
        }
    }
} 