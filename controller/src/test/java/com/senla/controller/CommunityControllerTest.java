package com.senla.controller;

import com.senla.dto.CommunityDto;
import com.senla.dto.UserShortDto;
import com.senla.facade.CommunityFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommunityController.class)
public class CommunityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommunityFacade communityFacade;

    private final Long communityId = 1L;
    private final List<CommunityDto> communityDtoList = new ArrayList<>();
    private final CommunityDto communityDto = new CommunityDto(1L, "Любители математики", new UserShortDto(1L));

    @Test
    public void shouldFetchAllCommunities() throws Exception {
        communityDtoList.add(new CommunityDto(1L, "Любители математики", new UserShortDto(1L)));
        communityDtoList.add(new CommunityDto(1L, "Любители математики", new UserShortDto(1L)));
        communityDtoList.add(new CommunityDto(1L, "Любители математики", new UserShortDto(1L)));
        given(communityFacade.getAllCommunities()).willReturn(communityDtoList);
        this.mvc.perform(get("/communities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(communityDtoList.size())));
    }


    @Test
    void shouldFetchOneUserById() throws Exception {
        given(communityFacade.getDtoCommunity(communityId)).willReturn(communityDto);
        this.mvc.perform(get("/communities/{id}", communityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(communityDto.getId())))
                .andExpect(jsonPath("$.name", is(communityDto.getName())));
    }

  /*  @Test
    void shouldReturn404WhenFindUserById() throws Exception {
        final Long userId = 1L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/api/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.createUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User(null, "newuser1@gmail.com", "pwd", "Name");

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())))
        ;
    }

    @Test
    void shouldReturn400WhenCreateNewUserWithoutEmail() throws Exception {
        User user = new User(null, null, "pwd", "Name");

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", is("application/problem+json")))
                .andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
                .andExpect(jsonPath("$.title", is("Constraint Violation")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", is("email")))
                .andExpect(jsonPath("$.violations[0].message", is("Email should not be empty")))
                .andReturn()
        ;
    }

    @Test
    void shouldUpdateUser() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        given(userService.updateUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())));

    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingUser() throws Exception {
        Long userId = 1L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());
        User user = new User(userId, "user1@gmail.com", "pwd", "Name");

        this.mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).deleteUserById(user.getId());

        this.mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())));

    }

    @Test
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        Long userId = 1L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());

    }*/


}
