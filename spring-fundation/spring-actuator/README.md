**Spring Actuator**

Enable actuator by adding https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator dependency.

Expose Default Spring Actuator by configuring SecurityWebFilterChain bean.

Add a new custom actuator endpoint(using @Component and @Endpoint(id = ...)) and return a custom response. 


**Description**

In root path CMD to run: mvn spring-boot:run

Actuator: http://localhost:8083/actuator

Custom Endpoint: http://localhost:8083/actuator/custom-endpoint

In root path CMD to stop: ctrl+C, y
