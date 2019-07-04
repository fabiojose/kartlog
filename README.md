# Kart Race Ranking

To use this app, first [run](#run), wait until the app's prompt
it and proceed as follow:

- Enter your talk headline, example: `My Awesome Talk 30min`

```sh
18:50:05,343 |-INFO in ch.qos.logback.classic.gaffer.ConfigurationDelegate@5aa9e4eb - Added status listener of type [ch.qos.logback.core.status.OnConsoleStatusListener]
18:50:05,399 |-INFO in ch.qos.logback.classic.gaffer.ConfigurationDelegate@5aa9e4eb - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
18:50:05,400 |-INFO in ch.qos.logback.classic.gaffer.ConfigurationDelegate@5aa9e4eb - Naming appender as [CONSOLE]
> [type here your awesome talk headline]
> [enter if you are done!]
  Track 1:
    09:00:00 AM My Awesome Talk 30min
    12:00:00 PM Lunch
    05:00:00 PM networking
```

- Watch out this amazing asciinema to see the app in action

[![asciicast](https://asciinema.org/a/9J00Flwrt8CEV339IlBnig3AY.png)](https://asciinema.org/a/9J00Flwrt8CEV339IlBnig3AY)

## Tools

To compile and run this solution you need:

- JDK 1.8+

- Apache Maven 3.6+

## Compile and Package

### Package

To __package__ the fat jar, run the following command:

```sh
mvn clean package
```

#### Run

To __run__ the App:

```sh
java -jar target/kartlog-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Compile and Test

The following command will compile and test. By default
will run the integration test and prints out the solution in the 
sysout. 

```sh
  mvn clean test verify
```

If you want to see detailed logs, run:

```sh
 APP_LOG_LEVEL=debug mvn clean test verify
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

