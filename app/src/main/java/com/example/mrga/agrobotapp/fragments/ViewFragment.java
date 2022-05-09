package com.example.mrga.agrobotapp.fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrga.agrobotapp.LoginActivity;
import com.example.mrga.agrobotapp.MainActivity;
import com.example.mrga.agrobotapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {
    Context context;

    RequestQueue rq;
    TextView seedd,posd,farm;
    Button btn;
    private static String URL_VIEW= "http://10.240.72.11/agrobot/show.php";
    String seedlevel,position,farmland;
    private final String CHANNEL_ID="personale Notifaction";
    private final int NOTIFCATION_ID=001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View vieww= inflater.inflate(R.layout.fragment_view, container, false);


        rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        seedd=(TextView)vieww.findViewById(R.id.seed);
        posd=(TextView)vieww.findViewById(R.id.pos);
       // btn = (Button)vieww.findViewById(R.id.);
        //
        send();



       return vieww;
    }

    public void send(){
//
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL_VIEW,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray sensorvalue = response.getJSONArray("sensorvalue");
                    for(int i=0;i<sensorvalue.length();i++){
                        JSONObject sensor = sensorvalue.getJSONObject(i);
                        seedlevel=sensor.getString("SeedLevel");
                        position=sensor.getString("Position");
                        seedd.setText(seedlevel);
                        posd.setText(position);
                        Integer critcalLevel = Integer.valueOf(seedlevel);
                        if(critcalLevel <=50 ){
                            generateNotification(critcalLevel);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jsonObjectRequest);
    }
    public void generateNotification(int critcalLevel) {

        // This basically taken from developer.android.com
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getActivity()).setSmallIcon(R.drawable.ic_event_note_black_24dp)
                .setContentTitle("Agrobot Alarm")
                .setContentText("Teff is empty pls add teff to a Coneiner"+""+ critcalLevel);
        // Make the notification play the default notification sound:
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getActivity(), LoginActivity.class);

        // The stack builder object will contain an artificial back stack for the started
        // Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(LoginActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
}
