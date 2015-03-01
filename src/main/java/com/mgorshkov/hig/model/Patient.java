package com.mgorshkov.hig.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkgo on 25/02/15.
 */
public class Patient {
    private int PatientSerNum;
    private HashSet<DataPoint> DataPoints;

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

    public HashSet getDataPoints() {
        return DataPoints;
    }

    public void setDataPoints(HashSet dataPoints) {
        DataPoints = dataPoints;
    }

    public long calculateFirstWait(){
        Iterator<DataPoint> it = DataPoints.iterator();
        DataPoint a = it.next();
        DataPoint b = it.next();

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        System.out.println(TimeUnit.MILLISECONDS.toHours(duration));

        return TimeUnit.MILLISECONDS.toHours(duration);
    }

}
