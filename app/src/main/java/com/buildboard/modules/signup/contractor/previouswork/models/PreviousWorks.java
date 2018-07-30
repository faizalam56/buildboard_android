package com.buildboard.modules.signup.contractor.previouswork.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class PreviousWorks {

    @SerializedName("Testimonial")
    private HashMap<Integer, ArrayList<PreviousWorkData>> testimonial;

    @SerializedName("Previous Work")
    private HashMap<Integer, ArrayList<PreviousWorkData>> previousWork;

    public HashMap<Integer, ArrayList<PreviousWorkData>> getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(HashMap<Integer, ArrayList<PreviousWorkData>> testimonial) {
        this.testimonial = testimonial;
    }

    public HashMap<Integer, ArrayList<PreviousWorkData>> getPreviousWork() {
        return previousWork;
    }

    public void setPreviousWork(HashMap<Integer, ArrayList<PreviousWorkData>> previousWork) {
        this.previousWork = previousWork;
    }
}