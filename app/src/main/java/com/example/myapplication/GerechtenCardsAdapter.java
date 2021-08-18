package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class GerechtenCardsAdapter extends RecyclerView.Adapter<GerechtenCardsAdapter.GerechtenCardViewHolder>{
    private GerechtenCard[] gerechtenCards;
    private Context ctx;

    //Hoe deze classe in elkaar zit als er een object van word gemaakt
    public GerechtenCardsAdapter(GerechtenCard[] gerechtenCards){ this.gerechtenCards = gerechtenCards; }

    //interne view holder classe, zorgt dat er bepaalde data uit een lijst in een row layout doet.
    public static class GerechtenCardViewHolder extends RecyclerView.ViewHolder{
        public TextView textView3;
        public ImageView imageView3;
        //krijgt kaartje binnen
        public GerechtenCardViewHolder(View v){
            super(v);
            textView3 = v.findViewById(R.id.textView3);
            imageView3 = v.findViewById(R.id.imageView3);
        }
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public GerechtenCardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //view zorgt voor het kaarjte in gerechtenCard card
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.gerecht_card, parent, false);
        GerechtenCardViewHolder gerechtenCardViewHolder = new GerechtenCardViewHolder(v);
        return gerechtenCardViewHolder;
    }

    //op de eerste kaart zie je de eerste gerechtenCard, op de laatste kaart, de laatste
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull GerechtenCardsAdapter.GerechtenCardViewHolder holder, int position) {
        holder.textView3.setText(gerechtenCards[position].getGerechtNaam());
        holder.imageView3.setImageResource(gerechtenCards[position].getImg());
    }

    //
    @Override
    public int getItemCount() {
        return gerechtenCards.length;
    }
}
