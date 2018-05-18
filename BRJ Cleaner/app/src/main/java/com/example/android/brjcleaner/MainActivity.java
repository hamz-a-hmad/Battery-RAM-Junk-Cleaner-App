package com.example.android.brjcleaner;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private long totalRam, freeRam, totalInternalStorage, freeInternalStorage;
    
    public ArrayList<FrontFeatures> frontFeatures;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        frontFeatures = new ArrayList<FrontFeatures>();
        
        frontFeatures.add(new FrontFeatures(R.drawable.ic_delete_black_36dp, "Junk Clean"));
        frontFeatures.add(new FrontFeatures(R.drawable.ic_timelapse_black_36dp, "RAM Clean"));
        frontFeatures.add(new FrontFeatures(R.drawable.ic_battery_charging_full_black_36dp, "Battery Optimizer"));
        frontFeatures.add(new FrontFeatures(R.drawable.ic_adb_black_36dp, "App Manager"));
        frontFeatures.add(new FrontFeatures(R.drawable.ic_info_black_36dp, "Device Information"));
        
        FeaturesAdapter featuresAdapter = new FeaturesAdapter(this, frontFeatures);
        
        ListView featuresListView = (ListView) findViewById(R.id.features_listview);
        featuresListView.setAdapter(featuresAdapter);
        featuresListView.setOnItemClickListener(this);
        
        freeRam = freeRamMemorySize();
        
        totalRam = totalRamMemorySize();
        
        long freeRaM = freeRam * 100 / totalRam;
        
        freeInternalStorage = getAvailableInternalMemorySize();
        
        totalInternalStorage = getTotalInternalMemorySize();
        
        long freeStorage = freeInternalStorage * 100 / totalInternalStorage;
        
        ArcProgress ram = (ArcProgress) findViewById(R.id.ram_progress);
        
        ArcProgress storage = (ArcProgress) findViewById(R.id.storage_progress);
        
        ram.setProgress((int) (100 - freeRaM));
        
        storage.setProgress((int) (100 - freeStorage));
        
        ArcProgress battery = (ArcProgress) findViewById(R.id.battery_progress);
        
        battery.setProgress(getBatteryPercentage());
    }
    
    private int getBatteryPercentage ()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        return level;
    }
    
    private long freeRamMemorySize ()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        
        long availableMegs = mi.availMem / 1048576;
        
        return availableMegs;
    }
    
    private long totalRamMemorySize ()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        
        long availableMegs = mi.totalMem / 1048576;
        
        return availableMegs;
    }
    
    private long getAvailableInternalMemorySize ()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
    
    private long getTotalInternalMemorySize ()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }
    
    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = null;
        
        if(position == 0)
        {
            intent = new Intent(this, JunkClean.class);
        }
        
        else if(position == 1)
        {
            intent = new Intent(this, RAMClean.class);
        }
        
        else if(position == 2)
        {
            intent = new Intent(this, BatteryOptimizer.class);
        }
        
        else if(position == 3)
        {
            intent = new Intent(this, InstalledAppManager.class);
        }
        
        else if(position == 4)
        {
            intent = new Intent(this, DeviceInformation.class);
        }
        
        startActivity(intent);
    }
}