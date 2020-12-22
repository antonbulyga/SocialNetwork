package com.senla.facade;

import com.senla.converters.community.ReverseCommunityDTOConverter;
import com.senla.converters.community.CommunityDTOConverter;
import com.senla.dto.community.CommunityDto;
import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommunityFacade {

    private final CommunityService communityService;
    private final CommunityDTOConverter communityDTOConverter;
    private final ReverseCommunityDTOConverter reverseCommunityDTOConverter;

    @Autowired
    public CommunityFacade(CommunityService communityService, CommunityDTOConverter communityDTOConverter,
                           ReverseCommunityDTOConverter reverseCommunityDTOConverter) {
        this.communityService = communityService;
        this.communityDTOConverter = communityDTOConverter;
        this.reverseCommunityDTOConverter = reverseCommunityDTOConverter;
    }

    public CommunityDto addCommunity(CommunityDto communityDto) {
        Community communityWithDetails = communityService.addCommunity(reverseCommunityDTOConverter.convert(communityDto));
        return communityDTOConverter.convert(communityWithDetails);
    }

    public CommunityDto addCommunity(Community community) {
        Community communityWithData = communityService.addCommunity(community);
        return communityDTOConverter.convert(communityWithData);
    }

    public void deleteCommunity(long id) {
        communityService.deleteCommunity(id);
    }

    public CommunityDto updateCommunity(CommunityDto communityDto) {
        Community community = reverseCommunityDTOConverter.convert(communityDto);
        Community communityWithDetails = communityService.updateCommunity(community);
        return communityDTOConverter.convert(communityWithDetails);
    }

    public List<CommunityDto> getAllCommunities() {
        List<Community> communities = communityService.getAllCommunities();
        return communities.stream().map(communityDTOConverter::convert).collect(Collectors.toList());
    }

    public CommunityDto addUserToCommunity(Long communityId, Long userId) {
        Community community = communityService.addUserToCommunity(communityId, userId);
        return communityDTOConverter.convert(community);
    }

    public void removeUserFromCommunity(Long communityId, Long userId) {
        communityService.removeUserFromCommunity(communityId, userId);
    }

    public CommunityDto getDtoCommunity(Long id) {
        return communityDTOConverter.convert(communityService.getCommunity(id));
    }

    public Community getCommunity(Long id) {
        return communityService.getCommunity(id);
    }


    public List<CommunityDto> getCommunityByName(String name) {
        List<Community> communities = communityService.getCommunityByName(name);
        return communities.stream().map(communityDTOConverter::convert).collect(Collectors.toList());
    }

    public List<CommunityDto> getCommunitiesByAdminUserId(Long adminId) {
        List<Community> communities = communityService.getCommunitiesByAdminUserId(adminId);
        return communities.stream().map(communityDTOConverter::convert).collect(Collectors.toList());
    }

    public Community convertCommunityDtoToCommunity(CommunityDto communityDto) {
        return reverseCommunityDTOConverter.convert(communityDto);
    }

    public int communityParticipateCheck(Long communityId, Long userId) {
        Community community = getCommunity(communityId);
        List<User> users = community.getUsers();
        int count = 0;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                count++;
            }
        }
        return count;
    }
}
