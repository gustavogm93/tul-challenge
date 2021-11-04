package com.tul.challenge.config.exception.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FormatMessage {

    public static String toJson( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .date(LocalDateTime.now().toString())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static String toJson( String result){
        Map<String, String> error = Map.of("Error", result);
        List<Map<String, String>> listOfErrors = List.of(error);

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(listOfErrors)
                .date(LocalDateTime.now().toString())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
