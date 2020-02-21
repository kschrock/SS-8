package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

// import android.support.v7.app.AppCompatActivity; cannot resolve symbol "v7"
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class DevHomeActivity extends AppCompatActivity {

    Button b1, b2, b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_home);

        b1 = findViewById(R.id.buttonGotoQR);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DevHomeActivity.this, QrScanner.class));
            }
        });

        b2 = findViewById(R.id.buttonGotoProfile);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DevHomeActivity.this, UserProfileActivity.class));
            }
        });

        b3 = findViewById(R.id.buttonGotoEventList);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DevHomeActivity.this, EventListActivity.class));
            }
        });
        b4 = findViewById(R.id.buttonGotoPaypal);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DevHomeActivity.this, PaypalActivity.class));
            }
        });
    }

    private void startScanner() {
        new IntentIntegrator(this).initiateScan();
    }
}
