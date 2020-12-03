package com.senla.service;

import com.senla.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile addProfile(Profile profile);

    void deleteProfile(Long id);

    Profile updateProfile(Profile profile);

    List<Profile> getAllProfiles();

    List<Profile> findProfilesByCity(String city);

    List<Profile> findProfilesByCountry(String country);

    List<Profile> findProfilesByFirstName(String firstName);

    List<Profile> findProfilesByLastName(String lastName);

    Profile findProfileByFirstNameAndLastName(String firstName, String LastName);

    List<Profile> findProfileByGender(Enum gender);

    Profile getProfile(Long id);

    Profile findProfileByUser_Id(Long userId);
}
