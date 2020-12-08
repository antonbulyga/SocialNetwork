package com.senla.dto.post;

import com.senla.dto.user.UserNestedDto;
import com.senla.dto.community.CommunityForUserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @NotNull(message = "Post id is mandatory")
    private long id;

    @NotBlank(message = "Field text mandatory")
    private String text;

    private UserNestedDto user;

    private CommunityForUserDto community;

    private LocalDateTime dateOfCreation;

    @NotNull(message = "Field count like is mandatory")
    private int countLike;
}
