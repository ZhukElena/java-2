package com.geekbrains.server.authorization;

import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InMemoryAuthServiceImpl implements AuthService {
    private final Connection connection;

    public InMemoryAuthServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации инициализирован");
    }

    @Override
    public synchronized String getNickNameByLoginAndPassword(String login, String password) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT * from auth WHERE login = '%s'", login));

        if (rs.isClosed()) {
            return null;
        }

        String usernameDB = rs.getString("username");
        String passwordDB = rs.getString("password");

        return ((passwordDB != null) && (passwordDB.equals(password))) ? usernameDB : null;
    }

    @Override
    public void end() {
        System.out.println("Сервис аутентификации отключен");
    }

    @Override
    public boolean changeUsername(String newUsername, String oldUsername) throws SQLException {
        if (!checkUsernameBusy(newUsername)) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(String.format("UPDATE auth set username = '%s' where username = '%s'",
                    newUsername, oldUsername));
            return true;
        }
        return false;
    }

    private boolean checkUsernameBusy(String username) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT * from auth WHERE username = '%s'", username));
        if (rs.isClosed()) {
            return false;
        }

        String userNameDB = rs.getString("username");
        return userNameDB.equals(username);
    }
}
