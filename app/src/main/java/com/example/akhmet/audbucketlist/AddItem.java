package com.example.akhmet.audbucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddItem extends Activity {

    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ImageButton btnCancel=findViewById(R.id.btnCancel);

        db= FirebaseDatabase.getInstance().getReference("events");

        final EditText txtName=findViewById(R.id.txtNewEvent);
        final EditText txtDescription=findViewById(R.id.txtNewDescription);
        Button btnAdd=findViewById(R.id.btnAddItem);
        final Date d = new Date(1521745071);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = db.push().getKey();
                eventClass event=new eventClass(d,txtName.getText().toString(),txtDescription.getText().toString());
                db.child(userId).setValue(event);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
