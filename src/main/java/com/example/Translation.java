package com.example;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Translation {
    private LocalDateTime timestamp;
    private Long duration; // minutes
}
