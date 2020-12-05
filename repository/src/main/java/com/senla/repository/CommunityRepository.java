package com.senla.repository;

import com.senla.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community getCommunityByName(String name);

    List<Community> getCommunitiesByAdminUser_Id(Long adminId);
}
