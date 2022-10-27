package org.sparkera.nft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "dbOneDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.db1") // application.yml 中对应属性的前缀
    public DataSource dbOneDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dbTwoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2") // application.yml 中对应属性的前缀
    public DataSource dbTwoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbThreeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db3") // application.yml 中对应属性的前缀
    public DataSource dbThreeDataSource() {
        return DataSourceBuilder.create().build();
    }

}
