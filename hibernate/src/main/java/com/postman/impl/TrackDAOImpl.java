package com.postman.impl;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class TrackDAOImpl extends HibernateAbstractDAO<Track> implements TrackDAO {
    private static final Logger LOGGER = LogManager.getLogger(TrackDAOImpl.class);

    @Override
    public Class getObjectClass() {
        return Track.class;
    }

    public void delete(long id) throws PersistenceException {
        Track track = read(id);
        delete(track);
    }

    public Track getTrackByNumberAndUser(Track track) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("number", track.getNumber()));
        if(track.getUser()==null){
            criteria.add(Restrictions.isNull("user"));
        }else {
            criteria.add(Restrictions.eq("user", track.getUser()));
        }
        return (Track)criteria.uniqueResult();
    }

    @Override
    public List<Track> getAllUserTracks(User user) {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("user", user))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}
