package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.business.entities.Diagnosis;
import com.mgorshkov.hig.business.entities.PatientDoctor;
import com.mgorshkov.hig.business.entities.Priority;
import com.mgorshkov.hig.model.DiagnosisModel;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.PriorityCode;
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

    Set<Patient> moreOneStd = new HashSet<>();
    Set<Patient> moreTwoStd = new HashSet<>();
    Set<Patient> moreThreeStd = new HashSet<>();

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    Set<Integer> oncologists = new HashSet<>();

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
            double waitingTime = p.calculateFirstWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFirstWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateFirstWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateFirstWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateFirstWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateFirstWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateSecondWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSecondWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateSecondWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateSecondWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateSecondWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateSecondWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateThirdWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateThirdWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateThirdWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateThirdWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateThirdWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateThirdWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateFourthWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFourthWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateFourthWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateFourthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateFourthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateFourthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateFifthWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateFifthWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateFifthWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateFifthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateFifthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateFifthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateSixthWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSixthWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateSixthWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateSixthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateSixthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateSixthWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
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
            double waitingTime = p.calculateSeventhWait(OncoTimeUnit.MINUTES, false);
            if(waitingTime == 0){
                zeroValues++;
            }
            totalForAverage += waitingTime;
        }
        totalForAverage = totalForAverage/(workingSet.size() - zeroValues);

        for(Patient p : workingSet){
            double waitingTime = p.calculateSeventhWait(OncoTimeUnit.MINUTES, false);

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
            if(p.calculateSeventhWait(OncoTimeUnit.MINUTES, false) == 0){
                it.remove();
            }else if(p.calculateSeventhWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 3*standardDeviation){
                moreThreeStd.add(p);
//                it.remove();
            }else if(p.calculateSeventhWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 2*standardDeviation){
                moreTwoStd.add(p);
            }else if(p.calculateSeventhWait(OncoTimeUnit.MINUTES, false) > totalForAverage + 1*standardDeviation){
                moreOneStd.add(p);
            }
        }

        if(setDebug) System.out.println(workingSet.size());
        if(setDebug) System.out.println((System.currentTimeMillis() - start) +" ms");
    }

    private void addDiagnosis(){
        HashSet<DiagnosisModel> diagnosis = new HashSet<>();

        for(Patient w : workingSet){
            DiagnosisModel diag = addDiagnosisCode(w.getPatientSerNum());
            w.setDiagnosis(diag);
            diagnosis.add(diag);

            String priority = addPriorityCode(w.getPatientSerNum());
            w.setPriorityCode(PriorityCode.getPCode(priority));

            int onco = addOncologistSerial(w.getPatientSerNum());
            w.setOncologist(onco);
            oncologists.add(onco);
        }

        ((MainUI) UI.getCurrent()).setDiagnosis(diagnosis);
        ((MainUI) UI.getCurrent()).setOncologists(oncologists);
    }

    private DiagnosisModel addDiagnosisCode(int patientSerNum){
        TypedQuery<Diagnosis> diag = entityManager.createNamedQuery("Diagnosis.findBySer", Diagnosis.class);
        diag.setParameter("patientSerNum", patientSerNum);

        try{
            Diagnosis d = diag.getResultList().get(0);
            DiagnosisModel dModel = new DiagnosisModel();
            dModel.setCategory(d.getDiagnosisCode());
            dModel.setDescription(d.getDescription());
            return dModel;
        }catch(IndexOutOfBoundsException o){
            return null;
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

    public Set<Set<Patient>> getExtremes(){
        HashSet<Set<Patient>> extremes = new HashSet<>();
        extremes.add(moreOneStd);
        System.out.println(moreOneStd.size());
        extremes.add(moreTwoStd);
        System.out.println(moreTwoStd.size());
        extremes.add(moreThreeStd);
        System.out.println(moreThreeStd.size());
        return extremes;
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }

    public Set<Patient> getWorkingSet() {
        return workingSet;
    }

}
