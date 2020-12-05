package com.senla.controller;

import com.senla.dto.ProfileDto;
import com.senla.entity.Profile;
import com.senla.entity.User;
import com.senla.enumeration.Gender;
import com.senla.exception.RestError;
import com.senla.facade.ProfileFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        List<ProfileDto> dtoList = profileFacade.getAllProfiles();
        log.info("Getting all profiles");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "add")
    public ResponseEntity<ProfileDto> addProfile(@Valid @RequestBody ProfileDto profileDto) {
        profileFacade.addProfile(profileDto);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "update")
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody ProfileDto profileDto) {
        User user = userFacade.getUserFromSecurityContext();
        Profile profileFromSecurityContext = user.getProfile();
        Profile profile = profileFacade.convertProfileFromProfileDto(profileDto);
        if (profileFromSecurityContext.getId().equals(profile.getId())) {
            profileFacade.updateProfile(profileDto);
            log.info("You have updated profile successfully");
            return new ResponseEntity<>(profileDto, HttpStatus.OK);

        } else {
            log.warn("You are trying to update someone else's profile");
            throw new RestError("You are trying to update someone else's profile");
        }

    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/user")
    public ResponseEntity<ProfileDto> findMyProfileByUserId() {
        User user = userFacade.getUserFromSecurityContext();
        ProfileDto profileDto = profileFacade.findProfileDtoByUser_Id(user.getId());
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/city")
    public ResponseEntity<List<ProfileDto>> findProfileByCity(@RequestParam(name = "city") String city) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByCity(city);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/country")
    public ResponseEntity<List<ProfileDto>> findProfileByCountry(@RequestParam(name = "country") String country) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByCountry(country);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/name")
    public ResponseEntity<List<ProfileDto>> findProfileByFirstName(@RequestParam(name = "name") String firstName) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByFirstName(firstName);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/surname")
    public ResponseEntity<List<ProfileDto>> findProfileByLastName(@RequestParam(name = "surname") String lastName) {
        List<ProfileDto> profileDtoList = profileFacade.findProfilesByLastName(lastName);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/fullname")
    public ResponseEntity<ProfileDto> findProfileByLastName(@RequestParam(name = "name") String firstName, @RequestParam(name = "surname") String lastName) {
        ProfileDto profileDto = profileFacade.findProfileByFirstNameAndLastName(firstName, lastName);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping(value = "search/gender")
    public ResponseEntity<List<ProfileDto>> findProfileByGender(@RequestParam(name = "gender") String genderString) {
        Enum gender = Gender.valueOf(genderString);
        List<ProfileDto> profileDtoList = profileFacade.findProfileByGender(gender);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable(name = "id") Long profileId) {
        ProfileDto profileDto = profileFacade.getProfile(profileId);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }
}

