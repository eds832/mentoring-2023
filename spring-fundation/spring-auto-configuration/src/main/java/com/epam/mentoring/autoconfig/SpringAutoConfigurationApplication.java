package com.epam.mentoring.autoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Eduard_Sardyka
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringAutoConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAutoConfigurationApplication.class, args);
    }
}
