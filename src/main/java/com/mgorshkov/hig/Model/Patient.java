package com.mgorshkov.hig.Model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mkgo on 25/02/15.
 */
public class Patient {
    private int PatientSerNum;
    private Set<DataPoint> DataPoints;

    public Patient(int patient){
        PatientSerNum = patient;
        DataPoints = new HashSet<>();
    }

    public void addDataPoint(DataPoint d){
        DataPoints.add(d);
    }

    public void removeDataPoint(DataPoint d){
        DataPoints.remove(d);
    }

    public int getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(int patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public Set getDataPoints() {
        return DataPoints;
    }

    public void setDataPoints(Set dataPoints) {
        DataPoints = dataPoints;
    }
}
