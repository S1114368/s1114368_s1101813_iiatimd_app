package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GerechtenCard {

        @ColumnInfo
        private int img;

        @ColumnInfo
        private String gerechtNaam;

        @PrimaryKey
        private int uuid;

        public GerechtenCard (int img, String gerechtNaam, int uuid){
            this.img = img;
            this.gerechtNaam = gerechtNaam;
            this.uuid = uuid;
        }

        public int getImg(){
            return this.img;
        }
        public String getGerechtNaam(){
            return this.gerechtNaam;
        }
        public int getUuid(){
            return this.uuid;
        }
}
