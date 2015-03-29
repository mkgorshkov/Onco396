package com.mgorshkov.hig.model;

import com.mgorshkov.hig.model.enums.DataPointType;
import com.mgorshkov.hig.model.enums.Stage;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mkgo on 25/02/15.
 */
public class DataPoint {
    private Date timePoint;
    private DataPointType type;
    private Stage stage;

    public DataPoint(Date date, DataPointType type, Stage stage){
        this.timePoint = date;
        this.type = type;
        this.stage = stage;
    }

    public Date getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public DataPointType getType() {
        return type;
    }

    public void setType(DataPointType type) {
        this.type = type;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean startsOnFriday(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.setTime(timePoint);

        return c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
}
