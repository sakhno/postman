package com.postman.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class DebugInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LogManager.getLogger(DebugInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String params = request.getQueryString();
        if (params == null) {
            params = "";
        } else {
            params = "?" + params;
        }
        LOGGER.debug(request.getRequestURL().toString() + params);
        return super.preHandle(request, response, handler);
    }
}
