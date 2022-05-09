package com.example.mrga.agrobotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
   SessionManager sessionManager;
    Button login;
    EditText username,password;
    TextView text;
   String UserName,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username=(EditText) findViewById(R.id.username);
         password=(EditText) findViewById(R.id.pass);
         text=(TextView)findViewById(R.id.signup);

         login=(Button)findViewById(R.id.login);




    }
    public void LoginFromUser(){
        UserName=username.getText().toString().trim();
        
    }

}
