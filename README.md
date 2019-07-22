# Kart Race Ranking

## Tools

To compile and run this solution you need:

- JDK 11+

- Apache Maven 3.6+

## Compile and Package

### Package

To __package__ as a fat jar, run the following command:

```sh
mvn clean package
```

### Run

To __run__ the App:

```sh
java -jar target/kartlog-1.0-SNAPSHOT-jar-with-dependencies.jar \
     --file=<FILE WITH LOGS>
```

### Compile and Test

If you want to see detailed logs, run:

```sh
 APP_LOG_LEVEL=debug mvn clean test
```

If you want just the unit testing, run:

```sh
  mvn clean test
```

## Code coverage

To see the code coverage follow these steps:

1. Run `mvn clean test`

2. Run `mvn org.jacoco:jacoco-maven-plugin:0.8.3:report`

3. Open the report located at: `target/site/jacoco/index.html` 

