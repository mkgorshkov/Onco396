package com.mgorshkov.hig.business.exporter;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.entities.Diagnosis;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.UI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    Set<Patient> workingSet = new HashSet<>();

    public ExportToCSV(Set<Patient> workingSet, OncoTimeUnit t){
        this.workingSet = workingSet;
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

                Double w1 = p.calculateFirstWait(t);
                Double w2 = p.calculateSecondWait(t);
                Double w3 = p.calculateThirdWait(t);
                Double w4 = p.calculateFourthWait(t);
                Double w5 = p.calculateFifthWait(t);
                Double w6 = p.calculateSixthWait(t);
                Double w7 = p.calculateSeventhWait(t);

                line.append(p.getPatientSerNum());
                line.append(CSV_SEPERATOR);
                line.append(p.getDiagnosis());
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

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }


}
