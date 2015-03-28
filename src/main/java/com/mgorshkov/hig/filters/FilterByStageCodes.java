package com.mgorshkov.hig.filters;

import com.mgorshkov.hig.business.utils.DiagnosisSorter;
import com.mgorshkov.hig.model.Patient;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class FilterByStageCodes {

    private Set<Patient> workingSet = new HashSet<>();
    private HashMap<String, HashSet<String>> setBrokenByDiagnosis = new HashMap<>();

    public FilterByStageCodes(){
    }

    public FilterByStageCodes(Set<Patient> workingSet){
        this.workingSet = workingSet;
        filter();
    }

    private void filter(){
        for(Patient p : workingSet){
            String diagnosis = p.getDiagnosis();
            int index = diagnosis.indexOf(".");
            String truncated = diagnosis;

            if(index > 0){
                truncated = diagnosis.substring(0, index);
            }

            if(setBrokenByDiagnosis.containsKey(truncated)){
                setBrokenByDiagnosis.get(truncated).add(diagnosis);
            }else{
                HashSet<String> toAdd = new HashSet();
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

    public HashMap getBrokenSet(){
        return setBrokenByDiagnosis;
    }

    public List<String> sortedkeys(){
        ArrayList<String> sortedList = new ArrayList<>();
        sortedList.addAll(setBrokenByDiagnosis.keySet());
        Collections.sort(sortedList, new DiagnosisSorter());

        return sortedList;
    }
}
