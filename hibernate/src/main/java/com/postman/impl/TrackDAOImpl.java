package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.TrackDAO;
import com.postman.model.Track;
import com.postman.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
@Qualifier("hibernateTrackDAO")
public class TrackDAOImpl extends HibernateAbstractDAO<Track> implements TrackDAO {
    private static final Logger LOGGER = LogManager.getLogger(TrackDAOImpl.class);

    @Override
    public Class getObjectClass() {
        return Track.class;
    }

    public Track getTrackByNumberAndUser(Track track) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("number", track.getNumber()));
        if (track.getUser() == null) {
            criteria.add(Restrictions.isNull("user"));
        } else {
            criteria.add(Restrictions.eq("user", track.getUser()));
        }
        return (Track) criteria.uniqueResult();
    }

    @Override
    public List<Track> getAllUserTracks(User user) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("user", user))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public List<Track> getAllActiveTracks() {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("active", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}
