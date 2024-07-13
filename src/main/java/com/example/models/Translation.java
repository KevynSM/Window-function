package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Translation {
    private LocalDateTime timestamp;
    private Long duration; // minutes

}
