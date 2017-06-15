package com.example.gauthama.emiandsmssync.data.remote;

import com.example.gauthama.emiandsmssync.data.model.SMS;
import com.example.gauthama.emiandsmssync.data.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by gauthama on 13/6/17.
 */

public interface SMSRemoteSync {

    String ENDPOINT = "https://boiling-eyrie-75655.herokuapp.com";

    @POST("/user")
    Observable<User> postUser(@Body User user);

    @POST("/sms")
    Observable<SMS> postSMS(@Body SMS sms);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static SMSRemoteSync newSMSRemoteSync() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SMSRemoteSync.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(SMSRemoteSync.class);
        }
    }
}
