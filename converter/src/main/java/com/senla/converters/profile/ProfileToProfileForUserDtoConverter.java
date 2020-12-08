package com.senla.converters.profile;

import com.senla.dto.profile.ProfileForUserDto;
import com.senla.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ProfileToProfileForUserDtoConverter implements Converter<Profile, ProfileForUserDto> {

    @Override
    public ProfileForUserDto convert(Profile profile) {
        return ProfileForUserDto.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .city(profile.getCity())
                .country(profile.getCountry())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }
}
