package ro.pub.cs.systems.eim.practicaltest02;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String command;
    private TextView resultTextView;

    public ClientThread(String address, int port, String command, TextView resultTextView) {
        this.address = address;
        this.port = port;
        this.command = command;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                return;
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final String response = bufferedReader.readLine();
            if (response != null) {
                resultTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(response);
                    }
                });
            } else {
                resultTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText("No response from server");
                    }
                });
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
