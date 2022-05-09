package com.example.mrga.agrobotapp.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mrga.agrobotapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class   ReportFragment extends Fragment {


    private String jsonResult;

    //private String url = "http://10.240.72.11/agrobot/report.php?string1=" + string1;
    private ListView listView;
    EditText et;
    Button bt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View reportt= inflater.inflate(R.layout.fragment_report, container, false);

        listView = (ListView)reportt.findViewById(R.id.listview1);
        et=(EditText)reportt.findViewById(R.id.ddate);
        bt =(Button)reportt.findViewById(R.id.generate);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accessWebService();
            }
        });
        //initList();

        return reportt;
    }


    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getActivity(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrawer();
        }
    }
    public void accessWebService() {

        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        final String string1= et.getText().toString();
         String url = "http://10.240.72.11/agrobot/report.php?string1=" + string1;
        task.execute(new String[] { url });
    }
    public void ListDrawer() {
        List<Map<String, String>> reportList = new ArrayList<Map<String, String>>();

        try {
           // Log.i("tagconvertstr", "["+result+"]");
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("emp_info");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("sowDate");
                String number = jsonChildNode.optString("Area");
                String outPut = name + "sowed date-" + number+"distance";
                reportList.add(createReport("report", outPut));
            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), reportList,
                android.R.layout.simple_list_item_1,
                new String[] { "report" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);
    }
    private HashMap<String, String> createReport(String name, String number) {
        HashMap<String, String> reporting = new HashMap<String, String>();
        reporting.put(name, number);
        return reporting;
    }
}
