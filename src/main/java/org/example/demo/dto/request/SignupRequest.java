package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Signup Request")
public class SignupRequest {

    @Schema(description = "Username", example = "Александр")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters long")
    @NotBlank(message = "Username can not is empty")
    private String username;

    @Schema(description = "Password", example = "MySecretKey1234")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters long")
    @NotBlank(message = "Password can not is empty")
    private String password;

    @Schema(description = "Email", example = "user@gmail.com")
    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in formate user@gmail.com")
    private String email;
}
