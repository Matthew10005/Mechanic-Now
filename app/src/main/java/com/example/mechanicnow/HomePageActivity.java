 package com.example.mechanicnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

 public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawer_Layout);
        navigationView = findViewById(R.id.nav_id);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
     public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

     @Override
     public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
         return true;
     }


     // private void setSupportActionBar(Toolbar toolbar) {
     //}
 }