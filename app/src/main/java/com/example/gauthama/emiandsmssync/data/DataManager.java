package com.example.gauthama.emiandsmssync.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.gauthama.emiandsmssync.data.local.PreferencesHelper;
import com.example.gauthama.emiandsmssync.data.model.SMS;
import com.example.gauthama.emiandsmssync.data.model.User;
import com.example.gauthama.emiandsmssync.data.remote.SMSRemoteSync;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


public class DataManager {

    public static final String ID = "_id";

    public static final String BODY = "body";

    public static final String DATE = "date";

    public static final String ADDRESS = "address";

    private String SMSInboxURI = "content://sms/inbox";


    private static DataManager dataManager;
    private PreferencesHelper preferencesHelper;
    private SMSRemoteSync smsRemoteSync;
    private Context context;

    private DataManager(Context cxt) {
        context = cxt;
        preferencesHelper = new PreferencesHelper(context);
        smsRemoteSync = SMSRemoteSync.Creator.newSMSRemoteSync();

    }

    public static synchronized DataManager getDataManager(Context context) {

        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public Observable<SMS> SyncSMS(SMS sms) {

        return smsRemoteSync.postSMS(sms);


    }

    public Observable<User> pushPhonedata(User user) {

        return smsRemoteSync.postUser(user);


    }

    public void storePhoneData(String phone, long id) {
        preferencesHelper.storePhoneData(phone, id);

    }

    public void storeLatestMessageId(long id) {
        preferencesHelper.storeMessageIds(id);
    }

    public long getLatestMessageId() {
        return preferencesHelper.getLatestSMSId();
    }

    public long getPhoneId() {
        return preferencesHelper.getPhoneId();
    }

    public String getPhoneNo() {
        return preferencesHelper.getPhoneNo();
    }


    public List<SMS> getSMS(long phoneid) {
        Cursor cursor = context.getContentResolver().query(Uri.parse(SMSInboxURI), null, null,
                null, null);
        List<SMS> smses = new ArrayList<SMS>();
        int idCol = cursor.getColumnIndex(ID);
        int idBody = cursor.getColumnIndex(BODY);
        int idDate = cursor.getColumnIndex(DATE);
        int idAddress = cursor.getColumnIndex(ADDRESS);
        try {
            while (cursor.moveToNext()) {
                SMS sms = new SMS();
                sms.body = cursor.getString(idBody);
                sms.user_id = phoneid;
                sms.messageid = cursor.getLong(idCol);
                sms.date = cursor.getString(idDate);
                sms.sender = cursor.getString(idAddress);
                smses.add(0, sms);

            }
        } finally {
            cursor.close();
        }



        return smses;
    }


}
