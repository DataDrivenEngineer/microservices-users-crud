package dbelousov.backend.hellorestapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("mysql")
public class DataSourceMySQLConfiguration {

    Logger log = LoggerFactory.getLogger(DataSourceMySQLConfiguration.class);

    @Bean
    public DataSource getDataSource() {

        return DataSourceBuilder
                .create()
                .username(System.getenv("MYSQL_USERNAME"))
                .password(System.getenv("MYSQL_PASSWORD"))
                .url(System.getenv("SPRING_MYSQL_URL"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }


}
