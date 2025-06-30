package com.example.myapplication.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.backend.Room;
import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {
    List<Room> rooms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        rooms = getIntent().getParcelableArrayListExtra("rooms");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managermenu);
        findViewById(R.id.buttonAddAvailability).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_availability();
            }
        });
        findViewById(R.id.buttonShowBookings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_bookings();
            }
        });
        findViewById(R.id.buttonShowBookingsPerArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_bookings_per_area();
            }
        });
    }
    public void add_availability(){
        Intent intent = new Intent(ManagerActivity.this, RoomListActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) rooms);
        startActivity(intent);
    }
    public void show_bookings(){
        Intent intent = new Intent(ManagerActivity.this, ShowListActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) rooms);
        startActivity(intent);
    }
    public void show_bookings_per_area(){
        Intent intent = new Intent(ManagerActivity.this, ShowBookingsAreaActivity.class);
        startActivity(intent);
    }
}
