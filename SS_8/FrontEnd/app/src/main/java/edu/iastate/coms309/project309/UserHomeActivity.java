package edu.iastate.coms309.project309;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {




    private Button events, comps, prof, discount,shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        events = findViewById(R.id.buttonEventList);
        comps = findViewById(R.id.buttonCompanyList);


        discount=findViewById(R.id.discount);

        prof = findViewById(R.id.buttonProfile);
     shop=findViewById(R.id.buttonShop);

        events.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, EventListActivity.class));
            }
        });

        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, CompanyListActivity.class));
            }
        });

        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, PaypalActivity.class));
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, UserProfileActivity.class));
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, PointShopActivity.class));
            }
        });


    }
}
