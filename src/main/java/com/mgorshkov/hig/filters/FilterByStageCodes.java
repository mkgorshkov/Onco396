package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.business.entities.Diagnosis;
import com.mgorshkov.hig.business.utils.DiagnosisSorter;
import com.mgorshkov.hig.model.DiagnosisModel;
import com.mgorshkov.hig.model.Patient;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class FilterByStageCodes {

    private Set<Patient> workingSet = new HashSet<>();
    private HashMap<String, HashSet<DiagnosisModel>> setBrokenByDiagnosis = new HashMap<>();

    public FilterByStageCodes(){
    }

    public FilterByStageCodes(Set<Patient> workingSet){
        this.workingSet = workingSet;
        filter();
    }

    private void filter(){
        for(Patient p : workingSet){
            DiagnosisModel diagnosis = p.getDiagnosis();
            if(diagnosis == null){
                System.out.println("Null: "+p.getPatientSerNum());
                diagnosis = new DiagnosisModel("NA", "NA.NA");
            }

            int index = diagnosis.getCategory().indexOf(".");
            String truncated = "";

            if(index > 0){
                truncated = diagnosis.getCategory().substring(0, index);
            }

            if(setBrokenByDiagnosis.containsKey(truncated)){
                setBrokenByDiagnosis.get(truncated).add(diagnosis);
            }else{
                HashSet<DiagnosisModel> toAdd = new HashSet();
                toAdd.add(diagnosis);
                setBrokenByDiagnosis.put(truncated, toAdd);
            }
        }
    }

    public Set<Patient> getWorkingSet() {
        return workingSet;
    }

    public void setWorkingSet(Set<Patient> workingSet) {
        this.workingSet = workingSet;
    }

    public HashMap<String, HashSet<DiagnosisModel>> getBrokenSet(){
        return setBrokenByDiagnosis;
    }

    public List<String> sortedkeys(){
        ArrayList<String> sortedList = new ArrayList<>();
        sortedList.addAll(setBrokenByDiagnosis.keySet());
        Collections.sort(sortedList, new DiagnosisSorter());

        return sortedList;
    }
}
