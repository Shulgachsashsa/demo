package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request signIn")
public class SignInRequest {

    @Schema(description = "Username", example = "Александр")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters long")
    @NotBlank(message = "Username can not is empty")
    private String username;

    @Schema(description = "Password", example = "MySecretKey234")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters long")
    @NotBlank(message = "Password can not is empty")
    private String password;
}
