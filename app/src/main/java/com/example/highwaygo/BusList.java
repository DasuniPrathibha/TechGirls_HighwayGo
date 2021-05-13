package com.example.highwaygo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highwaygo.adapters.BusAdapter;
import com.example.highwaygo.model.Bus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusList extends AppCompatActivity {

    RecyclerView recyclerView;
    BusAdapter busAdapter;

    //DB Instances
    DatabaseReference dbRef; //Realtime


    //Data
    ArrayList<Bus> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        //Init
        recyclerView = findViewById(R.id.recyclerView);
        dbRef = FirebaseDatabase.getInstance().getReference("Bus");
        Log.i("BADA", "  dbRef = " + dbRef);

        list = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(busAdapter);

        //Getting data from realtime db
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Bus bus = dataSnapshot.getValue(Bus.class);
                    Log.i("BADA", "  BusList --->  busName = " + dataSnapshot.getValue(Bus.class).getName());
                    list.add(bus);
                    Log.i("BADA", "  BusList --->  busName = " + bus.getName());
                }
                busAdapter = new BusAdapter(getApplicationContext(), list);
                busAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}