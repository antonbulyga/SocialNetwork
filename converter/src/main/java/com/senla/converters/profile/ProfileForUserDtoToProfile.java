package com.senla.converters.profile;

import com.senla.dto.profile.ProfileForUserDto;
import com.senla.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileForUserDtoToProfile implements Converter<ProfileForUserDto, Profile> {
    @Override
    public Profile convert(ProfileForUserDto profileForUserDto) {
        return Profile.builder()
                .id(profileForUserDto.getId())
                .city(profileForUserDto.getCity())
                .country(profileForUserDto.getCountry())
                .dateOfBirth(profileForUserDto.getDateOfBirth())
                .firstName(profileForUserDto.getFirstName())
                .gender(profileForUserDto.getGender())
                .lastName(profileForUserDto.getLastName())
                .phoneNumber(profileForUserDto.getPhoneNumber())
                .user(null)
                .build();
    }
}
