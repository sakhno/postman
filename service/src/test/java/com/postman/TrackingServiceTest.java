package com.postman;

import com.postman.config.TestConfig;
import com.postman.model.Message;
import com.postman.model.PostService;
import com.postman.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TrackingServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(TrackingServiceTest.class);
    private final static String TEST_TRACK1 = "RI859368361CN";
    private final static String TEST_TRACK2 = "RO218556783CN";

    @Autowired
    private TrackingService trackingService;

    @Test
    public void getTrackInfoTest() throws IOException, TrackNotFoundException {
        PostService postService = trackingService.getPostService(TEST_TRACK2);
        trackingService.addSingleTrack(TEST_TRACK2, postService);
        Track track = trackingService.getSingleTrack(TEST_TRACK2, postService);
        Assert.assertNotNull(track.getNumber());
        if (track != null && track.getMessages() != null) {
            for (Message message : track.getMessages()) {
                LOGGER.debug(message.getText());
            }
        }
    }
}
