package org.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriversReviewsResponse {
    private Long id;
    private int averageForDriver;
    private TripUserResponse tripUser;
}
