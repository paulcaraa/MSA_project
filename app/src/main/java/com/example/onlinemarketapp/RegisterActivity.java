package com.example.onlinemarketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText inputName, inputSurname, inputEmail, inputPassword, inputUsername;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.register_btn);
        inputName = (EditText) findViewById(R.id.register_name_input);
        inputSurname = (EditText) findViewById(R.id.register_surname_input);
        inputUsername = (EditText) findViewById(R.id.register_username_input);
        inputEmail = (EditText) findViewById(R.id.register_email_input);
        inputPassword = (EditText) findViewById(R.id.register_password_input);

        createAccountButton.setOnClickListener((new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                createAccount();

            }
        }));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createAccount() {
        String username = inputUsername.getText().toString();
        String name = inputName.getText().toString();
        String surname = inputSurname.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String userType = "buyer";
        loadingBar = new ProgressDialog(this);

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please write your username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(surname)){
            Toast.makeText(this, "Please write your surname", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating your account");
            loadingBar.setMessage("Please wait while we set up your account");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validateEmail(username, name, surname, email, password, userType);
        }

    }

    private void validateEmail(final String username, final String name, final String surname, final String email, final String password, final String userType) {
        final DatabaseReference myDatabaseReference;
        myDatabaseReference = FirebaseDatabase.getInstance().getReference();



        myDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(username).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username", username);
                    userdataMap.put("email", email);
                    userdataMap.put("name", name);
                    userdataMap.put("surname", surname);
                    userdataMap.put("password", password);
                    userdataMap.put("userType", userType);
                    userdataMap.put("pending", false);


                    myDatabaseReference.child("Users").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Account has been created successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Error! Something went home. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "There is already an account made using the username " + username, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("aaaa");
            }
        });
    }
}
