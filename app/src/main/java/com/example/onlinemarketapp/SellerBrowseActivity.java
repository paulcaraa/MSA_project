package com.example.onlinemarketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.onlinemarketapp.Prevalent.Prevalent;

public class SellerBrowseActivity extends AppCompatActivity {

    private Button logoutButton, accountButton, addProductButton, myProductsButton;
    private Spinner categorySpinner;
    private ImageView logo;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_browse);
        Toast.makeText(this, "Welcome seller!...", Toast.LENGTH_SHORT).show();

        logoutButton = findViewById(R.id.logout_button);
        accountButton = findViewById(R.id.account_button);
        addProductButton = findViewById(R.id.add_prod_button);
        categorySpinner = findViewById(R.id.category_spinner); //to do
        myProductsButton = findViewById(R.id.my_prod_button);
        logo = findViewById(R.id.login_applogo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                category = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SellerBrowseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SellerBrowseActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SellerBrowseActivity.this, SellerAddProductActivity.class);
                startActivity(intent);
            }
        });

        myProductsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SellerBrowseActivity.this, SellerMyProductsActivity.class);
                startActivity(intent);
            }
        });

    }
}
