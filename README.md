# ReqRes API

This repository contains automated tests for the ReqRes.in API using Java and RestAssured.

## Overview

ReqRes.in is a popular online API testing service that provides mock API endpoints for testing purposes. This project aims to automate API testing for ReqRes using Java programming language and RestAssured library.

## Prerequisites

- Java Development Kit (JDK) installed
- Maven build tool installed
- IDE (like IntelliJ IDEA or Eclipse) for Java development

## Setup Instructions

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/01TeeTheDev01/ReqRes_API_Automation.git
   ```

2. **Import Project into IDE:**

    - Open your IDE (IntelliJ IDEA or Eclipse).
    - Import the project as a Maven project.

3. **Install Dependencies:**

   The project uses Maven for dependency management. Dependencies are specified in the `pom.xml` file. Maven should automatically download the dependencies upon project import.

4. **Run Tests:**

    - Navigate to the `src/test/java/com/reqres/tests` directory.
    - Run individual test class or the entire suite using IDE or Maven commands.

## Project Structure

The project structure is organized as follows:

- **`src/test/java/com/reqres/tests`**: Contains test classes and test suites.
- **`src/test/java/com/reqres/`**: Contains utility classes.

## Test Cases

The tests cover various functionalities of the ReqRes API, including:

- User registration and login
- CRUD operations on user resources
- Error handling scenarios

## Running Tests

Tests can be run either through IDE (by running individual test classes or suites) or using Maven commands:

```bash
mvn clean test
```

## Reporting

The project is configured to generate test reports using Allure plugin.

After running tests, reports can be found in the `allure-results` directory.

## Contributors

- [01TeetheDev01](https://github.com/01TeeTheDev01)