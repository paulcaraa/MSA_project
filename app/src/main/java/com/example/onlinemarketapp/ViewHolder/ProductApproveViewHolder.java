package com.example.onlinemarketapp.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarketapp.LoginActivity;
import com.example.onlinemarketapp.Model.Products;
import com.example.onlinemarketapp.R;

public class ProductApproveViewHolder extends RecyclerView.ViewHolder
{
    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductPhone;
    public ImageView imageView;
    public Products product;


    public ProductApproveViewHolder(final View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtProductPhone = (TextView) itemView.findViewById(R.id.product_phone);

        itemView.findViewById(R.id.approve_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setPending(false);
                product.setApproved(true);
                Toast.makeText(itemView.getContext(), "Item approved!", Toast.LENGTH_SHORT).show();

            }
        });

        itemView.findViewById(R.id.disprove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setPending(false);
                product.setApproved(false);
                Toast.makeText(itemView.getContext(), "Item disproved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
