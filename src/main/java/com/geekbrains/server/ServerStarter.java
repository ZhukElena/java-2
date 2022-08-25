package com.geekbrains.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerStarter {
    private static final Logger LOGGER = LogManager.getLogger(ServerStarter.class);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        PropertyConfigurator.configure("src/main/resources/logs/config/log4j.properties");
        LOGGER.info("Сервер запущен");

        Connection connection = createConnection();
        createUsers(connection);
        new Server(connection);
        disconnect(connection);
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/db/mainDB.db");
    }

    private static void disconnect(Connection connection) throws SQLException {
        connection.close();
    }

    private static void createUsers(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        long startTime = System.currentTimeMillis();

        connection.setAutoCommit(false);
        for (int i = 0; i < 3000; i++) {
            stmt.addBatch(String.format("INSERT INTO auth (login, password, username) VALUES ('user%s', '%s', " +
                            "'username%s')",
                    i, i * i * i, i));
        }
        stmt.executeBatch();
        connection.setAutoCommit(true);

        System.out.println(System.currentTimeMillis() - startTime);
    }
}
