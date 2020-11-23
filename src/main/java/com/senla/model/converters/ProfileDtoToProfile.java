
package com.senla.model.converters;

import com.senla.model.dto.ProfileDto;
import com.senla.model.entity.Profile;
import com.senla.model.entity.User;
import com.senla.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileDtoToProfile implements Converter<ProfileDto, Profile> {
    private UserService userService;

    @Autowired
    public ProfileDtoToProfile(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Profile convert(ProfileDto profileDto) {
        User user = null;
        return Profile.builder()
                .id(profileDto.getId())
                .dateOfBirth(profileDto.getDateOfBirth())
                .gender(profileDto.getGender())
                .phoneNumber(profileDto.getPhoneNumber())
                .firstName(profileDto.getFirstName())
                .lastName(profileDto.getLastName())
                .user(profileDto.getUser() == null ? user : userService.getUser(profileDto.getUser().getId()))
                .city(profileDto.getCity())
                .country(profileDto.getCountry())
                .build();
    }
}

