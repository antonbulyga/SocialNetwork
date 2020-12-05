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
                .firstName(profile.getFirstName())
                .build();
    }
}
