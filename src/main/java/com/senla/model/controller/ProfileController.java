package com.senla.model.controller;

import com.senla.model.converters.ProfileDtoToProfile;
import com.senla.model.converters.ProfileToProfileDto;
import com.senla.model.dto.ProfileDto;
import com.senla.model.entity.Profile;
import com.senla.model.entity.User;
import com.senla.model.enumeration.Gender;
import com.senla.model.service.ProfileService;
import com.senla.model.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/profiles/")
@Slf4j
public class ProfileController {
    private ProfileService profileService;
    private ProfileToProfileDto profileToProfileDto;
    private ProfileDtoToProfile profileDtoToProfile;
    private UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, ProfileToProfileDto profileToProfileDto,
                             ProfileDtoToProfile profileDtoToProfile, UserService userService) {
        this.profileService = profileService;
        this.profileToProfileDto = profileToProfileDto;
        this.profileDtoToProfile = profileDtoToProfile;
        this.userService = userService;
    }

    @PostMapping(value = "add")
    public ResponseEntity<ProfileDto> addProfile(@RequestBody ProfileDto profileDto) {
        Profile profile = profileDtoToProfile.convert(profileDto);
        profileService.addProfile(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteProfile() {
        User user = userService.getUserFromSecurityContext();
        Profile profile = profileService.findProfileByUser_Id(user.getId());
        profileService.deleteProfiles(profile.getId());
        return ResponseEntity.ok()
                .body("You have deleted the profile successfully");
    }

    @PostMapping(value = "update")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto profileDto) {
        Profile profile = profileDtoToProfile.convert(profileDto);
        User user = userService.getUserFromSecurityContext();
        if(user.getProfile().equals(profile)){
            profileService.updateProfile(profile);
        }
        else {
            log.error("You are trying to update someone else's profile");
            return null;
        }

        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/user")
    public ResponseEntity<ProfileDto> findMyProfileByUserId() {
        User user = userService.getUserFromSecurityContext();
        Profile profile = profileService.findProfileByUser_Id(user.getId());
        if(profile == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ProfileDto profileDto = profileToProfileDto.convert(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/city")
    public ResponseEntity<List<ProfileDto>> findProfileByCity(@RequestParam(name = "city") String city){
        List<Profile> profiles = profileService.findProfileByCity(city);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return  new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/country")
    public ResponseEntity<List<ProfileDto>> findProfileByCountry(@RequestParam(name = "country") String country){
        List<Profile> profiles = profileService.findProfileByCountry(country);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return  new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/name")
    public ResponseEntity<List<ProfileDto>> findProfileByFirstName(@RequestParam(name = "name") String firstName){
        List<Profile> profiles = profileService.findProfileByFirstName(firstName);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return  new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/surname")
    public ResponseEntity<List<ProfileDto>> findProfileByLastName(@RequestParam(name = "surname") String lastName){
        List<Profile> profiles = profileService.findProfileByLastName(lastName);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return  new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/fullname")
    public ResponseEntity<List<ProfileDto>> findProfileByLastName(@RequestParam(name = "name") String firstName, @PathVariable(name = "surname") String lastName){
        List<Profile> profiles = (List<Profile>) profileService.findProfileByFirstNameAndLastName(firstName, lastName);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/gender")
    public ResponseEntity<List<ProfileDto>> findProfileByGender(@RequestParam(name = "gender") String genderString){
        Enum gender = Gender.valueOf(genderString);
        List<Profile> profiles = profileService.findProfileByGender(gender);
        List<ProfileDto> profileDtoList = profiles.stream().map(profile -> profileToProfileDto.convert(profile)).collect(Collectors.toList());
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable(name = "id") Long profileId) {
        Profile profile = profileService.getProfile(profileId);
        ProfileDto profileDto = profileToProfileDto.convert(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }
}

