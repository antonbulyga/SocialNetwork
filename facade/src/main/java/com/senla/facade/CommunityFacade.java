package com.senla.facade;

import com.senla.converters.community.ReverseCommunityDTOConverter;
import com.senla.converters.community.CommunityDTOConverter;
import com.senla.dto.community.CommunityDto;
import com.senla.entity.Community;
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
        communityService.addCommunity(reverseCommunityDTOConverter.convert(communityDto));
        return communityDto;
    }

    public CommunityDto addCommunity(Community community) {
        communityService.addCommunity(community);
        return communityDTOConverter.convert(community);
    }

    public void deleteCommunity(long id) {
        communityService.deleteCommunity(id);
    }

    public CommunityDto updateCommunity(CommunityDto communityDto) {
        Community community = reverseCommunityDTOConverter.convert(communityDto);
        communityService.updateCommunity(community);
        return communityDto;
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


    public CommunityDto getCommunityByName(String name) {
        Community community = communityService.getCommunityByName(name);
        return communityDTOConverter.convert(community);
    }

    public List<CommunityDto> getCommunitiesByAdminUserId(Long adminId) {
        List<Community> communities = communityService.getCommunitiesByAdminUserId(adminId);
        return communities.stream().map(communityDTOConverter::convert).collect(Collectors.toList());
    }

    public Community convertCommunityDtoToCommunity(CommunityDto communityDto) {
        return reverseCommunityDTOConverter.convert(communityDto);
    }
}
