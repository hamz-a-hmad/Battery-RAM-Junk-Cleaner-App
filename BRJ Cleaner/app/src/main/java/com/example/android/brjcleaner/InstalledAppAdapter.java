package com.example.android.brjcleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sherlock on 1/1/2018.
 */

public class InstalledAppAdapter extends ArrayAdapter<InstalledApp>
{
    private Context context;
    
    public InstalledAppAdapter (@NonNull Context context, @NonNull ArrayList<InstalledApp> objects)
    {
        super(context, 0, objects);
        this.context = context;
    }
    
    @NonNull
    @Override
    public View getView (final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_install_item, parent, false);
        }
        
        final InstalledApp installedApp = getItem(position);
        
        TextView appName = (TextView) convertView.findViewById(R.id.app_name);
        appName.setText(installedApp.appName);
        
        TextView appSize = (TextView) convertView.findViewById(R.id.app_size);
        appSize.setText(installedApp.appSize);
        
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
        appIcon.setImageDrawable(installedApp.appImageIcon);
        
        Button uninstall = (Button) convertView.findViewById(R.id.app_button);
        uninstall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                InstalledAppManager installedAppManager = (InstalledAppManager) context;
                installedAppManager.uninstallApp(installedApp);
            }
        });
        
        return convertView;
    }
}