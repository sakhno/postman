package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.Message;
import com.postman.MessageDAO;
import com.postman.PersistenceException;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class MessageDAOImpl extends HibernateAbstractDAO<Message> implements MessageDAO {
    @Override
    public Class getObjectClass() {
        return Message.class;
    }

    public void delete(long id) throws PersistenceException {
        Message message = read(id);
        delete(message);
    }
}
