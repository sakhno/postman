package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.PersistenceException;
import com.postman.PostService;
import com.postman.PostServiceDAO;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
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
