**Custom Configuration**

Create Custom configuration for data source based on conditional properties:

Introduce a configuration(using @Configuration) which should return a data source if an instance of provided interface doesn't exist(@ConditionalOnMissingBean).

Add test which tests your application by saving an entity to the data source.


**Description**

In root path CMD to run: mvn spring-boot:run

Start on port http://localhost:8082

In root path CMD to stop: ctrl+C, y

In root path CMD to test: mvn clean test
