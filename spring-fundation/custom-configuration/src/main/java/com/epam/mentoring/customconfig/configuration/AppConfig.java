package com.epam.mentoring.customconfig.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author Eduard_Sardyka
 */
@Configuration
@PropertySource("classpath:db.properties")
@ComponentScans(value = { @ComponentScan("com.epam.mentoring.customconfig.repository") })
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean(type = "DataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.dirver"));
        dataSource.setUrl(env.getProperty("db.jdbcUrl"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

}