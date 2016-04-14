package com.postman;

import com.postman.model.VerificationToken;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface VerificationTokenDAO extends GenericDAO<VerificationToken> {
    VerificationToken getByToken(String token) throws PersistenceException;
}
