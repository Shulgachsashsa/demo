package org.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.entity.enums.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripUserResponse {
    private Long id;
    private State state;
    private int places;
    private UserResponse user;
}
