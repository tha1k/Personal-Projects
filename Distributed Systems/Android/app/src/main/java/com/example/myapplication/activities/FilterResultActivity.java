package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

import java.util.List;

public class FilterResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = getIntent().getParcelableArrayListExtra("rooms");
        for (Room room : roomList){
            System.out.println(room.toString());
        }
        adapter = new FilterAdapter(roomList, this);
        recyclerView.setAdapter(adapter);

    }
}
