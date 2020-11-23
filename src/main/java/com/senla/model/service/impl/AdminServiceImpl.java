package com.senla.model.service.impl;

import com.senla.model.service.PostService;
import com.senla.model.service.UserService;
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

