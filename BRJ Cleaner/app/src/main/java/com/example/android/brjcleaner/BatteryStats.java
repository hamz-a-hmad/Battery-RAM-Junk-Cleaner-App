package com.example.android.brjcleaner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BatteryStats extends AppCompatActivity
{
    ListView batteryList;
    BatteryStatsAdapter adapter;
    ArrayList<ChargingInfo> cinfo;
    SQLiteDatabase bd;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_stats);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        bd = getApplicationContext().openOrCreateDatabase("BatteryInfo", MODE_PRIVATE, null);
        
        cinfo = new ArrayList<ChargingInfo>();
        
        batteryList = (ListView) findViewById(R.id.stats_list_View);
        
        inflateList();
        
        batteryList.setAdapter(new BatteryStatsAdapter(getApplicationContext(), cinfo));
    }
    
    public void inflateList ()
    {
        Cursor c = bd.rawQuery("SELECT * FROM " + "stats" , null);
        int Column1 = c.getColumnIndex("startdate");
        int Column2 = c.getColumnIndex("startTime");
        int Column3 = c.getColumnIndex("endDate");
        int Column4 = c.getColumnIndex("EndTime");
        int Column5 = c.getColumnIndex("BatteryBefore");
        int Column6 = c.getColumnIndex("BatteryAfter");
        
        // Check if our result was valid.
        if(c != null)
        {
            if(c.moveToFirst())
            {
                // Loop through all Results
                do
                {
                    String a = c.getString(Column1);
                    String b = c.getString(Column2);
                    String d = c.getString(Column3);
                    String e = c.getString(Column4);
                    String f = c.getString(Column5);
                    String g = c.getString(Column6);
                    
                    cinfo.add(new ChargingInfo(a, b, d, e, f, g));
                }
                while (c.moveToNext());
            }
        }
    }
}