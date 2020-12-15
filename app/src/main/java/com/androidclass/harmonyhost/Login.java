package com.androidclass.harmonyhost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private DatabaseHelper database;
    private EditText mEditText_email, mEditText_password;
    private TextView mTextView_register;
    private Button mButton_Login;

    private EditText mEditText_name;
    String userName = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new DatabaseHelper(this);
        mEditText_email = (EditText)findViewById(R.id.editText_email);
        mEditText_password = (EditText)findViewById(R.id.editText_password);
        mTextView_register = (TextView)findViewById(R.id.textView_login);
        mButton_Login = (Button)findViewById(R.id.button_login);

        mEditText_name = findViewById(R.id.editText_name);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // Check if user exists and get reference
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        userName = preferences.getString("userName", "");
        if(userName.equals("")){
            userRef = firebaseDatabase.getReference("users/"+ userName);
            addEventListener();
            userRef.setValue("");
        }

        mTextView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });

        mButton_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateEmail();
                validatePassword();

                if (TextUtils.isEmpty(mEditText_email.getText().toString()) || TextUtils.isEmpty(mEditText_password.getText().toString())) {
                    Toast.makeText(Login.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean findUserInDatabase = database.checkUser(mEditText_email.getText().toString().trim(), mEditText_password.getText().toString().trim());
                    // If the fields are validated, it will take the user to the login page
                    if ((findUserInDatabase == true) && (validateEmail() && validatePassword()) == true) {
                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();

                        // Logging the user in
                        userName = mEditText_name.getText().toString();
                        mEditText_name.setText("");
                        if(!userName.equals("")){

                            mButton_Login.setEnabled(false);
                            userRef = firebaseDatabase.getReference("users/" + userName);
                            addEventListener();
                            userRef.setValue("");
                        }

                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    // This method will validate email
    protected boolean validateEmail(){
        String emailInput = mEditText_email.getText().toString().trim();
        if(!EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEditText_email.setError("Please enter a valid email address");
            return false;
        }
        else{
            return true;
        }
    }

    // This is the pattern to validate Email
    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    // This method will validate the password
    public boolean validatePassword(){
        String passwordInput = mEditText_password.getText().toString().trim();
        if(!PASSWORD_VALIDATION.matcher(passwordInput).matches()) {
            mEditText_password.setError("Password too weak");
            return false;
        }
        else {
            return true;
        }
    }

    // This is the pattern to validate the password
    public static final Pattern PASSWORD_VALIDATION = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{1,}");

    private void addEventListener(){
        // Read from database
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Success, continue to the next screen after saving user Name
                if(!userName.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userName", userName);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), Home.class));
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error
                mButton_Login.setText("LOG IN");
                mButton_Login.setEnabled(true);
                Toast.makeText(Login.this,"Error!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}