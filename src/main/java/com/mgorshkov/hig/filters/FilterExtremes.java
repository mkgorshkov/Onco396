package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.model.Patient;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class FilterExtremes {

    private Set<Patient> workingSet;

    boolean setDebug = true; //Apologies to the JAVA gods for flag programming.

    public FilterExtremes(Set<Patient> workingSet){
        this.workingSet = workingSet;

        calculateFirstWaiting();
        calculateSecondWaiting();
        calculateThirdWaiting();
        calculateFourthWaiting();
        calculateFifthWaiting();
        calculateSixthWaiting();
        calculateSeventhWaiting();
    }

    private void calculateFirstWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateFirstWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateFirstWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateFirstWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateSecondWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateSecondWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateSecondWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateSecondWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateThirdWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateThirdWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateThirdWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateThirdWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateFourthWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateFourthWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateFourthWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateFourthWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateFifthWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateFifthWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateFifthWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateFifthWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateSixthWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateSixthWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateSixthWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateSixthWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void calculateSeventhWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            long waitingTime = p.calculateSeventhWait();
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            long waitingTime = p.calculateSeventhWait();

            if(waitingTime != 0){
                variance += (waitingTime - totalForAverage)*(waitingTime - totalForAverage);
            }
        }
        variance = variance/(workingSet.size() - zeroValues);
        standardDeviation = Math.sqrt(variance);

        System.out.println("Average: "+totalForAverage);
        System.out.println("Std Dev: "+standardDeviation);

        Iterator<Patient> it = workingSet.iterator();
        while(it.hasNext()){
            Patient p = it.next();
            if(p.calculateSeventhWait() > totalForAverage + standardDeviation){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }
}
