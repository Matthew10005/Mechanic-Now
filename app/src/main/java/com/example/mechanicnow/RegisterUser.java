package com.example.mechanicnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextDate;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        editTextFullName = (EditText)findViewById(R.id.fullname);
        editTextEmail = (EditText)findViewById(R.id.email);
        editTextPassword = (EditText)findViewById(R.id.password);
        editTextDate = (EditText)findViewById(R.id.dateofbirth);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();
        String fullName =editTextFullName.getText().toString().trim();
        String date =editTextDate.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full NAme is required");
            editTextFullName.requestFocus();
            return;
        }

        if(age.isEmpty
        }
    }
}