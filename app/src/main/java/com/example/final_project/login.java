package com.example.final_project;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class login extends AppCompatActivity {
    protected static String CURRENT_USER = "";
    // Disable the back button

    @Override
    public void onBackPressed() {
        if (MainActivity.backAllowed) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context c = this;

        // set error_txt to invisible
        TextView error_txt = findViewById(R.id.error_txt2);
        error_txt.setVisibility(View.INVISIBLE);

        Button login_user = findViewById(R.id.loginButton);

        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase db = new UserDatabase(c, "UserRegistration");

                List<String> columnHeaders = db.getColumnHeaders();

                Log.d("====== main activity =====", columnHeaders.toString());

                // get edittext views
                EditText usernameEdit = findViewById(R.id.usernameText);
                EditText passwordEdit = findViewById(R.id.passwordText);

                // convert to string
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String dbPassword = db.checkPassword(username);

                if (username.isEmpty() || password.isEmpty()) {
                    error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                    error_txt.setVisibility(View.VISIBLE);
                }

                else if (!dbPassword.equals("")) {
                    Log.d("===== Checking Password =====", "Password not empty");
                    try{
                        String userHashedPassword = Arrays.toString(User_reg.messageDigest(password));
                        Log.d("==== equal password ====", String.valueOf(dbPassword.equals(userHashedPassword)));
                        if(dbPassword.equals(userHashedPassword)){
                            Log.d("Hash check","===hashed==");

                            CURRENT_USER = username;
                            MainActivity.backAllowed = false;

                            Intent intent = new Intent(login.this, home.class);
                            startActivity(intent);
                        }}
                    catch (NoSuchAlgorithmException e) {
                        error_txt.setText("Username does not match password");
                        error_txt.setVisibility(View.VISIBLE);
                    }

                }

                else {
                    error_txt.setText("Username does not match password");
                    error_txt.setVisibility(View.VISIBLE);
                }






            }
        });


       Button back_main = findViewById(R.id.button0);



        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), MainActivity.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });
    }


}