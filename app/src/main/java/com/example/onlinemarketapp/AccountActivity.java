package com.example.onlinemarketapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlinemarketapp.Model.Users;
import com.example.onlinemarketapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    private String username, userType;
    private TextView username_text, name_text, surname_text, phone_text, email_text, address_text, usertype_text;
    private Button changeDetails, applySeller, logoutButton, browseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        username = Prevalent.currentOnlineUser.getUsername();
        userType = Prevalent.currentOnlineUser.getUserType();



        username_text = (TextView) findViewById(R.id.username_output);
        name_text = (TextView) findViewById(R.id.first_name_output);
        surname_text = (TextView) findViewById(R.id.surname_output);
        phone_text = (TextView) findViewById(R.id.phone_output);
        email_text = (TextView) findViewById(R.id.email_output);
        address_text = (TextView) findViewById(R.id.address_output);
        usertype_text = (TextView) findViewById(R.id.usertype_output);

        changeDetails = (Button) findViewById(R.id.change_details_button);
        applySeller = (Button) findViewById(R.id.seller_apply_button);
        logoutButton = (Button) findViewById(R.id.logout_button);
        browseButton = (Button) findViewById(R.id.browse_button);

        if(userType.equals("buyer") && !Prevalent.currentOnlineUser.isPending()){
            applySeller.setVisibility(View.VISIBLE);
        }


        final DatabaseReference myDatabaseReference;
        myDatabaseReference = FirebaseDatabase.getInstance().getReference();
        myDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(username).exists()) {
                    Users userData = dataSnapshot.child("Users").child(username).getValue(Users.class);
                    username_text.setText(username);
                    name_text.setText(userData.getName());
                    surname_text.setText(userData.getSurname());
                    phone_text.setText(userData.getPhone());
                    email_text.setText(userData.getEmail());
                    address_text.setText(userData.getAddress());
                    usertype_text.setText(userType);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
    });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, BrowseActivity.class);
                if(userType.equals("seller")){
                    intent = new Intent(AccountActivity.this, SellerBrowseActivity.class);
                }
                startActivity(intent);
            }
        });

        changeDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ChangeDetailsActivity.class);
                startActivity(intent);
            }
        });

        applySeller.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ref.child("Users").child(username).child("pending").setValue(true);
                        Users userData = dataSnapshot.child("Users").child(username).getValue(Users.class);
                        Prevalent.currentOnlineUser = userData;
                        Intent intent = new Intent(AccountActivity.this, BrowseActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}