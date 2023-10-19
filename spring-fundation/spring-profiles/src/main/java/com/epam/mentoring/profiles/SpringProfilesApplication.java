package com.epam.mentoring.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Eduard_Sardyka
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringProfilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProfilesApplication.class, args);
    }
}
