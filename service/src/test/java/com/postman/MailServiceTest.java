package com.postman;

import com.postman.config.TestConfig;
import com.postman.model.Language;
import com.postman.model.Message;
import com.postman.model.Track;
import com.postman.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MailServiceTest {
    // single message constants
    private static final String TO_ADDRESS = "antonsakhno.work@gmail.com";
    private static final String SUBJECT_EN = "MailService test";
    private static final String SUBJECT_RU = "тестовая тема";
    private static final String TEXT_EN = "MailService test complited";
    private static final String TEXT_RU = "тестовый текст на русском";

    //multiple message constants
    private static final String TRACK_NUMBER = "Tracknumber";
    private static final String MESSAGE = "Track message";
    private static final String MESSAGE_RU = "Сообщение";
    private Map<User, List<Message>> userMessages;


    @Autowired
    private MailService mailService;

    @Before
    public void setUp() {
        userMessages = generateUserMessages();
    }

    @Test
    public void checkMessageSend() {
        assertTrue(mailService.sendMail(TO_ADDRESS, SUBJECT_EN + ", " + SUBJECT_RU, TEXT_EN + ",\n" + TEXT_RU));
    }

    @Test
    public void checkNotifyStatuses() {
        assertTrue(mailService.notifyStatuses(userMessages));
    }

    private Map<User, List<Message>> generateUserMessages() {
        Map<User, List<Message>> result = new HashMap<>();
        // creating 2 users and putting them to userMessages Map
        for (int n = 1; n < 3; n++) {
            User user = new User();
            user.setLogin(TO_ADDRESS);
            user.setLanguage(Language.RU);
            List<Message> messages = new ArrayList<>();
            //creating 2 tracks for each user
            for (int i = 1; i < 3; i++) {
                Track track = new Track();
                track.setNumber(TRACK_NUMBER + i);
                //creating 2 messages for each track and putting them to List
                for (int j = 1; j < 3; j++) {
                    Message message = new Message(MESSAGE + j, new Date());
                    message.setTrack(track);
                    messages.add(message);

                    Message messageRu = new Message(MESSAGE_RU + j, new Date());
                    messageRu.setTrack(track);
                    messages.add(messageRu);
                }
            }
            result.put(user, messages);
        }
        return result;
    }
}
