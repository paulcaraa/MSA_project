package com.example.onlinemarketapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarketapp.Model.Products;
import com.example.onlinemarketapp.R;
import com.example.onlinemarketapp.ViewProductsActivity;

public class BigProductViewHolder extends RecyclerView.ViewHolder
{
    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductPhone;
    public ImageView imageView;
    public Products product;


    public BigProductViewHolder(final View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtProductPhone = (TextView) itemView.findViewById(R.id.product_phone);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = itemView.getContext();
//                context.startActivity(new Intent(context, ViewProductsActivity.class));
//            }
//        });
    }
}
