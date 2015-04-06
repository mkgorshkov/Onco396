package com.mgorshkov.hig.model;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.business.entities.Priority;
import com.mgorshkov.hig.business.utils.HospitalHolidays;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.PriorityCode;
import com.mgorshkov.hig.model.enums.Stage;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkgo on 25/02/15.
 */
public class Patient {
    private int PatientSerNum;
    private HashSet<DataPoint> DataPoints;
    private DiagnosisModel diagnosis;
    private PriorityCode priorityCode;
    private int oncologist;
    private HospitalHolidays hospitalHolidays = new HospitalHolidays();

    public Patient(int patient){
        PatientSerNum = patient;
        DataPoints = new HashSet<>();
        oncologist = 0;
        priorityCode = PriorityCode.NA;
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

    public double calculateFirstWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }

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

    public double calculateSecondWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }


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

    public double calculateThirdWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }


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

    public double calculateFourthWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }


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

    public double calculateFifthWait(OncoTimeUnit t, boolean remove){
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

    public double calculateSixthWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }


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

    public double calculateSeventhWait(OncoTimeUnit t, boolean remove){
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

        long duration = 0;

        if(remove){
            duration = hospitalHolidays.removeHolidays(a.getTimePoint(), b.getTimePoint());
        }else {
            duration = b.getTimePoint().getTime() - a.getTimePoint().getTime();
        }


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

    public PriorityCode getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(PriorityCode priorityCode) {
        this.priorityCode = priorityCode;
    }

    public int getOncologist() {
        return oncologist;
    }

    public void setOncologist(int oncologist) {
        this.oncologist = oncologist;
    }

    public boolean isUndergoingTreatment(Date date){
        boolean afterFirst = false;
        boolean beforeLast = false;

        for(DataPoint d : DataPoints){
            if(d.getStage().equals(Stage.CT_SCAN)){
                if(d.getTimePoint().before(date)) afterFirst = true;
            }else if(d.getStage().equals(Stage.READY_FOR_TREATMENT)){
                if(d.getTimePoint().after(date)) beforeLast = true;
            }
        }

        return (afterFirst && beforeLast);
    }

    public Stage getCurrentStage(Date date){
        DataPoint[] ordered = new DataPoint[8];

        Iterator<DataPoint> it = DataPoints.iterator();
        while(it.hasNext()){
            DataPoint d = it.next();
            ordered[d.getStage().ordinal()] = d;
        }

        for(int i = 0; i<ordered.length; i++){
            if(ordered[i].getTimePoint().before(date) && ordered[i+1].getTimePoint().after(date)){
                return Stage.values()[i];
            }
        }

        return null;
    }

    public double calculateTotalWaitingTime(OncoTimeUnit t, boolean remove){
        return calculateFirstWait(t, remove) + calculateSecondWait(t, remove) + calculateThirdWait(t, remove) + calculateFourthWait(t, remove) + calculateFifthWait(t, remove) + calculateSixthWait(t, remove) + calculateSeventhWait(t, remove);
    }

    public DiagnosisModel getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(DiagnosisModel diagnosis) {
        this.diagnosis = diagnosis;
    }
}
