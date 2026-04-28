package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Delete trip")
public class DeleteTripRequest {

    private Long tripId;

}
