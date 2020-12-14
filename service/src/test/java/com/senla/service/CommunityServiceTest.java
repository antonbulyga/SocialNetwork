package com.senla.service;

import com.senla.entity.Community;
import com.senla.repository.CommunityRepository;
import com.senla.service.community.CommunityService;
import com.senla.service.community.CommunityServiceImpl;
import com.senla.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = ConfigurationServiceTest.class)
public class CommunityServiceTest {

    private CommunityService communityService;

    @Mock
    private CommunityRepository communityRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp(){
        communityService = new CommunityServiceImpl(communityRepository, userService);
    }

    private final Community community = new Community(1L, "Любители математики");
    final Long communityId = 1L;

    @Test
    public void getCommunityTest() {
        when(communityRepository.findById(community.getId())).thenReturn(Optional.of(community));
        Community returnCommunity = communityService.getCommunity(community.getId());
        Assertions.assertEquals(community, returnCommunity);
    }

    @Test
    public void updateCommunityTest() {
        given(communityRepository.save(community)).willReturn(community);
        final Community expected = communityService.updateCommunity(community);
        assertThat(expected).isNotNull();
        verify(communityRepository).save(any(Community.class));
    }

    @Test
    public void findCommunityById(){
        given(communityRepository.findById(community.getId())).willReturn(Optional.of(community));
        final Optional<Community> expected  = communityService.getCommunityById(community.getId());
        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldReturnFindAll() {
        List<Community> communities = new ArrayList();
        communities.add(new Community(communityId, "Любители математики"));
        communities.add(new Community(communityId, "Любители математики"));
        communities.add(new Community(communityId, "Любители математики"));
        given(communityRepository.findAll()).willReturn(communities);
        List<Community> expected = communityService.getAllCommunities();
        assertEquals(expected, communities);
    }

    public void getCommunityByNameTest() {
        when(communityRepository.getCommunityByName(community.getName())).thenReturn(community);
        Community resultCommunity = communityService.getCommunityByName(community.getName());
        Assertions.assertEquals(community, resultCommunity);
    }

    public void getCommunityByUserAdmin() {
        List<Community> communities = new ArrayList();
        communities.add(new Community(communityId, "Любители математики"));
        communities.add(new Community(communityId, "Любители математики"));
        communities.add(new Community(communityId, "Любители математики"));
        given(communityRepository.getCommunitiesByAdminUser_Id(community.getAdminUser().getId())).willReturn(communities);
        List<Community> expected = communityService.getAllCommunities();
        assertEquals(expected, communities);
    }

    @Test
    public void shouldBeDelete(){
        when(communityRepository.findById(community.getId())).thenReturn(Optional.of(community));
        communityService.deleteCommunity(communityId);
        verify(communityRepository, times(1)).deleteById(community.getId());
    }
}
