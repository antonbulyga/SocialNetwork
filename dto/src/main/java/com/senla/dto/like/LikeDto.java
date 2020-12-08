package com.senla.dto.like;

import com.senla.dto.post.PostForUserAndCommunityDto;
import com.senla.dto.user.UserNestedDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    @NotNull(message = "Like id is mandatory")
    private long id;

    private PostForUserAndCommunityDto post;

    private UserNestedDto user;
}
