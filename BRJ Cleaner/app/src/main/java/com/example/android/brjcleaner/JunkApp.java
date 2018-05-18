package com.example.android.brjcleaner;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.List;

/**
 * Created by Sherlock on 1/2/2018.
 */

public class JunkApp
{
    public String appName;
    public String appPackage;
    public Drawable appImageIcon;
    public String junkSize;
    public boolean status;
    public File cacheDirectory;
    
    private Context context;
    
    public JunkApp (Context mContext, String mAppPackage, long mJunkSize, File mCacheDirectory)
    {
        context = mContext;
        status = true;
        junkSize = formatData(mJunkSize);
        appPackage = mAppPackage;
        
        cacheDirectory = mCacheDirectory;
        
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
    
    public void deleteFiles (File cache)
    {
        if(cache.isDirectory())
        {
            for (File file : cache.listFiles())
            {
                if(file.isDirectory())
                {
                    deleteFiles(file);
                }
                
                else
                {
                    file.delete();
                }
            }
            
            cache.delete();
        }
        
        else if(cache.isFile())
        {
            cache.delete();
        }
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