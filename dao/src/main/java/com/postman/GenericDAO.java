package com.postman;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface GenericDAO<T> {
    T create(T object) throws PersistenceException;
    T read(long id) throws PersistenceException;
    void update(T object) throws PersistenceException;
    void delete(long id) throws PersistenceException;
    List<T> readAll() throws PersistenceException;
}
