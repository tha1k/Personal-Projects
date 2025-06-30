package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
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
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private EditText editArea, editPeople, editPrice, editRating;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    List<Room> filteredRooms = new ArrayList<>();
    private Room filteredRoom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mHandler = new Handler(Looper.getMainLooper());
        editArea = findViewById(R.id.editArea);
        editPeople = findViewById(R.id.editPeople);
        editPrice = findViewById(R.id.editPrice);
        editRating = findViewById(R.id.editRating);
        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFiltersToServer(editArea.getText().toString(), editPeople.getText().toString(),
                        editPrice.getText().toString(), editRating.getText().toString());
            }
        });
    }
    private void sendFiltersToServer(String area, String people, String price, String rating) {
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String filters = "APP\nFILTERS\n" +
                        "9001" + "\n" +
                        area + "\n" +
                        people + "\n" +
                        rating + "\n" +
                        price + "\n" +
                        "TELOSARXEIOY";
                out.println(filters);

                // Receive response from server
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine);
                }
                runOnUiThread(() -> Toast.makeText(FilterActivity.this, response.toString(), Toast.LENGTH_SHORT).show());
                in.close();
                out.close();
                socket.close();
                mHandler.postDelayed(this::getFilteredRooms, 500);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(FilterActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    private void getFilteredRooms(){
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                Socket socket = new Socket(ip, 8000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String filters = "FILTERED\n" +
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
                filteredRooms.clear();
                if(array_response.length != 0){
                    for (int i = 0; i < array_response.length; i += 7) {
                        String roomName = array_response[i];
                        System.out.println(roomName);
                        String noOfPersons = array_response[i+1];
                        System.out.println(noOfPersons);
                        String area = array_response[i+2];
                        System.out.println(area);
                        String rating = array_response[i+3];
                        System.out.println(rating);
                        String noOfReviews = array_response[i+4];
                        System.out.println(noOfReviews);
                        String price = array_response[i+5];
                        System.out.println(price);
                        String roomImage = array_response[i+6];
                        System.out.println(roomImage);
                        filteredRoom = new Room(roomName, Integer.parseInt(noOfPersons), area,
                                Double.parseDouble(rating), Integer.parseInt(noOfReviews), Double.parseDouble(price), roomImage);
                        filteredRooms.add(filteredRoom);
                    }
                    runOnUiThread(this::filter_result);
                }else{
                    runOnUiThread(() -> Toast.makeText(FilterActivity.this, "No results", Toast.LENGTH_SHORT).show());
                }
                in.close();
                out.close();
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("AFTER THREAD");
        for(Room room : filteredRooms){
            System.out.println(room.toString());
        }
    }
    public void filter_result(){
        Intent intent = new Intent(FilterActivity.this, FilterResultActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) filteredRooms);
        startActivity(intent);
    }
}
