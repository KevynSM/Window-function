package com.example.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AveragePerMinute {
    private LocalDateTime date;

    @SerializedName("average_delivery_time")
    private double averageDeliveryTime; // minutes
}
