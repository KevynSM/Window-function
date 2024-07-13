package com.example.utils;

import com.example.models.Translation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateParser {
    public static LocalDateTime getEnd(List<Translation> translationList) {
        return translationList.getLast().getTimestamp().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
    }

    public static LocalDateTime getStart(List<Translation> translationList) {
        return translationList.getFirst().getTimestamp().truncatedTo(ChronoUnit.MINUTES);
    }
}
