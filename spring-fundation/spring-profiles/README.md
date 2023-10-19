**Spring Profiles (optional, should be done when previous items are complete)**

Separate Data Source Configurations Using Profiles:

There should be 2 different Data Sources - one for QA and one for DEV.

Introduce 2 data sources(using @Component and @Profile("QA")/ @Component and @Profile("DEV"))

Implement test(using @ActiveProfile("DEV")) which tests your application by requesting data from DEV Data Source.


**Description**

In root path CMD to run: mvn spring-boot:run

Start on port http://localhost:8084

In root path CMD to stop: ctrl+C, y

In root path CMD to test: mvn clean test
