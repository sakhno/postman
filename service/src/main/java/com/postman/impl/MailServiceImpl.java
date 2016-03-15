package com.postman.impl;

import com.postman.MailService;
import com.postman.Message;
import com.postman.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class MailServiceImpl implements MailService{
    private final static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private static final String DEFAULT_FROM_ADDRESS = "service.postman@gmail.com";
    @Autowired
    private MailSender mailSender;

    @Override
    public boolean sendMail(String toAddress, String subject, String message) {
        return sendMail(toAddress, subject, message, DEFAULT_FROM_ADDRESS);
    }

    @Override
    public boolean sendMail(String toAddress, String subject, String message, String fromAddress) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(fromAddress);
        mailSender.send(simpleMailMessage);
        return true;
    }

    @Override
    public boolean notifyStatuses(Map<User, List<Message>> userMap) {
        List<SimpleMailMessage> letters = new ArrayList<>();
        for(Map.Entry<User, List<Message>> entry:userMap.entrySet()){
            letters.add(makeNotifyLetter(entry.getKey(), entry.getValue()));
        }
        mailSender.send(letters.toArray(new SimpleMailMessage[letters.size()]));
        LOGGER.debug(letters.size()+" notify letters sended");
        return true;
    }

    private SimpleMailMessage makeNotifyLetter(User user, List<Message> messages){
        ResourceBundle label = ResourceBundle.getBundle("messages", new Locale(user.getLanguage().name()));
        SimpleMailMessage letter = new SimpleMailMessage();
        letter.setTo(user.getLogin());
        letter.setFrom(DEFAULT_FROM_ADDRESS);
        letter.setSubject(label.getString("mail.notification.subject"));
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for(Message message:messages){
            sb.append(message.getTrack().getNumber());
            if(message.getTrack().getName()!=null){
                sb.append('(').append(message.getTrack().getName()).append(')');
            }
            sb.append(" - ").append(dateFormat.format(message.getDate())).append(' ').append(message.getText())
                    .append("\n\n");
        }
        letter.setText(sb.toString());
        return letter;
    }
}
