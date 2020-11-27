package com.senla.service.impl;

import com.senla.entity.Profile;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.ProfileRepository;
import com.senla.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(Long id) {
        log.info("Getting profile by id");
        return profileRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Profile with id = %s is not found", id)));
    }

    @Override
    public Profile findProfileByUser_Id(Long userId) {
        return profileRepository.findByUser_Id(userId);
    }

    @Override
    public Profile addProfile(Profile profile) {
        profile.getUser().setProfile(profile);
        profileRepository.save(profile);
        log.info("Adding profile");
        return profile;
    }

    @Override
    public void deleteProfile(long id) {
        getProfile(id);
        log.info("Deleting profile");
       profileRepository.deleteById(id);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        profile.getUser().setProfile(profile);
        profileRepository.save(profile);
        log.info("Saving profile");
        return profile;
    }

    @Override
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        log.info("Getting all profiles");
        return profiles;
    }

    @Override
    public List<Profile> findProfilesByCity(String city) {
       List<Profile> profiles = profileRepository.findByCity(city);
        log.info("Finding profile by city");
        return profiles;
    }

    @Override
    public List<Profile> findProfilesByCountry(String country) {
        List<Profile> profiles = profileRepository.findByCountry(country);
        log.info("Finding profile by country");
        return profiles;
    }

    @Override
    public List<Profile> findProfilesByFirstName(String firstName) {
        List<Profile> profiles = profileRepository.findByFirstName(firstName);
        log.info("Finding profile by first name");
        return profiles;
    }

    @Override
    public List<Profile> findProfilesByLastName(String lastName) {
        List<Profile> profiles = profileRepository.findByLastName(lastName);
        log.info("Finding profile by last name");
        return profiles;
    }

    @Override
    public Profile findProfileByFirstNameAndLastName(String firstName, String LastName) {
        Profile profile = profileRepository.findByFirstNameAndLastName(firstName, LastName);
        log.info("Finding profile by first name and last name");
        return profile;
    }

    @Override
    public List<Profile> findProfileByGender(Enum gender) {
        List<Profile> profiles = profileRepository.findByGender(gender);
        log.info("Finding profile by gender");
        return profiles;
    }
}
