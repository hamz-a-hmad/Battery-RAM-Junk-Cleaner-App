package com.example.android.brjcleaner;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Toqeer ABBSI on 01-Jan-18.
 */

public class HardwareAdapter extends ArrayAdapter
{
    public ArrayList<Hardware> hardware;
    
    public HardwareAdapter (@NonNull Context context, ArrayList<Hardware> h)
    {
        super(context, 0, h);
        hardware = h;
    }
    
    @Override
    public View getView (final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //check();
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_hardware, parent, false);
        }
        if(hardware.size() > 0)
        {
            Hardware h = (Hardware) getItem(position);
            
            ImageView icon = (ImageView) convertView.findViewById(R.id.hardware_icon);
            icon.setImageResource(h.icon);
            
            TextView tv = (TextView) convertView.findViewById(R.id.hardware_name);
            tv.setText(h.name);
            
            Switch check = (Switch) convertView.findViewById(R.id.hardware_check);
            check.setChecked(!h.status);
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged (CompoundButton compoundButton, boolean b)
                {
                    if(!b)
                    {
                        DeletedItem(position);
                        
                    }
                    
                }
            });
        }
        return convertView;
    }
    
    public void TurnBluetoothoff ()
    {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        ba.disable();
    }
    
    public void TurnWifioff ()
    {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
    }
    
    public boolean toggleMobileDataConnection (boolean ON)
    {
        ConnectivityManager dataManager;
        dataManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        Method dataMtd = null;
        try
        {
            dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        }
        catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        dataMtd.setAccessible(true);
        try
        {
            dataMtd.invoke(dataManager, ON);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    
    public void setBrightness (int brightness)
    {
        ContentResolver cResolver = getContext().getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (255 * brightness / 100) + 95);
    }
    
    //255 * brightness / 100) + 95
    @Override
    public void notifyDataSetChanged ()
    {
        super.notifyDataSetChanged();
    }
    
    @Override
    public void remove (@Nullable Object object)
    {
        hardware.remove(object);
        notifyDataSetChanged();
    }
    
    @Nullable
    @Override
    public Object getItem (int position)
    {
        return hardware.get(position);
    }
    
    public void DeletedItem (int i)
    {
        String name = (String) hardware.get(i).name;
        switch (name)
        {
            case "Brightness":
            {
                
                setBrightness(15);
                this.remove(hardware.get(i));
                break;
            }
            case "Data":
            {
                
                
                toggleMobileDataConnection(false);
                this.remove(hardware.get(i));
                
                break;
            }
            case "Wifi":
            {
                
                
                TurnWifioff();
                this.remove(hardware.get(i));
                break;
            }
            case "Bluetooth":
            {
                
                
                TurnBluetoothoff();
                this.remove(hardware.get(i));
                
                break;
            }
            
        }
    }
    
    public void turnGPSOff ()
    {
        Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        getContext().getApplicationContext().startActivity(intent1);
        
    }
    
    
}