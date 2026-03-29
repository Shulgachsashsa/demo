package org.example.demo.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.request.StartDriverRequest;
import org.example.demo.entity.Driver;
import org.example.demo.entity.Minio;
import org.example.demo.entity.User;
import org.example.demo.exceptions.NoAuthenticationUserFoundException;
import org.example.demo.exceptions.UserAlreadyIsDriverException;
import org.example.demo.repository.DriverRepository;
import org.example.demo.repository.MinioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StartDriverService {

    private final DriverRepository driverRepository;
    private final MinioService minioService;
    private final UserService userService;
    private final MinioRepository minioRepository;
    private final RatingOfDriverService ratingOfDriverService;

    public void createDriverAccount(StartDriverRequest request, List<MultipartFile> carPhotos) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        User user = userService.getCurrentUser().orElseThrow(() ->
                new NoAuthenticationUserFoundException("No authentication user found"));

        if (driverRepository.existsDriverById(user.getId()))
            throw new UserAlreadyIsDriverException("You are already a driver");

        Driver driver = Driver.builder().
                numberPhone(request.getNumberPhone()).
                numberCar(request.getCarNumber()).
                modelCar(request.getModelCar()).
                user(user).
                build();

        driverRepository.save(driver);
        ratingOfDriverService.createRatingForDriver(driver);

        if (!carPhotos.isEmpty()) {
            for (MultipartFile multipartFile: carPhotos) {

                Minio minio = Minio.builder().
                        driver(driver).
                        namePicture(minioService.uploadFile(multipartFile)).
                        build();

                minioRepository.save(minio);
            }
        }
    }

    public void updateCar() {

    }
}
