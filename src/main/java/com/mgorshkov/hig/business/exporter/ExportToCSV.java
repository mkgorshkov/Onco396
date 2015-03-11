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
    final private static String[] headers = {"Patient Serial Number", "Diagnosis Code", "Waiting Time 1", "Waiting Time 2", "Waiting Time 3", "Waiting Time 4", "Waiting Time 5", "Waiting Time 6", "Waiting Time 7"};

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

                line.append(p.getPatientSerNum());
                line.append(CSV_SEPERATOR);
                line.append(p.getDiagnosis());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFirstWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSecondWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateThirdWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFourthWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFifthWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSixthWait(t));
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSeventhWait(t));
                line.append(CSV_SEPERATOR);

                write.write(line.toString());
                write.newLine();
            }

            write.flush();
            write.close();

        }catch(IOException e){
            System.err.println("Something went wrong outputting to CSV");
        }

        FileResource res = new FileResource(new File(PATH));
        Page.getCurrent().open(res, null, false);
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }


}
