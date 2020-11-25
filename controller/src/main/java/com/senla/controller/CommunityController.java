package com.senla.controller;

import com.senla.converters.CommunityDtoToCommunity;
import com.senla.converters.CommunityToCommunityDto;
import com.senla.dto.CommunityDto;
import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.service.CommunityService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/communities/")
public class CommunityController {

    private CommunityService communityService;
    private CommunityToCommunityDto communityToCommunityDto;
    private CommunityDtoToCommunity communityDtoToCommunity;
    private UserService userService;

    @Autowired
    public CommunityController(CommunityService communityService, CommunityToCommunityDto communityToCommunityDto,
                               CommunityDtoToCommunity communityDtoToCommunity, UserService userService) {
        this.communityService = communityService;
        this.communityToCommunityDto = communityToCommunityDto;
        this.communityDtoToCommunity = communityDtoToCommunity;
        this.userService = userService;
    }


    @GetMapping(value = "")
    public ResponseEntity<List<CommunityDto>> getAllCommunities(){
        List<Community> communityList = communityService.getAll();
        List<CommunityDto> communityDtoList = new ArrayList<>();
        for (int i = 0; i < communityList.size(); i++) {
            CommunityDto result = communityToCommunityDto.convert(communityList.get(i));
            communityDtoList.add(result);
        }
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<CommunityDto> addCommunity(@RequestBody CommunityDto communityDto) {
        Community community = communityDtoToCommunity.convert(communityDto);
        communityService.addCommunity(community);
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteCommunity(@RequestParam (name = "id") long id) {
        User user = userService.getUserFromSecurityContext();
        Community community = communityService.getCommunity(id);
        User adminUser = community.getAdminUser();
        if(user.equals(adminUser)){
            communityService.delete(id);
            return ResponseEntity.ok()
                    .body("You have deleted community successfully");
        }
        else {
            throw new RestError("You are trying to delete a group that you do not represent as an administrator");
        }

    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CommunityDto> getCommunityById(@PathVariable(name = "id") Long id) {
        Community community = communityService.getCommunity(id);
        CommunityDto communityDto = communityToCommunityDto.convert(community);
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<CommunityDto> updateCommunity(@RequestBody CommunityDto communityDto) {
        User user = userService.getUserFromSecurityContext();
        Community community = communityDtoToCommunity.convert(communityDto);
        User adminUser = community.getAdminUser();
        if(user.equals(adminUser)){
            communityService.editCommunity(community);
            return new ResponseEntity<>(communityDto, HttpStatus.OK);
        }
        else {
            throw new RestError("You are trying to update a group that you do not represent as an administrator");
        }

    }

    @GetMapping(value = "search/name")
    public ResponseEntity<CommunityDto> getCommunityByName(@PathVariable(name = "name") String name) {
        Community community = communityService.getCommunitiesByName(name);
        CommunityDto communityDto = communityToCommunityDto.convert(community);
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/admin")
    public ResponseEntity<List<CommunityDto>> getCommunitiesByAdminUser_Id(@PathVariable(name = "adminId") Long adminId) {
        List<Community> communities = communityService.getCommunitiesByAdminUserId(adminId);
        List<CommunityDto> communityDtoList = communities.stream().map(community -> communityToCommunityDto.convert(community)).collect(Collectors.toList());
        return new ResponseEntity<>(communityDtoList, HttpStatus.OK);
    }
}
