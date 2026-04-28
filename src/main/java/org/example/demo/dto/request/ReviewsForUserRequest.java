package org.example.demo.dto.request;

import lombok.Data;

@Data
public class ReviewsForUserRequest {
    private int averageForUser;
    private Long tripUserId;
}
