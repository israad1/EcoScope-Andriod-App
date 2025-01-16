package com.example.final_project;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Check_sustainability2 extends AppCompatActivity {
    private Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sustainability2);
        TextView resultTextView = findViewById(R.id.text_display);

        String category = getIntent().getStringExtra("category");

        new CheckSustainabilityTask(resultTextView).execute(category);

        //go back to home page
        back_button = findViewById(R.id.button3);
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

    private static class CheckSustainabilityTask extends AsyncTask<String, Void, String> {
        private final TextView resultTextView;

        CheckSustainabilityTask(TextView resultTextView) {
            this.resultTextView = resultTextView;
        }

        @Override
        protected String doInBackground(String... params) {
            return chatGPT("Is the product category '" + params[0] + "' sustainable?");
        }

        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
        }

        private String chatGPT(String prompt) {
            String url = "https://api.openai.com/v1/chat/completions";
            String apiKey = "sk-proj-XzbbEdHQ7sbwc70xsj94T3BlbkFJFUwLJ9TqBlHpcSkt5pfc";
            String model = "gpt-3.5-turbo";

            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");

                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();
                    return extractMessageFromJSONResponse(response.toString());
                } else {
                    // Log and return the server's error response
                    String errorLine;
                    BufferedReader brError = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    StringBuilder responseError = new StringBuilder();
                    while ((errorLine = brError.readLine()) != null) {
                        responseError.append(errorLine);
                    }
                    brError.close();
                    return "Server returned HTTP response code: " + responseCode + " with error message: " + responseError.toString();
                }
            } catch (IOException e) {
                return "Failed to fetch data: " + e.getMessage();
            }
        }


        private String extractMessageFromJSONResponse(String response) {
            int start = response.indexOf("content")+ 11;
            int end = response.indexOf("\"", start);
            return response.substring(start, end);
        }
    }
}
