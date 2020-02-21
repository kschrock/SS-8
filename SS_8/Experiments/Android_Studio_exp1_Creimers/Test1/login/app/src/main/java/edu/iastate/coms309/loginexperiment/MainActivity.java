package edu.iastate.coms309.loginexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button string, json, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        string = findViewById(R.id.btnStringRequest);
        json = findViewById(R.id.btnJsonRequest);
        img = findViewById(R.id.btnImageRequest);

        string.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StringRequestActivity.class));
            }
        });

        json.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JsonRequest.class));
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageRequest.class));
            }
        });

    }
}
