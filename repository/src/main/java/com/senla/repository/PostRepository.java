package com.senla.repository;

import com.senla.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getPostByUser_Id(Long userId);
    List<Post> getPostByCommunity_Id(Long communityId);
}
