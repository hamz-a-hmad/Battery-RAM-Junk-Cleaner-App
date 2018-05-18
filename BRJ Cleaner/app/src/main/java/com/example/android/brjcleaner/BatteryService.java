package com.example.android.brjcleaner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Toqeer ABBSI on 02-Jan-18.
 */

public class BatteryService extends Service
{
    SQLiteDatabase bd;
    BroadcastReceiver batteryReciever;
    
    @Override
    public int onStartCommand (Intent intent, int flags, final int startId)
    {
        bd = getApplicationContext().openOrCreateDatabase("BatteryInfo", MODE_PRIVATE, null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS stats(startdate VARCHAR, startTime VARCHAR,endDate VARCHAR, EndTime VARCHAR, BatteryBefore VARCHAR, BatteryAfter VARCHAR);");
        
        batteryReciever = new BroadcastReceiver()
        {
            private String sdate, stime, edate, etime, batteryBefore, batteryAfter;
            
            @Override
            public void onReceive (Context context, Intent intent)
            {
                String action = intent.getAction();
                if(action.equals(Intent.ACTION_POWER_CONNECTED))
                {
                    sdate = ParseDate(System.currentTimeMillis());
                    stime = ParseTime(System.currentTimeMillis());
                    batteryBefore = getBatteryPercentage() + "";
                    
                    Toast.makeText(getApplicationContext(), "Power Connected", Toast.LENGTH_SHORT).show();
                }
                else if(action.equals(Intent.ACTION_POWER_DISCONNECTED))
                {
                    edate = ParseDate(System.currentTimeMillis());
                    etime = ParseTime(System.currentTimeMillis());
                    
                    batteryAfter = getBatteryPercentage() + "";
                    
                    bd.execSQL("insert into stats (startdate,startTime,endDate,EndTime,BatteryBefore,BatteryAfter) values ('" + sdate + "', '" + stime + "','" + edate + "','" + etime + "','" + batteryBefore + "','" + batteryAfter + "')");
                    
                    Toast.makeText(getApplicationContext(), "Power Disconnected", Toast.LENGTH_SHORT).show();
                }
            }
        };
        
        this.registerReceiver(this.batteryReciever, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        this.registerReceiver(this.batteryReciever, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        
        return START_STICKY;
    }
    
    @Nullable
    
    @Override
    public IBinder onBind (Intent intent)
    {
        return null;
    }
    
    public String ParseDate (long t)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(t);
        return format.format(d);
    }
    
    public String ParseTime (long t)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date(t);
        return format.format(d);
    }
    
    private int getBatteryPercentage ()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        return level;
    }
}