package org.corefin.configuration;


import org.apache.commons.dbcp2.BasicDataSource;
import org.corefin.dao.LoanDao;
import org.corefin.dao.LoanInstallmentDao;
import org.corefin.dao.McaDao;
import org.corefin.dao.PaymentDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbiConfiguration {

    @Bean
    public DataSource dataSource(
            @Value("${database.username}") String dbUser,
            @Value("${database.password}") String dbPassword,
            @Value("${database.url}") String dbUrl,
            @Value("${database.timezone}") String connectionTimezone,
            @Value("${database.maxPooledConnections}") int maxPooledConnections,
            @Value("${database.minIdleConnections}") int minIdleConnections,
            @Value("${database.initialPooledConnections}") int intialPooledConnections
    ) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(dbUser);
        basicDataSource.setPassword(dbPassword);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.addConnectionProperty("connectionTimeZone", connectionTimezone);
        basicDataSource.setMaxTotal(maxPooledConnections);
        basicDataSource.setMinIdle(minIdleConnections);
        basicDataSource.setInitialSize(intialPooledConnections);
        return basicDataSource;
    }
    @Bean
    public Jdbi jdbi(DataSource ds) {
        return Jdbi.create(ds);
    }

    @Bean
    public LoanDao loanDao(Jdbi db) {
        return new LoanDao(db);
    }
    @Bean
    public LoanInstallmentDao loanInstallmentDaoDao(Jdbi db) {
        return new LoanInstallmentDao(db);
    }

    @Bean
    public PaymentDao paymentDao(Jdbi db) {
        return new PaymentDao(db);
    }
    @Bean
    public McaDao mcaDao(Jdbi db) {
        return new McaDao(db);
    }
}
