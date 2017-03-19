package com.postman;

import com.postman.config.TestConfig;
import com.postman.model.Message;
import com.postman.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Ignore
public class TranslationServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(TranslationServiceTest.class);
    //Strings to translate
    private static final String STRING_TO_TRANSLATE_EN_1 = "translation test";
    private static final String STRING_TO_TRANSLATE_EN_2 = "the second translation from English";
    private static final String STRING_TO_TRANSLATE_FR_1 = "la première traduit de la langue Français";
    private static final String STRING_TO_TRANSLATE_FR_2 = "la seconde traduction du Français";
    private static final String STRING_TO_TRANSLATE_FR_3 = "La troisième traduction Français";
    private static final String STRING_TO_TRANSLATE_CH = "测试从中文翻译";
    //Expected results
    private static final String STRING_EXPECTED_EN_1 = "тестовый перевод";
    private static final String STRING_EXPECTED_EN_2 = "второй перевод с английского";
    private static final String STRING_EXPECTED_FR_1 = "первый перевод с французского языка";
    private static final String STRING_EXPECTED_FR_2 = "второй перевод французского";
    private static final String STRING_EXPECTED_FR_3 = "Третий французский перевод";
    private static final String STRING_EXPECTED_CH = "Тестовый перевод с китайского";

    private static final String BAD_TRACK = "RH307575081CN";

    private static final Locale LOCALE_RU = new Locale("ru");

    @Autowired
    private TranslationService translationService;
    @Autowired
    private TrackingService trackingService;

    @Test
    public void SingleStringTranslationTest() throws Exception {
        String result = translationService.translate(STRING_TO_TRANSLATE_EN_1, LOCALE_RU);
        assertEquals(STRING_EXPECTED_EN_1, result);
    }

    @Test
    public void MultipleStringTranslationTest() throws Exception {
        String[] textMessages = {
                STRING_TO_TRANSLATE_EN_1,
                STRING_TO_TRANSLATE_FR_1,
                STRING_TO_TRANSLATE_CH,
                STRING_TO_TRANSLATE_FR_2,
                STRING_TO_TRANSLATE_FR_3,
                STRING_TO_TRANSLATE_EN_2};
        debagArrayContent(textMessages);
        String[] result = translationService.translate(textMessages, LOCALE_RU);
        debagArrayContent(result);
        assertEquals(6, result.length);
        assertEquals(STRING_EXPECTED_EN_1, result[0]);
        assertEquals(STRING_EXPECTED_FR_1, result[1]);
        assertEquals(STRING_EXPECTED_CH, result[2]);
        assertEquals(STRING_EXPECTED_FR_2, result[3]);
        assertEquals(STRING_EXPECTED_FR_3, result[4]);
        assertEquals(STRING_EXPECTED_EN_2, result[5]);
    }

    @Test
    public void ListOfMessagesTranslationTest() throws Exception {
        //creating test message List
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(STRING_TO_TRANSLATE_EN_1, new Date()));
        messages.add(new Message(STRING_TO_TRANSLATE_FR_1, new Date()));
        messages.add(new Message(STRING_TO_TRANSLATE_CH, new Date()));
        messages.add(new Message(STRING_TO_TRANSLATE_FR_2, new Date()));
        messages.add(new Message(STRING_TO_TRANSLATE_FR_3, new Date()));
        messages.add(new Message(STRING_TO_TRANSLATE_EN_2, new Date()));
        //translating messages
        List<Message> translatedMessages = translationService.translate(messages, LOCALE_RU);
        assertEquals(6, translatedMessages.size());
        assertEquals(STRING_EXPECTED_EN_1, translatedMessages.get(0).getText());
        assertEquals(STRING_EXPECTED_FR_1, translatedMessages.get(1).getText());
        assertEquals(STRING_EXPECTED_CH, translatedMessages.get(2).getText());
        assertEquals(STRING_EXPECTED_FR_2, translatedMessages.get(3).getText());
        assertEquals(STRING_EXPECTED_FR_3, translatedMessages.get(4).getText());
        assertEquals(STRING_EXPECTED_EN_2, translatedMessages.get(5).getText());

        assertNotNull(translatedMessages.get(0).getDate());
        assertNotNull(translatedMessages.get(1).getDate());
        assertNotNull(translatedMessages.get(2).getDate());
        assertNotNull(translatedMessages.get(3).getDate());
        assertNotNull(translatedMessages.get(4).getDate());
        assertNotNull(translatedMessages.get(5).getDate());
    }

    @Test
    public void NullTests() throws Exception {
        String[] singleResult = translationService.translate(new String[0], LOCALE_RU);
        assertEquals(0, singleResult.length);
        List<Message> messages = translationService.translate(new ArrayList<Message>(), LOCALE_RU);
        assertEquals(0, messages.size());

    }

//    @Test
//    public void badTrackTranslationTest() throws Exception {
//        Track track = trackingService.getSingleTrack(BAD_TRACK);
//        List<Message> translatedMessages = translationService.translate(track.getMessages(), LOCALE_RU);
//        LOGGER.info(translatedMessages);
//        assertNotNull(translatedMessages);
//        assertEquals(track.getMessages().size(), translatedMessages.size());
//    }

    private void debagArrayContent(String[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String str : array) {
            sb.append("\"");
            sb.append(str);
            sb.append("\", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append("]");
        LOGGER.debug(sb.toString());
    }
}
