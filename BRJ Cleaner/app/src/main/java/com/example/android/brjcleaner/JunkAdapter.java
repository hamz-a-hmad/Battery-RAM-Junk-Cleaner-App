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
 * Created by Sherlock on 1/2/2018.
 */

public class JunkAdapter extends ArrayAdapter<JunkApp>
{
    
    public JunkAdapter (@NonNull Context context, @NonNull ArrayList<JunkApp> objects)
    {
        super(context, 0, objects);
    }
    
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_item, parent, false);
        }
        
        final JunkApp junkApp = getItem(position);
        
        TextView appName = (TextView) convertView.findViewById(R.id.app_name);
        appName.setText(junkApp.appName);
        
        TextView appSize = (TextView) convertView.findViewById(R.id.app_size);
        appSize.setText(junkApp.junkSize);
        
        ImageView appImage = (ImageView) convertView.findViewById(R.id.app_icon);
        appImage.setImageDrawable(junkApp.appImageIcon);
        
        Switch checkSwitch = (Switch) convertView.findViewById(R.id.app_switch);
        checkSwitch.setChecked(junkApp.status);
        
        checkSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if(junkApp.status)
                {
                    junkApp.status = false;
                }
                
                else
                {
                    junkApp.status = true;
                }
            }
        });
        
        return convertView;
    }
}