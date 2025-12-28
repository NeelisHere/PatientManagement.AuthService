package com.patientManagement.AuthService.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email is required!")
    @Email(message = "Email address not valid!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, max = 14, message = "Password length should be of min=8 and max=14 length!")
    private String password;
}
