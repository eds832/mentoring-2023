**Spring Auto Configuration**

Create Basic Spring application which will have Data Source Configuration provided by spring Auto-Configuration:

Introduce a configuration(using @Configuration) which should have a method annotated with @Bean which returns a configured data source instance.

Use https://mvnrepository.com/artifact/com.h2database/h2 as a Data Source.

Add test which tests your application by saving an entity to the data source.


**Description**

In root path CMD to run: mvn spring-boot:run

Start on port http://localhost:8081

In root path CMD to stop: ctrl+C, y

In root path CMD to test: mvn clean test
