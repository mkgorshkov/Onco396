package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.business.entities.Diagnosis;
import com.mgorshkov.hig.business.entities.PatientDoctor;
import com.mgorshkov.hig.business.entities.Priority;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.vaadin.ui.UI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class FilterExtremes {

    private Set<Patient> workingSet;

    boolean setDebug = true; //Apologies to the JAVA gods for flag programming.
    boolean removeZeros = true;

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;

    public FilterExtremes(Set<Patient> workingSet){
        this.workingSet = workingSet;

            setEntityManager();

            calculateFirstWaiting();
            calculateSecondWaiting();
            calculateThirdWaiting();
            calculateFourthWaiting();
            calculateFifthWaiting();
            calculateSixthWaiting();
            calculateSeventhWaiting();

            addDiagnosis();
    }

    private void calculateFirstWaiting(){
        long start = System.currentTimeMillis();
        if(setDebug) System.out.println(workingSet.size());

        long totalForAverage = 0;
        long variance = 0;
        int zeroValues = 0;
        double standardDeviation = 0;

        for(Patient p : workingSet){
            double waitingTime = p.calculateFirstWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFirstWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateFirstWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateFirstWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateSecondWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSecondWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateSecondWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateSecondWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateThirdWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateThirdWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateThirdWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateThirdWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateFourthWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFourthWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateFourthWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateFourthWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateFifthWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFifthWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateFifthWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateFifthWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateSixthWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSixthWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateSixthWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateSixthWait(OncoTimeUnit.MINUTES) == 0){
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
            double waitingTime = p.calculateSeventhWait(OncoTimeUnit.MINUTES);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSeventhWait(OncoTimeUnit.MINUTES);

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
            if(p.calculateSeventhWait(OncoTimeUnit.MINUTES) > totalForAverage + 3*standardDeviation || p.calculateSeventhWait(OncoTimeUnit.MINUTES) == 0){
                it.remove();
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void addDiagnosis(){
        HashSet<String> diagnosis = new HashSet<>();

        for(Patient w : workingSet){
            String diag = addDiagnosisCode(w.getPatientSerNum());
            w.setDiagnosis(diag);
            diagnosis.add(diag);

            String priority = addPriorityCode(w.getPatientSerNum());
            w.setPriorityCode(priority);

            int onco = addOncologistSerial(w.getPatientSerNum());
            w.setOncologist(onco);
        }

        ((MainUI) UI.getCurrent()).setDiagnosis(diagnosis);
    }

    private String addDiagnosisCode(int patientSerNum){
        TypedQuery<Diagnosis> diag = entityManager.createNamedQuery("Diagnosis.findBySer", Diagnosis.class);
        diag.setParameter("patientSerNum", patientSerNum);

        try{
            Diagnosis d = diag.getResultList().get(0);
            return d.getDiagnosisCode();
        }catch(IndexOutOfBoundsException o){
            return "";
        }
    }

    private String addPriorityCode(int patientSerNum){
        TypedQuery<Priority> diag = entityManager.createNamedQuery("Priority.findByPatient", Priority.class);
        diag.setParameter("patientSerNum", patientSerNum);

        try{
            Priority d = diag.getResultList().get(0);
            return d.getPriorityCode();
        }catch(IndexOutOfBoundsException o){
            return "";
        }
    }

    private Integer addOncologistSerial(int patientSerNum){
        TypedQuery<PatientDoctor> diag = entityManager.createNamedQuery("PatientDoctor.findBySerialAndOncologist", PatientDoctor.class);
        diag.setParameter("patientSerNum", patientSerNum);

        try{
            PatientDoctor d = diag.getResultList().get(0);
            return d.getDoctorSerNum();
        }catch(IndexOutOfBoundsException o){
            return 0;
        }
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }

    public Set<Patient> getWorkingSet() {
        return workingSet;
    }

}
