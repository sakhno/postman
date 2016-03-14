package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface MailService {

    boolean sendMail(String toAddress, String subject, String message);
    boolean sendMail(String toAddress, String subject, String message, String fromAddress);

}
