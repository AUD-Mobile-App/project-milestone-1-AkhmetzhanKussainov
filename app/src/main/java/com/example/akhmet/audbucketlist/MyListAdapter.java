package com.example.akhmet.audbucketlist;

/**
 * Created by Akhmet on 3/14/2018.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


//Custom List adapter for making the UI
public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final eventClass[] events;
   // private final String[] songName;
  //  private final String[] songAuthor;

    //constructor
    public MyListAdapter(Activity context, eventClass[] eventIn) {
        super(context, R.layout.mylist,new String[eventIn.length]);
        // TODO Auto-generated constructor stub

        this.events=eventIn;
        this.context=context;
        Arrays.sort(eventIn,new sortByDates());
      /*  this.songName=itemname;
        this.songAuthor =songAuthor;*/
    }

    public MyListAdapter(Activity context,String[] itemName, List<eventClass> eventIn) {
        super(context, R.layout.mylist,itemName);
        // TODO Auto-generated constructor stub
        this.events=new eventClass[eventIn.size()];
        for(int i=0;i<events.length;i++)
        {
            events[i]=eventIn.get(i);
        }
        this.context=context;
        Arrays.sort(events,new sortByDates());
      /*  this.songName=itemname;
        this.songAuthor =songAuthor;*/
    }

    public MyListAdapter(Activity context, List<eventClass> eventIn) {
        super(context, R.layout.mylist,new String[eventIn.size()]);
        // TODO Auto-generated constructor stub
        this.events=new eventClass[eventIn.size()];
        for(int i=0;i<events.length;i++)
        {
            events[i]=eventIn.get(i);
        }
        this.context=context;
        Arrays.sort(events,new sortByDates());
      /*  this.songName=itemname;
        this.songAuthor =songAuthor;*/
    }




    //getView to set the values for list_view.xml
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        //set text appearances for the list
        TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        TextView txtDueDate=(TextView) rowView.findViewById(R.id.txtDueDate);
        txtName.setText(events[position].getName());
        DateFormat dateFormat=DateFormat.getDateInstance();
        String date=dateFormat.format(events[position].getDueDate());
        txtDueDate.setText("Deadline: "+date);

        return rowView;

    }

    public class sortByDates implements Comparator<eventClass>
    {

        @Override
        public int compare(eventClass a, eventClass b) {
            return a.getDueDate().compareTo(b.getDueDate());
        }
    }
}