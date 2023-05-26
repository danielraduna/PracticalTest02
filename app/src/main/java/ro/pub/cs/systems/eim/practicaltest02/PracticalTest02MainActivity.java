package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPortEditText, clientAddressEditText, clientPortEditText;
    private EditText number1EditText, number2EditText;
    private Button connectButton, addButton, multiplyButton;
    private TextView resultTextView;

    ServerThread serverThread;
    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        number1EditText = findViewById(R.id.number1_edit_text);
        number2EditText = findViewById(R.id.number2_edit_text);
        connectButton = findViewById(R.id.connect_button);
        addButton = findViewById(R.id.button_add);
        multiplyButton = findViewById(R.id.button_multiply);
        resultTextView = findViewById(R.id.weather_forecast_text_view);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverPort = serverPortEditText.getText().toString();
                serverThread = new ServerThread(Integer.parseInt(serverPort));
                serverThread.start();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();
                String number1 = number1EditText.getText().toString();
                String number2 = number2EditText.getText().toString();
                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), "add," + number1 + "," + number2, resultTextView);
                clientThread.start();
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();
                String number1 = number1EditText.getText().toString();
                String number2 = number2EditText.getText().toString();
                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), "mul," + number1 + "," + number2, resultTextView);
                clientThread.start();
            }
        });
    }
}