package com.example.config;

import lombok.*;
import picocli.CommandLine.Option;

import java.io.File;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputOptions {

    // Specifies the input file containing translation data.
    @Option(names = "--input_file", description = "Input file", required = true)
    private File file;

    // Specifies the size of the time window for calculations.
    @Option(names = "--window_size", description = "window size", required = true)
    private int windowSize;
}
