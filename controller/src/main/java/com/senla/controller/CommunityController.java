package com.senla.controller;

import com.senla.dto.community.CommunityDto;
import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.CommunityFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/communities")
@Slf4j
public class CommunityController {

    private final CommunityFacade communityFacade;
    private final UserFacade userFacade;

    @Autowired
    public CommunityController(CommunityFacade communityFacade, UserFacade userFacade) {
        this.communityFacade = communityFacade;
        this.userFacade = userFacade;
    }

    /**
     * Get all communities
     *
     * @return list of the communities dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "")
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        List<CommunityDto> communityDtoList = communityFacade.getAllCommunities();
        if (communityDtoList == null) {
            log.info("No communities");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }

    /**
     * Add community
     *
     * @param communityDto
     * @return community dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/add")
    public ResponseEntity<CommunityDto> addCommunity(@Valid @RequestBody CommunityDto communityDto) {
        User user = userFacade.getUserFromSecurityContext();
        Community community = communityFacade.convertCommunityDtoToCommunity(communityDto);
        User adminUser = community.getAdminUser();
        if (user.equals(adminUser)) {
            communityFacade.addCommunity(community);
            log.info("Adding community");
            return new ResponseEntity<>(communityDto, HttpStatus.OK);
        } else {
            log.error("Getting community by id");
            throw new RestError("You are trying to add a group that you do not represent as an administrator");
        }
    }

    /**
     * Join the community as user
     *
     * @param communityId
     * @return
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/join")
    public ResponseEntity<CommunityDto> joinToCommunity(@RequestParam(name = "communityId") Long communityId) {
        User user = userFacade.getUserFromSecurityContext();
        Community community = communityFacade.getCommunity(communityId);
        List<User> users = community.getUsers();
        int count = 0;
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                count++;
            }
        }
        if (count > 0) {
            log.error("The user is already in the group");
            throw new RestError("The user is already in the group");
        } else {
            log.info("Adding user to the community");
            CommunityDto communityDto = communityFacade.addUserToCommunity(communityId, user.getId());
            return new ResponseEntity<>(communityDto, HttpStatus.OK);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/leave")
    public ResponseEntity<String> leaveFromCommunity(@RequestParam(name = "communityId") Long communityId) {
        User user = userFacade.getUserFromSecurityContext();
        Community community = communityFacade.getCommunity(communityId);
        List<User> users = community.getUsers();
        int count = 0;
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                count++;
            }
        }
        if (count > 0) {
            log.info("Leaving from the community");
            communityFacade.removeUserFromCommunity(communityId, user.getId());
            return ResponseEntity.ok()
                    .body("You have left from the community successfully");
        } else {
            log.error("The user does not participate in the group");
            throw new RestError("The user does not participate in the group");
        }
    }

    /**
     * Delete community
     *
     * @param id community id
     * @return response as a string
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteCommunity(@RequestParam(name = "id") long id) {
        User user = userFacade.getUserFromSecurityContext();
        Community community = communityFacade.getCommunity(id);
        User adminUser = community.getAdminUser();
        if (user.equals(adminUser)) {
            communityFacade.deleteCommunity(id);
            log.info("Deleting community");
            return ResponseEntity.ok()
                    .body("You have deleted community successfully");
        } else {
            log.error("You are trying to delete a group that you do not represent as an administrator");
            throw new RestError("You are trying to delete a group that you do not represent as an administrator");
        }

    }

    /**
     * Get community be id
     *
     * @param id community id
     * @return community dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public ResponseEntity<CommunityDto> getCommunityById(@PathVariable(name = "id") Long id) {
        CommunityDto communityDto = communityFacade.getDtoCommunity(id);
        log.info("Getting community by id");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    /**
     * Update community
     *
     * @param communityDto
     * @return community dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "/update")
    public ResponseEntity<CommunityDto> updateCommunity(@Valid @RequestBody CommunityDto communityDto) {
        User user = userFacade.getUserFromSecurityContext();
        Community community = communityFacade.convertCommunityDtoToCommunity(communityDto);
        User adminUser = community.getAdminUser();
        if (user.equals(adminUser)) {
            communityFacade.updateCommunity(communityDto);
            log.info("Updating community");
            return new ResponseEntity<>(communityDto, HttpStatus.OK);
        } else {
            log.error("Getting community by id");
            throw new RestError("You are trying to update a group that you do not represent as an administrator");
        }
    }

    /**
     * Get community by name
     *
     * @param name community name
     * @return community dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/search/name")
    public ResponseEntity<CommunityDto> getCommunityByName(@RequestParam(name = "name") String name) {
        CommunityDto communityDto = communityFacade.getCommunityByName(name);
        log.info("Getting community by name");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    /**
     * Get list of the communities by admin user id
     *
     * @param adminId admin user id
     * @return list of the community dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/search/admin")
    public ResponseEntity<List<CommunityDto>> getCommunitiesByAdminUser_Id(@RequestParam(name = "adminId") Long adminId) {
        List<CommunityDto> communityDtoList = communityFacade.getCommunitiesByAdminUserId(adminId);
        log.info("Getting community by admin user");
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }
}
