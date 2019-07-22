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

Output Example:

```txt
Melhor volta:
	038 - F.MASSA
	Volta 3
	Tempo 0:01:02.769
	Velocidade 44.334000

| Posição|Código Piloto  |Nome Piloto         | Completadas|Tempo de Prova  |#   Melhor Volta|  Vel. Média|      Atraso|
|       1|038            |F.MASSA             |           4|0:04:11.578     |3    0:01:02.769|   44.245750| 0:00:00.000|
|       2|002            |K.RAIKKONEN         |           4|0:04:15.153     |4    0:01:03.076|   43.627251| 0:00:05.117|
|       3|033            |R.BARRICHELLO       |           4|0:04:16.080     |3    0:01:03.716|   43.467999| 0:00:05.583|
|       4|023            |M.WEBBER            |           4|0:04:17.722     |4    0:01:04.216|   43.191250| 0:00:08.972|
|       5|015            |F.ALONSO            |           4|0:04:54.221     |2    0:01:07.011|   38.066250| 0:00:49.738|
|       6|011            |S.VETTEL            |           3|0:06:27.276     |3    0:01:18.097|   25.745667| 0:02:40.754|

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

