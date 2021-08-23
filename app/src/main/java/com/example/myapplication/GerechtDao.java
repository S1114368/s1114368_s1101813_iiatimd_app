package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface GerechtDao {

    @Query("SELECT * FROM gerecht")
    List<Gerecht> getAll();

    @Insert
    void insert(Gerecht gerecht);
}
