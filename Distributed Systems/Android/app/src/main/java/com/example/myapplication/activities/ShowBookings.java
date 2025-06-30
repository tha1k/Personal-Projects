package com.example.myapplication.activities;

import android.os.Bundle;
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

public class ShowBookings extends AppCompatActivity {
    private TextView replace;
    Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bookings);
        room = getIntent().getParcelableExtra("room");
        replace = findViewById(R.id.textShowReplace);

        sendRoomToServer();
    }

    private void sendRoomToServer() {
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String roomString = "APP\nCOUNT\n" +
                        "9001" + "\n" +
                        room.getRoomName() + "\n" +
                        room.getArea() + "\n" +
                        "TELOSARXEIOY";
                out.println(roomString);
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine);
                }
                in.close();
                out.close();
                socket.close();
                System.out.println(response.toString());
                runOnUiThread(() -> {
                    if (!response.toString().equals("No bookings.")) {
                        String formattedResponse = formatResponse(response.toString());
                        replace.setText(formattedResponse);
                    }else {
                        replace.setText("No bookings.");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ShowBookings.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private String formatResponse(String response) {
        // Assuming the response follows a certain format
        String[] bookings = response.split("Booking ");
        StringBuilder formattedResponse = new StringBuilder();

        for (int i = 1; i < bookings.length; i++) {
            String booking = bookings[i].trim();
            String[] details = booking.split("\t");
            formattedResponse.append("Booking ").append(i).append(":\n")
                    .append(details[1].trim()).append("\n")
                    .append(details[2].trim()).append("\n\n");
        }

        return formattedResponse.toString();
    }
}
