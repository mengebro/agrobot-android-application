package com.example.mrga.agrobotapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText Fname,Lname,Username,Email,Password,ConfirmPass,PhoneNumber;
    Button create;
    TextView txt;
    AlertDialog.Builder builder;
    AwesomeValidation awesomevalidation;
   // String Fname,Lname,Username,Email,Password,ConfirmPass,PhoneNumber;
   String URL_REGIST= "http://10.240.72.11/agrobot/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        valid();
        builder = new AlertDialog.Builder(RegistrationActivity.this);
//taking input values from users for the secak of registration
        create=(Button)findViewById(R.id.signUP);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(RegistrationActivity.this,"Registration Succefull !!!",Toast.LENGTH_LONG).show();
                //Formvalidaton();
                // Register();
                // startActivity(new Intent(RegistrationActivity.this,SignInActivity.class));

                if (awesomevalidation.validate()){
                    Register();
                }
                else{
                    Toast.makeText(RegistrationActivity.this,"fill correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt=(TextView)findViewById(R.id.loginSignUp);



        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,SignInActivity.class));
            }
        });

    }


    public void valid(){
        awesomevalidation = new AwesomeValidation(ValidationStyle.BASIC);
        Fname=(EditText)findViewById(R.id.fname);
        Lname=(EditText)findViewById(R.id.lname);
        Username=(EditText)findViewById(R.id.uname);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        ConfirmPass=(EditText)findViewById(R.id.comfirmpass);
        PhoneNumber=(EditText)findViewById(R.id.pnumber);
       // String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        String regexPassword ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        awesomevalidation.addValidation(RegistrationActivity.this , R.id.fname, "[a-zA-Z\\s]+", R.string.firstnameerr);
        awesomevalidation.addValidation(RegistrationActivity.this , R.id.lname, "[a-zA-Z\\s]+", R.string.lasttnameerr);
        awesomevalidation.addValidation(RegistrationActivity.this , R.id.uname, "[a-zA-Z\\s]+", R.string.usernameerr);
        awesomevalidation.addValidation(RegistrationActivity.this , R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerr);
        awesomevalidation.addValidation(RegistrationActivity.this,R.id.password,regexPassword, R.string.passworderr);
        awesomevalidation.addValidation(RegistrationActivity.this,R.id.comfirmpass,R.id.password, R.string.conf_pass_err);


    }

//    public void Formvalidaton() {
//
//        int length = Password.length();
//        final String fname=this.Fname.getText().toString();
//        final String lname=this.Lname.getText().toString();
//        final String username=this.Username.getText().toString();
//        final String email=this.Email.getText().toString();
//        final String password=this.Password.getText().toString();
//        final String confirmPass=this.ConfirmPass.getText().toString();
//        final String phoneNumber=this.PhoneNumber.getText().toString();
//
//        if (!fname.isEmpty()  || !lname.isEmpty()  || !username.isEmpty() || !email.isEmpty()  || !password.isEmpty()  || !confirmPass.isEmpty()
//                || !phoneNumber.isEmpty()
//                ) {
//
//            if (length >= 8) {
//
//                if (Password.equals(ConfirmPass)==true) {
//                    //Toast.makeText(RegistrationActivity.this, Password, Toast.LENGTH_LONG).show();
//
//                    Register();
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "pass is not match", Toast.LENGTH_LONG).show();
//                }
//
//            } else {
//                Toast.makeText(RegistrationActivity.this, "You should enter more than 8 chatarter ", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(RegistrationActivity.this, "fill the fileds ", Toast.LENGTH_LONG).show();
//
//        }
//Register();
//    }
    public void Register(){
        final String fname=this.Fname.getText().toString();
        final String lname=this.Lname.getText().toString();
        final String username=this.Username.getText().toString();
        final String email=this.Email.getText().toString();
        final String password=this.Password.getText().toString();
       // final String confirmPass=this.ConfirmPass.getText().toString();
        final String phoneNumber=this.PhoneNumber.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        try{
////                            JSONObject jsonObject = new JSONObject(response);
////                            String success = jsonObject.getString("success");
////                            if(success.equals("1")){
////                                Toast.makeText(RegistrationActivity.this,"inputted successfully",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(RegistrationActivity.this,"register error" + e.toString(),Toast.LENGTH_SHORT).show();
//                        }
                        builder.setTitle("Insertion status");
                        builder.setMessage(response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Fname.setText("");
                                Lname.setText("");
                                Username.setText("");
                                Email.setText("");
                                Password.setText("");
                                ConfirmPass.setText("");
                                PhoneNumber.setText("");

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrationActivity.this,"register error" + error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Fname",fname);
                params.put("Lname",lname);
                params.put("Username",username);
                params.put("Email",email);
                params.put("Password",password);
                params.put("PhoneNumber",phoneNumber);
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
        Mysingleton.getmInstance(RegistrationActivity.this).addTorequestque(stringRequest);
    }
}
