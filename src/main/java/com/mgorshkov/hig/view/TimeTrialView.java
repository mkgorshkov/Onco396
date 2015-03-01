package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.entities.Appointment;
import com.mgorshkov.hig.entities.Document;
import com.mgorshkov.hig.entities.Task;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 *
 * Want to compare the running time of selecting all of the appointments and then filtering through
 * the data structure with JAVA vs. selecting the appointments with predefined serial number and
 * status of the appointment.
 *
 */
@CDIView(value = TimeTrialView.VIEW_NAME)
public class TimeTrialView extends VerticalLayout implements View {

    //CDI and JPA
    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "TimeTrial";
    //Tabs
    TabSheet tabs = new TabSheet();
    VerticalLayout appointmentTab = new VerticalLayout();
    VerticalLayout taskTab = new VerticalLayout();
    VerticalLayout documentTab = new VerticalLayout();
    //Buttons
    Button runDirectSelectionAppointment = new Button("Run Direct", FontAwesome.DATABASE);
    Button runProgrammaticSelectionAppointment = new Button("Run Programmatic ", FontAwesome.CODE);
    Button runDirectSelectionTask = new Button("Run Direct", FontAwesome.DATABASE);
    Button runProgrammaticSelectionTask = new Button("Run Programmatic ", FontAwesome.CODE);
    Button runDirectSelectionDocument = new Button("Run Direct", FontAwesome.DATABASE);
    Button runProgrammaticSelectionDocument = new Button("Run Programmatic ", FontAwesome.CODE);
    //Headers
    final Label appointmentSelectionLabel = new Label("Appointment Selection");
    final Label taskSelectionLabel = new Label("Task Selection");
    final Label documentSelectionLabel = new Label("Document Selection");
    final Label resultsLabelAppointment = new Label("Results");
    final Label resultsLabelTask = new Label("Results");
    final Label resultsLabelDocument = new Label("Results");
    //Results
    TextArea resultsAppointment = new TextArea();
    TextArea resultsTask = new TextArea();
    TextArea resultsDocument = new TextArea();

    public void init(){
        setEntityManager();
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        addTabs();
    }

    private void addTabs(){
        addComponent(tabs);

        tabs.setSizeFull();
        tabs.addTab(appointmentTab, "Appointment Selection");
        tabs.addTab(taskTab, "Task Selection");
        tabs.addTab(documentTab, "Document Selection");

        addAppointment();
        addTask();
        addDocument();
    }



