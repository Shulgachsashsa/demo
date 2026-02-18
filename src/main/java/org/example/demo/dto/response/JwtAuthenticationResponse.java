package org.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response token")
public class JwtAuthenticationResponse {

    @Schema(description = "Token", example = "fsa3fd28qddj3f2h3f91dhs.f3FJ29Fpd23f9...")
    private String key;
}
