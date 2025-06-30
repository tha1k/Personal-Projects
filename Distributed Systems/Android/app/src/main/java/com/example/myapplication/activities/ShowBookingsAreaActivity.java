package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ShowBookingsAreaActivity extends AppCompatActivity {
    Room room;
    private EditText startDate, endDate;
    private TextView replace;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        replace = findViewById(R.id.textAreaReplace);
        findViewById(R.id.buttonAreaSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = findViewById(R.id.editAreaStart);
                endDate = findViewById(R.id.editAreaEnd);
                sendDatesToServer(startDate.getText().toString(), endDate.getText().toString());
            }
        });
    }

    private void sendDatesToServer(String startDate, String endDate) {
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String dates = "APP\nAREA\n" +
                        "9001" + "\n" +
                        startDate + "\n" +
                        endDate+ "\n" +
                        "TELOSARXEIOY";
                out.println(dates);
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine);
                }
                in.close();
                out.close();
                socket.close();
                runOnUiThread(() -> {
                    replace.setText(response.toString());
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ShowBookingsAreaActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
