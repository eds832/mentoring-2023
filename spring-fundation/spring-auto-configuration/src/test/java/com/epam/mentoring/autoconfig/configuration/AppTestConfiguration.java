package com.epam.mentoring.autoconfig.configuration;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Eduard_Sardyka
 */
@TestConfiguration
@ComponentScans(value = { @ComponentScan("com.epam.mentoring.autoconfig.repository") })
@Profile("test")
public class AppTestConfiguration {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");
        return dataSource;
    }
}