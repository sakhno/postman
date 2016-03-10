package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface PostServiceDAO extends GenericDAO<PostService> {

    PostService getPostServiceByCode(String code);
}
