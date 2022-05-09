package com.example.mrga.agrobotapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    Button forgot;
    EditText unam,phon;
    String uname,phone;
    String url = "http://10.240.72.11/agrobot/forgot_password.php";
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot=(Button)findViewById(R.id.forgot);
        unam=(EditText)findViewById(R.id.un);
        phon=(EditText)findViewById(R.id.phn);
        forg();

    }

    public void forg() {
    forgot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            uname=unam.getText().toString();
            phone= phon.getText().toString();
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("success")){
                        Toast.makeText(getApplicationContext(),"successfully  sent please check ur email",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ForgotPassword.this,"FAILED",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String ,String> params = new HashMap<>();
                    params.put("uname",uname);
                    params.put("phone",phone);
                    return super.getParams();
                }
            };
        }
    });
    }
}
