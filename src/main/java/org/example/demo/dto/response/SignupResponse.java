package org.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Sign up response")
public class SignupResponse {

    @Schema(description = "Message", example = "registration")
    @NotBlank(message = "Message can not is empty")
    private String message;

    @Schema(description = "Email", example = "user@gmail.com")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in format example@gmail.com")
    private String email;

    @Schema(description = "Time life code")
    private Integer expiresInMinutes;
}
