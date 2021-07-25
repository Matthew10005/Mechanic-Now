package com.example.mechanicnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener{
    TextView banner2;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextCompany, editTextPhone;
    ProgressBar progressBar;
    Button registerMechanic;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        editTextFullName = (EditText)findViewById(R.id.fullnameM);
        editTextEmail = (EditText)findViewById(R.id.Memail);
        editTextPassword = (EditText)findViewById(R.id.Mpassword);
        editTextCompany = (EditText)findViewById(R.id.Mcompany);
        editTextPhone = (EditText)findViewById(R.id.Mphone);

        registerMechanic = (Button) findViewById(R.id.registerMechanic);
        registerMechanic.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        banner2 = findViewById(R.id.banner2);
        banner2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerMechanic:
                createMechanic();
                break;
        }

    }

    private void createMechanic() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String company = editTextCompany.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Please provide valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (company.isEmpty()) {
            editTextCompany.setError("Company is required");
            editTextCompany.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editTextPhone.setError("Phone Number is required");
            editTextPhone.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Mechanic mechanic = new Mechanic(fullName, phone, company, email);
                        FirebaseDatabase.getInstance().getReference("Mechanics")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(mechanic).addOnCompleteListener(new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity2.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(RegisterActivity2.this, MainActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity2.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity2.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }