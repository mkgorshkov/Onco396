package com.mgorshkov.hig.business.utils;

import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.Patient;

import java.util.Comparator;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class ChronologicalOrderSorter implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
        DataPoint d1 = ((DataPoint) o1.getDataPoints().iterator().next());
        DataPoint d2 = ((DataPoint) o2.getDataPoints().iterator().next());
        return d1.getTimePoint().compareTo(d2.getTimePoint());
    }
}
