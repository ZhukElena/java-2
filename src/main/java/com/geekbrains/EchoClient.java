package com.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class EchoClient {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8080;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;
    private boolean isInterrupted = false;

    public EchoClient() {
        try {
            openConnection();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        scanner = new Scanner(System.in);

        Thread writeThread = new Thread(() -> {
            while (!isInterrupted) {
                if (scanner.hasNext()) {
                    try {
                        String output = scanner.next();
                        out.writeUTF(output);
                        if (output.equals("/end")) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        writeThread.start();


        Thread readThread = new Thread(() -> {
            while (!isInterrupted) {
                String messageFromServer;
                try {
                    messageFromServer = in.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                if (Objects.equals(messageFromServer, "/end")) {
                    isInterrupted = true;
                    break;
                }
                System.out.println(messageFromServer);
            }
        });

        readThread.start();

        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.close();
        in.close();
        scanner.close();
    }


    public void closeConnection() {
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String text) {
        try {
            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EchoClient();
    }
}
