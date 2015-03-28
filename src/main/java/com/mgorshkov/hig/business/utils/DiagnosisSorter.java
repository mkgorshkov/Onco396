package com.mgorshkov.hig.business.utils;

import java.util.Comparator;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class DiagnosisSorter implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        //See if the first place is an int or letter
        if(o1.isEmpty()){
            return 1;
        }else if(o2.isEmpty()){
            return -1;
        }else if(o1.substring(0, 1).compareTo(o2.substring(0,1)) == 0){
            return Integer.parseInt(o1.substring(1)) - Integer.parseInt(o2.substring(1));
        }
        return o1.substring(0, 1).compareTo(o2.substring(0,1));
    }
}
