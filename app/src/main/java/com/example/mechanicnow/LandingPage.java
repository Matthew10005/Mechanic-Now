package com.example.mechanicnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingPage extends AppCompatActivity {

    CardView mechanic, motorist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mechanic = findViewById(R.id.driverProfile);
        motorist = findViewById(R.id.selectHelp);

        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, MechanicLogin.class);
                startActivity(intent);
            }
        });

        motorist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }
}