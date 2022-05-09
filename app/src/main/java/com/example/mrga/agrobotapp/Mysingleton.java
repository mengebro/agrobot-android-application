package com.example.mrga.agrobotapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mctx;
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";

    private Mysingleton(Context context){

        mctx=context;
        requestQueue=getRequestQueue();
    }
    public  static synchronized Mysingleton getmInstance(Context context){
        if(mInstance==null)
        {
            mInstance= new Mysingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());

        }
        return requestQueue;
    }
    public  <T>void addTorequestque(Request<T> request){
        requestQueue.add(request);
    }
    public void logout() {
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mctx.startActivity(new Intent(mctx, LoginActivity.class));
    }
}
