package com.postman.impl;

import com.postman.MongoDBAbstractDAO;
import com.postman.PersistenceException;
import com.postman.UserDAO;
import com.postman.VerificationTokenDAO;
import com.postman.model.VerificationToken;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Qualifier("mongoVerificationTokenDAO")
public class VerificationTokenMongoDAOImpl extends MongoDBAbstractDAO<VerificationToken> implements VerificationTokenDAO {

    @Autowired
    @Qualifier("mongoUserDAO")
    private UserDAO userDAO;

    @Override
    protected String collectionName() {
        return TOKEN_COLLECTION_NAME;
    }

    @Override
    protected VerificationToken parseResult(Document document) throws PersistenceException {
        if (document == null) {
            return null;
        }
        return new VerificationToken()
                .setId(document.getLong("_id"))
                .setToken(document.getString("token"))
                .setDateExpire(document.getDate("dateExpire"))
                .setUser(userDAO.read(document.getLong("userId")));
    }

    @Override
    protected Document createObjectDocument(VerificationToken object) {
        return new Document("_id", object.getId())
                .append("token", object.getToken())
                .append("dateExpire", object.getDateExpire())
                .append("userId", object.getUser().getId());
    }

    @Override
    protected long getObjectId(VerificationToken object) {
        return object.getId();
    }

    @Override
    public VerificationToken getByToken(String token) throws PersistenceException {
        System.out.println(getCollection().find(eq("token", token)).first());
        return parseResult(getCollection().find(eq("token", token)).first());
    }
}
