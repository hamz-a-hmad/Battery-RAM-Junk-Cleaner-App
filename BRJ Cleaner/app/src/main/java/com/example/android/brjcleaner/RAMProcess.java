package com.example.android.brjcleaner;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Sherlock on 1/1/2018.
 */

public class RAMProcess
{
    public String appPackage;
    public String appName;
    public Drawable appImageIcon;
    public String estimatedRamOccupied;
    public boolean status;
    Context context;
    
    public RAMProcess (String mAppPackage, String mEstimatedRamOccupied, boolean mStatus, Context mContext)
    {
        context = mContext;
        appPackage = mAppPackage;
        appName = getApplicationName(appPackage);
        
        if(TextUtils.isEmpty(appName))
        {
            appName = appPackage;
        }
        
        appImageIcon = getApplicationIcon(appPackage);
        estimatedRamOccupied = mEstimatedRamOccupied;
        status = mStatus;
    }
    
    private String getApplicationName (String packageName)
    {
        PackageManager pm = context.getPackageManager();
        
        List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        
        for (ApplicationInfo applicationInfo : applications)
        {
            if(applicationInfo.packageName.equals(packageName))
            {
                return ((String) (pm.getApplicationLabel(applicationInfo)));
            }
        }
        
        return "";
    }
    
    private Drawable getApplicationIcon (String packageName)
    {
        PackageManager pm = context.getPackageManager();
        
        try
        {
            return pm.getApplicationIcon(packageName);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void killApps ()
    {
        List<ActivityManager.RunningAppProcessInfo> processes;
        ActivityManager amg;
        
        // using Activity service to list all process
        amg = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        // list all running process
        processes = amg.getRunningAppProcesses();
        
        for (ActivityManager.RunningAppProcessInfo info : processes)
        {
            // kill selected process
            if((info.processName.equals(appPackage)))
            {
                Log.e("Apps Killed", info.processName);
                android.os.Process.killProcess(info.pid);
                android.os.Process.sendSignal(info.pid, android.os.Process.SIGNAL_KILL);
                amg.killBackgroundProcesses(info.processName);
            }
        }
    }
}