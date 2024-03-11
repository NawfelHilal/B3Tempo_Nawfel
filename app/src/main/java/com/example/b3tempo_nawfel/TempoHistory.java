 package com.example.b3tempo_nawfel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TempoHistory {

    @SerializedName("dates")
    @Expose
    private List<TempoDate> tempoDates;

    public List<TempoDate> getDates() {
        return tempoDates;
    }

    public void setDates(List<TempoDate> tempoDates) {
        this.tempoDates = tempoDates;
    }

}