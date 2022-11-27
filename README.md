# Rock Paper Scissors Server

RockPaperScissorsServer is used as a REST endpoint for RockPaperScissorsClient

## Build Source

To compile and test the code (Java 8):

```terminal
mvn clean install
```
or to run it without tests:
```terminal
mvn clean install -DskipTests
```
## Start

To start the application on local environment:

```bash 
mvn spring-boot:run
```

Then your server should be running in `http://localhost:8080/`

## Metrics

To be able to access the metrics (micrometer+prometheus) access:

```bash 
http://localhost:8080/actuator/prometheus
```
Custom added metrics are the ones with structure: RPS_...