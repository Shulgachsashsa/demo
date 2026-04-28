package org.example.demo.controllers;

import io.minio.errors.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.StartDriverRequest;
import org.example.demo.service.StartDriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Tag(name = "Start work as a driver")
public class StartDriverController {

    private final StartDriverService startDriverService;

    @PostMapping(value = "/start", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> startDriver(
            @RequestPart("request") @Valid StartDriverRequest request,
            @RequestPart("carPhotos") @Valid List<MultipartFile> carPhotos) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        startDriverService.createDriverAccount(request, carPhotos);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Success",
                "success", true
        ));
    }

}
