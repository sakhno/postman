package com.postman;

import com.postman.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MailServiceImplTest {
    private static final String TO_ADDRESS = "sakhno83@gmail.com";
    private static final String SUBJECT = "Test email subject.";
    private static final String TEXT = "Test email text";
    @Autowired
    private MailService mailService;

    @Test
    public void sendMail(){
        mailService.sendMail(TO_ADDRESS, SUBJECT, TEXT);
        Assert.assertNotNull(mailService);
    }
}
