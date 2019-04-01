package com.marcllort.a21points;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blood {

    @SerializedName("diastolic")
    @Expose
    private Integer diastolic;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("systolic")
    @Expose
    private Integer systolic;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getBlood() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}