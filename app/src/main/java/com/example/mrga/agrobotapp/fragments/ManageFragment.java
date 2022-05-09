package com.example.mrga.agrobotapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrga.agrobotapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class  ManageFragment extends Fragment {

      EditText dist;
      Button order,rows;
      String row;

    private static String URL_MNG= "http://10.240.72.11/agrobot/manage.php";
    private static String URL_ROW= "http://10.240.72.11/agrobot/rowspace.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View manage= inflater.inflate(R.layout.fragment_manage, container, false);
        Spinner mySpinner = (Spinner)manage.findViewById(R.id.spinner1);
        //valid();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    //startActivity(new Intent(ViewFragment.this, HomeActivity.class));
                    row= "20 cm";
                } else if (i == 2) {
                    // startActivity(new Intent(ViewFragment.this, WorkActivity.class));
                    row = "15 cm";
                }
//                else if (i == 0) {
//                    // startActivity(new Intent(ViewFragment.this, WorkActivity.class));
//                    Toast.makeText(getActivity(),"inputted successfully!",Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dist=(EditText)manage.findViewById(R.id.inputdistance);
        order =(Button)manage.findViewById(R.id.order);
        rows =(Button)manage.findViewById(R.id.rows);
        String dst = dist.getText().toString();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dst = dist.getText().toString();
                int val = Integer.valueOf(dst);
                if(TextUtils.isEmpty(dst)) {
                    dist.setError("enter the distance");
                    return;
                }
                if (val >0 && val<300){
                manag();
                }
                else {
                    dist.setError("enter distance between 0 - 300");
                }
            }
        });
        rows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_space();
            }
        });

        return  manage;
    }
    private  void manag(){
        order.setVisibility(View.VISIBLE);
        final String distance = this.dist.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MNG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                //Toast.makeText(ManageFragment.this,"inputted successfully",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),"inputted successfully!",Toast.LENGTH_SHORT).show();
                            }
                        }
                         catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("distance",distance);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void row_space(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ROW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                //Toast.makeText(ManageFragment.this,"inputted successfully",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),"inputted successfully!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("distance",row);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void valid() {
        //username=(EditText) findViewById(R.id.username);
       // password=(EditText) findViewById(R.id.pass);
        if(dist.getText().toString().trim().length()==0){
            dist.setError(getString(R.string.usernameerr));
            dist.requestFocus();
        }

    }
}
