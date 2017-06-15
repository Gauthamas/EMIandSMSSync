package com.example.gauthama.emiandsmssync.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gauthama.emiandsmssync.service.SMSSyncIntentService;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SMSSyncIntentService.class));
    }
}
