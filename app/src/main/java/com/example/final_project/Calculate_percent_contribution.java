package com.example.final_project;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class Calculate_percent_contribution extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_percent_contribution);

        // Find checkboxes
        CheckBox checkbox1 = findViewById(R.id.checkBox);
        CheckBox checkbox2 = findViewById(R.id.checkBox2);
        CheckBox checkbox3 = findViewById(R.id.checkBox3);
        CheckBox checkbox4 = findViewById(R.id.checkBox4);
        CheckBox checkbox5 = findViewById(R.id.checkBox5);
        CheckBox checkbox6 = findViewById(R.id.checkBox6);
        CheckBox checkbox7 = findViewById(R.id.checkBox7);
        CheckBox checkbox8 = findViewById(R.id.checkBox8);
        CheckBox checkbox9 = findViewById(R.id.checkBox9);
        CheckBox checkbox10 = findViewById(R.id.checkBox10);
        Button calculateButton = findViewById(R.id.calculateButton);

        // Listener to update checkbox count
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            int checkedCount = 0;
            if (checkbox1.isChecked()) checkedCount++;
            if (checkbox2.isChecked()) checkedCount++;
            if (checkbox3.isChecked()) checkedCount++;
            if (checkbox4.isChecked()) checkedCount++;
            if (checkbox5.isChecked()) checkedCount++;
            if (checkbox6.isChecked()) checkedCount++;
            if (checkbox7.isChecked()) checkedCount++;
            if (checkbox8.isChecked()) checkedCount++;
            if (checkbox9.isChecked()) checkedCount++;
            if (checkbox10.isChecked()) checkedCount++;

            Toast.makeText(this, "Checked: " + checkedCount, Toast.LENGTH_SHORT).show();
        };

        // Set the listener on checkboxes
        checkbox1.setOnCheckedChangeListener(listener);
        checkbox2.setOnCheckedChangeListener(listener);
        checkbox3.setOnCheckedChangeListener(listener);
        checkbox4.setOnCheckedChangeListener(listener);
        checkbox5.setOnCheckedChangeListener(listener);
        checkbox6.setOnCheckedChangeListener(listener);
        checkbox7.setOnCheckedChangeListener(listener);
        checkbox8.setOnCheckedChangeListener(listener);
        checkbox9.setOnCheckedChangeListener(listener);
        checkbox10.setOnCheckedChangeListener(listener);

        // Button click listener to calculate and navigate
        calculateButton.setOnClickListener(v -> {
            int checkedCount = 0;
            if (checkbox1.isChecked()) checkedCount++;
            if (checkbox2.isChecked()) checkedCount++;
            if (checkbox3.isChecked()) checkedCount++;
            if (checkbox4.isChecked()) checkedCount++;
            if (checkbox5.isChecked()) checkedCount++;
            if (checkbox6.isChecked()) checkedCount++;
            if (checkbox7.isChecked()) checkedCount++;
            if (checkbox8.isChecked()) checkedCount++;
            if (checkbox9.isChecked()) checkedCount++;
            if (checkbox10.isChecked()) checkedCount++;
            int percent = 100 * checkedCount / 10;

            Intent intent = new Intent(Calculate_percent_contribution.this, Display_percent_commitment.class);
            intent.putExtra("percent", percent);
            startActivity(intent);
        });
    }
}
