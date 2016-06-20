package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.PostServiceDAO;
import com.postman.model.PostService;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
@Qualifier("hibernatePostServiceDAO")
public class PostServiceDAOImpl extends HibernateAbstractDAO<PostService> implements PostServiceDAO {
    @Override
    public Class getObjectClass() {
        return PostService.class;
    }

    public PostService getPostServiceByCode(String code) {
        Query query = getCurrentSession().createQuery("from PostService where code=:code")
                .setParameter("code", code);
        return (PostService) query.uniqueResult();
    }
}
