package com.hendisantika.springbootmultidatabase.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-multidatabase
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/02/20
 * Time: 06.49
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "sqlServerEntityManagerFactory",
        transactionManagerRef = "sqlServerTransactionManager",
        basePackages = "com.hendisantika.springbootmultidatabase.repository")
public class SqlServerConfig {
    @Bean
    @ConfigurationProperties(prefix = "sqlserver.datasource")
    public DataSourceProperties sqlServerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource sqlServerDataSource(@Qualifier("sqlServerDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "sqlServerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory(@Qualifier("sqlServerDataSource") DataSource sqlServerDataSource, EntityManagerFactoryBuilder builder) {

        return builder.dataSource(sqlServerDataSource)
                .packages("com.sma.backend.multidb.database.sqlserver.domain")
                .persistenceUnit("sqlserver")
                .build();

    }
}
