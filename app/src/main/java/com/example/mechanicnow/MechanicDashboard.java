package com.example.mechanicnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

public class MechanicDashboard extends AppCompatActivity {

    CardView profile, map;
    Button logout;
    RelativeLayout dash;
    TextView dash_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        dash_user = findViewById(R.id.dash_user);
        profile = findViewById(R.id.profile_card);
        map = findViewById(R.id.maps_card);
        logout = findViewById(R.id.logoutMechanic);


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        String full_name = userDetails.get(SessionManager.KEY_NAME);

        dash_user.setText("Welcome " + full_name);

        dash = findViewById(R.id.dash_board);
        dash.setBackgroundResource(R.drawable.layout_header);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MechanicDashboard.this, UserProfile.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MechanicDashboard.this, MapsActivityTow.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MechanicDashboard.this, LandingPage.class);
                startActivity(intent);
            }
        });

    }
}