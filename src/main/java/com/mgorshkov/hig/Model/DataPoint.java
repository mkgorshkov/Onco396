package com.mgorshkov.hig.Model;

import java.util.Date;

/**
 * Created by mkgo on 25/02/15.
 */
public class DataPoint {
    private Date TimePoint;
    private DataPointType Type;

    public DataPoint(Date date, DataPointType type){
        TimePoint = date;
        Type = type;
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
