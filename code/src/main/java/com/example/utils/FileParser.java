package com.example.utils;

import com.example.models.AveragePerMinute;
import com.example.models.Translation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileParser {
    // DateTimeFormatter for parsing input date-time strings
    static DateTimeFormatter dateTimeFormatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    // DateTimeFormatter for formatting output date-time strings
    static DateTimeFormatter dateTimeFormatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Gson instance for JSON serialization and deserialization
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, getLocalDateTimeJsonDeserializer())
            .registerTypeAdapter(LocalDateTime.class, getLocalDateTimeJsonSerializer())
            .create();

    /**
     * Provides a JsonDeserializer for LocalDateTime objects.
     * This deserializer parses date-time strings into LocalDateTime instances.
     *
     * @return JsonDeserializer for LocalDateTime
     */
    private static JsonDeserializer<LocalDateTime> getLocalDateTimeJsonDeserializer() {
        return (jsonElement, type, jsonDeserializationContext) -> {
            String dateTimeString = jsonElement.getAsString();
            return LocalDateTime.parse(dateTimeString, dateTimeFormatterInput);
        };
    }

    /**
     * Provides a JsonSerializer for LocalDateTime objects.
     * This serializer formats LocalDateTime instances into date-time strings.
     *
     * @return JsonSerializer for LocalDateTime
     */
    private static JsonSerializer<LocalDateTime> getLocalDateTimeJsonSerializer() {
        return (localDateTime, type, jsonSerializationContext) -> {
            String dateTimeString = localDateTime.format(dateTimeFormatterOutput);
            return jsonSerializationContext.serialize(dateTimeString);
        };
    }

    /**
     * Reads a JSON file and parses its content into an array of Translation objects.
     *
     * @param file the JSON file to be read
     * @return an array of Translation objects, or null if an error occurs
     */
    public static Translation[] readTranslationFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(br, Translation[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts an AveragePerMinute object into its JSON string representation.
     *
     * @param averagePerMinute the AveragePerMinute object to be converted
     * @return the JSON string representation of the AveragePerMinute object
     */
    public static String toJsonString(AveragePerMinute averagePerMinute) {
        return gson.toJson(averagePerMinute);
    }
}
