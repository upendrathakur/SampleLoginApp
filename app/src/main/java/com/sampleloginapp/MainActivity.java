package com.sampleloginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button button;
    private TextView info;
    private int counter=5;
    private  TextView signUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.loginName);
        password =  (EditText) findViewById(R.id.loginPassword);
        button = (Button) findViewById(R.id.loginButton);
        info = (TextView) findViewById(R.id.infoTxt);
        signUp = (TextView) findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();

        info.setText("No of attempts remaining :"+String.valueOf(5));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter login name & password.",Toast.LENGTH_SHORT).show();
                }
                else {
                    login(name.getText().toString(), password.getText().toString());
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterUser.class));
            }
        });
    }
    private void login(String userName,String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Invalid Login Name & Password.",
                                    Toast.LENGTH_SHORT).show();
                            counter--;
                            if(counter==0){
                                button.setEnabled(false);
                            }
                            info.setText("No of attempts remaining :"+String.valueOf(counter));
                        }

                    }
                });

    }
}
