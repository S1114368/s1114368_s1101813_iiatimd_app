package com.example.myapplication;

import android.text.Editable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "gerecht")
public class Gerecht implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "naam")
    String naam;

    @ColumnInfo(name = "aantal personen")
    Editable aantal_personen;

    @ColumnInfo(name = "categorie")
    String categorie;

    @ColumnInfo(name = "instructies")
    JSONArray instructies;

    @ColumnInfo(name = "ingredienten")
    List<String> ingredienten;

    public Gerecht(String naam, Editable aantal_personen, String categorie, JSONArray instructies, JSONArray ingredienten){
        this.naam = naam;
        this.aantal_personen = aantal_personen;
        this.categorie = categorie;
        this.instructies = instructies;
        this.ingredienten = (List<String>) ingredienten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Editable getAantalPersonen() {
        return aantal_personen;
    }

    public void setAantalPersonen(Editable aantal) {
        this.aantal_personen = aantal;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<String> getIngredienten() {
        return ingredienten;
    }

    public List<String> getInstructies() {
        return (List<String>) instructies;
    }

    public void setInstructies(List<String>instructies) {
        this.instructies = (JSONArray) instructies;
    }

}
