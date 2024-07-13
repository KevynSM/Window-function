package com.example;


import com.google.gson.*;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main( String[] args ) {
        CLI cli = new CLI();
        new CommandLine(cli).parseArgs(args);

        List<Translation> translationList = Arrays.asList(readTranslationFromFile(cli.getFile()));

        LocalDateTime start = translationList.getFirst().getTimestamp().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime end = translationList.getLast().getTimestamp().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);

        while(!start.isAfter(end)) {
            LocalDateTime finalStart = start;
            List<Translation> windowValues = translationList.stream()
                    .filter(translation -> translation.getTimestamp().isAfter(finalStart.minusMinutes(10)) && translation.getTimestamp().isBefore(finalStart))
                    .sorted(Comparator.comparing(Translation::getTimestamp).reversed())
                    .limit(cli.getWindowSize())
                    .toList();

            long sumOfDuration = windowValues.stream().map(Translation::getDuration).reduce(Long::sum).orElse(0L);
            double avg = 0;
            if (sumOfDuration != 0) {
                avg =  (double) sumOfDuration / windowValues.size();
            }

            System.out.println(MessageFormat.format("timestamp: {0}, average_delivery_time: {1}", start, avg));

            start = start.plusMinutes(1);
        }
    }

    public static Translation[] readTranslationFromFile(File file) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) -> {
                    String dateTimeString = jsonElement.getAsString();
                    return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
                })
                .create();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(br, Translation[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
