package com.example.final_project;


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

import androidx.appcompat.app.AppCompatActivity;

public class Tips_articles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_articles);

        // Set up click listeners for each button
        setupButtonClickListener(R.id.b1, "https://www.investopedia.com/terms/s/sustainability.asp#:~:text=In%20the%20broadest%20sense%2C%20sustainability,available%20for%20the%20long%20term.");
        setupButtonClickListener(R.id.b2, "https://www.un.org/en/academic-impact/sustainability");
        setupButtonClickListener(R.id.b3, "https://www.wm.com/us/en/recycle-right/recycling-101");
        setupButtonClickListener(R.id.b4, "https://www.saveonenergy.com/resources/energy-consumption/");

    }

    private void setupButtonClickListener(int buttonId, final String url) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use an Intent to open the URL in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button backbutton = findViewById(R.id.button5);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), home.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });
    }


}
