package com.example.highwaygo;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private static final String TAG = "home";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView textViewUserEmail;
    private TextView textViewUserName;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Spinner spinner1;
    private Spinner spinner2;
    private TextView tvDate;
    private Button angry_btn;

//    public static final String FROM_BUS = "firstlocation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        tvDate = (TextView) findViewById(R.id.tvDate);
        angry_btn = (Button) findViewById(R.id.angry_btn);

        progressDialog = new ProgressDialog(this);
        angry_btn.setOnClickListener(this);

        //From
        Spinner dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"FROM", "Kottawa", "Kaduwela", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        //To
        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"TO", "Galle", "Vavuniya", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        angry_btn.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, login.class));
        }

        getSupportActionBar().setTitle("Searching Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(home.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDatesetListener
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "OnDateSet:date :" + day + "/" + (month + 1) + "/" + year);
                String date = day + "/" + (month + 1) + "/" + year;
                // String status="Journey Date";
                // mDisplayDate.setText(status+"\n"+date);
                mDisplayDate.setText(date);
            }
        };

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void searchBuses() {


        String from = spinner1.getSelectedItem().toString().trim();
        String to = spinner2.getSelectedItem().toString().trim();
        String date = tvDate.getText().toString().trim();
        // String key = databaseReference.push().getKey();

        if (TextUtils.equals(from,"FROM")) {
            //email is empty
            Toast.makeText(this, "Please select departure place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(to,"TO")) {
            //password is empty
            Toast.makeText(this, "Please select destination place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            //password is empty
            Toast.makeText(this, "Please select journey date", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Searching Buses Please Wait...");
        progressDialog.show();
        BookingDetail bookingDetail = new BookingDetail(from, to, date);

        FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).child("BookingDetails").setValue(bookingDetail);
        //child("bookings").child(userId);

//        String fromDetail = spinner1.getSelectedItem().toString().trim();
        Intent intent=new Intent(home.this,BusActivity.class);
//        startActivity(new Intent(getApplicationContext(), BusActivity.class));
        intent.putExtra("FROM_BUS",from);
        intent.putExtra("TO_BUS",to);
        intent.putExtra("DATE_BUS",date);
        startActivity(intent);
        progressDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                Intent ho = new Intent(home.this, home.class);
                startActivity(ho);
                break;
            case R.id. nav_history :
                Intent hi = new Intent(home.this, my_bookings.class);
                startActivity(hi);
                break;
            case R.id.nav_cancel:
                Intent ca = new Intent(home.this, CancelActivity.class);
                startActivity(ca);
                break;
            case R.id.nav_logout:
//                Intent lo = new Intent(home.this, LogoutActivity.class);
//                startActivity(lo);
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,login.class));
                break;
            case R.id.nav_profile:
                Intent pr = new Intent(home.this, view_profile.class);
                startActivity(pr);
                break;
            case R.id.nav_share:
                Intent sh = new Intent(home.this, share.class);
                startActivity(sh);
                break;
            case R.id.nav_rate:
                Intent ra = new Intent(home.this, rate_us.class);
                startActivity(ra);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view == angry_btn) {
            searchBuses();
        }
    }
}