package com.example.android.brjcleaner;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BatteryOptimizer extends AppCompatActivity
{
    private BroadcastReceiver mBatInfoReceiver;
    HardwareAdapter ha;
    com.github.lzyzsd.circleprogress.ArcProgress batteryLevel;
    ArrayList<Hardware> devices = new ArrayList<Hardware>();
    
    ListView lv;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_optimizer);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Button b = (Button) findViewById(R.id.stats);
        
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent i = new Intent(getApplicationContext(), BatteryStats.class);
                startActivity(i);
            }
        });
        
        if(!checkServiceRunning())
        {
            Intent i = new Intent(this, BatteryService.class);
            startService(i);
        }
        
        batteryLevel = (com.github.lzyzsd.circleprogress.ArcProgress) findViewById(R.id.arc_progress);
        batteryLevel.setProgress(getBatteryPercentage());
        
        checkDevices();
        
        lv = (ListView) findViewById(R.id.hardware_list);
        ha = new HardwareAdapter(getApplicationContext(), devices);
        lv.setAdapter(ha);
    
        TextView empty_textview = (TextView) findViewById(R.id.empty_textview);
        lv.setEmptyView(empty_textview);
        
        if(devices.size() == 0)
        {
            b.setVisibility(View.INVISIBLE);
        }
    }
    
    public void checkDevices ()
    {
        if(BluetoothAdapter.getDefaultAdapter().isEnabled())
        {
            devices.add(new Hardware("Bluetooth", false));
        }
        
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        
        if(wifiManager.isWifiEnabled())
        {
            devices.add(new Hardware("Wifi", false));
        }
        
        boolean mobileDataAllowed = Settings.Secure.getInt(getContentResolver(), "mobile_data", 1) == 1;
        
        if(mobileDataAllowed)
        {
            devices.add(new Hardware("Data", false));
        }
        
        try
        {
            Log.e("Brightness" , ((Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS) * 100 )/ 255)+"");
            
            if(((Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS) * 100 )/ 255) > 60)
            {
                devices.add(new Hardware("Brightness", false));
            }
        }
        catch (Settings.SettingNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean checkServiceRunning ()
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if("com.example.android.brjcleaner.BatteryService".equals(service.service.getClassName()))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private int getBatteryPercentage ()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        return level;
    }
}