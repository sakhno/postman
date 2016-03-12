package com.postman.impl;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class TrackingMoreServiceImpl implements TrackingService {
    private static final Logger LOGGER = LogManager.getLogger(TrackingMoreServiceImpl.class);
    private static final String SERVICE_URL = "http://api.trackingmore.com/v2";
    private static final String API_KEY = "b324e0b1-e438-4061-8458-1ccf311545bb";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Track getSingleTrack(String trackingNumber, PostService postService) throws TrackNotFoundException{
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        TMSingleTrack tmobject = restTemplate.exchange(SERVICE_URL+"/trackings/"+postService.getCode()+"/"+trackingNumber, HttpMethod.GET, entity, TMSingleTrack.class).getBody();
        if(tmobject.getMeta().getCode()==4031){
            throw new TrackNotFoundException();
        }
        Track track = convertToTrack(tmobject.getTrack());
        track.setOriginPostService(postService);
        return track;
    }

    @Override
    public PostService getPostService(String trackCode) throws TrackNotFoundException{
        String jsonRequest = "{\"tracking_number\":\""+trackCode+"\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, getHeaders());
        TMSinglePostService response = restTemplate.postForObject(SERVICE_URL+"/carriers/detect", entity, TMSinglePostService.class );
        if(response.getMeta().getCode()!=200){
            throw new TrackNotFoundException();
        }
        TMCarrier carrier = response.getCarriers()[0];
        PostService postService = new PostService();
        postService.setCode(carrier.getCode());
        postService.setName(carrier.getName());
        LOGGER.debug(postService);
        return postService;
    }

    @Override
    public boolean addSingleTrack(String trackCode, PostService postService) throws TrackNotFoundException{
        HttpEntity<String> entity = new HttpEntity<>(getAddTrackBody(trackCode, postService), getHeaders());
        TMCommonRequest result = restTemplate.exchange(SERVICE_URL+"/trackings/post", HttpMethod.POST, entity, TMCommonRequest.class).getBody();
        LOGGER.debug(result.toString());
        int responseCode = result.getMeta().getCode();
        if(responseCode==200||responseCode==4016){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean addSingleTrack(String trackCode) throws TrackNotFoundException{
        PostService postService = getPostService(trackCode);
        return addSingleTrack(trackCode, postService);
    }

    @Override
    public Track getSingleTrack(String trackCode) throws TrackNotFoundException{
        PostService postService = getPostService(trackCode);
        return getSingleTrack(trackCode, postService);
    }

    @Override
    public Map<String, Track> getAllTracks() {
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        TMMultipleTracks response = restTemplate.exchange(SERVICE_URL+"/trackings/get", HttpMethod.GET, entity, TMMultipleTracks.class ).getBody();
        Map<String, Track> result = new HashMap();
        for(TMTrack tmTrack: response.getData().getItems()){
            result.put(tmTrack.getTrackingNumber(), convertToTrack(tmTrack));
        }
        return result;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Trackingmore-Api-Key", API_KEY);
        return headers;
    }

    private String getAddTrackBody(String trackCode, PostService postService){
        StringBuilder result = new StringBuilder();
        result.append('{')
                .append("\"tracking_number\" : \"").append(trackCode).append("\",")
                .append("\"carrier_code\" : \"").append(postService.getCode()).append('\"')
                .append('}');
        LOGGER.debug(result.toString());
        return result.toString();
    }

    private Track convertToTrack(TMTrack tmTrack){
        Track track = new Track();
        track.setDateCreated(tmTrack.getCreated());
        TMMessage[] originalMessages = null;
        TMMessage[] destinationMessages = null;
        if(tmTrack.getOriginInfo()!=null){
            originalMessages = tmTrack.getOriginInfo().getTrackinfo();
        }
        if(tmTrack.getDestinationInfo()!=null){
            destinationMessages = tmTrack.getDestinationInfo().getTrackinfo();
        }
        track.setMessages(convertToMessage(originalMessages, destinationMessages, track));
        track.setNumber(tmTrack.getTrackingNumber());
        return track;
    }

    private List<Message> convertToMessage(TMMessage[] originalMessages, TMMessage[] destinationMessages, Track track){
        List<Message> messages = new ArrayList<>();
        addMessagesToList(originalMessages, messages, track);
        addMessagesToList(destinationMessages, messages, track);
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return messages;
    }

    private void addMessagesToList(TMMessage[] apiMessages, List<Message> messages, Track track){
        if(apiMessages==null){return;}
        for(TMMessage tmMessage: apiMessages){
            Message message = new Message();
            message.setDate(tmMessage.getDate());
            message.setText(tmMessage.getStatusDescription()+", "+tmMessage.getDetails());
            message.setTrack(track);
            messages.add(message);
        }
    }
}
