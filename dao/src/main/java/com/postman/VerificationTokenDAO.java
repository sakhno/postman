package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface VerificationTokenDAO extends GenericDAO<VerificationToken> {
    VerificationToken getByToken(String token) throws PersistenceException;
}
