package com.senla.controller;

import com.senla.converters.CommunityDtoToCommunity;
import com.senla.converters.CommunityToCommunityDto;
import com.senla.dto.CommunityDto;
import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.CommunityFacade;
import com.senla.facade.UserFacade;
import com.senla.service.CommunityService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/communities/")
@Slf4j
public class CommunityController {

    private final CommunityFacade communityFacade;
    private final UserFacade userFacade;

    @Autowired
    public CommunityController(CommunityFacade communityFacade, UserFacade userFacade) {
        this.communityFacade = communityFacade;
        this.userFacade = userFacade;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        List<CommunityDto> communityDtoList = communityFacade.getAllCommunities();
        if (communityDtoList == null) {
            log.info("No communities");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<CommunityDto> addCommunity(@RequestBody CommunityDto communityDto) {
        communityFacade.addCommunity(communityDto);
        log.info("Adding community");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteCommunity(@RequestParam(name = "id") long id) throws RestError {
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

    @GetMapping(value = "{id}")
    public ResponseEntity<CommunityDto> getCommunityById(@PathVariable(name = "id") Long id) {
        CommunityDto communityDto = communityFacade.getDtoCommunity(id);
        log.info("Getting community by id");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<CommunityDto> updateCommunity(@RequestBody CommunityDto communityDto) throws RestError {
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

    @GetMapping(value = "search/name")
    public ResponseEntity<CommunityDto> getCommunityByName(@PathVariable(name = "name") String name) {
        CommunityDto communityDto = communityFacade.getCommunityByName(name);
        log.info("Getting community by name");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/admin")
    public ResponseEntity<List<CommunityDto>> getCommunitiesByAdminUser_Id(@PathVariable(name = "adminId") Long adminId) {
        List<CommunityDto> communityDtoList = communityFacade.getCommunitiesByAdminUserId(adminId);
        log.info("Getting community by admin user");
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }
}
