/**
 * MainBroadcastReceiver.java
 *
 * Project #3, A2
 *
 * Author: Alex Viznytsya
 *
 * CS 478 Software Development for Mobile Platforms
 * Spring 2018, UIC
 *
 * Date: 04/02/2018
 */

package edu.uic.cs478.sp18.project3.a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String activity = intent.getStringExtra("activity");
        Log.i("A2 Broadcast Receiver: ", "Received " + activity);
        Intent i = new Intent();
        if(activity.equals("attractions")) {
            i.setClassName("edu.uic.cs478.sp18.project3.a2",
                    "edu.uic.cs478.sp18.project3.a2.Attractions");
        } else if(activity.equals("restaurants")) {
            i.setClassName("edu.uic.cs478.sp18.project3.a2",
                    "edu.uic.cs478.sp18.project3.a2.Restaurants");
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
