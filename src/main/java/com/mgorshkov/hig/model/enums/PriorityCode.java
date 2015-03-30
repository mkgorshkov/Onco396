package com.mgorshkov.hig.model.enums;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public enum PriorityCode {

    P1("SGAS_P1"), P2("SGAS_P2"), P3("SGAS_P3"), P4("SGAS_P4"), NA("NONE");

    private final String priorityCodeName;

    private PriorityCode(String p){
        priorityCodeName = p;
    }

    @Override
    public String toString(){
        return priorityCodeName;
    }

    public static PriorityCode getPCode(String s){
        if(s.equals("SGAS_P1")){
            return P1;
        }else if(s.equals("SGAS_P2")){
            return P2;
        }else if(s.equals("SGAS_P3")){
            return P3;
        }else if(s.equals("SGAS_P4")){
            return P4;
        }

        return NA;
    }
}
