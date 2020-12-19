package com.senla.facade;

import com.senla.converters.profile.ProfileDTOConverter;
import com.senla.converters.profile.ReverseProfileDTOConverter;
import com.senla.dto.profile.ProfileDto;
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

    @Autowired
    public ProfileFacade(ProfileService profileService, ProfileDTOConverter profileDTOConverter, ReverseProfileDTOConverter reverseProfileDTOConverter) {
        this.profileService = profileService;
        this.profileDTOConverter = profileDTOConverter;
        this.reverseProfileDTOConverter = reverseProfileDTOConverter;
    }

    public ProfileDto addProfile(ProfileDto profileDto) {
        Profile profile = profileService.addProfile(reverseProfileDTOConverter.convert(profileDto));
        return profileDTOConverter.convert(profile);
    }

    public ProfileDto updateProfile(ProfileDto profileDto) {
        Profile profile = profileService.updateProfile(reverseProfileDTOConverter.convert(profileDto));
        return profileDTOConverter.convert(profile);
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

    public List<ProfileDto> findProfileByFirstNameAndLastName(String firstName, String LastName) {
        List<Profile> profiles = profileService.findProfileByFirstNameAndLastName(firstName, LastName);
        return profiles.stream().map(profileDTOConverter::convert).collect(Collectors.toList());
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
