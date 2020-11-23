package com.senla.model.repository;

import com.senla.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    Community getCommunitiesByName(String name);
    List<Community> getCommunitiesByAdminUser_Id(Long adminId);
}
