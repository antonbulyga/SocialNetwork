package com.senla.controller;

import com.senla.dto.ProfileDto;
import com.senla.entity.Profile;
import com.senla.entity.User;
import com.senla.enumeration.Gender;
import com.senla.facade.ProfileFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/profiles/")
@Slf4j
public class ProfileController {

    private final ProfileFacade profileFacade;
    private final UserFacade userFacade;

    @Autowired
    public ProfileController(ProfileFacade profileFacade, UserFacade userFacade) {
        this.profileFacade = profileFacade;
        this.userFacade = userFacade;
    }

    @PostMapping(value = "add")
    public ResponseEntity<ProfileDto> addProfile(@RequestBody ProfileDto profileDto) {
        profileFacade.addProfile(profileDto);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteProfile() {
        User user = userFacade.getUserFromSecurityContext();
        Profile profile = profileFacade.findProfileByUser_Id(user.getId());
        profileFacade.deleteProfiles(profile.getId());
        return ResponseEntity.ok()
                .body("You have deleted the profile successfully");
    }

    @PutMapping(value = "update")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto profileDto) {
        User user = userFacade.getUserFromSecurityContext();
        Profile profile = profileFacade.convertProfileFromProfileDto(profileDto);
        if (user.getProfile().equals(profile)) {
            profileFacade.updateProfile(profileDto);
        } else {
            log.error("You are trying to update someone else's profile");
            return null;
        }

        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/user")
    public ResponseEntity<ProfileDto> findMyProfileByUserId() {
        User user = userFacade.getUserFromSecurityContext();
        ProfileDto profileDto = profileFacade.findProfileDtoByUser_Id(user.getId());
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/city")
    public ResponseEntity<List<ProfileDto>> findProfileByCity(@RequestParam(name = "city") String city) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByCity(city);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/country")
    public ResponseEntity<List<ProfileDto>> findProfileByCountry(@RequestParam(name = "country") String country) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByCountry(country);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/name")
    public ResponseEntity<List<ProfileDto>> findProfileByFirstName(@RequestParam(name = "name") String firstName) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByFirstName(firstName);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/surname")
    public ResponseEntity<List<ProfileDto>> findProfileByLastName(@RequestParam(name = "surname") String lastName) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByLastName(lastName);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "search/fullname")
    public ResponseEntity<ProfileDto> findProfileByLastName(@RequestParam(name = "name") String firstName, @PathVariable(name = "surname") String lastName) {
        ProfileDto profileDto = profileFacade.findProfileByFirstNameAndLastName(firstName, lastName);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/gender")
    public ResponseEntity<List<ProfileDto>> findProfileByGender(@RequestParam(name = "gender") String genderString) {
        Enum gender = Gender.valueOf(genderString);
        List<ProfileDto> profileDtoList = profileFacade.findProfileByGender(gender);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable(name = "id") Long profileId) {
        ProfileDto profileDto = profileFacade.getProfile(profileId);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }
}

