package com.postman;

import com.postman.model.Message;
import com.postman.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface MailService {

    boolean sendMail(String toAddress, String subject, String message);

    boolean sendMail(String toAddress, String subject, String message, String fromAddress);

    boolean notifyStatuses(Map<User, List<Message>> userMap);
}
