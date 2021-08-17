package com.example.mechanicnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName;
    TextInputLayout email;
    TextInputLayout phone_number;
    //TextInputLayout pass_word;
    TextInputLayout birth_date;
    TextView fullNameLabel, usernameLabel;
    DatabaseReference reference;
    ImageView profileImage;

    String full_name,email_add,phone,birth, user_name,pass;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        fullName = findViewById(R.id.profile_full_name);
        email = findViewById(R.id.profile_email);
        phone_number = findViewById(R.id.profile_phone);
        //pass_word = findViewById(R.id.profile_password);
        birth_date = findViewById(R.id.profile_date);
        fullNameLabel = findViewById(R.id.profile_full);
        usernameLabel = findViewById(R.id.profile_user_name);
        profileImage = findViewById(R.id.profile_picture);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
       /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserProfileFragment()).commit();
        bottomMenu();*/

        reference = FirebaseDatabase.getInstance().getReference("Users");

        //shows all the users data
        showAllData();

    }

    private void showAllData() {
        Intent intent = getIntent();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        full_name = userDetails.get(SessionManager.KEY_NAME);
        email_add = userDetails.get(SessionManager.KEY_EMAIL);
        birth = userDetails.get(SessionManager.KEY_DATE);
        phone = userDetails.get(SessionManager.KEY_PHONE);
        //pass = userDetails.get(SessionManager.KEY_PASSWORD);

        fullNameLabel.setText(full_name);
        usernameLabel.setText(user_name);
        fullName.getEditText().setText(full_name);
        email.getEditText().setText(email_add);
        birth_date.getEditText().setText(birth);
        phone_number.getEditText().setText(phone);
        //pass_word.getEditText().setText(pass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(UserProfile.this, Dashboard.class);
        startActivity(intent);
        finish();

    }

    public void updateUser(View view) {
        if (isNameChanged() | isDateChanged() | isEmailChanged() | isPhoneNumberChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data is similar and cannot be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!phone_number.equals(phone_number.getEditText().getText().toString())){
            reference.child(phone).child("phone").setValue(phone_number.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!email.equals(email.getEditText().getText().toString())){
            reference.child(phone).child("email").setValue(email.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isDateChanged() {
        if(!birth_date.equals(birth_date.getEditText().getText().toString())){
            reference.child(phone).child("date").setValue(birth_date.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

   /* private boolean isPasswordChanged() {
        if(!pass_word.equals(pass_word.getEditText().getText().toString())){
            reference.child(user_name).child("password").setValue(pass_word.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }*/

    private boolean isNameChanged() {
        if (!fullName.equals(fullName.getEditText().getText().toString())) {
            reference.child(phone).child("name").setValue(fullName.getEditText().getText().toString());
            reference.child(phone).child("name").setValue(fullNameLabel.getText().toString());
            return true;
        } else {
            return false;
        }
    }

   /* private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_profile:
                        fragment = new UserProfileFragment();
                        break;
                    case R.id.bottom_nav_map:
                        fragment = new UserMapFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

            }
        });
    }*/
}