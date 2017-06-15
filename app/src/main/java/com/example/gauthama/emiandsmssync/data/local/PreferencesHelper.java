package com.example.gauthama.emiandsmssync.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "android_pref_file";
    public static final String LATEST_SMS = "latest_sms";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PHONE_NUMBER_ID = "phone_id";

    private SharedPreferences mPref;

    public PreferencesHelper(Context context){
       mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void storeMessageIds(long value){
        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(LATEST_SMS, value);
        editor.commit();
    }

    public long getLatestSMSId(){
        return mPref.getLong(LATEST_SMS, 0L);

    }

    public long getPhoneId(){
        return mPref.getLong(PHONE_NUMBER_ID, 0L);

    }

    public String getPhoneNo(){
        return mPref.getString(PHONE_NUMBER, "");

    }

    public void storePhoneData(String phoneno, long phoneid){

        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(PHONE_NUMBER_ID, phoneid);
        editor.putString(PHONE_NUMBER, phoneno);
        editor.commit();
    }

}
