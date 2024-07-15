package com.example.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AveragePerMinute {

    // The date and time for which the average delivery time is calculated.
    private LocalDateTime date;

    // The average delivery time in minutes.
    @SerializedName("average_delivery_time")
    private double averageDeliveryTime;
}
