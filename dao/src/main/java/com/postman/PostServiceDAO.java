package com.postman;

import com.postman.model.PostService;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface PostServiceDAO extends GenericDAO<PostService> {

    PostService getPostServiceByCode(String code) throws PersistenceException;
}
