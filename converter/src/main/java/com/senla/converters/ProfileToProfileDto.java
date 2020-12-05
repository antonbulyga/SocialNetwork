package com.senla.converters;

import com.senla.dto.ProfileDto;
import com.senla.dto.UserShortDto;
import com.senla.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToProfileDto implements Converter<Profile, ProfileDto> {

    private UserToUserShortDto userToUserShortDto;

    @Autowired
    public ProfileToProfileDto(UserToUserShortDto userToUserShortDto) {
        this.userToUserShortDto = userToUserShortDto;
    }


    @Override
    public ProfileDto convert(Profile profile) {
        if (profile == null) {
            return null;
        }

        UserShortDto user = null;
        return ProfileDto.builder()
                .id(profile.getId())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .phoneNumber(profile.getPhoneNumber())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .city(profile.getCity())
                .country(profile.getCountry())
                .user(profile.getUser() == null ? user : userToUserShortDto.convert(profile.getUser()))
                .build();
    }
}
