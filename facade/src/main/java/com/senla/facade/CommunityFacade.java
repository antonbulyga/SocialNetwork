package com.senla.facade;

import com.senla.converters.CommunityDtoToCommunity;
import com.senla.converters.CommunityToCommunityDto;
import com.senla.dto.CommunityDto;
import com.senla.entity.Community;
import com.senla.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommunityFacade {

    private final CommunityService communityService;
    private final CommunityToCommunityDto communityToCommunityDto;
    private final CommunityDtoToCommunity communityDtoToCommunity;

    @Autowired
    public CommunityFacade(CommunityService communityService, CommunityToCommunityDto communityToCommunityDto,
                           CommunityDtoToCommunity communityDtoToCommunity) {
        this.communityService = communityService;
        this.communityToCommunityDto = communityToCommunityDto;
        this.communityDtoToCommunity = communityDtoToCommunity;
    }

    public CommunityDto addCommunity(CommunityDto communityDto){
        communityService.addCommunity(communityDtoToCommunity.convert(communityDto));
        return communityDto;
    }

    public void deleteCommunity(long id){
        communityService.deleteCommunity(id);
    }

    public CommunityDto updateCommunity(CommunityDto communityDto){
        Community community = communityDtoToCommunity.convert(communityDto);
        communityService.updateCommunity(community);
        return communityDto;
    }

    public List<CommunityDto> getAllCommunities(){
        List<Community> communities = communityService.getAllCommunities();
        return communities.stream().map(p -> communityToCommunityDto.convert(p)).collect(Collectors.toList());
    }

    public CommunityDto getDtoCommunity(Long id){
        return communityToCommunityDto.convert(communityService.getCommunity(id));
    }

    public Community getCommunity(Long id){
        return communityService.getCommunity(id);
    }


    public CommunityDto getCommunityByName(String name){
        Community community = communityService.getCommunitiesByName(name);
        return communityToCommunityDto.convert(community);
    }

    public List<CommunityDto> getCommunitiesByAdminUserId(Long adminId){
        List<Community> communities = communityService.getCommunitiesByAdminUserId(adminId);
        return communities.stream().map(c -> communityToCommunityDto.convert(c)).collect(Collectors.toList());
    }

    public Community convertCommunityDtoToCommunity(CommunityDto communityDto){
        return communityDtoToCommunity.convert(communityDto);
    }
}
