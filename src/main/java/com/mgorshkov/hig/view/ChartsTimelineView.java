package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
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

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setCombo();
        setTopBar();

        setGrid(0);
        setCharts(0);
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
        grid = new Table();
        grid.setSizeFull();

        grid.addContainerProperty("Selected", CheckBox.class, null);
        grid.addContainerProperty("Patient Serial Number", String.class, null);

        grid.setColumnWidth("Selected", 80);

        String[] patients = patientSerNums(stage);
        for(int i = 0; i<patients.length; i++){
            grid.addItem(new Object[]{new CheckBox(), ""+patients[i]}, i);
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

    }
}

