package com.epam.mentoring.customconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Eduard_Sardyka
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringCustomConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCustomConfigurationApplication.class, args);
    }
}
