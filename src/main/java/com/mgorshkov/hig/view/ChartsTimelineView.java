package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = PatientSummaryView.VIEW_NAME)
public class ChartsTimelineView extends VerticalLayout implements View,Button.ClickListener {

    public final static String VIEW_NAME = "ChartsTimelineView";
    Set<Patient> workingSet = new HashSet<>();
    Boolean[] isInGraph;

    Label title = new Label(VIEW_NAME);

    final static String[] choices = {"Stage 1: CT Scan - Initial Contour", "Stage 2: Initial Contour - MD Contour", "Stage 3: MD Contour - CT Planning Sheet", "Stage 4: CT Planning Sheet - Dose Calculation", "Stage 5: Dose Calculation - MD Approve", "Stage 6: MD Approve - Physics QA", "Stage 7: Physics QA - Ready for Treatement"};
    ComboBox selector = new ComboBox("Stage Select: ");
    Chart chart;
    Table grid;
    Button update = new Button("Update");

    HorizontalLayout topBar = new HorizontalLayout();
    VerticalLayout leftTopBar = new VerticalLayout();

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;

        setBooleanList();
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setCombo();
        setTopBar();

        setGrid(0);
        setCharts(0);
    }

    private void setBooleanList(){
        isInGraph = new Boolean[workingSet.size()];
        Arrays.fill(isInGraph, Boolean.FALSE);
    }

    private void setTopBar() {
        leftTopBar.addComponent(selector);
        leftTopBar.addComponent(update);
        leftTopBar.setSpacing(true);

        update.addClickListener(this);
        leftTopBar.setComponentAlignment(update, Alignment.MIDDLE_RIGHT);
        topBar.addComponent(leftTopBar);
        topBar.setSizeFull();
        topBar.setSpacing(true);
        addComponent(topBar);
        setExpandRatio(topBar, 0.3f);
    }

    private void setGrid(int stage){
        setBooleanList();
        grid = new Table();
        grid.setSizeFull();

        grid.addContainerProperty("Selected", CheckBox.class, null);
        grid.addContainerProperty("Patient Serial Number", String.class, null);

        grid.setColumnWidth("Selected", 80);

        String[] patients = patientSerNums(stage);
        System.out.println("Patients "+patients.length);
        for(int i = 0; i<patients.length; i++){
            final String current = ""+patients[i];
            final int crtI = i;

            CheckBox t = new CheckBox();
            t.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    if(isInGraph[crtI]){
                        isInGraph[crtI] = false;
                    } else{
                        isInGraph[crtI] = true;
                    }
                }
            });
            grid.addItem(new Object[]{t, ""+patients[i]}, i);
        }

        topBar.addComponent(grid);
    }

    private void setCombo(){
        selector.addItems(choices);
        selector.setNullSelectionAllowed(false);
        selector.setValue(choices[0]);
        selector.setWidth("100%");
    }

    private void setCharts(int stage){
        chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle(choices[stage]);

        XAxis x = new XAxis();
        x.setCategories(patientSerNums(stage));
        x.setTitle("Patient Serial Number");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Waiting Time (Minutes)");
        conf.addyAxis(y);

        ArrayList<Number> values = new ArrayList<>();

        if (stage == 0) {
            for(Patient p : workingSet){
                values.add(p.calculateFirstWait());

            }
        }else if(stage == 1){
            for(Patient p : workingSet){
                values.add(p.calculateSecondWait());

            }
        }else if(stage == 2){
            for(Patient p : workingSet){
                values.add(p.calculateThirdWait());

            }
        }else if(stage == 3){
            for(Patient p : workingSet){
                values.add(p.calculateFourthWait());

            }
        }else if(stage == 4){
            for(Patient p : workingSet){
                values.add(p.calculateFifthWait());

            }
        }else if(stage == 5){
            for(Patient p : workingSet){
                values.add(p.calculateSixthWait());

            }
        }else if(stage == 6){
            for(Patient p : workingSet){
                values.add(p.calculateSeventhWait());

            }
        }

        Number[] valuesToAdd = new Number[values.size()];
        for(int i = 0; i<valuesToAdd.length; i++){
            valuesToAdd[i] = values.get(i);
        }

        conf.setSeries(new ListSeries("", valuesToAdd));
        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.7f);

    }

    private void setCharts(int stage, Set<Patient> input){

        chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle(choices[stage]);

        XAxis x = new XAxis();
        x.setCategories(patientSerNums(stage));
        x.setTitle("Patient Serial Number");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Waiting Time (Minutes)");
        conf.addyAxis(y);

        ArrayList<Number> values = new ArrayList<>();

        if (stage == 0) {
            for(Patient p : input){
                values.add(p.calculateFirstWait());

            }
        }else if(stage == 1){
            for(Patient p : input){
                values.add(p.calculateSecondWait());

            }
        }else if(stage == 2){
            for(Patient p : input){
                values.add(p.calculateThirdWait());

            }
        }else if(stage == 3){
            for(Patient p : input){
                values.add(p.calculateFourthWait());

            }
        }else if(stage == 4){
            for(Patient p : input){
                values.add(p.calculateFifthWait());

            }
        }else if(stage == 5){
            for(Patient p : input){
                values.add(p.calculateSixthWait());

            }
        }else if(stage == 6){
            for(Patient p : input){
                values.add(p.calculateSeventhWait());

            }
        }

        Number[] valuesToAdd = new Number[values.size()];
        for(int i = 0; i<valuesToAdd.length; i++){
            valuesToAdd[i] = values.get(i);
        }

        conf.setSeries(new ListSeries("", valuesToAdd));
        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.7f);

    }

    private String[] patientSerNums(int stage){
        ArrayList<Integer> sers = new ArrayList<>();

        switch(stage){
            case 0 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 1 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 2 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 3 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 4 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 5 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
            case 6 : for(Patient p : workingSet){
                sers.add(p.getPatientSerNum());
            }
                break;
        }

        Collections.sort(sers);

        String[] serialToReturn = new String[sers.size()];

        for(int i = 0; i<sers.size(); i++){
            serialToReturn[i] = ""+sers.get(i);
        }

        return serialToReturn;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init(((MainUI) getUI()).getPatientData());
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        String[] selectors = null;
        int stageValue = 0;

        if(selector.getValue().toString().equals(choices[0])){
            selectors = patientSerNums(0);
            stageValue = 0;
        }else if(selector.getValue().toString().equals(choices[1])){
            selectors = patientSerNums(1);
            stageValue = 1;

        }else if(selector.getValue().toString().equals(choices[2])){
            selectors = patientSerNums(2);
            stageValue = 2;

        }else if(selector.getValue().toString().equals(choices[3])){
            selectors = patientSerNums(3);
            stageValue = 3;

        }else if(selector.getValue().toString().equals(choices[4])){
            selectors = patientSerNums(4);
            stageValue = 4;

        }else if(selector.getValue().toString().equals(choices[5])){
            selectors = patientSerNums(5);
            stageValue = 5;

        }else if(selector.getValue().toString().equals(choices[6])){
            selectors = patientSerNums(6);
            stageValue = 6;

        }

        Set<Patient> chosenValues = new HashSet<>();
        for(int i = 0; i<selectors.length; i++){
            if(isInGraph[i]){
                chosenValues.add(isInPatientData(Integer.parseInt(selectors[i])));
            }
        }

        removeComponent(chart);
        topBar.removeComponent(grid);
        setCharts(stageValue, chosenValues);
        setGrid(stageValue);
     }

    public Patient isInPatientData(int setNum){
        for(Patient p : workingSet){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
    }

}

