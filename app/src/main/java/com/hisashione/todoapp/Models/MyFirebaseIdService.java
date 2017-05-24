package com.hisashione.todoapp.Models;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by HisashiOne on 15/05/17.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    public  static final  String TOKEN_BROADCAST = "myToken";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Tag_2", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        //sendRegistrationToServer(refreshedToken);
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        StoreToken(refreshedToken);
    }



    private  void StoreToken (String token){

        SharedPrefManager.getmInstance(getApplicationContext()).storeToken(token);
    }
}

