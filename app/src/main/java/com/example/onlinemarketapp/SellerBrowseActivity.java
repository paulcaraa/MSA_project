package com.example.onlinemarketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.onlinemarketapp.Model.Products;
import com.example.onlinemarketapp.Prevalent.Prevalent;
import com.example.onlinemarketapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SellerBrowseActivity extends AppCompatActivity {

    private Button logoutButton, accountButton, addProductButton, myProductsButton;
    private Spinner categorySpinner;
    private ImageView logo;
    private String category;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

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

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

    @Override
    protected void onStart()
    {

        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
            {
                Objects.requireNonNull(holder).txtProductName.setText(model.getName());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                return new ProductViewHolder(view);
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
