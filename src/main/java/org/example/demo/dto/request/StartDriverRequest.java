package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Create account driver")
public class StartDriverRequest {

    @Schema(description = "car number", example = "1111 AB-4")
    @Size(min = 9, max = 9, message = "Car number must be 9 characters long")
    @NotBlank(message = "Car number can not is empty")
    private String carNumber;

    @Schema(description = "number phone", example = "+375292512022")
    @Size(min = 13, max = 13, message = "Number phone must be 13 characters long")
    @NotBlank(message = "Number phone can not is empty")
    private String numberPhone;

    @Schema(description = "model car", example = "BMW")
    @Size(min = 2, max = 40, message = "Model car must be between 2 and 40 characters long")
    @NotBlank(message = "Model car can not is empty")
    private String modelCar;
}
