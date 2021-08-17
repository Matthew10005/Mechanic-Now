package com.example.mechanicnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MechanicLogin extends AppCompatActivity implements View.OnClickListener{

    Button callSignUp, loginBtn, msign;
    TextView logo_text, slogan_text;
    TextInputLayout phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mechanic_login);

        callSignUp = findViewById(R.id.no_account);
        logo_text = findViewById(R.id.logo_name);
        slogan_text = findViewById(R.id.slogan);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);

        msign = (Button) findViewById(R.id.no_account);
        msign.setOnClickListener(this);
    }

    private Boolean validatePhone(){
        String val = phone.getEditText().getText().toString();

        if(val.isEmpty()){
            phone.setError("Phone Number Cannot be Empty");
            return false;
        }
        else{
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        String passwordValidate = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else if (!val.matches(passwordValidate)) {
            password.setError("Incorrect Password");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void userSignUp(View view) {
        Intent intent = new Intent(MechanicLogin.this, RegisterPage.class);

       /*Pair[] pairs = new Pair[7];
        pairs[1] = new Pair<View, String>(logo_text,"logo_text");
        pairs[2] = new Pair<View, String>(slogan_text,"sub_trans");
        pairs[3] = new Pair<View, String>(phone,"email_trans");
        pairs[4] = new Pair<View, String>(password,"pass_trans");
        pairs[5] = new Pair<View, String>(loginBtn,"log_trans");
        pairs[6] = new Pair<View, String>(callSignUp,"sign_trans");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MechanicLogin.this, pairs);
        startActivity(intent,options.toBundle());*/
    }

    public void loginUser(View view) {
        if(!validatePhone() | !validatePassword()){
            return;
        }else{
            isUserExist();
        }
    }

    private void isUserExist() {
        final String userEnteredPhone = phone.getEditText().getText().toString().trim();
        final String userEnteredPass = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mechanics");

        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone.setError(null);
                    phone.setErrorEnabled(false);

                    String passFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);

                    if (passFromDB.equals(userEnteredPass)) {

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredPhone).child("name").getValue(String.class);
                        String garageFromDB = dataSnapshot.child(userEnteredPhone).child("garage").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredPhone).child("email").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MechanicDashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("garage", garageFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("password", passFromDB);


                        SessionManager sessionManager = new SessionManager(MechanicLogin.this);
                        sessionManager.createLoginSession(nameFromDB, garageFromDB, emailFromDB, phoneFromDB);

                        startActivity(intent);

                        Toast.makeText(MechanicLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    phone.setError("The user does not exist");
                    phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.no_account:
                startActivity(new Intent(this, MechanicRegistration.class));
                break;}}
}