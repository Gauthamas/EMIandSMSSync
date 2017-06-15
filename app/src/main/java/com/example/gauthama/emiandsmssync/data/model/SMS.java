package com.example.gauthama.emiandsmssync.data.model;

import com.google.gson.annotations.SerializedName;

public class SMS {
    @SerializedName("id")
    public long id;

    @SerializedName("user-id")
    public long user_id;

    @SerializedName("body")
    public String body;

    @SerializedName("sender")
    public String sender;

    @SerializedName("date")
    public String date;

    @SerializedName("message_id")
    public long messageid;






}
