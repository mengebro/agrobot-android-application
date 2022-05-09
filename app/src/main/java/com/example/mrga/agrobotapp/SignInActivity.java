package com.example.mrga.agrobotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    SessionManager sessionManager;
    Button login;
    EditText username,password;
    TextView text,forget;
    private RequestQueue requestQueue;
    private static final String URL = "http://10.240.72.11/agrobot/user_control.php";
    private StringRequest request;
    AwesomeValidation awesomevalidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
                sessionManager = new SessionManager(getApplicationContext());
                if(sessionManager.loggedin()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }


        valid();

        text=(TextView)findViewById(R.id.signup);
        forget=(TextView)findViewById(R.id.frgt);

        login=(Button)findViewById(R.id.login);
        requestQueue = Volley.newRequestQueue(this);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,RegistrationActivity.class));
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ForgotPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(radioID) ;
                //startActivity(new Intent(SignInActivity.this,MainActivity.class));
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")) {

                                Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                               /* if (radioButton.getText().equals("User")) {
                               /* HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put("username",username.getText().toString());
                                hashMap.put("password",password.getText().toString());
                               // sessionManager.createSession(hashMap.get(username),hashMap.get(password));
                               sessionManager.createLoginSession(hashMap.get(username),hashMap.get(password));

                                    sessionManager.setLoggedin(true);

                                    Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                                if (radioButton.getText().equals("Kebele")) {
                                    sessionManager.setLoggedin(true);
                                    Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                                    finish();
                                }*/
                            }else {
                                Toast.makeText(getApplicationContext(), "Error" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("username",username.getText().toString());
                        hashMap.put("password",password.getText().toString());
                        //sessionManager.createSession(hashMap.get(username),hashMap.get(password));
                       // sessionManager.createSession(hashMap.get(username),hashMap.get(password));
                    ///   sessionManager.createLoginSession(hashMap.get(username),hashMap.get(password));
                       sessionManager.createLoginSession(hashMap.get(username),hashMap.get(password));
                        return hashMap;
                    }

                };

                requestQueue.add(request);
            }
        });




    }

    private void valid() {
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.pass);
        if(username.getText().toString().trim().length()==0){
            username.setError(getString(R.string.usernameerr));
            username.requestFocus();
        }
       else if(password.getText().toString().trim().length()==0){
            password.setError(getString(R.string.passworderr));
            password.requestFocus();
        }
    }
    public void checkedbutton(View view){
        int radioID=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(radioID) ;
    }


}
