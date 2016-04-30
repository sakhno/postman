package com.postman.impl;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.postman.TranslationService;
import com.postman.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Service
public class TranslationServiceImpl implements TranslationService {
    private static final Logger LOGGER = LogManager.getLogger(TranslationServiceImpl.class);
    private static final String CONFIG_FILE_NAME = "azure_translation.properties";

    @PostConstruct
    private void initMethod(){
        URL url = getClass().getClassLoader().getResource(CONFIG_FILE_NAME);
        if(url!=null){
            //trying to set azure properties from local file
            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)){
                Properties prop = new Properties();
                prop.load(inputStream);
                Translate.setClientId(prop.getProperty("AZURE_CLIENT_ID"));
                Translate.setClientSecret(prop.getProperty("AZURE_CLIENT_SECRET"));
            } catch (IOException e) {
                LOGGER.error(e);
            }

        }else {
            //trying to set azure properties from system variables (heroku)
            Translate.setClientId(System.getProperty("AZURE_CLIENT_ID"));
            Translate.setClientSecret(System.getProperty("AZURE_CLIENT_SECRET"));
        }



    }

    @Override
    public String translate(String str, Locale locale) throws Exception{
        return Translate.execute(str, Language.fromString(locale.toLanguageTag()));
    }

    @Override
    public String[] translate(String[] textMessages, Locale locale) throws Exception {
        if(textMessages.length==0){
            return textMessages;
        }
        //detecting text languages
        String[] languages = Detect.execute(textMessages);
        // creating map containing separate map for every language.
        // Inner map has original array index as key and text as value.
        Map<String, Map<Integer, String>> messages = new HashMap<>();
        for(int i = 0;i<languages.length; i++){
            if(messages.containsKey(languages[i])){
                messages.get(languages[i]).put(i, textMessages[i]);
            }else {
                Map<Integer, String> map = new LinkedHashMap<>();
                map.put(i, textMessages[i]);
                messages.put(languages[i], map);
            }
        }
        //translating every language and putting them to resulting Map
        Map<Integer, String> resultingMap = new TreeMap<>();
        for(Map.Entry<String, Map<Integer, String>> entry :messages.entrySet()){
            translateSameLanguageMessages(entry.getValue(), entry.getKey(), locale);
            resultingMap.putAll(entry.getValue());
        }
        //converting resulting map to array
        String[] result = resultingMap.values().toArray(new String[textMessages.length]);
        return result;
    }

    @Override
    public List<Message> translate(List<Message> messages, Locale locale) throws Exception {
        if(messages.isEmpty()){
            return messages;
        }
        //creating array of all message texts
        String[] stringMessages = new String[messages.size()];
        for(int i = 0; i<messages.size();i++){
            stringMessages[i] = messages.get(i).getText();
        }
        //translating string messages
        stringMessages = translate(stringMessages, locale);
        //updating message List with translated messages
        for(int i = 0; i<messages.size();i++){
            messages.get(i).setText(stringMessages[i]);
        }
        return messages;
    }

    private void translateSameLanguageMessages(Map<Integer, String> messages, String fromLanguage, Locale locale) throws Exception{
        String[] strArray = messages.values().toArray(new String[messages.size()]);
        strArray = Translate.execute(strArray, Language.fromString(fromLanguage), Language.fromString(locale.toLanguageTag()));
        int count = 0;
        for(Map.Entry<Integer, String> langEntry: messages.entrySet()){
            langEntry.setValue(strArray[count]);
            count++;
        }
    }
}
