package com.example.onlinemarketapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinemarketapp.Model.Users;
import com.example.onlinemarketapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeDetailsActivity extends AppCompatActivity {

    private String username, userType;
    private EditText name_text, surname_text, phone_text, email_text, address_text;
    private Button changeDetails, cancelButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        username = Prevalent.currentOnlineUser.getUsername();
        userType = Prevalent.currentOnlineUser.getUserType();

        name_text = (EditText) findViewById(R.id.first_name_output);
        surname_text = (EditText) findViewById(R.id.surname_output);
        phone_text = (EditText) findViewById(R.id.phone_output);
        email_text = (EditText) findViewById(R.id.email_output);
        address_text = (EditText) findViewById(R.id.address_output);

        changeDetails = (Button) findViewById(R.id.change_details_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        changeDetails.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                applyChanges();
                loadingBar.dismiss();

                Intent intent = new Intent(ChangeDetailsActivity.this, AccountActivity.class);
                if(userType.equals("seller")){
                    intent = new Intent(ChangeDetailsActivity.this, SellerBrowseActivity.class);
                }
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ChangeDetailsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyChanges(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        String name = name_text.getText().toString();
        String surname = surname_text.getText().toString();
        String phone = phone_text.getText().toString();
        String email = email_text.getText().toString();
        String address = address_text.getText().toString();
        loadingBar = new ProgressDialog(this);

        if(!TextUtils.isEmpty(name)){
            System.out.println(username);
            ref.child("Users").child(username).child("name").setValue(name);
        }
        if(!TextUtils.isEmpty(surname)){
            ref.child("Users").child(username).child("surname").setValue(surname);
        }
        if(!TextUtils.isEmpty(phone)){
            ref.child("Users").child(username).child("phone").setValue(phone);
        }
        if(!TextUtils.isEmpty(email)){
            ref.child("Users").child(username).child("email").setValue(email);
        }
        if(!TextUtils.isEmpty(address)){
            ref.child("Users").child(username).child("address").setValue(address);
        }
        else {
            loadingBar.setTitle("Changing your details.. ");
            loadingBar.setMessage("Please wait while we update your account");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users userData = dataSnapshot.child("Users").child(username).getValue(Users.class);
                    Prevalent.currentOnlineUser = userData;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
}
