package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class AddAvailabilityActivity extends AppCompatActivity {
    Room room;
    private EditText startDate, endDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dates);
        room = getIntent().getParcelableExtra("room");

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = findViewById(R.id.editStartAdd);
                endDate = findViewById(R.id.editEndAdd);
                sendDatesToServer(startDate.getText().toString(), endDate.getText().toString());
                added_dates();
            }
        });
    }

    private void sendDatesToServer(String startDate, String endDate) {
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000); // Ensure this port is correct and not in use by another service
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String dates = "APP\nAVAILABLE_DATES\n" +
                        "9001" + "\n" +
                        room.getRoomName() + "\n" +
                        room.getArea() + "\n" +
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
                runOnUiThread(() -> Toast.makeText(AddAvailabilityActivity.this, response.toString(), Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(AddAvailabilityActivity.this, "Failed to connectw to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void added_dates() {
        Intent intent = new Intent(AddAvailabilityActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
