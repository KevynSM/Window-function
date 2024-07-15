# Window Function Application

## Overview

This application, **window-function**, processes input data using  a window function and can be executed via a command-line interface.

## Quick Start

If you want to test the application, navigate to the `RUN_AND_TEST` folder. Please note that **Java JDK 21** is required to run the JAR file.

```bash
cd RUN_AND_TEST
```

Then, run the application using:

```bash
java -jar window-function-1.0-SNAPSHOT.jar --input_file ./input.json --window_size 10
```

## Prerequisites

If you plan to build the application from source, ensure you have the following installed:

- **Java JDK 21**
- **Maven** (only if building from source)

## Parameters

When running the application, the following parameters are required:

- `--input_file`: Path to the JSON file containing your input data (e.g., `./input.json`).
- `--window_size`: Size of the window for processing the data (e.g., `10`).

## Troubleshooting

- **Java Version Issues**: Ensure you are using Java JDK 21. Check your Java version with:
  ```bash
  java -version
  ```

- **File Not Found**: Ensure you are in the RUN_AND_TEST folder when running the command.

- **JSON Configuration**: If the input.json file is not well configured, the application will fail to run. Make sure the JSON structure is correct.

- **Missing Parameters**: If any required parameters are missing, the application will also fail to run. Ensure all necessary parameters are provided.


