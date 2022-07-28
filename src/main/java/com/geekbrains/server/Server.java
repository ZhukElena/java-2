package com.geekbrains.server;

import com.geekbrains.CommonConstants;
import com.geekbrains.server.authorization.AuthService;
import com.geekbrains.server.authorization.InMemoryAuthServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final AuthService authService;

    private List<ClientHandler> connectedUsers;

    public Server(Connection connection) {
        authService = new InMemoryAuthServiceImpl(connection);
        try (ServerSocket server = new ServerSocket(CommonConstants.SERVER_PORT)) {
            authService.start();
            connectedUsers = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException exception) {
            System.out.println("Ошибка в работе сервера");
            exception.printStackTrace();
        } finally {
            if (authService != null) {
                authService.end();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isNickNameBusy(String nickName) {
        for(ClientHandler handler: connectedUsers) {
            if (handler.getNickName().equals(nickName)) {
                return true;
            }
        }

        return false;
    }

    public synchronized void broadcastMessage(String message) {
        for(ClientHandler handler: connectedUsers) {
            handler.sendMessage(message);
        }
    }

    public synchronized void broadcastDirectMessage(String nickname, String message) {
        for (ClientHandler handler : connectedUsers) {
            if (handler.getNickName().equalsIgnoreCase(nickname) || message.startsWith(handler.getNickName())) {
                handler.sendMessage(message);
            }
        }
    }

    public synchronized void addConnectedUser(ClientHandler handler) {
        connectedUsers.add(handler);
    }

    public synchronized void disconnectUser(ClientHandler handler) {
        connectedUsers.remove(handler);
    }

    public void changeUserName(String newUsername, String oldUsername) throws SQLException {
        if (authService.changeUsername(newUsername, oldUsername)) {
            for (ClientHandler connectedUser : connectedUsers) {
                if (connectedUser.getNickName().equals(oldUsername)) {
                    connectedUser.setNickName(newUsername);
                    broadcastMessage(oldUsername + " now calls himself " + newUsername);
                    break;
                }
            }
        }
    }

}
