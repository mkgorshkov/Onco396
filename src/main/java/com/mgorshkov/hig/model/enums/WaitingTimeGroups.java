package com.mgorshkov.hig.model.enums;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public enum WaitingTimeGroups {

    ONE(0.25),
    TWO(0.5),
    THREE(1),
    FOUR(1.5),
    FIVE(2),
    SIX(2.5),
    SEVEN(3),
    EIGHT(4),
    NINE(5),
    TEN(6),
    ELEVEN(7),
    TWELVE(8),
    THIRTEEN(9),
    FOURTEEN(10),
    FIFTEEN(12),
    SIXTEEN(14),
    SEVENTEEN(16),
    EIGHTEEN(18),
    NINETEEN(20),
    TWENTY(25),
    TWENTYONE(30);

    final double value;

    WaitingTimeGroups(double val){
        value = val;
    }

    public double getValue(){
        return value;
    }

    public double getValueAsHours(){
        return getValue()*24;
    }

    public double getValueAsMinutes(){
        return getValue()*24*60;
    }

    public static WaitingTimeGroups returnClosestWaitingTime(Double input, OncoTimeUnit t){
        WaitingTimeGroups[] g = WaitingTimeGroups.values();

        if(t == OncoTimeUnit.DAYS){
            for(int i = 0; i<g.length; i++){
                if(input <= g[0].getValue()){
                    return g[0];
                }
                else if(input >= g[g.length-1].getValue()){
                    return g[g.length-1];
                }
                else if(input >= g[i].getValue() && input <= g[i+1].getValue()){
                    return g[i];
                }
            }
        }else if(t == OncoTimeUnit.HOURS){
            for(int i = 0; i<g.length; i++){
                if(input <= g[0].getValueAsHours()){
                    return g[0];
                }
                else if(input >= g[g.length-1].getValueAsHours()){
                    return g[g.length-1];
                }
                else if(input >= g[i].getValueAsHours() && input <= g[i+1].getValueAsHours()){
                    return g[i];
                }
            }
        }else if(t == OncoTimeUnit.MINUTES){
            for(int i = 0; i<g.length; i++){
                if(input <= g[0].getValueAsMinutes()){
                    return g[0];
                }
                else if(input >= g[g.length-1].getValueAsMinutes()){
                    return g[g.length-1];
                }
                else if(input >= g[i].getValueAsMinutes() && input <= g[i+1].getValueAsMinutes()){
                    return g[i];
                }
            }
        }

        return null;
    }
}
