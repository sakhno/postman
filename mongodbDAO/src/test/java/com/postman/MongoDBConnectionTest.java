package com.postman;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.postman.config.MongoDBConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoDBConfig.class)
public class MongoDBConnectionTest {
    private final static Logger LOGGER = LogManager.getLogger(MongoDBConnectionTest.class);
    private final static String COLLECTION_NAME = "testCollection";
    private final static String TEST = "test";
    private final static Document DOCUMENT = new Document(TEST, TEST);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoDbFactory mongoDbFactory;
    @Autowired
    private MongoClient mongoClient;

    @Test
    public void connectionTest() {
        String dBName = System.getProperty("mongoDBName");
        MongoDatabase test = mongoClient.getDatabase(dBName);
        MongoCollection testCollection = test.getCollection(COLLECTION_NAME);
        testCollection.drop();
        testCollection.insertOne(DOCUMENT);
        assertTrue(DOCUMENT.equals(testCollection.findOneAndDelete(DOCUMENT)));
    }
}
