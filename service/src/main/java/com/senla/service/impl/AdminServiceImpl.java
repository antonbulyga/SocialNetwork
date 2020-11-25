package com.senla.service.impl;

import com.senla.service.UserService;
import com.senla.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {
    private UserService userService;
    private PostService postService;

    @Autowired
    public AdminServiceImpl(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


}

