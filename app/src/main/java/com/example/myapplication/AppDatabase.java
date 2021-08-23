package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Gerecht.class} ,  version = 1)

public abstract  class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "gerecht_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract GerechtDao gerechtDao();

}
