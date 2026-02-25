package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request email code")
public class EmailVerificationRequest {

    @Schema(description = "Email", example = "user@gmail.com")
    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in formate user@gmail.com")
    private String email;

    @Schema(description = "Verification code", example = "123456")
    @Size(min = 6, max = 6, message = "Code consists of 6 characters long")
    @NotBlank(message = "Code can not is empty")
    private String code;

}
