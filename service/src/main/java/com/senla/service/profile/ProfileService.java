package com.senla.service.profile;

import com.senla.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile addProfile(Profile profile);

    Profile updateProfile(Profile profile);

    List<Profile> getAllProfiles();

    List<Profile> findProfilesByCity(String city);

    List<Profile> findProfilesByCountry(String country);

    List<Profile> findProfilesByFirstName(String firstName);

    List<Profile> findProfilesByLastName(String lastName);

    List<Profile> findProfileByFirstNameAndLastName(String firstName, String LastName);

    List<Profile> findProfilesByGender(Enum gender);

    Profile getProfile(Long id);

    Profile findProfileByUser_Id(Long userId);

}
