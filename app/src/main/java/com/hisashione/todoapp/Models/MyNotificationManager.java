package com.hisashione.todoapp.Models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Created by HisashiOne on 15/05/17.
 */

public class MyNotificationManager   {

    private Context ctx;
    public static final int NOTIFICATION_ID = 234;




    public MyNotificationManager(Context ctx){

        this.ctx = ctx;


    }


    public  void showNotification (String from, String notification, Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, NOTIFICATION_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        Notification mNotification = builder.setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), android.R.mipmap.sym_def_app_icon))
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification);


    }
}
