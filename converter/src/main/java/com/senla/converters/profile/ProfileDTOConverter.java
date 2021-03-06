package com.senla.converters.profile;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.profile.ProfileDto;
import com.senla.dto.user.UserNestedDto;
import com.senla.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileDTOConverter implements Converter<Profile, ProfileDto> {

    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public ProfileDTOConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }


    @Override
    public ProfileDto convert(Profile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileDto.builder()
                .id(profile.getId())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .phoneNumber(profile.getPhoneNumber())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .city(profile.getCity())
                .country(profile.getCountry())
                .user(profile.getUser() == null ? null : userToUserNestedDtoConverter.convert(profile.getUser()))
                .build();
    }
}
