package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//display commitmnent percent from check boxes

public class Display_percent_commitment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_percent_commitment);

        TextView tvCount = findViewById(R.id.textView14);
        int count = getIntent().getIntExtra("percent", 0);  // Default to 0 if no data passed
        tvCount.setText("Your Percent Contribution is: " + count + "%. Keep Up the Good Work and aim for higher next time!");

        Button back_button = findViewById(R.id.button2);



        back_button.setOnClickListener(new View.OnClickListener() {
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
