package com.example.android.brjcleaner;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherlock on 1/1/2018.
 */

public class InstalledApp
{
    public String appPackage;
    public String appName;
    public String appSize;
    public Drawable appImageIcon;
    
    public ArrayList<String> appPackages = new ArrayList<String>();
    
    private Context context;
    
    public InstalledApp (Context mContext, String mAppPackage, long mAppSize)
    {
        context = mContext;
        
        appPackage = mAppPackage;
        appSize = formatData(mAppSize);
        
        appName = getApplicationName(appPackage);
        appImageIcon = getApplicationIcon(appPackage);
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
    
    private String formatData (long sizeInB)
    {
        long sizeInKB = sizeInB / 1024;
        
        long sizeInMB = sizeInKB / 1024;
        
        if(sizeInMB == 0)
        {
            return sizeInKB + "." + (sizeInB % 1024) + " KB";
        }
        
        return sizeInMB + "." + (sizeInKB % 1024) + " MB";
    }
}
