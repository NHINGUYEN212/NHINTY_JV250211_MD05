package com.ra.md05_session10.model.dto.request;

import com.ra.md05_session10.validator.UsernameUnique;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterDTO {
    @NotBlank(message = "username can not blank")
    @UsernameUnique
    private String username;

    @NotBlank(message = "password can not blank")
    private String password;

    @NotBlank(message = "email can not blank")
    private String email;

    @NotBlank(message = "phone can not blank")
    private String phone;

    @NotBlank(message = "role can not blank")
    private String role ;
}
