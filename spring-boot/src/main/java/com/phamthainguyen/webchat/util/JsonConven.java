package com.phamthainguyen.webchat.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phamthainguyen.webchat.models.request.SendMessageRequest;

public class JsonConven {

    public static String convertListToJson(List<Long> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    public static List<Long> convertJsonToListLong(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Long>>(){});
        } catch (IOException e) {
            return null;
        }
    }

    public static SendMessageRequest convertStringToObject(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SendMessageRequest request = objectMapper.readValue(json, SendMessageRequest.class);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertObjectToString(SendMessageRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
