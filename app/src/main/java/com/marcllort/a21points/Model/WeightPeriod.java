package com.marcllort.a21points.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeightPeriod {

        @SerializedName("period")
        @Expose
        private String period;
        @SerializedName("weighIns")
        @Expose
        private List<Weight> weighIns = null;

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public List<Weight> getWeighIns() {
            return weighIns;
        }

        public void setWeighIns(List<Weight> weighIns) {
            this.weighIns = weighIns;
        }


}
