package com.styleme.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

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

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            createObjectMapper();
        }
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JSONObjectMapperFactory.objectMapper = objectMapper;
    }

    public static ObjectReader getObjectReader(Class c) {
        if (objectMapper == null) {
            createObjectMapper();
        }
        return objectMapper.reader(c);
    }

    public static ObjectWriter getObjectWriter(Class c) {
        if (objectMapper == null) {
            createObjectMapper();
        }
        return objectMapper.writerWithType(c);
    }

    private static void createObjectMapper() {
        objectMapper = new ObjectMapper();
    }
}