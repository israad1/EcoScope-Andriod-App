package com.example.final_project;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

public class User_reg extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if (MainActivity.backAllowed) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        Context c = this;

        // set error_txt to invisible
        TextView error_txt = findViewById(R.id.error_txt);
        error_txt.setVisibility(View.INVISIBLE);

        Button register_user = findViewById(R.id.registerButton);

        register_user.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                UserDatabase db = new UserDatabase(c, "UserRegistration");


                List<String> columnHeaders = db.getColumnHeaders();

                Log.d("====== main activity =====", columnHeaders.toString());

                // get edittext views
                EditText nameEdit = findViewById(R.id.nameText);
                EditText ageEdit = findViewById(R.id.ageText);
                EditText genderEdit = findViewById(R.id.genderText);
                EditText usernameEdit = findViewById(R.id.username);
                EditText passwordEdit = findViewById(R.id.password);

                // convert to string
                String name = nameEdit.getText().toString();
                String age = ageEdit.getText().toString();
                String gender = genderEdit.getText().toString();
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                LocalDateTime created = LocalDateTime.now();

                try {
                    writeFile("User.txt",name,age,username); //filename:info.txt

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                    error_txt.setVisibility(View.VISIBLE);
                }

                else {
                    if (verifyPass(password) & verifyUsername(username)) {
                        try {
                            int converted_age = Integer.parseInt(age);
                            byte[] hashed_password = messageDigest(password);
                            if (db.checkUniqueUser(username).isEmpty()) {
                                db.addInfo(username, hashed_password, name, age, gender, created.toString());

                                MainActivity.backAllowed = false;

                                Intent intent = new Intent(User_reg.this, login.class);
                                startActivity(intent);
                            }

                            else {
                                error_txt.setText("Uh oh, someone already has that username, please choose a new one.");
                                error_txt.setVisibility(View.VISIBLE);
                            }
                        }
                        catch(Exception e) {
                            error_txt.setText("Please give a valid age.");
                            error_txt.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                        error_txt.setVisibility(View.VISIBLE);
                    }
                }



            }
        });
    }

    private Boolean verifyUsername(String username) {
        if(username.length() >= 5 && username.length()<=10) {

            // check each character is alphanumeric
            for (int i = 0; i < username.length(); i++) {
                char character = username.charAt(i);

                // if not alphanumeric return false
                if (!Character.isLetterOrDigit(character)) {
                    return false;
                }
            }

            // return true since all characters are alphanumeric and length >= 5
            return true;
        }

        else {
            return false;
        }
    }

    private Boolean verifyPass(String password) {
        if(password.length() >= 8 && password.length()<=12) {
            char firstChar = password.charAt(0);    //gets first character

            return Character.isUpperCase(firstChar);    //returns if first character is upper case
        }

        else {
            return false;
        }
    }

    protected static byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }



    //for JSON files
    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestAppPermissions() {
        //  if (hasReadPermissions() && hasWritePermissions()) {
        if (hasWritePermissions()) {

            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[] {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
    }
    public void writeFile(String filename,String name,String age, String username) throws JSONException, FileNotFoundException {

        String myDir = Environment.getExternalStorageDirectory() +"/Download/"+filename; //creating a file in the internal storage/Documents folder on phone.

        // String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+filename; //creating a file in the internal storage/Documents folder on phone.
        Log.d("PrintDir","====="+myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject actJSON = new JSONObject();   //create a JSONObject
        actJSON.put("Name",name);
        actJSON.put("Age",age);
        actJSON.put("Username",username);
        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
        try {
            myOutWriter.append(actJSON.toString() + "\n");  //write JSONObject to file
            myOutWriter.close();
            fOut.close();
        }
        catch (Exception e){
            e.printStackTrace();  //to handle exceptions and errors.
        }

    }
}