package com.senla.facade;

import com.senla.converters.ProfileDtoToProfile;
import com.senla.converters.ProfileToProfileDto;
import com.senla.dto.ProfileDto;
import com.senla.entity.Profile;
import com.senla.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileFacade {

    private final ProfileService profileService;
    private final ProfileToProfileDto profileToProfileDto;
    private final ProfileDtoToProfile profileDtoToProfile;

    @Autowired
    public ProfileFacade(ProfileService profileService, ProfileToProfileDto profileToProfileDto, ProfileDtoToProfile profileDtoToProfile) {
        this.profileService = profileService;
        this.profileToProfileDto = profileToProfileDto;
        this.profileDtoToProfile = profileDtoToProfile;
    }

    public ProfileDto addProfile(ProfileDto profileDto){
        profileService.addProfile(profileDtoToProfile.convert(profileDto));
        return profileDto;
    }

    public void deleteProfiles(long id){
        profileService.deleteProfiles(id);
    }

    public ProfileDto updateProfile(ProfileDto profileDto){
        Profile profile = profileDtoToProfile.convert(profileDto);
        profileService.updateProfile(profile);
        return profileDto;
    }

    public List<ProfileDto> getAllProfiles(){
        List<Profile> profiles = profileService.getAllProfiles();
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByCity(String city){
        List<Profile> profiles = profileService.findProfilesByCity(city);
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByCountry(String country){
        List<Profile> profiles = profileService.findProfilesByCountry(country);
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByFirstName(String firstName){
        List<Profile> profiles = profileService.findProfilesByFirstName(firstName);
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public List<ProfileDto> findProfilesByLastName(String lastName){
        List<Profile> profiles = profileService.findProfilesByLastName(lastName);
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public ProfileDto findProfileByFirstNameAndLastName(String firstName, String LastName){
        return profileToProfileDto.convert(profileService.findProfileByFirstNameAndLastName(firstName, LastName));
    }


    public List<ProfileDto> findProfileByGender(Enum gender){
        List<Profile> profiles = profileService.findProfileByGender(gender);
        return profiles.stream().map(p -> profileToProfileDto.convert(p)).collect(Collectors.toList());
    }

    public ProfileDto getProfile(Long id){
        return profileToProfileDto.convert(profileService.getProfile(id));
    }

    public ProfileDto findProfileDtoByUser_Id(Long userId){
        return profileToProfileDto.convert(profileService.findProfileByUser_Id(userId));
    }

    public Profile findProfileByUser_Id(Long userId){
        return profileService.findProfileByUser_Id(userId);
    }

    public Profile convertProfileFromProfileDto(ProfileDto profileDto){
        return profileDtoToProfile.convert(profileDto);
    }

    public ProfileDto convertProfileDtoFromProfile(Profile profile){
        return profileToProfileDto.convert(profile);
    }
}
