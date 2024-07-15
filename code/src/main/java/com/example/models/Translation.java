package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Translation {

    // The timestamp when the translation event occurred.
    private LocalDateTime timestamp;

    // The duration of the translation in minutes.
    private Long duration; // minutes

}
