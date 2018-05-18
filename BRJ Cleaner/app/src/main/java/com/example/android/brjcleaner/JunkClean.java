package com.example.android.brjcleaner;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.File;
import java.util.ArrayList;

public class JunkClean extends AppCompatActivity implements View.OnClickListener
{
    private ArrayList<JunkApp> junkAppArrayList;
    private JunkAdapter junkAdapter;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junk_clean);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        junkAppArrayList = new ArrayList<JunkApp>();
        
        clearApplicationData(getApplicationContext());
        
        junkAdapter = new JunkAdapter(getApplicationContext(), junkAppArrayList);
        
        ListView junkAppListView = (ListView) findViewById(R.id.junk_listview);
        junkAppListView.setAdapter(junkAdapter);
    
        TextView empty_textview = (TextView) findViewById(R.id.empty_textview);
        junkAppListView.setEmptyView(empty_textview);
        
        Button cleanButton = (Button) findViewById(R.id.clean_button);
        cleanButton.setOnClickListener(this);
        
        long freeInternalStorage = getAvailableInternalMemorySize();
        
        long totalInternalStorage = getTotalInternalMemorySize();
        
        long freeStorage = freeInternalStorage * 100 / totalInternalStorage;
        
        ArcProgress storageProgress = (ArcProgress) findViewById(R.id.storage_arc_progress);
        storageProgress.setProgress((int) (100 - freeStorage));
    
        if(junkAppArrayList.size() == 0)
        {
            cleanButton.setVisibility(View.INVISIBLE);
        }
    }
    
    public void clearApplicationData (Context context)
    {
        
        if(context.getCacheDir() != null)
        {
            File root = new File(context.getExternalCacheDir().getParent());
            
            root = new File(root.getParent());
            
            String[] children = root.list();
            
            for (int i = 0; i < children.length; i++)
            {
                if(!children[i].equals("com.example.android.brjcleaner"))
                {
                    calculateCache(new File(root.toString() + "/" + children[i]), children[i]);
                }
            }
        }
    }
    
    public void calculateCache (File dir, String packageName)
    {
        if(dir != null && dir.isDirectory())
        {
            String[] children = dir.list();
            
            for (int i = 0; i < children.length; i++)
            {
                if(children[i].equals("cache"))
                {
                    long size = getFileFolderSize(new File(dir.toString() + "/cache"));
                    
                    if(size != 0)
                    {
                        junkAppArrayList.add(new JunkApp(getApplicationContext(), packageName, size, new File(dir.toString() + "/cache")));
                    }
                }
            }
        }
    }
    
    public long getFileFolderSize (File dir)
    {
        long size = 0;
        
        if(dir.isDirectory())
        {
            for (File file : dir.listFiles())
            {
                if(file.isFile())
                {
                    size += file.length();
                }
                
                else
                {
                    size += getFileFolderSize(file);
                }
            }
            
            size += dir.length();
        }
        
        else if(dir.isFile())
        {
            size += dir.length();
        }
        
        return size;
    }
    
    @Override
    public void onClick (View v)
    {
        for (int i = 0; i < junkAppArrayList.size(); )
        {
            JunkApp junkApp = junkAppArrayList.get(i);
            
            if(junkApp.status)
            {
                junkApp.deleteFiles(junkApp.cacheDirectory);
                junkAppArrayList.remove(i);
            }
            else
            {
                i++;
            }
        }
        
        junkAdapter.notifyDataSetChanged();
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