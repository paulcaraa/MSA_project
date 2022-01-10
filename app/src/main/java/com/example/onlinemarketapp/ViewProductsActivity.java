package com.example.onlinemarketapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarketapp.Model.Products;
import com.example.onlinemarketapp.ViewHolder.BigProductViewHolder;
import com.example.onlinemarketapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ViewProductsActivity extends AppCompatActivity {
    private Products my_prod;
    private Button backButton;
    private ImageView logo;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        Intent myIntent = getIntent();
        my_prod = (Products) myIntent.getSerializableExtra("prod");

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        backButton = findViewById(R.id.back_button);
        logo = findViewById(R.id.login_applogo);

        recyclerView = findViewById(R.id.recycler_menu_2);
        recyclerView.setHasFixedSize(true);
        recyclerView.stopScroll();
        layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewProductsActivity.this.finish();
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


        FirebaseRecyclerAdapter<Products, BigProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, BigProductViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull BigProductViewHolder holder, int position, @NonNull Products model)
            {
                model = my_prod;
                Objects.requireNonNull(holder).txtProductName.setText(model.getName());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                holder.txtProductPhone.setText("Phone No.: " + model.getPhone());
                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public BigProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_layout, parent, false);
                return new BigProductViewHolder(view);
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
