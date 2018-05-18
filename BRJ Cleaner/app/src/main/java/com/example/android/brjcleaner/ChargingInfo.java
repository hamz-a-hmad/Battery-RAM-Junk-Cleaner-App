package com.example.android.brjcleaner;

/**
 * Created by Toqeer ABBSI on 02-Jan-18.
 */

public class ChargingInfo
{
    String sdate, stime, edate, etime, batterybefore, batteryAfter;
    
    public ChargingInfo (String sd, String st, String ed, String et, String bb, String ba)
    {
        sdate = sd;
        stime = st;
        edate = ed;
        etime = et;
        batterybefore = bb;
        batteryAfter = ba;
    }
}