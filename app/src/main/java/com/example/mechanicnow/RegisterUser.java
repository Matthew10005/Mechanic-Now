package com.example.mechanicnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAge, editTextPhone;
    ProgressBar progressBar;


    private FirebaseAuth mAuth;
    //private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        //fstore = FirebaseFirestore.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);


        editTextFullName = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextAge = findViewById(R.id.age);
        editTextPhone = findViewById(R.id.phone);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

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
        if (age.isEmpty()) {
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, phone, age, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                } else {
                                    Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
}


   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                createUser();
                break;
        }
    }

    private void createUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String fullName = editTextFullName.getText().toString();
        String age = editTextAge.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email cannot be empty");
            editTextEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password cannot be empty");
            editTextPassword.requestFocus();
        } else if (TextUtils.isEmpty(fullName)) {
            editTextFullName.setError("Full Name cannot be empty");
            editTextFullName.requestFocus();
        } else if (TextUtils.isEmpty(age)) {
            editTextAge.setError("Age cannot be empty");
            editTextAge.requestFocus();
        } else if (password.length()<6) {
            editTextPassword.setError("Age cannot be less than 6 characters");
            editTextPassword.requestFocus();
        } else if (age.length()==1) {
            editTextAge.setError("Too young");
            editTextAge.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        User user = new User(fullName, age, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            }

                        });
                    }
                }
            });*/
        /*User user = new User(fullName, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override*/


/*userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("user").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("fName",fullName);
        user.put("email",email);
        user.put("phone",phone);
        user.put("age",age);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
@Override
public void onSuccess(Void unused) {
        Log.d(TAG,"onSuccess: user Profile is created for "+userID);
        }
        });*/