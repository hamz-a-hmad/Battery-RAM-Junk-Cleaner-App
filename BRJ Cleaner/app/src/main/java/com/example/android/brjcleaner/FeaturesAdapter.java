package com.example.android.brjcleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sherlock on 12/31/2017.
 */

public class FeaturesAdapter extends ArrayAdapter<FrontFeatures>
{
    public FeaturesAdapter (@NonNull Context context, @NonNull ArrayList<FrontFeatures> objects)
    {
        super(context, 0, objects);
    }
    
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feature_item, parent, false);
        }
        
        FrontFeatures feature = getItem(position);
        
        ImageView featureImage = (ImageView) convertView.findViewById(R.id.feature_item_image);
        featureImage.setImageResource(feature.imageId);
        
        TextView featureTitle = (TextView) convertView.findViewById(R.id.feature_item_title);
        featureTitle.setText(feature.title);
        
        return convertView;
    }
}