package com.example.highwaygo.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.highwaygo.BusList;
import com.example.highwaygo.R;

import java.util.Calendar;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private HomeViewModel homeViewModel;

//    private Context context;
    Spinner spn1, spn2;
    TextView date;
    Button btnSearch;

    DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        spn1 = root.findViewById(R.id.spinner1);
        spn2 = root.findViewById(R.id.spinner2);
        date = root.findViewById(R.id.tvDate);
        btnSearch = root.findViewById(R.id.search_btn);

        //Serch Intent
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BusList.class));
            }
        });

        //Spinners
        ArrayAdapter fromAdapter = ArrayAdapter.createFromResource( getContext(), R.array.fromCities, R.layout.spinner_light); //change this to design selected
        fromAdapter.setDropDownViewResource(R.layout.spinner_light_drop);
        spn1.setAdapter(fromAdapter);
        spn1.setOnItemSelectedListener(this);
        spn1.getSelectedItem();

        ArrayAdapter toAdapter = ArrayAdapter.createFromResource( getContext(), R.array.toCities, R.layout.spinner_light); //change this to design selected
        toAdapter.setDropDownViewResource(R.layout.spinner_light_drop);
        spn2.setAdapter(toAdapter);
        spn2.setOnItemSelectedListener(this);
        spn2.getSelectedItem();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, dateSetListener, year, month, day);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateBus = day + "/" + month + "/" + year;
                date.setText(dateBus);
            }
        };


        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}