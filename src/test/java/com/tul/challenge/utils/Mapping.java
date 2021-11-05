package com.tul.challenge.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapping {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
