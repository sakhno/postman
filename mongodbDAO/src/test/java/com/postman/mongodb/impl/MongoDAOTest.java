package com.postman.mongodb.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.postman.*;
import com.postman.mongodb.config.MongoDBConfig;
import com.postman.model.*;
import com.postman.mongodb.config.MongoTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.postman.mongodb.MongoDBAbstractDAO.doc;
import static org.junit.Assert.*;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoTestConfig.class)
public class MongoDAOTest {
    private final static Logger LOGGER = LogManager.getLogger(MongoDAOTest.class);
    private final static String LOGIN = "testLogin";
    private final static String NAME = "testName";
    private final static String NAME_CHANGED = "testNameChanged";
    private final static String PASSWORD = "testPassword";
    private final static boolean NOTIFY_BY_EMAIL = false;
    private final static boolean ACTIVE = true;
    private static final String TRACKS_COLLECTION_NAME = "tracks";
    private static final String USERS_COLLECTION_NAME = "users";
    private static final String TOKEN_COLLECTION_NAME = "tokens";
    private static final String TRACK_NUMBER = "track number";
    private static final String TEXT = "test text";
    private static final int MESSAGE_COUNT = 5;
    private static final String TOKEN = "verificationToken";
    private static final String CODE = "code";
    private static final String POSTSERVICE_COLLECTION_NAME = "postservices";

    private User user;
    private Track track;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TrackDAO trackDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private VerificationTokenDAO verificationTokenDAO;
    @Autowired
    private PostServiceDAO postServiceDAO;

    @Before
    public void setUp() throws PersistenceException {
        checkCounters();
        user = userDAO.create(createTestUser());
        track = trackDAO.create(createTestTrack());

        assertTrue(user.getId() > 0);
        assertTrue(track.getId() > 0);
    }

    @After
    public void cleanUp() throws PersistenceException {
        userDAO.delete(user.getId());
        trackDAO.delete(track.getId());
        assertNull(userDAO.read(user.getId()));
        assertNull(trackDAO.read(track.getId()));
    }

    @Test
    public void userDAOTest() throws PersistenceException {
        User userFromDB = userDAO.read(user.getId());
        assertEquals(user, userFromDB);

        user.setName(NAME_CHANGED);
        userDAO.update(user);
        userFromDB = userDAO.read(user.getId());
        assertEquals(NAME_CHANGED, userFromDB.getName());

        List<User> users = userDAO.readAll();
        assertTrue(!users.isEmpty());
    }

    @Test
    public void trackDAOTest() throws PersistenceException {
        StopWatch watch = new StopWatch("mongo bechmark");
        watch.start("update track");
        track.setName(NAME_CHANGED);
        trackDAO.update(track);
        watch.stop();
        watch.start("read track");
        track = trackDAO.read(track.getId());
        watch.stop();
        watch.start("read track 2");
        track = trackDAO.read(track.getId());
        watch.stop();
        assertEquals(NAME_CHANGED, track.getName());
        watch.start("read all tracks");
        List<Track> tracks = trackDAO.readAll();
        watch.stop();
        assertTrue(!tracks.isEmpty());
        LOGGER.debug('\n' + watch.prettyPrint());
    }

    @Test
    public void messageDAOTest() throws PersistenceException {
        List<Message> messages = messageDAO.readAll(track.getNumber(), user);
        assertEquals(MESSAGE_COUNT, messages.size());
        StopWatch watch = new StopWatch("MessageDAO benchmark");
        watch.start("get number of unread by track");
        int unreadMessagesCount = messageDAO.getNumberOfUnreadMessages(track);
        watch.stop();
        assertEquals(MESSAGE_COUNT / 2, unreadMessagesCount);
        watch.start("get number of unread by user");
        unreadMessagesCount = messageDAO.getNumberOfUnreadMessages(user);
        watch.stop();
        assertEquals(MESSAGE_COUNT / 2, unreadMessagesCount);
        watch.start("read all by track number and user");
        messages = messageDAO.readAll(track.getNumber(), user);
        watch.stop();
        assertTrue(!messages.isEmpty());
        watch.start("read all by track number only");
        messages = messageDAO.readAll(track.getNumber(), user);
        watch.stop();
        assertTrue(!messages.isEmpty());
        LOGGER.debug('\n' + watch.prettyPrint());
    }

    @Test
    public void verificationTokenDAOTest() throws PersistenceException {
        VerificationToken token = new VerificationToken()
                .setToken(TOKEN)
                .setDateExpire(new Date())
                .setUser(user);
        token = verificationTokenDAO.create(token);
        assertTrue(token.getId() > 0);
        token = verificationTokenDAO.getByToken(TOKEN);
        LOGGER.debug(token);
        assertNotNull(token.getUser());
        assertNotNull(token.getDateExpire());
        assertNotNull(token.getToken());
        verificationTokenDAO.delete(token.getId());
        token = verificationTokenDAO.read(token.getId());
        assertNull(token);
    }

    @Test
    public void postServiceDAOTest() throws PersistenceException {
        PostService ps = new PostService()
                .setName(NAME)
                .setCode(CODE);
        ps = postServiceDAO.create(ps);
        assertTrue(ps.getId() > 0);
        PostService psFromDB = postServiceDAO.getPostServiceByCode(CODE);
        assertEquals(ps, psFromDB);
        postServiceDAO.delete(ps.getId());
        psFromDB = postServiceDAO.getPostServiceByCode(CODE);
        assertNull(psFromDB);
    }

    private Track createTestTrack() {
        Track track = new Track();
        track.setUser(user);
        track.setDateCreated(new Date());
        track.setNumber(TRACK_NUMBER);
        List<Message> messages = new ArrayList<>();
        boolean flag;
        for (int n = 0; n < MESSAGE_COUNT; n++) {
            if (n % 2 == 0) {
                flag = true;
            } else {
                flag = false;
            }
            messages.add(new Message().setReaded(flag).setTrack(track).setText(TEXT + n).setDate(new Date()));
        }
        track.setMessages(messages);
        return track;
    }

    private User createTestUser() {
        User user = new User()
                .setLogin(LOGIN)
                .setName(NAME)
                .setPassword(PASSWORD)
                .setNotifyByEmail(NOTIFY_BY_EMAIL)
                .setActive(ACTIVE)
                .setLanguage(Language.EN);
        return user;
    }

    private void checkCounters() {
        MongoDatabase db = mongoClient.getDatabase(System.getProperty("mongoDBName"));
        MongoCollection counters = db.getCollection("counters");
        if (counters.find(eq("_id", USERS_COLLECTION_NAME)).first() == null) {
            counters.insertOne(doc("_id", USERS_COLLECTION_NAME).append("seq", 1L));
        }
        if (counters.find(eq("_id", TRACKS_COLLECTION_NAME)).first() == null) {
            counters.insertOne(doc("_id", TRACKS_COLLECTION_NAME).append("seq", 1L));
        }
        if (counters.find(eq("_id", TOKEN_COLLECTION_NAME)).first() == null) {
            counters.insertOne(doc("_id", TOKEN_COLLECTION_NAME).append("seq", 1L));
        }
        if (counters.find(eq("_id", POSTSERVICE_COLLECTION_NAME)).first() == null) {
            counters.insertOne(doc("_id", POSTSERVICE_COLLECTION_NAME).append("seq", 1L));
        }
    }

}
