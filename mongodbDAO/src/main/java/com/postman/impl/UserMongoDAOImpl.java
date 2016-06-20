package com.postman.impl;

import com.postman.MongoDBAbstractDAO;
import com.postman.PersistenceException;
import com.postman.TrackDAO;
import com.postman.UserDAO;
import com.postman.model.Language;
import com.postman.model.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Qualifier("mongoUserDAO")
public class UserMongoDAOImpl extends MongoDBAbstractDAO<User> implements UserDAO {
    @Autowired
    @Qualifier("mongoTrackDAO")
    private TrackDAO trackDAO;

    @Override
    protected String collectionName() {
        return USER_COLLECTION_NAME;
    }

    @Override
    public User getUserByLogin(String login) throws PersistenceException {
        Document filter = new Document("login", login);
        Document document = getCollection().find(filter).first();
        return parseResult(document);
    }

    @Override
    protected User parseResult(Document document) throws PersistenceException {
        if (document == null) {
            return null;
        }
        User user = new User();
        user.setId(document.getLong("_id"));
        user.setActive(document.getBoolean("active"));
        user.setLogin(document.getString("login"));
        user.setName(document.getString("name"));
        user.setPassword(document.getString("password"));
        user.setNotifyByEmail(document.getBoolean("notifyByEmail"));
        if (document.getString("language") != null) {
            user.setLanguage(Language.valueOf(document.getString("language")));
        }
        return user;
    }

    @Override
    protected Document createObjectDocument(User object) {
        Document document = new Document()
                .append("_id", object.getId())
                .append("login", object.getLogin())
                .append("name", object.getName())
                .append("password", object.getPassword())
                .append("active", object.isActive())
                .append("notifyByEmail", object.isNotifyByEmail());
        if (object.getLanguage() != null) {
            document.append("language", object.getLanguage().name());
        }
        return document;
    }

    @Override
    protected long getObjectId(User object) {
        return object.getId();
    }
}
