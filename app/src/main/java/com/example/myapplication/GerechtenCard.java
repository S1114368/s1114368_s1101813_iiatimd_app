package com.example.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonNls;

import java.util.List;

@Entity(tableName = "gerechtenCard")
public class GerechtenCard implements Parcelable {

        @PrimaryKey
        @NonNull
        private String gerechtNaam;

        @ColumnInfo
        private int aantal_personen;

        @ColumnInfo
        private String gerechtOntdekken;

        @ColumnInfo
        private String categorie;

        @ColumnInfo
        private List<String> ingredienten;

        @ColumnInfo
        private List<String> instructies;

    public GerechtenCard (String gerechtNaam, int aantal_personen, String categorie, List<String> ingredienten, List<String> instructies, String gerechtOntdekken){
            this.gerechtNaam = gerechtNaam;
            this.aantal_personen = aantal_personen;
            this.categorie = categorie;
            this.ingredienten = ingredienten;
            this.instructies = instructies;
    }

    @Ignore
    protected GerechtenCard(Parcel in) {
        gerechtNaam = in.readString();
        aantal_personen = in.readInt();
        categorie = in.readString();
        ingredienten = in.createStringArrayList();
        instructies = in.createStringArrayList();
    }
    public int getAantal_personen() {
        return aantal_personen;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getGerechtOntdekken() {return gerechtOntdekken;}

    public List<String> getIngredienten() {
        return ingredienten;
    }

    public List<String> getInstructies() {
        return instructies;
    }

    public int getImg(){
            int theImg;
            switch(this.categorie)
            {
                case "kip":
                    theImg = R.drawable.kip;
                    break;
                case "oven":
                    theImg = R.drawable.oven;
                    break;
                case "rund":
                    theImg = R.drawable.rund;
                    break;
                case "varken":
                    theImg = R.drawable.varken;
                    break;
                case "vega":
                    theImg = R.drawable.vega;
                    break;
                case "vis":
                    theImg = R.drawable.vis;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.categorie);
            }
            return theImg;
    }

    public String getGerechtNaam(){
            return this.gerechtNaam;
        }

    public static final Creator<GerechtenCard> CREATOR = new Creator<GerechtenCard>() {
        @Override
        public GerechtenCard createFromParcel(Parcel in) {
            return new GerechtenCard(in);
        }

        @Override
        public GerechtenCard[] newArray(int size) {
            return new GerechtenCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gerechtNaam);
        parcel.writeInt(aantal_personen);
        parcel.writeString(categorie);
        parcel.writeStringList(ingredienten);
        parcel.writeStringList(instructies);
    }
}
