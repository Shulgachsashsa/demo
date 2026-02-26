package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Resend code for request")
public class ResendCodeRequest {

    @Schema(description = "Email", example = "user@gmail.com")
    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in format user@gmail.com")
    private String email;
}
