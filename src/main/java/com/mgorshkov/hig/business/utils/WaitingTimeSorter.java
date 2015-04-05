package com.mgorshkov.hig.business.utils;

import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;

import java.util.Comparator;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class WaitingTimeSorter implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
        if(o1.calculateTotalWaitingTime(OncoTimeUnit.MINUTES) < o2.calculateTotalWaitingTime(OncoTimeUnit.MINUTES)){
            return -1;
        }else if(o1.calculateTotalWaitingTime(OncoTimeUnit.MINUTES) > o2.calculateTotalWaitingTime(OncoTimeUnit.MINUTES)){
            return 1;
        }
        return 0;
    }
}
