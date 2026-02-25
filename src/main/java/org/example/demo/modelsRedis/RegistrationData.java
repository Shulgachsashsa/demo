package org.example.demo.modelsRedis;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Schema(description = "Username", example = "user1")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters long")
    @NotBlank(message = "Username can not is empty")
    private String username;

    @Schema(description = "Email", example = "user@gmail.com")
    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in format example@gmail.com")
    private String email;

    @Schema(description = "Password", example = "MySecretKey1234")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters long")
    @NotBlank(message = "Password can not is empty")
    private String password;

    @Schema(description = "Verification code for email")
    @Size(min = 6, max = 6, message = "Verification code must be of 6 characters long")
    private String verificationCode;

    @Schema(description = "Date")
    private LocalDateTime createdAt;

    @Schema(description = "Quantity of attempts")
    private int attempts;

    public RegistrationData(String username, String email, String password, String code) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.verificationCode = code;
        this.createdAt = LocalDateTime.now();
        this.attempts = 0;
    }

}
