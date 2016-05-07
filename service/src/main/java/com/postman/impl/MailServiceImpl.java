package com.postman.impl;

import com.postman.MailService;
import com.postman.TranslationService;
import com.postman.model.Message;
import com.postman.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Service
public class MailServiceImpl implements MailService{
    private final static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private static final String DEFAULT_FROM_ADDRESS = "service.postman@gmail.com";

    @Autowired
    private Session session;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TranslationService translationService;

    @Override
    public boolean sendMail(String toAddress, String subject, String message) {
        return sendMail(toAddress, subject, message, DEFAULT_FROM_ADDRESS);
    }

    @Override
    public boolean sendMail(String toAddress, String subject, String message, String fromAddress) {
        javax.mail.Message mailMessage = new MimeMessage(session);
        try {
            mailMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toAddress));
            mailMessage.setFrom(new InternetAddress(fromAddress));
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            Transport.send(mailMessage);
        } catch (MessagingException e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean notifyStatuses(Map<User, List<Message>> userMap) {
        try {
            Transport transport = session.getTransport();
            transport.connect();
            int count = 0;
            for (Map.Entry<User, List<Message>> entry : userMap.entrySet()) {
                User user = entry.getKey();
                List<Message> messages = entry.getValue();
                transport.sendMessage(makeNotifyLetter(user, messages), new Address[]{new InternetAddress(user.getLogin())});
                count++;
            }
            LOGGER.debug(count + " notify letters sended");
            transport.close();
        } catch (MessagingException e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    private javax.mail.Message makeNotifyLetter(User user, List<Message> messages) throws MessagingException {
        MimeMessage mailMessage = new MimeMessage(session);
        Locale locale =  new Locale(user.getLanguage().name());
        mailMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(user.getLogin()));
        mailMessage.setFrom(new InternetAddress(DEFAULT_FROM_ADDRESS));
        mailMessage.setSubject(messageSource.getMessage("mail.notification.subject", null, locale));
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (Message message : messages) {
            sb.append(message.getTrack().getNumber());
            if (message.getTrack().getName() != null) {
                sb.append('(').append(message.getTrack().getName()).append(')');
            }
            String text = message.getText();
            try {
                //try to translate message, if failed send original text
                text = translationService.translate(text, locale);
            } catch (Exception e) {
                LOGGER.error(e);
            }
            sb.append(" - ").append(dateFormat.format(message.getDate())).append(' ').append(text).append("\n\n");
        }
        mailMessage.setContent(sb.toString(), "text/plain; charset=UTF-8");
        return mailMessage;
    }
}
