package com.sampleloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView alreadyRegister;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setUIViews();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterUser.this,"Registration successfully.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterUser.this,MainActivity.class));
                            }else
                            {
                                Toast.makeText(RegisterUser.this,"Registration failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        alreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUIViews() {
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        alreadyRegister = (TextView) findViewById(R.id.alreadyRegister);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    private boolean validate() {
        if (loginPassword.getText().toString().isEmpty() || loginEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
