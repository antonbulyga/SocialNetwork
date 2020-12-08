package com.senla.dto.message;

import com.senla.dto.user.UserNestedDto;
import com.senla.dto.dialog.DialogForMessageAndUserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    @NotNull(message = "Message id is mandatory")
    private long id;

    private UserNestedDto user;

    @NotBlank(message = "Text of the message is mandatory")
    private String message;

    private DialogForMessageAndUserDto dialog;

    private LocalDateTime creationTime;

}
