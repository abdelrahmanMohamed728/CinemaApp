package com.example.abdo.cinemaapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdo.cinemaapp.Model.Search;
import com.example.abdo.cinemaapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<Search> {
    public SearchAdapter(@NonNull Context context,  @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.searchlayout,parent,false);
        Search s = getItem(position);
        ImageView img = convertView.findViewById(R.id.searchImage);
        TextView txt = convertView.findViewById(R.id.searchName);
        txt.setText(s.getName());
        Picasso.with(getContext()).load(s.getImg()).into(img);
        return convertView;
    }
}
