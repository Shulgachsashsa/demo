package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Find trips by routes and date")
public class FindTripsByRoutesRequest {
    private String cityFrom;
    private String cityTo;
    private Date dateOfDeparture;
}
