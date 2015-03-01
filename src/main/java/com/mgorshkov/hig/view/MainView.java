package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.DataPointType;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.entities.Appointment;
import com.mgorshkov.hig.entities.Task;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

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
    TabSheet tabsheet = new TabSheet();

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        statusLabel.setSizeFull();

        addComponent(statusLabel);
        setEntityManager();
        addAppointmentsToUsers();
//        addFirstTaskToUsers();
//        filterUsers();

//        addComponent(tabsheet);

//        addTabsheet();

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
        TypedQuery<Appointment> query = entityManager.createNamedQuery("Appointment.findByAlias", Appointment.class);
        query.setParameter("aSerNum", new BigInteger("3"));
        List<Appointment> appointmentList = query.getResultList();

        System.out.println(appointmentList.size());

        for(Appointment app : appointmentList){
            if(!app.getStatus().equals("Cancelled") && !app.getStatus().equals("Open")){

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

    private void showGraphOne(){
        double mean = 0;

        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Waiting Time: CT-Sim to Initial Contour");

        XAxis x = new XAxis();
        x.setTitle("Patient SerNum");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Waiting Time (Hours)");
        conf.addyAxis(y);

        PlotLine plotLine = new PlotLine();
        plotLine.setColor(new SolidColor("red"));
        plotLine.setWidth(1);
        plotLine.setzIndex(0);
        plotLine.setDashStyle(DashStyle.DASHDOT);

        PlotLine plotLine2 = new PlotLine();
        plotLine.setColor(new SolidColor("orange"));
        plotLine.setWidth(1);
        plotLine.setzIndex(0);
        plotLine.setDashStyle(DashStyle.DASHDOT);


        y.setPlotLines(plotLine, plotLine2);

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
            mean += p.calculateFirstWait();
        }

        mean = mean/workingSet.size();
        plotLine.setValue(mean);
        PlotBandLabel label = new PlotBandLabel("Theoretical mean: "+mean);
        label.setAlign(HorizontalAlign.CENTER);
        Style style = new Style();
        style.setColor(new SolidColor("gray"));
        label.setStyle(style);
        plotLine.setLabel(label);


        x.setCategories(categories);

        double sd = 0;
        for (int j=0; j<values.length;j++)
        {
            sd = sd + Math.pow((Double.parseDouble(values[j].toString()) - mean), 2);
        }


        plotLine2.setValue(mean+sd);
        PlotBandLabel label2 = new PlotBandLabel("Mean + SD: "+(mean+sd));
        label.setAlign(HorizontalAlign.CENTER);
        Style style2 = new Style();
        style2.setColor(new SolidColor("gray"));
        label.setStyle(style2);
        plotLine2.setLabel(label2);


        conf.addSeries(new ListSeries("First Wait", values));
        chart.setSizeFull();
        tabsheet.addTab(chart, "First Waiting Time - Everyone");
    }

    private void showGraphTwo(){
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Sample Waiting Times");
        conf.setSubTitle("Patient 1234");

        XAxis x = new XAxis();
        x.setTitle("Patient SerNum");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Waiting Time (Hours)");
        conf.addyAxis(y);

        PlotLine plotLine = new PlotLine();
        plotLine.setColor(new SolidColor("red"));
        plotLine.setValue(55);
        plotLine.setWidth(1);
        plotLine.setzIndex(0);
        plotLine.setDashStyle(DashStyle.DASHDOT);
        PlotBandLabel label = new PlotBandLabel("Theoretical mean: 55");
        label.setAlign(HorizontalAlign.LEFT);
        Style style = new Style();
        style.setColor(new SolidColor("red"));
        label.setStyle(style);
        plotLine.setLabel(label);

        y.setPlotLines(plotLine);


        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("''+this.x +''+' Patient with: '"
                + "+ Highcharts.numberFormat(this.y, 1) +' hours wait'");
        conf.setTooltip(tooltip);


        conf.addSeries(new ListSeries("First Wait", 12));
        conf.addSeries(new ListSeries("Second Wait", 15));
        conf.addSeries(new ListSeries("Third Wait", 70));
        conf.addSeries(new ListSeries("Fourth Wait", 70));
        conf.addSeries(new ListSeries("Fifth Wait", 90));
        conf.addSeries(new ListSeries("Sixth Wait", 12));
        conf.addSeries(new ListSeries("Seventh Wait", 23));
        conf.addSeries(new ListSeries("Eighth Wait", 2));


        chart.setSizeFull();
        tabsheet.addTab(chart, "Waiting Times - Patient 1234");
    }

    private void addTabsheet(){
        tabsheet.setSizeFull();
        setExpandRatio(tabsheet, 0.9f);

        showGraphOne();
        showGraphTwo();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
