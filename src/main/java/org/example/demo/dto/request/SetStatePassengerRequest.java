package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.demo.entity.enums.State;

@Data
@Schema(name = "Update actually state of passenger")
public class SetStatePassengerRequest {
    private Long tripUserId;
    private Long userId;
    private State state;
}
