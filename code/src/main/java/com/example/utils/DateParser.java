package com.example.utils;

import com.example.models.Translation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateParser {
    /**
     * Gets the end time for the window function.
     * This method returns the timestamp of the last translation in the list.
     * If the timestamp is exactly on the minute, it returns it as is.
     * Otherwise, it truncates the timestamp to remove the seconds and nanoseconds,
     * and then adds one minute.
     *
     * @param translationList the list of translations
     * @return the end time; either the original timestamp if it's on the minute,
     *         or the timestamp with seconds removed, plus one minute
     */
    public static LocalDateTime getEnd(List<Translation> translationList) {
        LocalDateTime lastTimestamp = translationList.getLast().getTimestamp();

        return lastTimestamp.getSecond() == 0
                ? lastTimestamp
                : lastTimestamp.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
    }

    /**
     * Gets the start time for the window function.
     * This method returns the timestamp of the first translation in the list,
     * with the seconds and nanoseconds removed.
     *
     * @param translationList the list of translations
     * @return the start time, with the seconds and nanoseconds removed
     */
    public static LocalDateTime getStart(List<Translation> translationList) {
        return translationList.getFirst().getTimestamp().truncatedTo(ChronoUnit.MINUTES);
    }
}
