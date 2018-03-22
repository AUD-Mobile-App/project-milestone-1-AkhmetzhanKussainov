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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.IOException;
import java.io.InputStream;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
   // eventClass[] events;
    List<eventClass> eventsFirebase;
    SharedPreferences sharedPref;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    MyListAdapter adapter;
    List<String> keys;


    //Tag for parsing
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keys=new ArrayList<String>();
        eventsFirebase=new ArrayList<>();
        lv=findViewById(R.id.listView);
        Date date=new Date(1990,2,3);
       // eventsFirebase.add(new eventClass(date,"new name"));
     //   eventsFirebase.add(new eventClass(new Date(1920,1,5),"randomName"));
        adapter=new MyListAdapter(MainActivity.this,eventsFirebase);
        lv.setAdapter(adapter);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase= mFirebaseDatabase.getReference("events");

        sharedPref=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);


        FloatingActionButton fab;
        fab=findViewById(R.id.BtnFloating);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddItem.class));
            }
        });

        mDatabase.addChildEventListener(childEventListener);


//        new GetEvents().execute();


     //   itemname[0]=events[0].getName();
       // itemname[1]=events[1].getName();

    }

    @Override
    protected void onStart() {
        final SharedPreferences.Editor editor = sharedPref.edit();
        super.onStart();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("position", i);
                editor.commit();
                startActivity(new Intent(MainActivity.this, ItemInfoActivity.class));
            }
        });
    }




    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

            // A new comment has been added, add it to the displayed list
            eventClass event= dataSnapshot.getValue(eventClass.class);
            eventsFirebase.add(event);
            adapter=new MyListAdapter(MainActivity.this,eventsFirebase);
            lv.setAdapter(adapter);

            // ...
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so displayed the changed comment.
            Comment newComment = dataSnapshot.getValue(Comment.class);
            String commentKey = dataSnapshot.getKey();

            // ...
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so remove it.
            String commentKey = dataSnapshot.getKey();

            // ...
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            // A comment has changed position, use the key to determine if we are
            // displaying this comment and if so move it.
            Comment movedComment = dataSnapshot.getValue(Comment.class);
            String commentKey = dataSnapshot.getKey();

            // ...
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            Toast.makeText(MainActivity.this, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show();
        }
    };
}
