package com.postman;

import com.postman.config.TestConfig;
import com.postman.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CacheTest {
    private static final Logger LOGGER = LogManager.getLogger(CacheTest.class);
    private static final String TEST_TRACK_NUMBER = "TEST_TRACK_NUMBER";

    private Track track;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TrackDAO trackDAO;

    @Before
    public void setUp() throws PersistenceException {
        track = new Track();
        track.setActive(true);
        track.setDateCreated(new Date());
        track.setNumber(TEST_TRACK_NUMBER);
        track = trackDAO.create(track);
    }

    @After
    public void cleanUp() throws PersistenceException {
        trackDAO.delete(track.getId());
    }

    @Test
    public void cacheTest() throws PersistenceException {
        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);
        printStats(stats);
        track = trackDAO.read(track.getId());
        printStats(stats);
        //record doesn't present in cache (miss=1), put it in cache (put=1)
        assertTrue(stats.getSecondLevelCacheMissCount() == 1);
        assertTrue(stats.getSecondLevelCachePutCount() == 1);
        track = trackDAO.read(track.getId());
        track = trackDAO.read(track.getId());
        //record readed 2 times in short while, cache used 2 times (hit=2)
        assertTrue(stats.getSecondLevelCacheHitCount() == 2);
        printStats(stats);
    }

    @Test
    public void readTimeTest() throws PersistenceException {
        StopWatch watch = new StopWatch("TrackDAO read time test");
        watch.start("single track read");
        Track trackFromDB = trackDAO.read(track.getId());
        watch.stop();
        watch.start("single track read 2");
        trackFromDB = trackDAO.read(track.getId());
        watch.stop();
        watch.start("single track read 3");
        trackFromDB = trackDAO.read(track.getId());
        watch.stop();
        watch.start("single track read 4");
        trackFromDB = trackDAO.read(track.getId());
        watch.stop();
        watch.start("read all tracks");
        List<Track> tracks = trackDAO.readAll();
        watch.stop();
        watch.start("read all tracks 2");
        tracks = trackDAO.readAll();
        watch.stop();
        watch.start("read all tracks 3");
        tracks = trackDAO.readAll();
        watch.stop();
        watch.start("read all tracks 4");
        tracks = trackDAO.readAll();
        watch.stop();
        LOGGER.debug('\n' + watch.prettyPrint());
    }

    private void printStats(Statistics stats) {
        StringBuilder sb = new StringBuilder()
                .append('\n')
                .append("\t\t\t" + "Second Level Hit Count: " + stats.getSecondLevelCacheHitCount() + '\n')
                .append("\t\t\t" + "Second Level Miss Count: " + stats.getSecondLevelCacheMissCount() + '\n')
                .append("\t\t\t" + "Second Level Put Count: " + stats.getSecondLevelCachePutCount() + '\n')
                .append('\n');
        LOGGER.debug(sb);
    }
}
