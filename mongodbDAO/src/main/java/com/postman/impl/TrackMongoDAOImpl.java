package com.postman.impl;

import com.mongodb.client.FindIterable;
import com.postman.MongoDBAbstractDAO;
import com.postman.PersistenceException;
import com.postman.TrackDAO;
import com.postman.UserDAO;
import com.postman.model.PostService;
import com.postman.model.Track;
import com.postman.model.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Qualifier("mongoTrackDAO")
public class TrackMongoDAOImpl extends MongoDBAbstractDAO<Track> implements TrackDAO {
    @Autowired
    @Qualifier("mongoUserDAO")
    private UserDAO userDAO;

    @Override
    protected String collectionName() {
        return TRACK_COLLECTION_NAME;
    }

    @Override
    protected Track parseResult(Document document) throws PersistenceException {
        if (document == null) {
            return null;
        }
        Track track = new Track()
                .setId(document.getLong("_id"))
                .setNumber(document.getString("number"))
                .setName(document.getString("name"))
                .setActive(document.getBoolean("active"))
                .setDateCreated(document.getDate("dateCreated"));
        Long userId = document.getLong("userId");
        if (userId != null) {
            track.setUser(userDAO.read(userId));
        }
        track.setOriginPostService(parsePostService((Document) document.get("originPostService")));
        track.setDestinationPostService(parsePostService((Document) document.get("destinationPostService")));
        track.setMessages(parseMessages((List<Document>) document.get("messages")));
        return track;
    }

    private PostService parsePostService(Document doc) {
        if (doc == null) {
            return null;
        }
        return new PostService()
                .setCode(doc.getString("code"))
                .setName(doc.getString("name"));
    }

    @Override
    protected Document createObjectDocument(Track track) {
        Document document = new Document("_id", track.getId())
                .append("number", track.getNumber())
                .append("name", track.getName())
                .append("active", track.isActive())
                .append("dateCreated", track.getDateCreated())
                .append("messages", createMessagesDocument(track.getMessages()))
                .append("originPostService", createPostServiceDocument(track.getOriginPostService()))
                .append("destinationPostService", createPostServiceDocument(track.getDestinationPostService()));
        if (track.getUser() != null) {
            document.append("userId", track.getUser().getId());
        }
        return document;
    }

    private Document createPostServiceDocument(PostService postService) {
        if (postService == null) {
            return null;
        }
        return new Document("code", postService.getCode())
                .append("name", postService.getName());
    }

    @Override
    protected long getObjectId(Track object) {
        return object.getId();
    }

    @Override
    public Track getTrackByNumberAndUser(Track track) throws PersistenceException {
        Document filter = new Document("number", track.getNumber());
        if(track.getUser()!=null){
            filter.append("userId", track.getUser().getId());
        }
        return parseResult(getCollection().find(filter).first());
    }

    @Override
    public List<Track> getAllUserTracks(User user) throws PersistenceException {
        return parseTracks(getCollection().find(eq("userId", user.getId())));
    }

    private List<Track> parseTracks(FindIterable<Document> documents) throws PersistenceException {
        List<Track> tracks = new ArrayList<>();
        for (Document doc : documents) {
            tracks.add(parseResult(doc));
        }
        return tracks;
    }

    @Override
    public List<Track> getAllActiveTracks() throws PersistenceException {
        return parseTracks(getCollection().find(eq("active", true)));
    }
}
