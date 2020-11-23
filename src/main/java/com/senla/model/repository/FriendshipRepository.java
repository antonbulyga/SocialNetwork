package com.senla.model.repository;

import com.senla.model.entity.Friendship;
import com.senla.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into friendship (user_one_id, user_two_id, action_user_id, status) VALUES (:userOne, :userTwo, :actionUser, 'REQUEST')",
            nativeQuery = true)
    void sentNewFriendRequest(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship set status = 'REQUEST', action_user_id = :actionUser where user_one_id = :userOne and user_two_id = :userTwo ",
            nativeQuery = true)
    void sentFriendRequest(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship set status = 'FRIEND', action_user_id = :actionUser where user_one_id = :userOne and user_two_id = :userTwo " +
            "and status = 'REQUEST'",
            nativeQuery = true)
    void addToFriends(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "select * from friendship where user_one_id = :userId or user_two_id = :userId and status = 'FRIEND'",
            nativeQuery = true)
    List<Friendship> getFriendsListOfUser(@Param("userId") User userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship set status = 'DECLINED', action_user_id = :actionUser where user_one_id = :userOne and user_two_id = :userTwo",
            nativeQuery = true)
    void deleteFriendship(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser );

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship set status = 'BLOCKED', action_user_id = :actionUser where user_one_id = :userOne and user_two_id = :userTwo",
            nativeQuery = true)
    void blockUser(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "update friendship set status = 'DECLINED', action_user_id = :actionUser where user_one_id = :userOne and user_two_id = :userTwo and status = 'BLOCKED'",
            nativeQuery = true)
    void unblockUser(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("actionUser") User actionUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "select * from friendship where (user_one_id = :userId or user_two_id = :userId) and status = 'REQUEST'",
            nativeQuery = true)
    List<Friendship> getRequests(@Param("userId") User userId);

}
