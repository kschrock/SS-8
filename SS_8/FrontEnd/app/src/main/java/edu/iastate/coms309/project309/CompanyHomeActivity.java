package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompanyHomeActivity extends AppCompatActivity {

    private Button event, prize,qrMaker, qrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);


        event = findViewById(R.id.buttonMakeEvent);
        prize = findViewById(R.id.buttonMakePrize);
        qrMaker=findViewById(R.id.buttonMakeQRCode);

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyHomeActivity.this, Notification.class));
            }
        });

        prize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyHomeActivity.this, MakePrizeActivity.class));
            }
        });
        qrMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyHomeActivity.this, QRMakerActivity.class));
            }
        });
       qrList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyHomeActivity.this, QrView.class));
            }
        });
    }
}
