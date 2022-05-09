package com.example.mrga.agrobotapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.AlertDialog.Builder;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrga.agrobotapp.fragments.ControlFragment;
import com.example.mrga.agrobotapp.fragments.HomeFragment;
import com.example.mrga.agrobotapp.fragments.LogoutFragment;
import com.example.mrga.agrobotapp.fragments.ManageFragment;
import com.example.mrga.agrobotapp.fragments.ReportFragment;
import com.example.mrga.agrobotapp.fragments.SettingFragment;
import com.example.mrga.agrobotapp.fragments.ViewFragment;

import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     SessionManager sessionManager;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private final String CHANNEL_ID="personale Notifaction";
    private final int NOTIFCATION_ID=001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        //actionBar.setTitle(getResources().getString(R.string.app_name));
        sessionManager = new SessionManager(getApplicationContext());




        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // so friends my item listener is not working beacouse of some line of code i miss..
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // i miss these line so now lets check it..

        // friends now create fragments


        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

        // so now implement onNavigationItemselected
        //agrobot=(TextView)findViewById(R.id.agrobot);


    }
    public void nification(){

        String message="this is the notfication message";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);

        Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("mesaage",message);

        PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(0,builder.build());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.home) {
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();

        }
        else if (id == R.id.report) {
            ReportFragment fragment = new ReportFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "View");
            fragmentTransaction.commit();
        }
        else if (id == R.id.view) {
            ViewFragment fragment = new ViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Report");
            fragmentTransaction.commit();
        }
        else if (id == R.id.control) {
            ControlFragment fragment = new ControlFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Control");
            fragmentTransaction.commit();
        }
        else if (id == R.id.manage) {
            ManageFragment fragment = new ManageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Manage");
            fragmentTransaction.commit();
        }
        else if (id == R.id.setting) {

            SettingFragment fragment = new SettingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Setting");
            fragmentTransaction.commit();
        }

        else if (id == R.id.logout) {
//            LogoutFragment fragment = new LogoutFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frame_layout, fragment, "Logout");
//
            //  logout();
               sessionManager.logout();
            startActivity(new Intent(this,SignInActivity.class));
            finish();
            }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout() {

        if (!sessionManager.loggedin()) {
            sessionManager.setLoggedin(true);
            finish();

            startActivity(new Intent(this,SignInActivity.class));
        }
    }

        @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.language){
            changelang();
            //Toast.makeText(MainActivity.this,"Change language",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.logout){

            sessionManager.logout();
            startActivity(new Intent(this,SignInActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void changelang() {
        final String[] listItems = {"Afaan Oromoo","አማርኛ","English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("choose language");

        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if( i == 0 ){
                    setLocale("om");
                    recreate();
                }

                else if( i == 1){
                    setLocale("am");
                    recreate();
                }
                else if(i == 2){
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }



            });

//
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
       SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();

      editor.putString("My_lang", lang);
       editor.apply();
    }

    public  void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language;
        language = prefs.getString("My_lang","");
        setLocale(language);
    }
}
