package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = PatientView.VIEW_NAME)
public class PatientView extends VerticalLayout implements View,ComboBox.ValueChangeListener {

    public final static String VIEW_NAME = "PatientView";
    Set<Patient> workingSet = new HashSet<>();

    Label title = new Label(VIEW_NAME);

    final static String[] labels = {"Stage 1: CT Scan - Initial Contour", "Stage 2: Initial Contour - MD Contour", "Stage 3: MD Contour - CT Planning Sheet", "Stage 4: CT Planning Sheet - Dose Calculation", "Stage 5: Dose Calculation - MD Approve", "Stage 6: MD Approve - Physics QA", "Stage 7: Physics QA - Ready for Treatement"};

    int[] choices;
    ComboBox selector = new ComboBox("Patient Select: ");
    Chart chart;

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setTitle();
        setCombo();
        setCharts(1, isInPatientData(1).getDiagnosis());
    }

    private void initAndSetCharts(Set<Patient> workingSet, int patientSer){
        this.workingSet = workingSet;

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setTitle();
        setCombo();
        setCharts(patientSer, isInPatientData(patientSer).getDiagnosis());
    }

    private void setTitle(){
        title.addStyleName(ValoTheme.LABEL_H2);
        addComponent(title);
        setExpandRatio(title, 0.1f);
    }

    private void setCombo(){
        selector = patientSerNums(selector);

        selector.setWidth("100%");
        selector.addValueChangeListener(this);

        addComponent(selector);
        setExpandRatio(selector, 0.1f);
    }

    private ComboBox patientSerNums(ComboBox selector){

        int[] serialToReturn = new int[workingSet.size()];

        ArrayList<Integer> names = new ArrayList<>();
        for(Patient p : workingSet){
            names.add(p.getPatientSerNum());
        }

        Collections.sort(names);

        int i = 0;
        for(int serNum : names){
            serialToReturn[i] = serNum;
            selector.addItem(serNum);
        }

        selector.setNullSelectionAllowed(false);
        selector.setValue(names.get(0));

        choices = serialToReturn;

        return selector;
    }

    private void setCharts(int patientSer, String diagnosis){
        chart = new Chart(ChartType.BAR);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Stages: "+patientSer);
        conf.setSubTitle("Diagnosis: "+diagnosis);


        XAxis x = new XAxis();
        x.setCategories(""+patientSer);
        x.setTitle("Patient Serial Number");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Waiting Time "+(((MainUI) getUI()).getTimeUnit()));
        conf.addyAxis(y);

        PlotOptionsSeries plot = new PlotOptionsSeries();
        plot.setStacking(Stacking.NORMAL);
        conf.setPlotOptions(plot);

        Legend legend = new Legend();
        legend.setBackgroundColor("#FFFFFF");
        legend.setReversed(true);
        conf.setLegend(legend);

        Patient p = isInPatientData(patientSer);

        conf.addSeries(new ListSeries(labels[6], p.calculateSeventhWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[5], p.calculateSixthWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[4], p.calculateFifthWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[3], p.calculateFourthWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[2], p.calculateThirdWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[1], p.calculateSecondWait(((MainUI) getUI()).getTimeUnit())));
        conf.addSeries(new ListSeries(labels[0], p.calculateFirstWait(((MainUI) getUI()).getTimeUnit())));

        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.8f);

    }

    public Patient isInPatientData(int setNum){
        for(Patient p : workingSet){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        if (((MainUI) getUI()).getCrtUser() != null) {
            initAndSetCharts(((MainUI) getUI()).getPatientData(), Integer.parseInt(((MainUI) getUI()).getCrtUser()));
        } else {
            init(((MainUI) getUI()).getPatientData());
        }
    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        removeComponent(chart);
        setCharts((Integer) selector.getValue(), isInPatientData((Integer) selector.getValue()).getDiagnosis());
    }

}

