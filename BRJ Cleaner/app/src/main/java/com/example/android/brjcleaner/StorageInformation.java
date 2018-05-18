package com.example.android.brjcleaner;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;

import java.lang.reflect.Method;

public class StorageInformation extends IPackageStatsObserver.Stub
{
    Context context;
    
    public static long TotalAppSize;
    
    public StorageInformation (Context context)
    {
        this.context = context;
    }
    
    public void getpackageSize (String packageName)
    {
        PackageManager pm = context.getPackageManager();
        
        try
        {
            Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
            getPackageSizeInfo.invoke(pm, packageName, this);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onGetStatsCompleted (PackageStats pStats, boolean succeeded) throws RemoteException
    {
        //        Log.e("Package Name", pStats.packageName + "");
        //        Log.e("Cache Size", pStats.cacheSize + "");
        //        Log.e("Data Size", pStats.dataSize + "");
        //        Log.e("APK Size", pStats.codeSize + "");
        
        TotalAppSize = (pStats.dataSize + pStats.codeSize);
        InstalledAppManager.valueReceived = true;
        
    }
}