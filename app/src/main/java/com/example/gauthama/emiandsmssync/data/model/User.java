package com.example.gauthama.emiandsmssync.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public long id;



    @SerializedName("name")
    public String name;

    @SerializedName("mobile")
    public String mobile;

}
