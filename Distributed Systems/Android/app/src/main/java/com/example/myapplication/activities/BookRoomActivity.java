package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

public class BookRoomActivity extends AppCompatActivity {

    private ImageView imageRoom;
    private TextView titleRoom, detailsRoom, priceRoom, reviewsRoom;
    private RatingBar ratingRoom;
    Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        imageRoom = findViewById(R.id.imageRoomDetail);
        titleRoom = findViewById(R.id.titleRoomDetail);
        detailsRoom = findViewById(R.id.detailsRoomDetail);
        priceRoom = findViewById(R.id.priceRoomDetail);
        ratingRoom = findViewById(R.id.ratingRoomDetail);
        reviewsRoom = findViewById(R.id.reviewsRoomDetail);

        room = getIntent().getParcelableExtra("room");

        if (room != null) {
            titleRoom.setText(room.getRoomName());
            detailsRoom.setText("Persons: " + room.getNoOfPersons() + " | Area: " + room.getArea());
            priceRoom.setText("$" + room.getPrice());
            ratingRoom.setRating((float) room.getStars());
            reviewsRoom.setText("(" + room.getNoOfReviews() + " reviews)");


            Glide.with(this).load(room.getImage()).into(imageRoom);
        }
        findViewById(R.id.buttonBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_dates();
            }
        });

    }
    public void add_dates(){
        Intent intent = new Intent(BookRoomActivity.this, AddBookingActivity.class);
        intent.putExtra("room", room);
        startActivity(intent);
    }
}
