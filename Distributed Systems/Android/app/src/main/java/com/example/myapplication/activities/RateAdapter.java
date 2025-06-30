package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.backend.Room;

import java.util.List;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private List<Room> roomList;
    private Context context;
    public RateAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.titleRoom.setText(room.getRoomName());
        holder.detailsRoom.setText("Persons: " + room.getNoOfPersons() + " | Area: " + room.getArea());
        holder.priceRoom.setText("$" + room.getPrice());
        holder.ratingRoom.setRating(room.getStars());
        holder.reviewsRoom.setText("(" + room.getNoOfReviews() + " reviews)");
        Glide.with(holder.itemView.getContext()).load(room.getImage()).into(holder.imageRoom);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RateActivity.class);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRoom;
        TextView titleRoom, detailsRoom, priceRoom, reviewsRoom;
        RatingBar ratingRoom;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRoom = itemView.findViewById(R.id.imageRoom);
            titleRoom = itemView.findViewById(R.id.titleRoom);
            detailsRoom = itemView.findViewById(R.id.detailsRoom);
            priceRoom = itemView.findViewById(R.id.priceRoom);
            ratingRoom = itemView.findViewById(R.id.ratingRoom);
            reviewsRoom = itemView.findViewById(R.id.reviewsRoom);
        }
    }
}
