package com.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    private static boolean isInterrupted = false;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен");
            //Ожидаем клиента
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            Thread readThread = new Thread(() -> {
                while (!isInterrupted) {
                    String message = null;
                    try {
                        message = in.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (message.equals("/end")) {
                        isInterrupted = true;
                        break;
                    }
                    System.out.println(message);
                }
            });

            readThread.start();

            Thread writeThread = new Thread(() -> {
                while (!isInterrupted) {
                    if (scanner.hasNext()) {
                        try {
                            String output = scanner.next();
                            out.writeUTF(output);
                            if (output.equals("/end")) {
                                isInterrupted = true;
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            writeThread.start();

            writeThread.join();
            in.close();
            out.close();
            scanner.close();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
