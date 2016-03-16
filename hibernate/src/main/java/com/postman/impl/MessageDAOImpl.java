package com.postman.impl;

import com.postman.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class MessageDAOImpl extends HibernateAbstractDAO<Message> implements MessageDAO {
    @Override
    public Class getObjectClass() {
        return Message.class;
    }

    @Override
    public List<Message> readAll(String trackNumber, User user) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("track.number", trackNumber));
        if(user==null){
            criteria.add(Restrictions.isNull("track.user"));
        }else {
            criteria.add(Restrictions.eq("track.user", user));
        }
        return criteria.list();
    }
}
