package com.geekbrains.server.authorization;

import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickNameByLoginAndPassword(String login, String password) throws SQLException;
    boolean changeUsername(String userName, String oldUsername) throws SQLException;
    void end();
}
