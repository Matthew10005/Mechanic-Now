package com.example.mechanicnow;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechanicRegistration extends AppCompatActivity {

    TextInputLayout reg_name, reg_garage, reg_email,reg_phone,reg_pass;
    Button reg_login,reg_sign_up;
    TextView logo_text, slogan_text;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mechanic_registration);

        logo_text = findViewById(R.id.logo_name);
        slogan_text = findViewById(R.id.slogan);

        reg_name = findViewById(R.id.full_name);
        reg_garage = findViewById(R.id.garage);
        reg_email = findViewById(R.id.email);
        reg_phone = findViewById(R.id.phone_number);
        reg_pass = findViewById(R.id.password);

        reg_sign_up = findViewById(R.id.sign_btn);
        reg_login = findViewById(R.id.login_btn);
    }

    private Boolean validateName(){
        String val = reg_name.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_name.setError("Name Cannot be Empty");
            return false;
        }
        else{
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGarage(){
        String val = reg_garage.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_garage.setError("Garage Cannot be Empty");
            return false;
        }
        else{
            reg_garage.setError(null);
            reg_garage.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            reg_email.setError("Email Cannot be Empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            reg_email.setError("Invalid Email Address");
            return false;
        } else{
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = reg_phone.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_phone.setError("Phone Cannot be Empty");
            return false;
        }
        else{
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = reg_pass.getEditText().getText().toString();
        String passwordValidate = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            reg_pass.setError("Password cannot be empty");
            return false;
        } else if (!val.matches(passwordValidate)) {
            reg_pass.setError("Password is too weak");
            return false;
        }else{
            reg_pass.setError(null);
            reg_pass.setErrorEnabled(false);
            return true;
        }
    }

    public void registerMechanic(View view) {
        if(!validateName() | !validateGarage() | !validatePassword() | !validatePhone() | !validateEmail()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Mechanics");

        String full_name = reg_name.getEditText().getText().toString().trim();
        String garage = reg_garage.getEditText().getText().toString().trim();
        String email_address = reg_email.getEditText().getText().toString().trim();
        String phone_number = reg_phone.getEditText().getText().toString().trim();
        String pass_word = reg_pass.getEditText().getText().toString().trim();

        RegistrationHelper registrationHelper = new RegistrationHelper(full_name, garage, email_address, phone_number, pass_word);
        reference.child(phone_number).setValue(registrationHelper);

        Toast.makeText(MechanicRegistration.this, "Registration Successful",Toast.LENGTH_SHORT).show();

    }

    public void userLogin(View view) {
        Intent intent = new Intent(MechanicRegistration.this, MechanicLogin.class);

        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String>(logo_text,"logo_text");
        pairs[1] = new Pair<View, String>(slogan_text,"sub_trans");
        pairs[2] = new Pair<View, String>(reg_email,"email_trans");
        pairs[3] = new Pair<View, String>(reg_pass,"pass_trans");
        pairs[4] = new Pair<View, String>(reg_login,"log_trans");
        pairs[5] = new Pair<View, String>(reg_sign_up,"sign_trans");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MechanicRegistration.this, pairs);
        startActivity(intent,options.toBundle());
    }
}