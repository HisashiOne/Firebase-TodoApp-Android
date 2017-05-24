package com.hisashione.todoapp.Models;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hisashione.todoapp.MainActivity;

/**
 * Created by HisashiOne on 15/05/17.
 */

public class MyFirebaseMesangingService   extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("Tag_2", "From: " + remoteMessage.getFrom());
        Log.d("Tag_2", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        notifyUser(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());


    }



    public  void notifyUser (String from, String notification){

        MyNotificationManager notificationManager = new MyNotificationManager(getApplicationContext());
        notificationManager.showNotification(from, notification, new Intent(getApplicationContext(), MainActivity.class));
    }

}
