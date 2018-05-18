package com.example.android.brjcleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Toqeer ABBSI on 02-Jan-18.
 */

public class BatteryStatsAdapter extends ArrayAdapter
{
    public BatteryStatsAdapter (@NonNull Context context, @NonNull ArrayList<ChargingInfo> objects)
    {
        super(context, 0, objects);
    }
    
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_list_item, parent, false);
        }
        
        ChargingInfo cinfo = (ChargingInfo) getItem(position);
        
        TextView batteryBefore = (TextView) convertView.findViewById(R.id.battery_before);
        batteryBefore.setText("Battery Before Connecting Charger: " + cinfo.batterybefore + "%");
        
        TextView connectTextView = (TextView) convertView.findViewById(R.id.connect);
        connectTextView.setText("From:" + "     " + cinfo.sdate + "     " + cinfo.stime);
        
        TextView disconnectTextView = (TextView) convertView.findViewById(R.id.disconnect);
        disconnectTextView.setText("Till:" + "     " + cinfo.edate + "     " + cinfo.etime);
        
        TextView batteryAfter = (TextView) convertView.findViewById(R.id.battery_after);
        batteryAfter.setText("Battery After Disconnecting Charger: " + cinfo.batteryAfter + "%");
        
        return convertView;
    }
}