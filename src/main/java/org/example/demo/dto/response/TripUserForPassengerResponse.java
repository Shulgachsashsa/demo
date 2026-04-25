package org.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.entity.enums.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripUserWithTripResponse {
    private Long id;
    private State state;
    private int places;
    private TripResponse trip;
}
