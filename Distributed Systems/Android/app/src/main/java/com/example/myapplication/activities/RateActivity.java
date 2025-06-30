package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class RateActivity extends AppCompatActivity {

    private ImageView imageRoom;
    private EditText rating;
    private TextView titleRoom, detailsRoom, priceRoom, reviewsRoom;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private RatingBar ratingRoom;
    Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        imageRoom = findViewById(R.id.imageRoomDetailRate);
        titleRoom = findViewById(R.id.titleRoomDetailRate);
        detailsRoom = findViewById(R.id.detailsRoomDetailRate);
        priceRoom = findViewById(R.id.priceRoomDetailRate);
        ratingRoom = findViewById(R.id.ratingRoomDetailRate);
        reviewsRoom = findViewById(R.id.reviewsRoomDetailRate);
        rating = findViewById(R.id.editRating);

        room = getIntent().getParcelableExtra("room");

        if (room != null) {
            titleRoom.setText(room.getRoomName());
            detailsRoom.setText("Persons: " + room.getNoOfPersons() + " | Area: " + room.getArea());
            priceRoom.setText("$" + room.getPrice());
            ratingRoom.setRating((float) room.getStars());
            reviewsRoom.setText("(" + room.getNoOfReviews() + " reviews)");


            Glide.with(this).load(room.getImage()).into(imageRoom);
        }
        findViewById(R.id.buttonRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_rate();
            }
        });

    }
    public void add_rate(){
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String rate = "APP\nRATING\n" +
                        "9001" + "\n" +
                        room.getRoomName() + "\n" +
                        room.getArea() + "\n" +
                        rating.getText().toString() + "\n" +
                        "TELOSARXEIOY";
                out.println(rate);
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine);
                }
                in.close();
                out.close();
                socket.close();
                runOnUiThread(() -> Toast.makeText(RateActivity.this, response.toString(), Toast.LENGTH_SHORT).show());
                mHandler.postDelayed(this::getRatedRoom, 500);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(RateActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    private void getRatedRoom(){
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String filters = "RATED\n" +
                        "TELOSARXEIOY";
                out.println(filters);

                // Receive response from server
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine);
                }
                String resultString = response.toString().replace("TELOSARXEIOY", "");
                String[] array_response = resultString.split("\\s");
                room.setStars(Double.parseDouble(array_response[3]));
                room.setNoOfReviews(Integer.parseInt(array_response[4]));
                in.close();
                out.close();
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("AFTER THREAD");
        mHandler.postDelayed(this::rate_result, 100);
    }
    public void rate_result(){
        Intent intent = new Intent(RateActivity.this, MainActivity.class);
        intent.putExtra("room", room);
        startActivity(intent);
    }
}
