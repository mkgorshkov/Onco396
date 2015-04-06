package com.mgorshkov.hig.business.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class HospitalHolidays {
    private Calendar c;

    public HospitalHolidays(){
        c = Calendar.getInstance();
    }

    public boolean isWeekend(Date input){
        c.setTime(input);

        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        }
        return false;
    }

    public boolean isHoliday(Date input){
        c.setTime(input);

        int year = c.getWeekYear();

        if(c.get(Calendar.DATE) == 1 && c.get(Calendar.MONTH) == Calendar.JANUARY){
            return true;
        } else if(c.get(Calendar.DATE) == 24 && c.get(Calendar.MONTH) == Calendar.JUNE) {
            return true;
        }else if(c.get(Calendar.DATE) == 1 && c.get(Calendar.MONTH) == Calendar.JULY) {
            return true;
        }else if(c.get(Calendar.DATE) == 25 && c.get(Calendar.MONTH) == Calendar.DECEMBER) {
            return true;
        }

        if(year == 2010){
            if(c.get(Calendar.DATE) == 2 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 24 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 5 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 6 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 11 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2011){
            if(c.get(Calendar.DATE) == 22 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 25 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 23 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 5 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 10 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2012){
            if(c.get(Calendar.DATE) == 6 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 9 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 21 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 3 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 8 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2013){
            if(c.get(Calendar.DATE) == 29 && c.get(Calendar.MONTH) == Calendar.MARCH) {
                return true;
            }else if(c.get(Calendar.DATE) == 1 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 20 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 2 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 14 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2014){
            if(c.get(Calendar.DATE) == 18 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 21 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 19 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 1 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 13 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2015){
            if(c.get(Calendar.DATE) == 3 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 6 && c.get(Calendar.MONTH) == Calendar.APRIL) {
                return true;
            }else if(c.get(Calendar.DATE) == 18 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 7 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 12 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }else if(year == 2016){
            if(c.get(Calendar.DATE) == 25 && c.get(Calendar.MONTH) == Calendar.MARCH) {
                return true;
            }else if(c.get(Calendar.DATE) == 28 && c.get(Calendar.MONTH) == Calendar.MARCH) {
                return true;
            }else if(c.get(Calendar.DATE) == 23 && c.get(Calendar.MONTH) == Calendar.MAY) {
                return true;
            }else if(c.get(Calendar.DATE) == 5 && c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                return true;
            }else if(c.get(Calendar.DATE) == 10 && c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                return true;
            }
        }

        return false;
    }

    public boolean isDayOff(Date input){
        return isWeekend(input) || isHoliday(input);
    }

    public long removeHolidays(Date d1, Date d2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        if((c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)){
            return 86400000/2;
        }

        int numberOfDays = 0;
        while (c1.before(c2)) {
            if(!isDayOff(c1.getTime())){
                numberOfDays++;
                c1.add(Calendar.DATE,1);
            }else{
                c1.add(Calendar.DATE,1);
            }
        }

        return ((long) numberOfDays) * 86400000;
    }
}
