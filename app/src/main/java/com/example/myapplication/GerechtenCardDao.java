package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
@Dao
public interface GerechtenCardDao {
    @Query("Select * from gerechtenCard")
    GerechtenCard[] getAll();

    @Insert
    void insertGerechtCard(GerechtenCard[] gerechtenCard);


}
