package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.backend.Parser;
import com.example.myapplication.backend.Room;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<Room> rooms;
    static int counter = 0;
    Room  changedRoom;

    String roomString = "APP\nROOMS\n";
    AssetManager manager;
    ServerSocket serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changedRoom = getIntent().getParcelableExtra("room");
        if (counter == 0) {
            loadRooms();
            try {
                serverSocket = new ServerSocket(9000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InetAddress ip = InetAddress.getByName("192.168.1.27");//CHANGE
                        Socket socket = new Socket(ip, 8000);
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        for (Room r : rooms) {
                            roomString += r.toStringForTCP();
                        }
                        roomString += "TELOSARXEIOY";

                        out.println(roomString);
                        socket.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Rooms sent to server", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }
                }
            }).start();
            counter++;
        }
        if(!(changedRoom == null)) {
            for (Room room : rooms) {
                if (room.getRoomName().equals(changedRoom.getRoomName())) {
                    room.setNoOfReviews(changedRoom.getNoOfReviews());
                    room.setStars(changedRoom.getStars());
                }
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_mode();
            }
        });
        findViewById(R.id.buttonClient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client_mode();
            }
        });
    }

    private void loadRooms() {
        Parser parser = new Parser();
        rooms = parser.roomsFromJSON(this, "rooms.json");
    }

    public void manager_mode() {
        Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) rooms);
        startActivity(intent);
    }

    public void client_mode() {
        Intent intent = new Intent(MainActivity.this, ClientActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) rooms);
        startActivity(intent);
    }
}