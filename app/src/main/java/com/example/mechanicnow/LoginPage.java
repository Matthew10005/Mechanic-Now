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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    Button callSignUp, loginBtn, signup, msignup, mlogin;
    TextView logo_text, slogan_text;
    TextInputLayout phone, password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        callSignUp = findViewById(R.id.no_account);
        logo_text = findViewById(R.id.logo_name);
        slogan_text = findViewById(R.id.slogan);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);

        signup = (Button) findViewById(R.id.no_account);
        signup.setOnClickListener(this);


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
        Intent intent = new Intent(LoginPage.this, RegisterPage.class);

       /*Pair[] pairs = new Pair[7];
        pairs[1] = new Pair<View, String>(logo_text,"logo_text");
        pairs[2] = new Pair<View, String>(slogan_text,"sub_trans");
        pairs[3] = new Pair<View, String>(phone,"email_trans");
        pairs[4] = new Pair<View, String>(password,"pass_trans");
        pairs[5] = new Pair<View, String>(loginBtn,"log_trans");
        pairs[6] = new Pair<View, String>(callSignUp,"sign_trans");*/

       // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginPage.this, RegisterPage.class);
        //startActivity(intent,options.toBundle());
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    phone.setError(null);
                    phone.setErrorEnabled(false);

                    String passFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);

                    if(passFromDB.equals(userEnteredPass)){

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredPhone).child("name").getValue(String.class);
                        String ageFromDB = dataSnapshot.child(userEnteredPhone).child("age").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredPhone).child("email").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("age",ageFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phone",phoneFromDB);
                        intent.putExtra("password",passFromDB);


                        SessionManager sessionManager = new SessionManager(LoginPage.this);
                        sessionManager.createLoginSession(nameFromDB,ageFromDB,emailFromDB,phoneFromDB);

                        startActivity(intent);

                        Toast.makeText(LoginPage.this, "Login Successful",Toast.LENGTH_SHORT).show();


                    }else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else {
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
                startActivity(new Intent(this, RegisterPage.class));
                break;
        }
    }
}