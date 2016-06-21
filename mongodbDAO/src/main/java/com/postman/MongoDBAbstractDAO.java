package com.postman;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.postman.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public abstract class MongoDBAbstractDAO<T> implements GenericDAO<T> {
    protected static final String TRACK_COLLECTION_NAME = "tracks";
    protected static final String USER_COLLECTION_NAME = "users";
    protected static final String TOKEN_COLLECTION_NAME = "tokens";
    protected static final String POST_SERVICE_COLLECTION_NAME = "postservices";
    private static final Logger LOGGER = LogManager.getLogger(MongoDBAbstractDAO.class);
    @Autowired
    private MongoClient mongoClient;

    public static Document doc(String key, String val) {
        return new Document(key, val);
    }

    public static Document doc(String key, long val) {
        return new Document(key, val);
    }

    public static Document doc(String key, Document val) {
        return new Document(key, val);
    }

    protected abstract String collectionName();

    protected abstract T parseResult(Document document) throws PersistenceException;

    protected abstract Document createObjectDocument(T object);

    protected MongoCollection<Document> getCollection() {
        return getDB().getCollection(collectionName());
    }

    private MongoDatabase getDB() {
        return mongoClient.getDatabase(System.getProperty("mongoDBName"));//TODO db name retrieving
    }

    private long getNextSequence(String name) {
        MongoCollection<Document> counters = getDB().getCollection("counters");
        Document doc = counters.findOneAndUpdate(doc("_id", name), inc("seq", 1));
        return doc.getLong("seq");
    }

    @Override
    public T read(long id) throws PersistenceException {
        Document document = getCollection().find(eq("_id", id)).first();
        return parseResult(document);
    }

    @Override
    public T create(T object) throws PersistenceException {
        Document doc = createObjectDocument(object);
        doc.replace("_id", getNextSequence(collectionName()));
        getCollection().insertOne(doc);
        return parseResult(doc);
    }

    @Override
    public void delete(long id) throws PersistenceException {
        getCollection().deleteOne(doc("_id", id));
    }

    @Override
    public void update(T object) throws PersistenceException {
        getCollection().updateOne(eq("_id", getObjectId(object)), doc("$set", createObjectDocument(object)));
    }

    @Override
    public List<T> readAll() throws PersistenceException {
        List<T> result = new ArrayList<>();
        for (Document item : getCollection().find()) {
            result.add(parseResult(item));
        }
        return result;
    }

    protected abstract long getObjectId(T object);

    protected List<Message> parseMessages(List<Document> doc) {
        List<Message> messages = new ArrayList<>();
        for (Document item : doc) {
            messages.add(parseMessage(item));
        }
        return messages;
    }

    protected Message parseMessage(Document doc) {
        return new Message()
                .setText(doc.getString("text"))
                .setDate(doc.getDate("date"))
                .setReaded(doc.getBoolean("readed"));
    }

    protected List<Document> createMessagesDocument(List<Message> messages) {
        List<Document> documents = new ArrayList<>();
        for (Message message : messages) {
            documents.add(createMessageDocument(message));
        }
        return documents;
    }

    protected Document createMessageDocument(Message message) {
        return new Document("text", message.getText())
                .append("date", message.getDate())
                .append("readed", message.isReaded());
    }
}
