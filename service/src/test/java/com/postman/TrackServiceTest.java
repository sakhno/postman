package com.postman;

import com.postman.config.TestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TrackServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(TrackServiceTest.class);
    private User user;
    private Track track;
    @Autowired
    private TrackService trackService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp()throws PersistenceException{
        user = userService.saveUser(new User());
        track = new Track();
        track.setUser(user);
        track.setDateCreated(new Date());
        List<Message> messages = new ArrayList<>();
        boolean flag;
        for(int n=1; n<6; n++){
            if(n%2==0){
                flag = true;
            }else {
                flag =false;
            }
            messages.add(new Message().setReaded(flag).setTrack(track));
        }
        track.setMessages(messages);
        track = trackService.saveTrack(track);
    }

    @Test
    public void getNumberTest()throws PersistenceException, TrackNotFoundException{
        LOGGER.debug(trackService.getNumberOfUnreadMessages(user));
        LOGGER.debug(trackService.getNumberOfUnreadMessages(track));
        Assert.assertEquals(3, trackService.getNumberOfUnreadMessages(track));
        Assert.assertEquals(3, trackService.getNumberOfUnreadMessages(user));
    }

    @After
    public void delete() throws PersistenceException {
        trackService.deleteTrack(track.getId());
        userService.deleteUser(user.getId());
    }
}
