package com.marcllort.a21points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marcllort.a21points.Blood;

public class ArrayBlood {

    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("readings")
    @Expose
    private List<Blood> readings = null;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Blood> getReadings() {
        return readings;
    }

    public void setReadings(List<Blood> readings) {
        this.readings = readings;
    }

}