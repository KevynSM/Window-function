package com.example.utils;

import com.example.models.Translation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateParser {
    /**
     * Gets the end time for the window function.
     * This method returns the timestamp of the last translation in the list,
     * truncated to the nearest minute, and then adds one minute.
     *
     * @param translationList the list of translations
     * @return the end time, truncated to the nearest minute and plus one minute
     */
    public static LocalDateTime getEnd(List<Translation> translationList) {
        return translationList.getLast().getTimestamp().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
    }

    /**
     * Gets the start time for the window function.
     * This method returns the timestamp of the first translation in the list,
     * truncated to the nearest minute.
     *
     * @param translationList the list of translations
     * @return the start time, truncated to the nearest minute
     */
    public static LocalDateTime getStart(List<Translation> translationList) {
        return translationList.getFirst().getTimestamp().truncatedTo(ChronoUnit.MINUTES);
    }
}
