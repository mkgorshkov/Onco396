package com.mgorshkov.hig.Model;

import java.util.Date;

/**
 * Created by mkgo on 25/02/15.
 */
public class DataPoint {
    private int Identifier;
    private Date TimePoint;
    private DataPointType Type;

    public DataPoint(int id, Date date, DataPointType type){
        Identifier = id;
        TimePoint = date;
        Type = type;
    }

    public int getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(int identifier) {
        Identifier = identifier;
    }

    public Date getTimePoint() {
        return TimePoint;
    }

    public void setTimePoint(Date timePoint) {
        TimePoint = timePoint;
    }

    public DataPointType getType() {
        return Type;
    }

    public void setType(DataPointType type) {
        Type = type;
    }
}
