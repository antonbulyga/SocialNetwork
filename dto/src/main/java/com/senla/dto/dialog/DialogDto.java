package com.senla.dto.dialog;

import com.senla.dto.message.MessageForListDto;
import com.senla.dto.user.UserNestedDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogDto {
    @NotNull(message = "{dialog.id}")
    private long id;

    @NotBlank(message = "{dialog.name}")
    private String name;

    private LocalDateTime timeCreation;

    private List<MessageForListDto> messages;

    @NotEmpty(message = "{dialog.userList.notEmpty}")
    private List<UserNestedDto> userList;

}
