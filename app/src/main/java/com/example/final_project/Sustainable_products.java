package com.example.final_project;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class Sustainable_products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustainable_products);

        RadioGroup radioGroup = findViewById(R.id.rd);
        Button checkButton = findViewById(R.id.Check_sustainability);

        checkButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            if (radioButton != null) {
                Intent intent = new Intent(this, Check_sustainability2.class);
                intent.putExtra("category", radioButton.getText().toString());
                startActivity(intent);
            }
        });
    }
}
