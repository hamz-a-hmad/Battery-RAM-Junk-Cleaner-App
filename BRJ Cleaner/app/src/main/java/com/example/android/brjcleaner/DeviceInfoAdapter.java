package com.example.android.brjcleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sherlock on 1/1/2018.
 */

public class DeviceInfoAdapter extends ArrayAdapter<DeviceInfo>
{
    
    public DeviceInfoAdapter (@NonNull Context context, @NonNull List<DeviceInfo> objects)
    {
        super(context, 0, objects);
    }
    
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        
        DeviceInfo info = getItem(position);
        
        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        title.setTextColor(0xff000000);
        title.setText(info.deviceInfoName);
        
        TextView value = (TextView) convertView.findViewById(android.R.id.text2);
        value.setTextColor(0xff808080);
        value.setText(info.deviceInfoValue);
        
        return convertView;
    }
}
