package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {
    List<Room> rooms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        rooms = getIntent().getParcelableArrayListExtra("rooms");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientmenu);
        findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter();
            }
        });
        findViewById(R.id.buttonRateRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate();
            }
        });
    }
    public void filter(){
        Intent intent = new Intent(ClientActivity.this, FilterActivity.class);
        startActivity(intent);
    }
    public void rate(){
        Intent intent = new Intent(ClientActivity.this, RateListActivity.class);
        intent.putParcelableArrayListExtra("rooms", (ArrayList<Room>) rooms);
        startActivity(intent);
    }
}
