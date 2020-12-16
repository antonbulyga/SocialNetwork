package com.senla.facade;

import com.senla.converters.profile.ReverseProfileDTOConverter;
import com.senla.converters.profile.ProfileForUserDtoToProfile;
import com.senla.converters.profile.ProfileDTOConverter;
import com.senla.dto.profile.ProfileDto;
import com.senla.dto.profile.ProfileForUserDto;
import com.senla.entity.Profile;
import com.senla.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileFacade {

    private final ProfileService profileService;
    private final ProfileDTOConverter profileDTOConverter;
    private final ReverseProfileDTOConverter reverseProfileDTOConverter;
    private final ProfileForUserDtoToProfile profileForUserDtoToProfile;

    @Autowired
    public ProfileFacade(ProfileService profileService, ProfileDTOConverter profileDTOConverter, ReverseProfileDTOConverter reverseProfileDTOConverter, ProfileForUserDtoToProfile profileForUserDtoToProfile) {
        this.profileService = profileService;
        this.profileDTOConverter = profileDTOConverter;
        this.reverseProfileDTOConverter = reverseProfileDTOConverter;
        this.profileForUserDtoToProfile = profileForUserDtoToProfile;
    }

    public ProfileDto addProfile(ProfileDto profileDto) {
        profileService.addProfile(reverseProfileDTOConverter.convert(profileDto));
        return profileDto;
    }

    public ProfileDto updateProfile(ProfileDto profileDto) {
        profileService.updateProfile(reverseProfileDTOConverter.convert(profileDto));
        return profileDto;
    }

    public List<ProfileDto> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByCity(String city) {
        List<Profile> profiles = profileService.findProfilesByCity(city);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByCountry(String country) {
        List<Profile> profiles = profileService.findProfilesByCountry(country);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByFirstName(String firstName) {
        List<Profile> profiles = profileService.findProfilesByFirstName(firstName);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByLastName(String lastName) {
        List<Profile> profiles = profileService.findProfilesByLastName(lastName);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public ProfileDto findProfileByFirstNameAndLastName(String firstName, String LastName) {
        return profileDTOConverter.convert(profileService.findProfileByFirstNameAndLastName(firstName, LastName));
    }


    public List<ProfileDto> findProfileByGender(Enum gender) {
        List<Profile> profiles = profileService.findProfilesByGender(gender);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
    }

    public ProfileDto getProfile(Long id) {
        return profileDTOConverter.convert(profileService.getProfile(id));
    }

    public ProfileDto findProfileDtoByUser_Id(Long userId) {
        return profileDTOConverter.convert(profileService.findProfileByUser_Id(userId));
    }

    public Profile findProfileByUser_Id(Long userId) {
        return profileService.findProfileByUser_Id(userId);
    }

    public Profile convertProfileFromProfileDto(ProfileDto profileDto) {
        return reverseProfileDTOConverter.convert(profileDto);
    }

    public ProfileDto convertProfileDtoFromProfile(Profile profile) {
        return profileDTOConverter.convert(profile);
    }
}
