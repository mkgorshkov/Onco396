package com.mgorshkov.hig.business;

import com.mgorshkov.hig.model.DataPoint;

import java.util.Comparator;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class DataPointDateComparator implements Comparator<DataPoint>{

    @Override
    public int compare(DataPoint o1, DataPoint o2) {
        return o1.getTimePoint().compareTo(o2.getTimePoint());
    }
}
