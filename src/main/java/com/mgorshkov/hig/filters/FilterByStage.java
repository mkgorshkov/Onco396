package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.business.DataPointDateComparator;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.Stage;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class FilterByStage {

    private Set<Patient> workingSet;

    boolean setDebug = true; //Apologies to the JAVA gods for flag programming.

    public FilterByStage(Set<Patient> workingSet){
        this.workingSet = workingSet;

        naiveFilterAtLeastOneStage();
        filterAtLeaseOneUniqueStage();
        filterChronologically();
    }

    private void naiveFilterAtLeastOneStage(){
        //Naively, take out all with <8 measurements
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.getDataPoints().size() < 8){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void filterAtLeaseOneUniqueStage(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        Iterator<Patient> itPatient = workingSet.iterator();
        while(itPatient.hasNext()){
            Patient p = itPatient.next();

            if(!hasUniqueStages(p.getDataPoints())){
                itPatient.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void filterChronologically(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        Iterator<Patient> itPatient = workingSet.iterator();
        while(itPatient.hasNext()){
            Patient p = itPatient.next();

            HashSet<DataPoint> sorted = checkChronological(p.getDataPoints());
            if(sorted.size() < 8){
                itPatient.remove();
            }else{
                p.setDataPoints(sorted);
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private boolean hasUniqueStages(Set<DataPoint> inPoints){
        List<Stage> stages = new ArrayList<>();

        Iterator<DataPoint> it = inPoints.iterator();
        while(it.hasNext()){
            Stage crt = it.next().getStage();
            if(!stages.contains(crt)) stages.add(crt);
        }

        if(stages.size() < 8){
            return false;
        }
        return true;
    }

    private HashSet<DataPoint> checkChronological(Set<DataPoint> inPoints){
        HashSet<DataPoint> sortedPoints = new HashSet<DataPoint>();

        ArrayList<DataPoint> inOrder = new ArrayList<>();
        inOrder.addAll(inPoints);

        Collections.sort(inOrder, new DataPointDateComparator());

        Stage crtStage = Stage.CT_SCAN;

        for(DataPoint p : inOrder){
            if(p.getStage() == crtStage){
                sortedPoints.add(p);
                crtStage = crtStage.getNext();
            }
        }

        return sortedPoints;
    }

    public Set<Patient> getWorkingSet() {
        return workingSet;
    }
}
