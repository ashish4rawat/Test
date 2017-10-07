package com.technical.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter{


        ArrayList<String> names ;
        ArrayList<String> distances ;

        public CustomAdapter(@NonNull Context context, ArrayList<String> names, ArrayList<String> distances ) {


                super(context,R.layout.custom_row ,names);
                this.names= names;
                this.distances = distances;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater ashsihInflator = LayoutInflater.from(getContext());
                View sustom_view = ashsihInflator.inflate(R.layout.custom_row, parent, false);

                String single_Name = names.get(position);
                String single_Number = distances.get(position);

                TextView name = (TextView) sustom_view.findViewById(R.id.name);
                TextView number = (TextView) sustom_view.findViewById(R.id.number);
                ImageView imageView = (ImageView)sustom_view.findViewById(R.id.imageView);


                name.setText(single_Name);
                number.setText(single_Number);
                imageView.setImageResource(R.drawable.defaultimgnew);

                return sustom_view;
        }


}
