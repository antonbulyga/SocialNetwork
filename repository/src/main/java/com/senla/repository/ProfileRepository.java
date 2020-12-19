package com.senla.repository;

import com.senla.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByCity(String city);

    List<Profile> findByCountry(String country);

    List<Profile> findByFirstName(String firstName);

    List<Profile> findByLastName(String lastName);

    List<Profile> findByFirstNameAndLastName(String firstName, String LastName);

    List<Profile> findByGender(Enum gender);

    List<Profile> findAll();

    Profile findByUser_Id(Long userId);

}
