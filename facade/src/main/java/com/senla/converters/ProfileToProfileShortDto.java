package com.senla.converters;

import com.senla.dto.ProfileShortDto;
import com.senla.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ProfileToProfileShortDto implements Converter<Profile, ProfileShortDto> {
    @Override
    public ProfileShortDto convert(Profile profile) {
        return ProfileShortDto.builder()
                .id(profile.getId())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .phoneNumber(profile.getPhoneNumber())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .city(profile.getCity())
                .country(profile.getCountry())
                .build();
    }
}
