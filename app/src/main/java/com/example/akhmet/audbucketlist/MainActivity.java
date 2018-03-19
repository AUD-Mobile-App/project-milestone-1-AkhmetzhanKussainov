package com.example.akhmet.audbucketlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    private ProgressDialog pDialog;
    eventClass[] events;
    String[] itemname;
    SharedPreferences sharedPref;


    //Tag for parsing
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.listView);

      //  Date date=new Date(1990,2,3);
      //  events[0]=new eventClass(date,"name1");
      //  events[1]=new eventClass(date,"name2");


        FloatingActionButton fab;
        fab=findViewById(R.id.BtnFloating);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddItem.class));
            }
        });





        new GetEvents().execute();


     //   itemname[0]=events[0].getName();
       // itemname[1]=events[1].getName();




    }




    private class GetEvents extends AsyncTask<Void, Void, Void> {
        //make actions before starting of execution
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //connect to the server,load and parse json
        @Override
        protected Void doInBackground(Void... arg0) {

            // Making a request to url and getting response
            String jsonStr = loadJSONFromAsset(MainActivity.this);

            Log.e(TAG, "Response from url: " + jsonStr);

            //check if recived json
            if (jsonStr != null) {
                try {
                    //initializing json objects
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //take array "songs" to parse
                    JSONArray jsonEvents = jsonObj.getJSONArray("events");

                    events=new eventClass[jsonEvents.length()];
                    //parsing of json values to store into songInfo
                    for (int i = 0; i < jsonEvents.length(); i++) {

                        JSONObject c = jsonEvents.getJSONObject(i);
                        //parsing by tag
                        String name = c.getString("name");
                        String description = c.getString("description");
                        Long dueDate = c.getLong("dueDate");
                        Date d = new Date(dueDate * 1000);
                        eventClass event=new eventClass(d,name);
                        events[i]=event;
                    }

                }//catch error during parsing
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }

            }//if could not receive json
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
        //commands after parsing json file
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            itemname=new String[events.length];
            if (pDialog.isShowing())
                pDialog.dismiss();
            MyListAdapter adapter=new MyListAdapter(MainActivity.this,itemname,events);
            lv.setAdapter(adapter);
            sharedPref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            final SharedPreferences.Editor editor=sharedPref.edit();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    editor.putInt("position",i);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,ItemInfoActivity.class));
                }
            });







        }

        public String loadJSONFromAsset(Context context) {
            String json = null;
            try {
                InputStream is = getResources().openRawResource(R.raw.events);

                int size = is.available();

                byte[] buffer = new byte[size];

                is.read(buffer);

                is.close();

                json = new String(buffer, "UTF-8");


            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }







    }
}
