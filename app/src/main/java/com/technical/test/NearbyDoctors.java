package com.technical.test;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NearbyDoctors extends AppCompatActivity {

        ListView doctorListView;
        ArrayAdapter adapter;
        ArrayList<String> doctorNames  = new ArrayList<>();
        ArrayList<String> doctorLocationDistance  = new ArrayList<>();
        ArrayList<String> number  = new ArrayList<>();
        LocationManager locationManager;
        LocationListener locationListener;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_nearby_doctors);


                doctorListView = (ListView)findViewById(R.id.doctorListView);

                adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,doctorNames);
                final ArrayAdapter adapter1 = new CustomAdapter(this,doctorNames,number);

                doctorListView.setAdapter(adapter1);

                ParseQuery<ParseUser> query = ParseUser.getCurrentUser().getQuery();
                query.whereEqualTo("riderOrDriver","driver");
                query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {


                                if(objects.size()>0 && e==null){

                                        for(final ParseObject object : objects){

                                                doctorNames.add(object.get("username").toString());

                                                // getting distances of our location and doctors




                                               // doctorLocationDistance.add(2.4 + " KM Away");
                                                number.add(object.get("number").toString());



                                        }
                                        adapter1.notifyDataSetChanged();

                                }
                                else{

                                        Toast.makeText(NearbyDoctors.this,"No Nearby Doctors",Toast.LENGTH_LONG).show();


                                }
                        }
                });


                doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+number.get(i)));
                                startActivity(intent);


                        }
                });





        }
}
