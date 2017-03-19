package com.postman.mongodb.impl;

import com.postman.mongodb.MongoDBAbstractDAO;
import com.postman.PersistenceException;
import com.postman.PostServiceDAO;
import com.postman.model.PostService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Qualifier("mongoPostServiceDAO")
public class PostServiceMongoDAOImpl extends MongoDBAbstractDAO<PostService> implements PostServiceDAO {
    @Override
    protected String collectionName() {
        return POST_SERVICE_COLLECTION_NAME;
    }

    @Override
    protected PostService parseResult(Document document) throws PersistenceException {
        if (document == null) {
            return null;
        }
        return new PostService()
                .setId(document.getLong("_id"))
                .setCode(document.getString("code"))
                .setName(document.getString("name"));
    }

    @Override
    protected Document createObjectDocument(PostService object) {
        return new Document("_id", object.getId())
                .append("code", object.getCode())
                .append("name", object.getName());
    }

    @Override
    protected long getObjectId(PostService object) {
        return object.getId();
    }

    @Override
    public PostService getPostServiceByCode(String code) throws PersistenceException {
        return parseResult(getCollection().find(eq("code", code)).first());
    }
}
