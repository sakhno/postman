package com.postman.impl;

import com.postman.MailService;
import com.postman.TranslationService;
import com.postman.model.Message;
import com.postman.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Service
public class MailServiceImpl implements MailService {
    private final static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private static final String DEFAULT_FROM_ADDRESS = "service.postman@gmail.com";

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TranslationService translationService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendMail(String toAddress, String subject, String message) {
        return sendMail(toAddress, subject, message, DEFAULT_FROM_ADDRESS);
    }

    @Override
    public boolean sendMail(String toAddress, String subject, String message, String fromAddress) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, toAddress);
            mimeMessage.setFrom(fromAddress);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean notifyStatuses(Map<User, List<Message>> userMap) {
        try {
            List<MimeMessage> emailsToSend = new ArrayList<>();
            for (Map.Entry<User, List<Message>> entry : userMap.entrySet()) {
                User user = entry.getKey();
                List<Message> messages = entry.getValue();
                emailsToSend.add(makeNotifyLetter(user, messages));
            }
            mailSender.send(emailsToSend.toArray(new MimeMessage[emailsToSend.size()]));
            LOGGER.debug(emailsToSend.size() + " notify letters sended");
        } catch (MessagingException e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    private MimeMessage makeNotifyLetter(User user, List<Message> messages) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        Locale locale = new Locale(user.getLanguage().name());
        mailMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(user.getLogin()));
        mailMessage.setFrom(new InternetAddress(DEFAULT_FROM_ADDRESS));
        mailMessage.setSubject(messageSource.getMessage("mail.notification.subject", null,
                "mail.notification.subject", locale));
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
