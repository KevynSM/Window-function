package com.example;


import com.example.WindowFunction.WindowTranslation;
import com.example.config.InputOptions;
import com.example.models.AveragePerMinute;
import com.example.models.Translation;
import picocli.CommandLine;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.example.utils.DateParser.getEnd;
import static com.example.utils.DateParser.getStart;
import static com.example.utils.FileParser.readTranslationFromFile;
import static com.example.utils.FileParser.toJsonString;

public class App {
    public static void main( String[] args ) {
        // First, we parse the command-line arguments, which include the JSON file path
        // and the size of the time window for calculations.
        InputOptions inputOptions = new InputOptions();
        new CommandLine(inputOptions).parseArgs(args);

        // Second, we read and parse the JSON file to obtain a list of Translation objects.
        List<Translation> translationList = Arrays.asList(readTranslationFromFile(inputOptions.getFile()));

        // Third, we determine the time range (start and end times) for the window function
        // based on the timestamps of the translations in the list.
        LocalDateTime start = getStart(translationList);
        LocalDateTime end = getEnd(translationList);

        // Finally, we iterate through each minute in the specified time range to calculate
        // the average duration of translations and print the results as JSON.
        WindowTranslation windowTranslation = new WindowTranslation(inputOptions.getWindowSize(), translationList);
        while (!start.isAfter(end)) {
            double avg = windowTranslation.getAvgPerWindowSize(start);
            // Print the average delivery time for the current minute in JSON format
            System.out.println(toJsonString(new AveragePerMinute(start, avg)));
            // Move to the next minute
            start = start.plusMinutes(1);
        }
    }

}
