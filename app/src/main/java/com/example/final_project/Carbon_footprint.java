package com.example.final_project;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Carbon_footprint extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private double emissionFactor = 0.0;
    private Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        Spinner spinner = findViewById(R.id.spinner);
        String[] vehicleTypes = {"Car", "Truck", "SUV"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicleTypes);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        Toast.makeText(getApplicationContext(), "Thanks for choosing the Vehicle type.", Toast.LENGTH_LONG).show();
        findViewById(R.id.calculate).setOnClickListener(v -> calculateCarbonFootprint());

        backbutton = findViewById(R.id.button4);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), home.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });

        Button tips_button = findViewById(R.id.button6);
        tips_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), Tips_articles.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });

    }

    private void calculateCarbonFootprint() {
        EditText electricityInput = findViewById(R.id.electricity);
        EditText milesInput = findViewById(R.id.miles);

        if (electricityInput.getText().toString().isEmpty() || milesInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        double electricity = Double.parseDouble(electricityInput.getText().toString());
        double miles = Double.parseDouble(milesInput.getText().toString()) * 30;
        final double ELECTRICITY_EMISSION_FACTOR = 0.4;
        final double MILES_EMISSION_FACTOR = emissionFactor;

        double totalFootprint = (electricity * ELECTRICITY_EMISSION_FACTOR) + (miles * MILES_EMISSION_FACTOR);
        displayResult(String.format("Total Carbon Footprint: %.2f kg CO2 per month", totalFootprint));
        String result_cc = String.format("%.2f", totalFootprint);

        try {
            writeFile("latest_Carbonfootprint.txt", result_cc); //filename:info.txt

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    //function to display results
    private void displayResult(String result) {
        frag_display_carbon_footprint fragment = frag_display_carbon_footprint.newInstance(result);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedVehicle = parent.getItemAtPosition(position).toString();
        switch (selectedVehicle) {
            case "Car":
                emissionFactor = 0.242; break;
            case "Truck":
                emissionFactor = 0.414; break;
            case "SUV":
                emissionFactor = 0.297; break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        emissionFactor = 0;
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
    public void writeFile(String filename,String result) throws JSONException, FileNotFoundException {

        String myDir = Environment.getExternalStorageDirectory() +"/Download/"+filename; //creating a file in the internal storage/Documents folder on phone.

        // String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+filename; //creating a file in the internal storage/Documents folder on phone.
        Log.d("PrintDir","====="+myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject actJSON = new JSONObject();   //create a JSONObject
        actJSON.put("Carbon footprint",result);

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
