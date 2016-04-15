package com.styleme.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/*
 *
 * @author Eibhlin McGeady
 *
 * Maps JSON objects
 */
public class JSONObjectMapperFactory {

    private static ObjectMapper objectMapper;

    private JSONObjectMapperFactory() {
    }

    public static ObjectReader getObjectReader(Class c) {
        if (objectMapper == null) {
            createObjectMapper();
        }
        return objectMapper.reader(c);
    }

    private static void createObjectMapper() {
        objectMapper = new ObjectMapper();
    }
}