
package com.senla.converters.profile;

import com.senla.dto.profile.ProfileDto;
import com.senla.entity.Profile;
import com.senla.entity.User;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReverseProfileDTOConverter implements Converter<ProfileDto, Profile> {

    private UserService userService;

    @Autowired
    public ReverseProfileDTOConverter(UserService userService) {
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

