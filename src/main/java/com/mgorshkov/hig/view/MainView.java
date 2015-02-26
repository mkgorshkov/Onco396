package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.Model.DataPoint;
import com.mgorshkov.hig.Model.DataPointType;
import com.mgorshkov.hig.Model.Patient;
import com.mgorshkov.hig.business.SessionBean;
import com.mgorshkov.hig.entities.Alias;
import com.mgorshkov.hig.entities.Appointment;
import com.mgorshkov.hig.entities.Task;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by mkgo on 25/02/15.
 */
@CDIView(value = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "";
    private Label statusLabel = new Label("Start.");
    Set<Patient> workingSet = new HashSet<>();

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        statusLabel.setSizeFull();

        addComponent(statusLabel);
        setEntityManager();
        addAppointmentsToUsers();
        addFirstTaskToUsers();
        filterUsers();
        showGraph();

        setExpandRatio(statusLabel, 0.1f);
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }

    private void addAppointmentsToUsers(){
        long start = System.currentTimeMillis();
        getAppointment();
        statusLabel.setValue(statusLabel.getValue() + "\nCT-Sum Added to Users. " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void addFirstTaskToUsers(){
        long start = System.currentTimeMillis();
        getIntialContour();
        statusLabel.setValue(statusLabel.getValue() + "\nInitial Contour Added to Users. " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void getAppointment(){
        TypedQuery<Appointment> query = entityManager.createNamedQuery("Appointment.findAll", Appointment.class);
        List<Appointment> appointmentList = query.getResultList();

        for(Appointment app : appointmentList){
            if(app.getAliasSerNum().equals(new BigInteger("3")) && !app.getStatus().equals("Cancelled") && !app.getStatus().equals("Open")){

                Patient crtPatient = isInPatientData(app.getPatientSerNum());

                if(crtPatient == null){

                    Patient patient = new Patient(app.getPatientSerNum());
                    patient.addDataPoint(new DataPoint(app.getScheduledStartTime(), DataPointType.APPOINTMENT));

                    workingSet.add(patient);

                }else{

                    workingSet.remove(crtPatient);

                    crtPatient.addDataPoint(new DataPoint(app.getScheduledStartTime(), DataPointType.APPOINTMENT));

                    workingSet.add(crtPatient);

                }
            }
        }
    }

    private void getIntialContour(){
        TypedQuery<Task> query = entityManager.createNamedQuery("Task.findAll", Task.class);
        List<Task> taskList = query.getResultList();

        for(Task task : taskList){
            if(task.getAliasSerNum().equals(new BigInteger("17")) && !task.getStatus().equals("Cancelled") && !task.getStatus().equals("Open")){

                Patient crtPatient = isInPatientData(task.getPatientSerNum());

                if(crtPatient == null){

                    Patient patient = new Patient(task.getPatientSerNum());
                    patient.addDataPoint(new DataPoint(task.getCreationDate(), DataPointType.TASK));

                    workingSet.add(patient);

                }else{

                    workingSet.remove(crtPatient);

                    crtPatient.addDataPoint(new DataPoint(task.getCreationDate(), DataPointType.TASK));

                    workingSet.add(crtPatient);
                }
            }
        }
    }

    private void filterUsers(){
        Set<Patient> copyPatient = new HashSet<>();
        copyPatient.addAll(workingSet);

        for(Patient patient : copyPatient){
            if(patient.getDataPoints().size() == 1){
                workingSet.remove(patient);
            }
        }

        copyPatient = new HashSet<>();
        copyPatient.addAll(workingSet);

        for(Patient patient: copyPatient){
            Iterator<DataPoint> it = patient.getDataPoints().iterator();
            DataPoint pointA = it.next();
            DataPoint pointB = it.next();
            if(pointA.getTimePoint() == null || pointB.getTimePoint() == null || pointA.getTimePoint().after(pointB.getTimePoint()) || pointA.getType() == pointB.getType()){
                workingSet.remove(patient);
            }
        }
    }

    private Patient isInPatientData(int setNum){
        for(Patient p : workingSet){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
    }

    private void showGraph(){
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Waiting Time: CT-Sim to Initial Contour");

        XAxis x = new XAxis();
        x.setTitle("Patient SerNum");

        YAxis y = new YAxis();
        y.setTitle("Waiting Time (Hours)");

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("''+this.x +''+' Patient with: '"
                + "+ Highcharts.numberFormat(this.y, 1) +' hours wait'");
        conf.setTooltip(tooltip);


        Number[] values = new Number[workingSet.size()];
        String[] categories = new String[workingSet.size()];

        int i = 0;

        for(Patient p : workingSet) {
            values[i] = p.calculateFirstWait();
            System.out.println(p.calculateFirstWait());
            categories[i] = ""+p.getPatientSerNum();
            i++;
        }

        x.setCategories(categories);

        conf.addSeries(new ListSeries("First Wait", values));
        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.9f);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
