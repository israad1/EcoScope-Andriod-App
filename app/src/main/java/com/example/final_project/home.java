package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button Carbon_footprint_Button;
    private Button Sustainable_products_Button;

    private Button alarm_Button;

    private Button tips_articlesButton;

    private Button check_percent_contribution_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Context c = this;

        welcomeMessage = findViewById(R.id.welcome);
        Carbon_footprint_Button = findViewById(R.id.bd);
        Sustainable_products_Button = findViewById(R.id.bv);
        alarm_Button = findViewById(R.id.bt);
        tips_articlesButton = findViewById(R.id.bta);
        check_percent_contribution_Button = findViewById(R.id.b_check_percent);



        Carbon_footprint_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), Carbon_footprint.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });


        Sustainable_products_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), Sustainable_products.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });

        alarm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), alarm_notifiction.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });



        tips_articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), Tips_articles.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });

        check_percent_contribution_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the next activity
                Intent messageIntent = new Intent(getApplicationContext(), Calculate_percent_contribution.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });

        UserDatabase db = new UserDatabase(c, "Users");

        String name = db.getName(login.CURRENT_USER);

        // Set the welcome message with the user's name
        welcomeMessage.setText("Welcome to EcoHelper " + name+ "!"+"\n"+ "This is Your EcoHelper: A Sustainable Living Guide designed to encourage \n" +
                "and facilitate sustainable living practices for You!");



    }




}