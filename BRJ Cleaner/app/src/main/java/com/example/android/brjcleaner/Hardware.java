package com.example.android.brjcleaner;

/**
 * Created by Toqeer ABBSI on 01-Jan-18.
 */

public class Hardware
{
    String name;
    boolean status;
    int icon;
    
    Hardware (String n, boolean s)
    {
        SetIcon(n);
        name = n;
        status = s;
    }
    
    private void SetIcon (String n)
    {
        switch (n)
        {
            case "Bluetooth":
            {
                icon = R.drawable.ic_bluetooth_black_36dp;
                break;
            }
            case "Wifi":
            {
                icon = R.drawable.ic_wifi_black_36dp;
                break;
            }
            case "Brightness":
            {
                icon = R.drawable.ic_brightness_7_black_36dp;
                break;
            }
            case "Data":
            {
                icon = R.drawable.ic_network_cell_black_36dp;
                break;
            }
            case "Gps":
            {
                icon = R.drawable.ic_gps_fixed_black_36dp;
                break;
            }
        }
        
    }
    
}
