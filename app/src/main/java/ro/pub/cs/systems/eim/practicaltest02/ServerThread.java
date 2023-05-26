package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private boolean isRunning = true;
    private ServerSocket serverSocket;
    private int port;

    public ServerThread(int port) {
        this.port = port;
    }

    public void stopServer() {
        isRunning = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        String[] parts = line.split(",");
                        String operation = parts[0];
                        long num1 = Long.parseLong(parts[1]);
                        long num2 = Long.parseLong(parts[2]);
                        int result;
                        switch (operation) {
                            case "add":
                                long sum = (long) num1 + num2;
                                if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                                    printWriter.println("overflow");
                                } else {
                                    printWriter.println(sum);
                                }
                                break;
                            case "mul":
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                long product = (long) num1 * num2;
                                if (product > Integer.MAX_VALUE || product < Integer.MIN_VALUE) {
                                    printWriter.println("overflow");
                                } else {
                                    printWriter.println(product);
                                }
                                break;
                            default:
                                printWriter.println("unknown operation");
                        }
                        line = bufferedReader.readLine();
                    }
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}