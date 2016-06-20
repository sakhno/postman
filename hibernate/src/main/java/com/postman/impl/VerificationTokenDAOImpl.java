package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.PersistenceException;
import com.postman.VerificationTokenDAO;
import com.postman.model.VerificationToken;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
@Qualifier("hibernateVerificationTokenDAO")
public class VerificationTokenDAOImpl extends HibernateAbstractDAO<VerificationToken> implements VerificationTokenDAO {
    @Override
    public Class getObjectClass() {
        return VerificationToken.class;
    }

    @Override
    public VerificationToken getByToken(String token) throws PersistenceException {
        Query query = getCurrentSession().createQuery("from VerificationToken where token=:token")
                .setParameter("token", token);
        return (VerificationToken) query.uniqueResult();
    }
}
