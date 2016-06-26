package com.postman.impl;

import com.postman.MessageDAO;
import com.postman.MongoDBAbstractDAO;
import com.postman.PersistenceException;
import com.postman.model.Message;
import com.postman.model.Track;
import com.postman.model.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Qualifier("mongoMessageDAO")
public class MessageMongoDAOImpl extends MongoDBAbstractDAO<Message> implements MessageDAO {
    @Override
    public List<Message> readAll(String trackNumber, User user) throws PersistenceException {
        List<Message> messages = new ArrayList<>();
        for (Document doc : getCollection().find(and(eq("number", trackNumber), eq("userId", user.getId())))) {
            messages.addAll(parseMessages(doc.get("messages", ArrayList.class)));
        }
        return messages;
    }

    @Override
    public int getNumberOfUnreadMessages(User user) {
        Document document = getCollection().aggregate(asList(
                unwind("$messages"),
                match(and(eq("userId", user.getId()), eq("messages.readed", false))),
                doc("$group", doc("_id", "null").append("count", doc("$sum", 1))))).first();
        return document.getLong("count").intValue();
    }

    @Override
    public int getNumberOfUnreadMessages(Track track) {
        Document document = getCollection().aggregate(asList(
                unwind("$messages"),
                match(and(eq("_id", track.getId()), eq("messages.readed", false))),
                doc("$group", doc("_id", "null").append("count", doc("$sum", 1))))).first();
        return document.getLong("count").intValue();
    }

    @Override
    protected String collectionName() {
        return TRACK_COLLECTION_NAME;
    }

    @Override
    protected Message parseResult(Document document) throws PersistenceException {
        return parseMessage(document);
    }

    @Override
    protected Document createObjectDocument(Message object) {
        return createMessageDocument(object);
    }

    @Override
    protected long getObjectId(Message object) {
        return object.getId();
    }
}
