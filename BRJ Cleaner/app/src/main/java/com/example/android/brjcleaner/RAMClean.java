package com.example.android.brjcleaner;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.List;

public class RAMClean extends AppCompatActivity implements View.OnClickListener
{
    private static final String SYSTEM_PACKAGE_NAME = "android";
    private ArrayList<RAMProcess> ramProcesses = null;
    
    private Button stopButton;
    
    private RAMProcessAdapter processAdapter;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramclean);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        ramProcesses = new ArrayList<RAMProcess>();
        
        getRunningApps();
        
        processAdapter = new RAMProcessAdapter(this, ramProcesses);
        
        ListView ramProcessListView = (ListView) findViewById(R.id.ram_listview);
        ramProcessListView.setAdapter(processAdapter);
    
        TextView empty_textview = (TextView) findViewById(R.id.empty_textview);
        ramProcessListView.setEmptyView(empty_textview);
        
        stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        
        long freeRam = freeRamMemorySize();
        
        long totalRam = totalRamMemorySize();
        
        long freeRAM = freeRam * 100 / totalRam;
        
        ArcProgress ramProgress = (ArcProgress) findViewById(R.id.ram_arc_progress);
        ramProgress.setProgress((int) (100 - freeRAM));
    
        if(ramProcesses.size() == 0)
        {
            stopButton.setVisibility(View.INVISIBLE);
        }
    }
    
    private void getRunningApps ()
    {
        ActivityManager activitymanager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        
        List<ActivityManager.RunningAppProcessInfo> RAP = activitymanager.getRunningAppProcesses();
        
        for (ActivityManager.RunningAppProcessInfo processInfo : RAP)
        {
            if(!(processInfo.processName.equals("com.example.android.brjcleaner")))
            {
                if(!(isSystemApp(processInfo.processName)))
                {
                    int sizeInKB = getRamUsage(activitymanager, processInfo.pid);
                    ramProcesses.add(new RAMProcess(processInfo.processName, formatData(sizeInKB), true, getApplicationContext()));
                }
            }
        }
    }
    
    private int getRamUsage (ActivityManager activitymanager, int pid)
    {
        android.os.Debug.MemoryInfo[] memoryInfo = activitymanager.getProcessMemoryInfo(new int[]{pid});
        
        return memoryInfo[0].getTotalPss();
    }
    
    private String formatData (int sizeInKB)
    {
        int sizeInMB = sizeInKB / 1024;
        
        if(sizeInMB == 0)
        {
            return sizeInKB + " KB";
        }
        
        return sizeInMB + "." + (sizeInKB % 1024) + " MB";
    }
    
    public boolean isSystemApp (String packageName)
    {
        PackageManager mPackageManager = (PackageManager) getApplicationContext().getPackageManager();
        
        try
        {
            // Get packageinfo for target application
            PackageInfo targetPkgInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            // Get packageinfo for system package
            PackageInfo sys = mPackageManager.getPackageInfo(SYSTEM_PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            // Match both packageinfo for there signatures
            return (targetPkgInfo != null && targetPkgInfo.signatures != null && sys.signatures[0].equals(targetPkgInfo.signatures[0]));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
    
    @Override
    public void onClick (View v)
    {
        for (int i = 0; i < ramProcesses.size(); )
        {
            RAMProcess currentRamProcess = ramProcesses.get(i);
            
            if(currentRamProcess.status)
            {
                currentRamProcess.killApps();
                ramProcesses.remove(i);
            }
            else
            {
                i++;
            }
        }
        
        processAdapter.notifyDataSetChanged();
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
}