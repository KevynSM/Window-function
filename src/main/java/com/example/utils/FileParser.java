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
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, getLocalDateTimeJsonDeserializer())
            .registerTypeAdapter(LocalDateTime.class, getLocalDateTimeJsonSerializer())
            .create();

    private static JsonDeserializer<LocalDateTime> getLocalDateTimeJsonDeserializer() {
        return (jsonElement, type, jsonDeserializationContext) -> {
            String dateTimeString = jsonElement.getAsString();
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        };
    }

    private static JsonSerializer<LocalDateTime> getLocalDateTimeJsonSerializer() {
        return (localDateTime, type, jsonSerializationContext) -> {
            String dateTimeString = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return jsonSerializationContext.serialize(dateTimeString);
        };
    }

    public static Translation[] readTranslationFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(br, Translation[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonString(AveragePerMinute averagePerMinute) {
        return gson.toJson(averagePerMinute);
    }
}
