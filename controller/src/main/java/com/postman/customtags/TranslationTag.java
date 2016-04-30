package com.postman.customtags;

import com.postman.TranslationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Configurable(autowire= Autowire.BY_TYPE)
public class TranslationTag extends RequestContextAwareTag {
    private static final Logger LOGGER = LogManager.getLogger(TranslationTag.class);
    private String value;
    private String language;

    private TranslationService translationService;



    public void setValue(String value) {
        this.value = value;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        translationService = getRequestContext().getWebApplicationContext().getBean(TranslationService.class);
        doTag();
        return SKIP_BODY;
    }

    public void doTag() throws JspException, IOException {
        //setting locale from tag parameter if mentioned in tag or from session if not
        Locale locale;
        if (language!=null&&!"".equals(language)){
            locale = new Locale(language);
        }else {
            locale = LocaleContextHolder.getLocale();
        }

        //setting output value to original string in case translation throws error
        String result = value;
        try {
            LOGGER.debug(value);
            LOGGER.debug(locale);
            LOGGER.debug(translationService);
            result = translationService.translate(value, locale);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        pageContext.getOut().write(result);
    }
}
