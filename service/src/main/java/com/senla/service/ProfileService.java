package com.senla.service;

import com.senla.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile addProfile(Profile profile);
    void deleteProfiles(long id);
    Profile updateProfile(Profile profile);
    List<Profile> getAllProfiles();
    List<Profile> findProfileByCity(String city);
    List<Profile> findProfileByCountry(String country);
    List<Profile> findProfileByFirstName(String firstName);
    List<Profile> findProfileByLastName(String lastName);
    Profile findProfileByFirstNameAndLastName(String firstName, String LastName);
    List<Profile> findProfileByGender(Enum gender);
    Profile getProfile(Long id);
    Profile findProfileByUser_Id(Long userId);
}
