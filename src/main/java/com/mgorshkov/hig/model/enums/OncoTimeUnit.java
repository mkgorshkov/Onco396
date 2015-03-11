package com.mgorshkov.hig.model.enums;

/**
 * Created by mkgo on 11/03/15.
 */
public enum OncoTimeUnit {
    MINUTES("Minutes"), HOURS("Hours"), DAYS("Days");

    private final String unitName;

    private OncoTimeUnit(final String unitName){
        this.unitName = unitName;
    }

    @Override
    public String toString(){
        return unitName;
    }
}
