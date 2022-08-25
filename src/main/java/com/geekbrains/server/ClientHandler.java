package com.geekbrains.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private final Server server;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        authentication();
                        readMessages();
                    } catch (IOException | SQLException exception) {
                        exception.printStackTrace();
                    } finally {
                        closeConnection();
                    }
                }
            }).start();
        } catch (IOException exception) {
            throw new RuntimeException("Проблемы при создании обработчика");
        }
    }

    public void authentication() throws IOException, SQLException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(ServerCommandConstants.AUTHORIZATION)) {
                String[] authInfo = message.split(" ");
                String nickName = server.getAuthService().getNickNameByLoginAndPassword(authInfo[1], authInfo[2]);
                if (nickName != null) {
                    if (!server.isNickNameBusy(nickName)) {
                        this.nickName = nickName;
                        File file = new File("src/main/resources/chat-history/" + nickName);
                        if (file.exists()) {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            for (int i = 0; i < 100; i++) {
                                String line = reader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    outputStream.writeUTF(line);
                                }
                            }
                        }
                        sendMessage("/authok " + nickName);
                        server.broadcastMessage(nickName + " зашел в чат");
                        LOGGER.info(nickName + " зашел в чат");
                        server.addConnectedUser(this);
                        return;
                    } else {
                        sendMessage("Учетная запись уже используется");
                    }
                } else {
                    sendMessage("Неверные логин или пароль");
                }
            }
        }
    }

    private void readMessages() throws IOException, SQLException {
        while (true) {
            String messageInChat = inputStream.readUTF();
            if (messageInChat.startsWith(ServerCommandConstants.DIRECT_MESSAGE)) {
                String[] directMessageNickname = messageInChat.split(" ");
                if (directMessageNickname.length < 3) {
                    continue;
                }
                String nickname = directMessageNickname[1];
                StringBuilder directMessage = new StringBuilder(directMessageNickname[2]);
                for (int i = 3; i < directMessageNickname.length; i++) {
                    directMessage.append(" ");
                    directMessage.append(directMessageNickname[i]);
                }

                server.broadcastDirectMessage(nickname, nickName + ": " + directMessage);
                continue;
            }

            System.out.println("от " + nickName + ": " + messageInChat);
            LOGGER.info(nickName + " прислал сообщение");
            if (messageInChat.equals(ServerCommandConstants.SHUTDOWN)) {
                return;
            }

            if (messageInChat.startsWith(ServerCommandConstants.CHANGE_USERNAME)) {
                String[] message = messageInChat.split(" ");
                String newUsername = message[1];
                server.changeUserName(newUsername, nickName);
                continue;
            }

            server.broadcastMessage(nickName + ": " + messageInChat);
        }
    }

    public void sendMessage(String message) {
        try {
            if (nickName != null) {
                FileWriter fileWriter = new FileWriter("src/main/resources/chat-history/" + nickName, true);
                fileWriter.append(message).append("\n");
                fileWriter.close();
                outputStream.writeUTF(message);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void closeConnection() {
        server.disconnectUser(this);
        server.broadcastMessage(nickName + " вышел из чата");
        LOGGER.info(nickName + " вышел из чата");
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
