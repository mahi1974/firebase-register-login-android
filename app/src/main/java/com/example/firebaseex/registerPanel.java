package com.example.firebaseex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerPanel extends AppCompatActivity {
    Button btnRegister;
    EditText fullNameTbx,emailTbx,passwordTbx,conPasswordTbx,userTbx;
    TextView loginRedirect;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= firebaseDatabase.getReference();
    myDataSend myDataSend=new myDataSend();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_panel);
        btnRegister=findViewById(R.id.registerBtn);
        fullNameTbx=findViewById(R.id.nameTbx);
        userTbx=findViewById(R.id.userNameTbx);
        emailTbx=findViewById(R.id.emailTbx);
        passwordTbx=findViewById(R.id.passwordTbx);
        conPasswordTbx=findViewById(R.id.confirmPasswordTbx);
        loginRedirect=findViewById(R.id.loginRedirectTextView);
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(registerPanel.this,MainActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=userTbx.getText().toString();
                String fname=fullNameTbx.getText().toString();
                String email=emailTbx.getText().toString();
                String pass=passwordTbx.getText().toString();
                String cpass=conPasswordTbx.getText().toString();
                if(!uname.isEmpty() && !fname.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !cpass.isEmpty()){
                    if(pass.equals(cpass)) {
                        saveUserData(fname, email, pass,uname);
                        fullNameTbx.setText(null);
                        emailTbx.setText(null);
                        passwordTbx.setText(null);
                        conPasswordTbx.setText(null);
                    }else {
                        Toast.makeText(registerPanel.this, "password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(registerPanel.this, "Please enter all  value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void saveUserData(String fName,String email,String password,String username)
    {
        myDataSend.setFullName(fName);
        myDataSend.setEmail(email);
        myDataSend.setPassword(password);
        myDataSend.setUsername(username);
        String userId=databaseReference.push().getKey();
        //myDataSend.setUserId(userId);
        if (userId != null) {
            databaseReference.child("users").child(userId).setValue(myDataSend)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(registerPanel.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                        // Redirect to login or next screen
                        Intent i =new Intent(registerPanel.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(registerPanel.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}