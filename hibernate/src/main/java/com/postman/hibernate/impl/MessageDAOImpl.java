package com.postman.hibernate.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.MessageDAO;
import com.postman.model.Message;
import com.postman.model.Track;
import com.postman.model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
@Qualifier("hibernateMessageDAO")
public class MessageDAOImpl extends HibernateAbstractDAO<Message> implements MessageDAO {
    @Override
    public Class getObjectClass() {
        return Message.class;
    }

    @Override
    public List<Message> readAll(String trackNumber, User user) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("track.number", trackNumber));
        if (user == null) {
            criteria.add(Restrictions.isNull("track.user"));
        } else {
            criteria.add(Restrictions.eq("track.user", user));
        }
        return criteria.list();
    }

    @Override
    public int getNumberOfUnreadMessages(User user) {
        Query query = getCurrentSession().createQuery("from Message where track.user=:user and readed = false");
        query.setParameter("user", user);
        return query.list().size();
    }

    @Override
    public int getNumberOfUnreadMessages(Track track) {
        Query query = getCurrentSession().createQuery("from Message where track=:track and readed = false");
        query.setParameter("track", track);
        System.out.println(query.list());
        return query.list().size();
    }
}
