package com.mgorshkov.hig.business.exporter;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.WaitingTimeGroups;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class ExportToCSV {
    final private static String CSV_SEPERATOR = ",";
    final private static String PATH = "results.csv";
    final private static String[] headers = {"Patient Serial Number", "Diagnosis Code", "Priority Code", "Oncologist Serial Num", "Waiting Time 1", "Waiting Time 2", "Waiting Time 3", "Waiting Time 4", "Waiting Time 5", "Waiting Time 6", "Waiting Time 7", "Total Waiting Time"};

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    OncoTimeUnit t;
    boolean removeHolidays;
    Set<Patient> workingSet = new HashSet<>();

    public ExportToCSV(Set<Patient> workingSet, OncoTimeUnit t, boolean remove){
        this.workingSet = workingSet;
        removeHolidays = remove;
        this.t = t;
        setEntityManager();
        write();
    }

    private void write(){
        try{
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH), "UTF-8"));

            StringBuffer line = new StringBuffer();

            for(String h : headers){
                line.append(h);
                line.append(CSV_SEPERATOR);
            }

            write.write(line.toString());
            write.newLine();

            for(Patient p : workingSet){
                line = new StringBuffer();

                Double w1 = p.calculateFirstWait(t, removeHolidays);
                w1 = classify(w1);
                Double w2 = p.calculateSecondWait(t, removeHolidays);
                w2 = classify(w2);
                Double w3 = p.calculateThirdWait(t, removeHolidays);
                w3 = classify(w3);
                Double w4 = p.calculateFourthWait(t, removeHolidays);
                w4 = classify(w4);
                Double w5 = p.calculateFifthWait(t, removeHolidays);
                w5 = classify(w5);
                Double w6 = p.calculateSixthWait(t, removeHolidays);
                w6 = classify(w6);
                Double w7 = p.calculateSeventhWait(t, removeHolidays);
                w7 = classify(w7);

                line.append(p.getPatientSerNum());
                line.append(CSV_SEPERATOR);
                if(p.getDiagnosis() == null){
                    line.append("");
                }else{
                    line.append(p.getDiagnosis().getCategory());
                }
                line.append(CSV_SEPERATOR);
                line.append(p.getPriorityCode());
                line.append(CSV_SEPERATOR);
                line.append(p.getOncologist());
                line.append(CSV_SEPERATOR);
                line.append(w1);
                line.append(CSV_SEPERATOR);
                line.append(w2);
                line.append(CSV_SEPERATOR);
                line.append(w3);
                line.append(CSV_SEPERATOR);
                line.append(w4);
                line.append(CSV_SEPERATOR);
                line.append(w5);
                line.append(CSV_SEPERATOR);
                line.append(w6);
                line.append(CSV_SEPERATOR);
                line.append(w7);
                line.append(CSV_SEPERATOR);
                line.append(w1+w2+w3+w4+w5+w6+w7);
                line.append(CSV_SEPERATOR);


                write.write(line.toString());
                write.newLine();
            }

            write.flush();
            write.close();

        }catch(IOException e){
            e.printStackTrace();
            System.err.println("Something went wrong outputting to CSV");
        }

        FileResource res = new FileResource(new File(PATH));
        Page.getCurrent().open(res, null, false);
    }

    private double classify(Double input){
        if(((MainUI) UI.getCurrent()).isBreakIntoGroups()){
            WaitingTimeGroups g = WaitingTimeGroups.returnClosestWaitingTime(input, (((MainUI) UI.getCurrent()).getTimeUnit()));

            if ((((MainUI) UI.getCurrent()).getTimeUnit() == OncoTimeUnit.DAYS)) {
                return g.getValue();
            }
            else if ((((MainUI) UI.getCurrent()).getTimeUnit() == OncoTimeUnit.HOURS)) {
                return g.getValueAsHours();
            }
            else if ((((MainUI) UI.getCurrent()).getTimeUnit() == OncoTimeUnit.MINUTES)) {
                return g.getValueAsMinutes();
            }
        }

        return input;
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }


}
