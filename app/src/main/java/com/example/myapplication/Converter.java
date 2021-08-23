package com.example.myapplication;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<String> stringToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String >>() {}.getType();
        List<String> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
