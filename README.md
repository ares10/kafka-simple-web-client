# kafka-simple-web-client
A simple configurable web client to to display Kafka topic messages

## Environmental variables required to operate

```console
## Application variables
export KAFKA_WEB_CLIENT_PORT="8080"

## Kafka
export KAFKA_SERVER_URI="localhost:9092"
```

Run with Maven

```console
mvn clean spring-boot:run
```
