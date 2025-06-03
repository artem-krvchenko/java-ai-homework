package com.innowise.service;

import com.innowise.dto.AddressDto;
import com.innowise.dto.CompanyDto;
import com.innowise.dto.GeoDto;
import com.innowise.dto.UserDto;
import com.innowise.exception.DuplicateResourceException;
import com.innowise.exception.ResourceNotFoundException;
import com.innowise.mapper.UserMapper;
import com.innowise.model.Address;
import com.innowise.model.Company;
import com.innowise.model.Geo;
import com.innowise.model.User;
import com.innowise.repository.UserRepository;
import com.innowise.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

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
        testGeo = new Geo(1L, "-37.3159", "81.1496");
        testGeoDto = new GeoDto("-37.3159", "81.1496");
        testAddress = new Address(1L, "Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", testGeo);
        testAddressDto = new AddressDto();
        testAddressDto.setStreet("Kulas Light");
        testAddressDto.setSuite("Apt. 556");
        testAddressDto.setCity("Gwenborough");
        testAddressDto.setZipcode("92998-3874");
        testAddressDto.setGeo(testGeoDto);
        testCompany = new Company(1L, "Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
        testCompanyDto = new CompanyDto();
        testCompanyDto.setName("Romaguera-Crona");
        testCompanyDto.setCatchPhrase("Multi-layered client-server neural-net");
        testCompanyDto.setBs("harness real-time e-markets");
        testUser = new User(1L, "Leanne Graham", "Bret", "Sincere@april.biz", testAddress, "1-770-736-8031 x56442", "hildegard.org", testCompany);
        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setName("Leanne Graham");
        testUserDto.setUsername("Bret");
        testUserDto.setEmail("Sincere@april.biz");
        testUserDto.setAddress(testAddressDto);
        testUserDto.setPhone("1-770-736-8031 x56442");
        testUserDto.setWebsite("hildegard.org");
        testUserDto.setCompany(testCompanyDto);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(testUserDto, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals(testUserDto, found);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_WhenUserDoesNotExist_ShouldCreateUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userMapper.toEntity(testUserDto)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto created = userService.createUser(testUserDto);

        assertNotNull(created);
        assertEquals(testUserDto, created);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_WhenEmailExists_ShouldThrowException() {
        when(userRepository.findByEmail(testUserDto.getEmail())).thenReturn(testUser);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(testUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userMapper.toEntity(testUserDto)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto updated = userService.updateUser(1L, testUserDto);

        assertNotNull(updated);
        assertEquals(testUserDto, updated);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, testUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).deleteById(anyLong());
    }
} 