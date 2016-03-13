package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface MailService {

    boolean sendMail(String address, String message);
    boolean sendMail(String address, String message, String from);

}