    private void addAppointment() {
        appointmentSelectionLabel.addStyleName(ValoTheme.LABEL_H2);
        appointmentTab.addComponent(appointmentSelectionLabel);

        appointmentTab.addComponent(runDirectSelectionAppointment);
        runDirectSelectionAppointment.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runDirectSelectionAppointment();
            }
        });
        appointmentTab.addComponent(runProgrammaticSelectionAppointment);
        runProgrammaticSelectionAppointment.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runProgrammaticSelectionAppointment();
            }
        });

        resultsLabelAppointment.addStyleName(ValoTheme.LABEL_H2);
        appointmentTab.addComponent(resultsLabelAppointment);
        appointmentTab.addComponent(resultsAppointment);

        resultsAppointment.setWidth("100%");
        resultsAppointment.setHeight("300px");
    }

    private void runDirectSelectionAppointment(){

        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 100;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Appointment> directQuery = entityManager.createNamedQuery("Appointment.findByAliasAndStatus", Appointment.class);
            directQuery.setParameter("aSerNum", new BigInteger("3"));
            directQuery.setParameter("aStatus", "Cancelled");
            directQuery.setParameter("aStatus2", "Open");

            List<Appointment> directList = directQuery.getResultList();
            long temp = (System.currentTimeMillis() - start);

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsAppointment.setValue(resultsAppointment.getValue()+
                "=======================Direct Selection==================================\n"+
                "Total Trials: "+trials+"\n"+
                "Total Time: " + (System.currentTimeMillis() - starttotal)+" ms\n"+
                "Max Time: "+max+" ms\n"+
                "Min Time: "+min+" ms\n"+
                "Average Time: "+average/trials+" ms\n"+
                "=======================Direct Selection==================================\n");

    }

    private void runProgrammaticSelectionAppointment(){

        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 100;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Appointment> directQuery = entityManager.createNamedQuery("Appointment.findAll", Appointment.class);

            List<Appointment> directList = directQuery.getResultList();
            List<Appointment> workingList = new ArrayList<>();
            workingList.addAll(directList);

            for(Appointment a : workingList){
                if(!a.getAliasSerNum().equals(new BigInteger("3")) || a.getStatus().equals("Cancelled") || a.getStatus().equals("Open")){
                    directList.remove(a);
                }
            }

            long temp = (System.currentTimeMillis() - start);

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsAppointment.setValue(resultsAppointment.getValue() +
                "====================Programmatic Selection===============================\n" +
                "Total Trials: " + trials + "\n" +
                "Total Time: " + (System.currentTimeMillis() - starttotal) + " ms\n" +
                "Max Time: " + max + " ms\n" +
                "Min Time: " + min + " ms\n" +
                "Average Time: " + average / trials + " ms\n" +
                "====================Programmatic Selection===============================\n");
    }

    private void addTask(){
        taskSelectionLabel.addStyleName(ValoTheme.LABEL_H2);
        taskTab.addComponent(taskSelectionLabel);

        taskTab.addComponent(runDirectSelectionTask);
        runDirectSelectionTask.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runDirectSelectionTask();
            }
        });
        taskTab.addComponent(runProgrammaticSelectionTask);
        runProgrammaticSelectionTask.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runProgrammaticSelectionTask();
            }
        });

        resultsLabelTask.addStyleName(ValoTheme.LABEL_H2);
        taskTab.addComponent(resultsLabelTask);
        taskTab.addComponent(resultsTask);

        resultsTask.setWidth("100%");
        resultsTask.setHeight("300px");
    }

    private void runDirectSelectionTask(){
        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 10;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Task> directQuery = entityManager.createNamedQuery("Task.findByAliasAndStatus", Task.class);
            directQuery.setParameter("aSerNum", new BigInteger("17"));
            directQuery.setParameter("aStatus", "Cancelled");
            directQuery.setParameter("aStatus2", "Open");

            TypedQuery<Task> directQuery2 = entityManager.createNamedQuery("Task.findByAliasAndStatus", Task.class);
            directQuery2.setParameter("aSerNum", new BigInteger("8"));
            directQuery2.setParameter("aStatus", "Cancelled");
            directQuery2.setParameter("aStatus2", "Open");

            TypedQuery<Task> directQuery3 = entityManager.createNamedQuery("Task.findByAliasAndStatus", Task.class);
            directQuery3.setParameter("aSerNum", new BigInteger("22"));
            directQuery3.setParameter("aStatus", "Cancelled");
            directQuery3.setParameter("aStatus2", "Open");

            TypedQuery<Task> directQuery4 = entityManager.createNamedQuery("Task.findByAliasAndStatus", Task.class);
            directQuery4.setParameter("aSerNum", new BigInteger("18"));
            directQuery4.setParameter("aStatus", "Cancelled");
            directQuery4.setParameter("aStatus2", "Open");

            TypedQuery<Task> directQuery5 = entityManager.createNamedQuery("Task.findByAliasAndStatus", Task.class);
            directQuery5.setParameter("aSerNum", new BigInteger("19"));
            directQuery5.setParameter("aStatus", "Cancelled");
            directQuery5.setParameter("aStatus2", "Open");

            List<Task> directList = directQuery.getResultList();
            directList.addAll(directQuery2.getResultList());
            directList.addAll(directQuery3.getResultList());
            directList.addAll(directQuery4.getResultList());
            directList.addAll(directQuery5.getResultList());

            long temp = (System.currentTimeMillis() - start);

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsTask.setValue(resultsTask.getValue()+
                "=======================Direct Selection==================================\n"+
                "Total Trials: "+trials+"\n"+
                "Total Time: " + (System.currentTimeMillis() - starttotal)+" ms\n"+
                "Max Time: "+max+" ms\n"+
                "Min Time: "+min+" ms\n"+
                "Average Time: "+average/trials+" ms\n"+
                "=======================Direct Selection==================================\n");

    }

    private void runProgrammaticSelectionTask(){

        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 10;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Task> directQuery = entityManager.createNamedQuery("Task.findAll", Task.class);

            List<Task> directList = directQuery.getResultList();
            List<Task> workingList = new ArrayList<>();
            workingList.addAll(directList);

            for(Task t : workingList){
                if((!t.getAliasSerNum().equals(new BigInteger("17")) && !t.getAliasSerNum().equals(new BigInteger("8")) && !t.getAliasSerNum().equals(new BigInteger("22")) && !t.getAliasSerNum().equals(new BigInteger("18")) && !t.getAliasSerNum().equals(new BigInteger("19"))) || t.getStatus().equals("Cancelled") || t.getStatus().equals("Open")){
                    directList.remove(t);
                }
            }

            long temp = (System.currentTimeMillis() - start);

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsTask.setValue(resultsTask.getValue() +
                "====================Programmatic Selection===============================\n" +
                "Total Trials: " + trials + "\n" +
                "Total Time: " + (System.currentTimeMillis() - starttotal) + " ms\n" +
                "Max Time: " + max + " ms\n" +
                "Min Time: " + min + " ms\n" +
                "Average Time: " + average / trials + " ms\n" +
                "====================Programmatic Selection===============================\n");
    }

    private void addDocument(){
        documentSelectionLabel.addStyleName(ValoTheme.LABEL_H2);
        documentTab.addComponent(documentSelectionLabel);

        documentTab.addComponent(runDirectSelectionDocument);
        runDirectSelectionDocument.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runDirectSelectionDocument();
            }
        });
        documentTab.addComponent(runProgrammaticSelectionTask);
        runProgrammaticSelectionTask.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                runProgrammaticSelectionDocument();
            }
        });

        resultsLabelDocument.addStyleName(ValoTheme.LABEL_H2);
        documentTab.addComponent(resultsLabelDocument);
        documentTab.addComponent(resultsDocument);

        resultsDocument.setWidth("100%");
        resultsDocument.setHeight("300px");
    }

    private void runDirectSelectionDocument(){
        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 10;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Document> directQuery = entityManager.createNamedQuery("Document.findByAlias", Document.class);

            List<Document> directList = directQuery.getResultList();

            long temp = (System.currentTimeMillis() - start);

            System.out.println(temp);
            System.out.println(directList.size());

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsDocument.setValue(resultsDocument.getValue()+
                "=======================Direct Selection==================================\n"+
                "Total Trials: "+trials+"\n"+
                "Total Time: " + (System.currentTimeMillis() - starttotal)+" ms\n"+
                "Max Time: "+max+" ms\n"+
                "Min Time: "+min+" ms\n"+
                "Average Time: "+average/trials+" ms\n"+
                "=======================Direct Selection==================================\n");

    }

    private void runProgrammaticSelectionDocument(){
        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        long average = 0;
        int trials = 10;

        long starttotal = System.currentTimeMillis();

        for(int i = 0; i<trials; i++) {
            long start = System.currentTimeMillis();

            TypedQuery<Document> directQuery = entityManager.createNamedQuery("Document.findAll", Document.class);

            List<Document> directList = directQuery.getResultList();
            List<Document> workingList = new ArrayList<>();
            workingList.addAll(directList);

            for(Document d : workingList){
                if(!d.getAliasSerNum().equals(new BigInteger("29")) && !d.getAliasSerNum().equals(new BigInteger("9")) && !d.getAliasSerNum().equals(new BigInteger("20")) && !d.getAliasSerNum().equals(new BigInteger("21"))){
                    directList.remove(d);
                }
            }

            long temp = (System.currentTimeMillis() - start);

            System.out.println(temp);
            System.out.println(directList.size());

            if(temp > max) max = temp;
            if(temp < min) min = temp;
            average = average + temp;
        }

        resultsDocument.setValue(resultsDocument.getValue() +
                "====================Programmatic Selection===============================\n" +
                "Total Trials: " + trials + "\n" +
                "Total Time: " + (System.currentTimeMillis() - starttotal) + " ms\n" +
                "Max Time: " + max + " ms\n" +
                "Min Time: " + min + " ms\n" +
                "Average Time: " + average / trials + " ms\n" +
                "====================Programmatic Selection===============================\n");
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
