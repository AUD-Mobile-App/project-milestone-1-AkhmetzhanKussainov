package com.example.akhmet.audbucketlist;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemInfoActivity extends AppCompatActivity {

    int inputPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        final SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        inputPosition=sharedPref.getInt("position",0);
    }
}
