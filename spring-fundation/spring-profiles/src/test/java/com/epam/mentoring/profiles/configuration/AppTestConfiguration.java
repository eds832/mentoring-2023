package com.epam.mentoring.profiles.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * @author Eduard_Sardyka
 */
@Component
@Profile("DEV")
public class AppTestConfiguration {

    @Bean
    @Profile("DEV")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:dev;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.setUsername("sa");
        dataSource.setPassword("dev");
        return dataSource;
    }
}