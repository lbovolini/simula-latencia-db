package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.SQLException;

@Component
public class TesteRepository {

    @Autowired
    private HikariDataSource dataSource;

    public void consulta() {
        long start = System.nanoTime();

        try (var connection = DataSourceUtils.getConnection(dataSource);
             var statement = connection.createStatement()) {

            System.out.println("TIME TOTAL 1 " + (System.nanoTime() - start)/1_000_000);

            var hasMore = statement.execute(
                    "select * from hello where name = 'Java'; " +
                    "select * from hello where name = 'JPA';" +
                    "select * from hello where name = 'JDBC';");

            System.out.println("TIME TOTAL 2 " + (System.nanoTime() - start)/1_000_000);
            System.out.println("READ ONLY? " + TransactionSynchronizationManager.isCurrentTransactionReadOnly());

            while (hasMore) {
                var resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    System.out.printf("Id %d - ", resultSet.getLong("id"));
                    System.out.println("Nome " + resultSet.getString("name"));
                }
                else {
                    System.out.println("SEM RESULTADO");
                }
                hasMore = statement.getMoreResults();
            }

            System.out.println("TIME TOTAL 3 " + (System.nanoTime() - start)/1_000_000);

            connection.commit();
            //DataSourceUtils.releaseConnection(connection, dataSource);
            System.out.println("TIME TOTAL 4 " + (System.nanoTime() - start)/1_000_000);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
