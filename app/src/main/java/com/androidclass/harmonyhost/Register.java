package com.androidclass.harmonyhost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.util.regex.Pattern;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class Register extends AppCompatActivity {

    DatabaseHelper database;
    private EditText mTextUsername, mTextEmail, mTextPassword;
    private Button mButtonRegister;
    private TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextEmail = (EditText)findViewById(R.id.editText_email);
        mTextUsername = (EditText)findViewById(R.id.editText_username);
        mTextPassword = (EditText)findViewById(R.id.editText_password);

        database = new DatabaseHelper(this);

        mTextViewLogin = (TextView)findViewById(R.id.textView_login);

        // By clicking the Login TextView, you will be able to go into the Login page
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(Register.this,Login.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister = (Button)findViewById(R.id.button_register);
        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // validateEmail() method is called to verify the email
                validateEmail();

                // validateUsername() method is called to verify the username
                validateUserName();

                // validatePassword() method is called to verify the password
                validatePassword();


                // This if statement checks if the fields are empty
                // If the fields are empty, the message "fields cannot be empty" will be displayed
                if(TextUtils.isEmpty(mTextEmail.getText().toString()) || TextUtils.isEmpty(mTextUsername.getText().toString()) || TextUtils.isEmpty(mTextPassword.getText().toString())){
                    Toast.makeText(Register.this, "fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    // If the fields are validated, it will take the user to the login page
                    if((validateEmail() &&  validateUserName() && validatePassword())==true) {
                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                        long val= database.addUser(mTextEmail.getText().toString(),mTextPassword.getText().toString());
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // This method will validate email
    protected boolean validateEmail(){
        String emailInput = mTextEmail.getText().toString().trim();
        if(!EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mTextEmail.setError("Please enter a valid email address");
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


    // This method will validate username
    public boolean validateUserName(){
        String userNameInput = mTextUsername.getText().toString().trim();
        if(!USERNAME_VALIDATION.matcher(userNameInput).matches()) {
            mTextUsername.setError("Please enter a valid username");
            return false;
        }
        else {
            return true;
        }
    }

    // This is the pattern to validate last name
    public static final Pattern USERNAME_VALIDATION = Pattern.compile("[a-zA-z\\s]{3,30}");


    // This method will validate the password
    public boolean validatePassword(){
        String passwordInput = mTextPassword.getText().toString().trim();
        if(!PASSWORD_VALIDATION.matcher(passwordInput).matches()) {
            mTextPassword.setError("Password too weak");
            return false;
        }
        else {
            return true;
        }
    }

    // This is the pattern to validate the password
    public static final Pattern PASSWORD_VALIDATION = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{1,}");

}