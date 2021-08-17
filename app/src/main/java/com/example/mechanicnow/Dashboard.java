package com.example.mechanicnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    CardView profile, map;
    Button logout;
    RelativeLayout dash;
    TextView dash_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        dash_user = findViewById(R.id.dash_user);
        profile = findViewById(R.id.profile_card);
        map = findViewById(R.id.maps_card);
        logout = findViewById(R.id.logoutUser);


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        String full_name = userDetails.get(SessionManager.KEY_NAME);

        dash_user.setText("Welcome " + full_name);

        dash = findViewById(R.id.dash_board);
        dash.setBackgroundResource(R.drawable.layout_header);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, UserProfile.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, LandingPage.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, MapsActivityUser.class);
                startActivity(intent);
            }
        });


        /*map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, MapsActivityUser.class);
                startActivity(intent);
            }
        });*/


     /*   profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Records.class);
                startActivity(intent);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Appointments.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ChatBot.class);
                startActivity(intent);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); //logout
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });*/

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maps_card:
                startActivity(new Intent(Dashboard.this, MapsActivityUser.class));
                break;
            case R.id.logoutUser:
                startActivity(new Intent(Dashboard.this, LandingPage.class));
                break;
        }
    }


}