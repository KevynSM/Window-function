package com.example.WindowFunction;

import com.example.models.Translation;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class WindowTranslation {
    private int windowSize;

    public double getAvgPerWindowSize(List<Translation> translationList, LocalDateTime start) {
        List<Translation> windowValues = getWindowValues(translationList, start);
        long sumOfDuration = getSumOfDuration(windowValues);
        return getAvg(sumOfDuration, windowValues);
    }

    private List<Translation> getWindowValues(List<Translation> translationList, LocalDateTime start) {
        return translationList.stream()
                .filter(translation -> isWithinWindowSize(start, translation))
                .toList();
    }

    private boolean isWithinWindowSize(LocalDateTime start, Translation translation) {
        return translation.getTimestamp().isAfter(start.minusMinutes(windowSize))
                && translation.getTimestamp().isBefore(start);
    }

    private static Long getSumOfDuration(List<Translation> windowValues) {
        return windowValues.stream().map(Translation::getDuration).reduce(Long::sum).orElse(0L);
    }

    private static double getAvg(long sumOfDuration, List<Translation> windowValues) {
        return (sumOfDuration != 0) ? (double) sumOfDuration / windowValues.size() : 0;
    }

}
