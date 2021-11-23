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

import com.example.onlinemarketapp.Model.Products;
import com.example.onlinemarketapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class GuestBrowseActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private Spinner categorySpinner;
    private ImageView logo;
    private String category = "All";
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_browse);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        categorySpinner = findViewById(R.id.category_spinner); //to do
        logo = findViewById(R.id.login_applogo);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter1);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                category = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(GuestBrowseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(GuestBrowseActivity.this, RegisterActivity.class);
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
