package com.postman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class ScheduledTaskService {
    private static final Logger LOGGER = LogManager.getLogger(ScheduledTaskService.class);

    @Autowired
    TrackService trackService;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void updateAllTracks() {
        Date startTime = new Date();
        try {
            trackService.updateAllActiveTracks();
            long time = new Date().getTime() - startTime.getTime();
            LOGGER.info("Scheduled update finished in " + time + "ms");
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
    }
}
