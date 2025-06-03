package com.innowise.mapper;

import com.innowise.dto.*;
import com.innowise.model.*;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setWebsite(user.getWebsite());

        if (user.getAddress() != null) {
            dto.setAddress(toAddressDto(user.getAddress()));
        }

        if (user.getCompany() != null) {
            dto.setCompany(toCompanyDto(user.getCompany()));
        }

        return dto;
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setWebsite(dto.getWebsite());

        if (dto.getAddress() != null) {
            user.setAddress(toAddressEntity(dto.getAddress()));
        }

        if (dto.getCompany() != null) {
            user.setCompany(toCompanyEntity(dto.getCompany()));
        }

        return user;
    }

    private AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }

        AddressDto dto = new AddressDto();
        dto.setStreet(address.getStreet());
        dto.setSuite(address.getSuite());
        dto.setCity(address.getCity());
        dto.setZipcode(address.getZipcode());

        if (address.getGeo() != null) {
            dto.setGeo(toGeoDto(address.getGeo()));
        }

        return dto;
    }

    private Address toAddressEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setSuite(dto.getSuite());
        address.setCity(dto.getCity());
        address.setZipcode(dto.getZipcode());

        if (dto.getGeo() != null) {
            address.setGeo(toGeoEntity(dto.getGeo()));
        }

        return address;
    }

    private GeoDto toGeoDto(Geo geo) {
        if (geo == null) {
            return null;
        }

        return new GeoDto(geo.getLat(), geo.getLng());
    }

    private Geo toGeoEntity(GeoDto dto) {
        if (dto == null) {
            return null;
        }

        return new Geo(null, dto.getLat(), dto.getLng());
    }

    private CompanyDto toCompanyDto(Company company) {
        if (company == null) {
            return null;
        }

        return new CompanyDto(company.getName(), company.getCatchPhrase(), company.getBs());
    }

    private Company toCompanyEntity(CompanyDto dto) {
        if (dto == null) {
            return null;
        }

        return new Company(null, dto.getName(), dto.getCatchPhrase(), dto.getBs());
    }
} 