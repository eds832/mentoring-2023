**Avro part (3 point)**

In this task, you need to create a Kafka producer and consumer using Avro to serialize messages.

Create a simple Kafka producer that sends a simple message to a topic serializing it using Avro.

Create a simple Kafka consumer which listens to a topic for a message, deserializes the message, and prints it.

Start Kafka, create a topic, and run consumer and producer.

Try to use different Avro schemas for serialization and deserialization. Include Avro schema version/id into the massage so a client can understand which schema to use.


**Description**

Application requires Docker installed

Run the following scripts in the root path: 

* mvn clean install
* cd docker
* docker system prune -a
* y
* docker volume prune
* y
* run.bat

Wait until Redpanda is available on http://localhost:9545/topics/

To start App: in avro-kafka\src\main\java\com\epam\mentoring\avro\Main.java run method main.

( mvn exec:java -Dexec.mainClass=com.epam.mentoring.avro.Main in the root path )

App prints logs describing:

* producing user messages, 
* consuming user messages,
* producing greeting messages with different schema versions,
* consuming greeting messages with different schema versions.

Following URLs show registered schemas

* http://localhost:8081/subjects/greeting-value/versions/1
* http://localhost:8081/subjects/greeting-value/versions/2
* http://localhost:8081/subjects/user-value/versions/1

To see topics:

* http://localhost:9545/topics/greeting
* http://localhost:9545/topics/user
