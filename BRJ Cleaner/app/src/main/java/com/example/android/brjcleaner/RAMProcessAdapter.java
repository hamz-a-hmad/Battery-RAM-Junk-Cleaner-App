package com.example.android.brjcleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sherlock on 1/1/2018.
 */

public class RAMProcessAdapter extends ArrayAdapter<RAMProcess>
{
    public RAMProcessAdapter (@NonNull Context context, @NonNull ArrayList<RAMProcess> objects)
    {
        super(context, 0, objects);
    }
    
    @NonNull
    @Override
    public View getView (final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_item, parent, false);
        }
        
        final RAMProcess process = getItem(position);
        
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
        
        if(process.appImageIcon == null)
        {
            appIcon.setImageResource(R.drawable.ic_android_black_36dp);
        }
        
        else
        {
            appIcon.setImageDrawable(process.appImageIcon);
        }
        
        TextView appName = (TextView) convertView.findViewById(R.id.app_name);
        appName.setText(process.appName);
        
        TextView ramOccupied = (TextView) convertView.findViewById(R.id.app_size);
        ramOccupied.setText(process.estimatedRamOccupied);
        
        Switch checkSwitch = (Switch) convertView.findViewById(R.id.app_switch);
        checkSwitch.setChecked(process.status);
        
        checkSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if(process.status)
                {
                    process.status = false;
                }
                
                else
                {
                    process.status = true;
                }
            }
        });
        
        return convertView;
    }
}