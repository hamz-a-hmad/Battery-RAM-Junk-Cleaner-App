package com.example.android.brjcleaner;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstalledAppManager extends AppCompatActivity
{
    private final int UNINSTALL_REQUEST_CODE = 69;
    
    public ArrayList<InstalledApp> installedAppArrayList;
    public InstalledAppAdapter installedAppAdapter;
    
    private InstalledApp appToRemove;
    
    public static boolean valueReceived = false;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        installedAppArrayList = new ArrayList<InstalledApp>();
        
        getInstalledApps();
        
        installedAppAdapter = new InstalledAppAdapter(this, installedAppArrayList);
        
        ListView installAppListView = (ListView) findViewById(R.id.install_app_listview);
        installAppListView.setAdapter(installedAppAdapter);
        
        long freeInternalStorage = getAvailableInternalMemorySize();
        
        long totalInternalStorage = getTotalInternalMemorySize();
        
        long freeStorage = freeInternalStorage * 100 / totalInternalStorage;
        
        ArcProgress storageProgress = (ArcProgress) findViewById(R.id.storage_arc_progress);
        storageProgress.setProgress((int) (100 - freeStorage));
    }
    
    private void getInstalledApps ()
    {
        PackageManager pm = getPackageManager();
        
        List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        
        for (ApplicationInfo applicationInfo : applications)
        {
            if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                installedAppArrayList.add(new InstalledApp(getApplicationContext(), applicationInfo.processName, getAppSize(applicationInfo.packageName)));
            }
        }
    }
    
    public void uninstallApp (InstalledApp installedApp)
    {
        appToRemove = installedApp;
        
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + installedApp.appPackage));
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        
        startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
    }
    
    private long getAppSize (String packageName)
    {
        StorageInformation storageInformation = new StorageInformation(this);
        storageInformation.getpackageSize(packageName);
        
        while (!valueReceived)
        {
        }
        
        valueReceived = false;
        
        return StorageInformation.TotalAppSize;
    }
    
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if(requestCode == UNINSTALL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                installedAppAdapter.remove(appToRemove);
                installedAppAdapter.notifyDataSetChanged();
            }
        }
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
}