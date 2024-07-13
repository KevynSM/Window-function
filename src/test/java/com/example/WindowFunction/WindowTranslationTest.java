package com.example.WindowFunction;

import com.example.models.AveragePerMinute;
import com.example.models.Translation;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.utils.DateParser.getEnd;
import static com.example.utils.DateParser.getStart;
import static org.junit.Assert.assertEquals;

public class WindowTranslationTest {

    DateTimeFormatter dateTimeFormatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    DateTimeFormatter dateTimeFormatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void getAvgPerWindowSize() {
        List<AveragePerMinute> expectedResult = getExpectedResult();
        List<Translation> translationList = getTranslationList();

        LocalDateTime start = getStart(translationList);
        LocalDateTime end = getEnd(translationList);

        List<AveragePerMinute> results = getAveragePerMinutes(start, end, translationList);

        assertExpectedIsEqualToResult(expectedResult, results);
    }

    private static void assertExpectedIsEqualToResult(List<AveragePerMinute> expectedResult, List<AveragePerMinute> results) {
        assertEquals(expectedResult.size(), results.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            assertEquals(expectedResult.get(i).getDate(), results.get(i).getDate());
            assertEquals(expectedResult.get(i).getAverageDeliveryTime(), results.get(i).getAverageDeliveryTime(), 0.001);
        }
    }

    private static List<AveragePerMinute> getAveragePerMinutes(LocalDateTime start, LocalDateTime end, List<Translation> translationList) {
        List<AveragePerMinute> results = new ArrayList<>();
        WindowTranslation windowTranslation = new WindowTranslation(10);
        while(!start.isAfter(end)) {
            double avg = windowTranslation.getAvgPerWindowSize(translationList, start);
            results.add(new AveragePerMinute(start, avg));
            start = start.plusMinutes(1);
        }
        return results;
    }

    private List<Translation> getTranslationList() {
        return List.of(
            new Translation(LocalDateTime.parse("2018-12-26 18:11:08.509654", dateTimeFormatterInput), 20L),
            new Translation(LocalDateTime.parse("2018-12-26 18:15:19.903159", dateTimeFormatterInput), 31L),
            new Translation(LocalDateTime.parse("2018-12-26 18:23:19.903159", dateTimeFormatterInput), 54L)
        );
    }

    private List<AveragePerMinute> getExpectedResult() {
        LocalDateTime start =  LocalDateTime.parse("2018-12-26 18:11:00", dateTimeFormatterOutput);
        return List.of(
                new AveragePerMinute(start, 0),
                new AveragePerMinute(start.plusMinutes(1), 20),
                new AveragePerMinute(start.plusMinutes(2), 20),
                new AveragePerMinute(start.plusMinutes(3), 20),
                new AveragePerMinute(start.plusMinutes(4), 20),
                new AveragePerMinute(start.plusMinutes(5), 25.5),
                new AveragePerMinute(start.plusMinutes(6), 25.5),
                new AveragePerMinute(start.plusMinutes(7), 25.5),
                new AveragePerMinute(start.plusMinutes(8), 25.5),
                new AveragePerMinute(start.plusMinutes(9), 25.5),
                new AveragePerMinute(start.plusMinutes(10), 25.5),
                new AveragePerMinute(start.plusMinutes(11), 31),
                new AveragePerMinute(start.plusMinutes(12), 31),
                new AveragePerMinute(start.plusMinutes(13), 42.5)
        );
    }
}
