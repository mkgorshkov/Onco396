package com.mgorshkov.hig.model;

import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.Stage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkgo on 25/02/15.
 */
public class Patient {
    private int PatientSerNum;
    private HashSet<DataPoint> DataPoints;
    private String diagnosis;
    private String priorityCode;
    private int oncologist;

    public Patient(int patient){
        PatientSerNum = patient;
        DataPoints = new HashSet<>();
        diagnosis = "";
        oncologist = 0;
        priorityCode = "";
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

    public double calculateFirstWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.CT_SCAN){
                a = next;
            }else if(next.getStage() == Stage.INITIAL_CONTOUR){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
           return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateSecondWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.INITIAL_CONTOUR){
                a = next;
            }else if(next.getStage() == Stage.MD_CONTOUR){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateThirdWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.MD_CONTOUR){
                a = next;
            }else if(next.getStage() == Stage.CT_PLANNING_SHEET){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateFourthWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.CT_PLANNING_SHEET){
                a = next;
            }else if(next.getStage() == Stage.DOSE_CALCULATION){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateFifthWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.DOSE_CALCULATION){
                a = next;
            }else if(next.getStage() == Stage.MD_APPROVE){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateSixthWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.MD_APPROVE){
                a = next;
            }else if(next.getStage() == Stage.PHYSICS_QA){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public double calculateSeventhWait(OncoTimeUnit t){
        Iterator<DataPoint> it = DataPoints.iterator();

        DataPoint a = null;
        DataPoint b = null;

        while(it.hasNext()){
            DataPoint next = it.next();

            if(next.getStage() == Stage.PHYSICS_QA){
                a = next;
            }else if(next.getStage() == Stage.READY_FOR_TREATMENT){
                b = next;
            }
        }

        long duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();

        if(TimeUnit.MILLISECONDS.toMinutes(duration) > 50400){
            return 0;
        }

        double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if(t == OncoTimeUnit.HOURS){
            return minutes / 60;
        }else if(t == OncoTimeUnit.DAYS){
            return minutes / 1440;
        }

        return minutes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public int getOncologist() {
        return oncologist;
    }

    public void setOncologist(int oncologist) {
        this.oncologist = oncologist;
    }
}
