package com.example;

import lombok.*;
import picocli.CommandLine.Option;

import java.io.File;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CLI {

    @Option(names = "--input_file", description = "Input file", required = true)
    private File file;

    @Option(names = "--window_size", description = "window size", required = true)
    private int windowSize;
}
