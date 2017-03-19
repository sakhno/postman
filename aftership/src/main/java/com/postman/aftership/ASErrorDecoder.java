package com.postman.aftership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postman.model.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by antonsakhno on 19.03.17.
 */
public class ASErrorDecoder implements ErrorDecoder {
    private static final Logger LOGGER = LogManager.getLogger(ASErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() >= 400 && response.status() < 500){
            return decodeHttpError(response);
        } else if(response.status() >= 500 && response.status() < 600) {
            return new RemoteServiceException();
        } else {
            return new Default().decode(methodKey, response);
        }
    }

    private ServiceException decodeHttpError(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response.body().asInputStream());
            int code = node.get("meta").get("code").asInt(0);
            switch (code) {
                case 4003:
                    return new TrackAllreadyExistsException();
                case 4004:
                    return new TrackNotFoundException();
                case 4005:
                    return new InvalidTrackNumberException();
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return new ServiceException();
    }
}
