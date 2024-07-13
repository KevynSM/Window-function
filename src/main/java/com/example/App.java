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
        // First we parse the args (json file and window size value)
        InputOptions inputOptions = new InputOptions();
        new CommandLine(inputOptions).parseArgs(args);

        // Secondly, we parse the json file
        List<Translation> translationList = Arrays.asList(readTranslationFromFile(inputOptions.getFile()));

        // thirdly, we get the range of which the window function will run
        LocalDateTime start = getStart(translationList);
        LocalDateTime end = getEnd(translationList);

        // Finally, we calculate the avg per window size in each minute inside the range,
        // and we print the result
        WindowTranslation windowTranslation = new WindowTranslation(inputOptions.getWindowSize());
        while(!start.isAfter(end)) {
            double avg = windowTranslation.getAvgPerWindowSize(translationList, start);
            System.out.println(toJsonString(new AveragePerMinute(start, avg)));
            start = start.plusMinutes(1);
        }
    }

}
