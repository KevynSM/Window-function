package com.example.WindowFunction;

import com.example.models.Translation;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class WindowTranslation {
    private int windowSize; // The size of the time window in minutes
    private List<Translation> translationList; // The list of translations

    /**
     * Calculates the average duration of translations within the specified time window.
     *
     * @param moment The current moment in time for which the average is calculated.
     * @return The average duration of translations within the window.
     */
    public double getAvgPerWindowSize(LocalDateTime moment) {
        List<Translation> windowValues = getWindowValues(moment);
        long sumOfDuration = getSumOfDuration(windowValues);
        return getAvg(sumOfDuration, windowValues);
    }

    /**
     * Retrieves the translation values within the time window.
     * This method updates the translation list to remove old elements and then filters
     * the translations that fall within the window size.
     *
     * @param moment The current moment in time for the window calculation.
     * @return A list of translations that fall within the window.
     */
    private List<Translation> getWindowValues(LocalDateTime moment) {
        // Update the list to drop the elements on the left side of the window
        this.translationList = dropOldElements(this.translationList, moment);

        // Return only the elements inside the window
        // The takeWhile method stops as soon as the first element doesn't pass the condition
        return this.translationList.stream()
                .takeWhile(translation -> isWithinWindowSize(moment, translation))
                .toList();
    }

    /**
     * Drops translations that are outside the window on the left side.
     *
     * @param translationList The original list of translations.
     * @param moment The current moment to define the window.
     * @return A new list of translations excluding old elements.
     */
    private List<Translation> dropOldElements(List<Translation> translationList, LocalDateTime moment) {
        return translationList.stream().dropWhile(translation -> translation.getTimestamp().isBefore(moment.minusMinutes(windowSize))).toList();
    }

    /**
     * Checks if the timestamp of the translation is before the specified moment.
     *
     * @param moment The current moment in time.
     * @param translation The translation to check.
     * @return true if the translation's timestamp is before the moment, false otherwise.
     */
    private boolean isWithinWindowSize(LocalDateTime moment, Translation translation) {
        return translation.getTimestamp().isBefore(moment);
    }

    /**
     * Calculates the sum of durations of the given translations.
     * Returns 0 if the list is empty.
     *
     * @param windowValues The list of translations within the window.
     * @return The sum of durations.
     */
    private static Long getSumOfDuration(List<Translation> windowValues) {
        return windowValues.stream().map(Translation::getDuration).reduce(Long::sum).orElse(0L);
    }

    /**
     * Calculates the average of the durations.
     * Returns 0 if the sum of durations is 0.
     *
     * @param sumOfDuration The total sum of durations.
     * @param windowValues The list of translations within the window.
     * @return The average duration.
     */
    private static double getAvg(long sumOfDuration, List<Translation> windowValues) {
        return (sumOfDuration != 0) ? (double) sumOfDuration / windowValues.size() : 0;
    }

}
