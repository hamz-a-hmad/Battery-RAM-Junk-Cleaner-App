package com.example.android.brjcleaner;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class DeviceInformation extends AppCompatActivity
{
    
    private ArrayList<DeviceInfo> deviceInfoArrayList;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_information);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        deviceInfoArrayList = new ArrayList<DeviceInfo>();
        
        deviceInfoArrayList.add(new DeviceInfo("VERSION.RELEASE", Build.VERSION.RELEASE));
        deviceInfoArrayList.add(new DeviceInfo("VERSION.INCREMENTAL", Build.VERSION.INCREMENTAL));
        deviceInfoArrayList.add(new DeviceInfo("VERSION.SDK.NUMBER", Build.VERSION.SDK_INT + ""));
        deviceInfoArrayList.add(new DeviceInfo("BOARD", Build.BOARD));
        deviceInfoArrayList.add(new DeviceInfo("BOOTLOADER", Build.BOOTLOADER));
        deviceInfoArrayList.add(new DeviceInfo("BRAND", Build.BRAND));
        deviceInfoArrayList.add(new DeviceInfo("CPU_ABI", Build.CPU_ABI));
        deviceInfoArrayList.add(new DeviceInfo("CPU_ABI2", Build.CPU_ABI2));
        deviceInfoArrayList.add(new DeviceInfo("DISPLAY", Build.DISPLAY));
        deviceInfoArrayList.add(new DeviceInfo("FINGERPRINT", Build.FINGERPRINT));
        deviceInfoArrayList.add(new DeviceInfo("HARDWARE", Build.HARDWARE));
        deviceInfoArrayList.add(new DeviceInfo("HOST", Build.HOST));
        deviceInfoArrayList.add(new DeviceInfo("ID", Build.ID));
        deviceInfoArrayList.add(new DeviceInfo("MANUFACTURER", Build.MANUFACTURER));
        deviceInfoArrayList.add(new DeviceInfo("MODEL", Build.MODEL));
        deviceInfoArrayList.add(new DeviceInfo("PRODUCT", Build.PRODUCT));
        deviceInfoArrayList.add(new DeviceInfo("SERIAL", Build.SERIAL));
        deviceInfoArrayList.add(new DeviceInfo("TAGS", Build.TAGS));
        deviceInfoArrayList.add(new DeviceInfo("TIME", Build.TIME + ""));
        deviceInfoArrayList.add(new DeviceInfo("TYPE", Build.TYPE));
        deviceInfoArrayList.add(new DeviceInfo("USER", Build.USER));
        
        DeviceInfoAdapter infoAdapter = new DeviceInfoAdapter(getApplicationContext(), deviceInfoArrayList);
        
        ListView deviceInfoListView = (ListView) findViewById(R.id.device_info_listview);
        deviceInfoListView.setAdapter(infoAdapter);
    }
}
