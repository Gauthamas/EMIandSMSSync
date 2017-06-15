package com.example.gauthama.emiandsmssync.service;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.gauthama.emiandsmssync.data.DataManager;
import com.example.gauthama.emiandsmssync.data.model.SMS;
import com.example.gauthama.emiandsmssync.data.model.User;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class SMSSyncIntentService extends IntentService {


    public static DataManager dataManager;

    public SMSSyncIntentService() {
        super("SMSSyncIntentService");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(SMSSyncIntentService.class.getName(),"syncing");
        dataManager = DataManager.getDataManager(this);
        if (intent != null) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_SMS);
            if(PackageManager.PERMISSION_GRANTED == permissionCheck) {

                    final long phoneId = dataManager.getPhoneId();
                    if(phoneId==0L){
                        TelephonyManager telemamanger = (TelephonyManager)
                                getSystemService(Context.TELEPHONY_SERVICE);
                        final User user = new User();
                        user.mobile= telemamanger.getLine1Number();
                        dataManager.pushPhonedata(user).subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(User usr) {
                                dataManager.storePhoneData(user.mobile, usr.id);
                                sendSMS(dataManager.getSMS(usr.id));

                            }
                        });

                    }
                    else {
                        sendSMS(dataManager.getSMS(phoneId));
                    }

                } else {
                    Log.e(this.getClass().getName(),"permission exceptions");
                }
            }
        }

    private void sendSMS(List<SMS> smses){
        Log.d(SMSSyncIntentService.class.getName(),"starting sms send");
        long latestId = dataManager.getLatestMessageId();
        for(final SMS sms: smses){
            if(sms.messageid>latestId){
                Log.d(SMSSyncIntentService.class.getName(),"sending");
                dataManager.SyncSMS(sms).subscribe(new Subscriber<SMS>() {
                    @Override
                    public void onCompleted() {
                        Log.d(SMSSyncIntentService.class.getName(),"completed");
                        dataManager.storeLatestMessageId(sms.messageid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(SMSSyncIntentService.class.getName(),e.toString());

                    }

                    @Override
                    public void onNext(SMS sms) {


                    }
                });
            }

        }

    }



}
