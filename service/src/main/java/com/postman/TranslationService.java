package com.postman;

import com.postman.model.Message;

import java.util.List;
import java.util.Locale;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface TranslationService {
    String translate(String str, Locale locale) throws Exception;

    String[] translate(String[] textMessages, Locale locale) throws Exception;

    List<Message> translate(List<Message> messages, Locale locale) throws Exception;
}
