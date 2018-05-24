package com.sampleloginapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private EditText userName;
    private RadioButton gender;
    private EditText mobileNo;
    private EditText address;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        firebaseFirestore = FirebaseFirestore.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        mobileNo = (EditText) findViewById(R.id.mobileNo);
        address = (EditText) findViewById(R.id.address);
        gender = (RadioButton) findViewById(R.id.gender);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

    }

    private void addUser(){
        Map<String,Object> user = new HashMap<>();
        user.put("username",userName.getText().toString());
        user.put("mobileno",mobileNo.getText().toString());
        user.put("address",address.getText().toString());
        user.put("gender",gender.getText().toString());

        firebaseFirestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SecondActivity.this,"Data is saved successfully.",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondActivity.this,"Submission fail.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readUser(){
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    //Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}
