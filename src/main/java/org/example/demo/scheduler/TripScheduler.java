package org.example.demo.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.repository.TripRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TripScheduler {

    private final TripRepository tripRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void expiredOldTrips() {
        int updated = tripRepository.expiredOldTrips();
        if (updated > 0)
            log.info("Expired {} trips", updated);
    }

}
