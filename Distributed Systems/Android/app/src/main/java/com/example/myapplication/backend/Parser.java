package com.example.myapplication.backend;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<Room> roomsFromJSON(Context context, String filename) {
        List<Room> rooms = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            InputStreamReader reader = new InputStreamReader(inputStream);

            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                String roomName = (String) jsonObject.get("roomName");
                int noOfPersons = ((Long) jsonObject.get("noOfPersons")).intValue();
                String area = (String) jsonObject.get("area");
                double stars = ((Long) jsonObject.get("stars"));
                int noOfReviews = ((Long) jsonObject.get("noOfReviews")).intValue();
                double price = ((Long) jsonObject.get("price"));
                String roomImage = (String) jsonObject.get("roomImage");

                Room room = new Room(roomName, noOfPersons, area, stars, noOfReviews, price, roomImage);
                rooms.add(room);
            }

            reader.close();
            inputStream.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return rooms;
    }
}
