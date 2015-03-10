package com.mgorshkov.hig.business.exporter;

import com.mgorshkov.hig.model.Patient;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public class ExportToCSV {
    final private static String CSV_SEPERATOR = ",";
    final private static String PATH = "results.csv";

    Set<Patient> workingSet = new HashSet<>();

    public ExportToCSV(Set<Patient> workingSet){
        this.workingSet = workingSet;
        write();
    }

    private void write(){
        try{
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH), "UTF-8"));

            for(Patient p : workingSet){
                StringBuffer line = new StringBuffer();

                line.append(p.getPatientSerNum());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFirstWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSecondWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateThirdWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFourthWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateFifthWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSixthWait());
                line.append(CSV_SEPERATOR);
                line.append(p.calculateSeventhWait());
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
}
