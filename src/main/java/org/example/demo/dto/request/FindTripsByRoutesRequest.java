package org.example.demo.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FindTripsByRoutes {
    private String cityFrom;
    private String cityTo;
}
