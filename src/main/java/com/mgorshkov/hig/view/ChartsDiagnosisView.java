package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.filters.FilterByStageCodes;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.DiagnosisModel;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.WaitingTimeGroups;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Not;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = ChartsDiagnosisView.VIEW_NAME)
public class ChartsDiagnosisView extends VerticalLayout implements View,ComboBox.ValueChangeListener {

    public final static String VIEW_NAME = "ChartsDiagnosisView";
    Set<Patient> workingSet = new HashSet<>();

    final static String[] CHOICES = {"Stage 1: CT Scan - Initial Contour", "Stage 2: Initial Contour - MD Contour", "Stage 3: MD Contour - CT Planning Sheet", "Stage 4: CT Planning Sheet - Dose Calculation", "Stage 5: Dose Calculation - MD Approve", "Stage 6: MD Approve - Physics QA", "Stage 7: Physics QA - Ready for Treatement"};
    final static Label CHOOSE_SOMETHING = new Label("Make selection above to see graphs.");
    List<String> diagnosisChoices;

    FilterByStageCodes filterStageCodes;

    ComboBox selector = new ComboBox("Stage Select: ");
    ComboBox diagnosis = new ComboBox("Diagnosis: ");
    Chart chart;

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;
        filterStageCodes = new FilterByStageCodes(workingSet);
        diagnosisChoices = filterStageCodes.sortedkeys();
        addDescriptionsToChoices();

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setCombo();
        addComponent(CHOOSE_SOMETHING);
        CHOOSE_SOMETHING.setWidthUndefined();
        setExpandRatio(CHOOSE_SOMETHING, 0.9f);
        setComponentAlignment(CHOOSE_SOMETHING, Alignment.MIDDLE_CENTER);
    }

    private void setCombo(){
        selector.addItems(CHOICES);
        selector.setNullSelectionAllowed(false);
        selector.setValue(CHOICES[0]);
        selector.setWidth("100%");
        selector.addValueChangeListener(this);

        diagnosis.addItems(diagnosisChoices);
        diagnosis.setWidth("100%");
        diagnosis.setNullSelectionItemId("All");
        diagnosis.setValue(null);
        diagnosis.addValueChangeListener(this);
        diagnosis.setFilteringMode(FilteringMode.CONTAINS);

        HorizontalLayout h = new HorizontalLayout(selector, diagnosis);
        h.setSizeFull();
        h.setSpacing(true);
        addComponent(h);
        setExpandRatio(h, 0.1f);
    }

    private void setCharts(final int stage, final HashMap<String, List<Patient>> input){
        chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle(CHOICES[stage]);

        XAxis x = new XAxis();

        String[] outList = new String[input.keySet().size()];
        int i = 0;
        for(String s : input.keySet()){
            outList[i] = input.get(s).get(0).getDiagnosis().getDescription() +" ("+s+")";
            i++;
        }

        x.setCategories(outList);
        x.setTitle("Diagosis Subcategory");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Number of Patients");
        conf.addyAxis(y);

        ArrayList<Number> values = new ArrayList<>();

        if (stage == 0) {
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 1){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 2){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 3){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 4){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 5){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }else if(stage == 6){
            for(String diagnosis : input.keySet()){
                values.add(input.get(diagnosis).size());
            }
        }

        Number[] valuesToAdd = new Number[values.size()];
        for(int j = 0; j<valuesToAdd.length; j++){
            valuesToAdd[j] = values.get(j);
        }

        conf.setSeries(new ListSeries("", valuesToAdd));
        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.8f);

        chart.addPointClickListener(new PointClickListener() {
            @Override
            public void onClick(PointClickEvent pointClickEvent) {
                int index1 = pointClickEvent.getCategory().indexOf("(");
                int index2 = pointClickEvent.getCategory().indexOf(")");

                drillDownGraph(stage, pointClickEvent.getCategory().substring(index1 + 1, index2), pointClickEvent.getCategory(), input.get(pointClickEvent.getCategory().substring(index1 + 1, index2)));
            }
        });

    }

    private void drillDownGraph(final int stage, String category, String description, List<Patient> patients){
        removeComponent(chart);
        chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle(CHOICES[stage]);
        conf.setSubTitle("Subcategory: "+description);

        XAxis x = new XAxis();
        x.setCategories(patientSerNums(stage, new HashSet<Patient>(patients)));
        x.setTitle("Patient Serial Number");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Patient Waiting Time ("+((MainUI) getUI()).getTimeUnit().toString()+")");
        conf.addyAxis(y);

        ArrayList<Number> values = new ArrayList<>();

        if (stage == 0) {
            for(Patient p : patients){
                    values.add(classify(p.calculateFirstWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));
            }
        }else if(stage == 1){
            for(Patient p : patients){
                    values.add(classify(p.calculateSecondWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));

            }
        }else if(stage == 2){
            for(Patient p : patients){
                    values.add(classify(p.calculateThirdWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));

            }
        }else if(stage == 3){
            for(Patient p : patients){
                    values.add(classify(p.calculateFourthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));

            }
        }else if(stage == 4){
            for(Patient p : patients){
                    values.add(classify(p.calculateFifthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));

            }
        }else if(stage == 5){
            for(Patient p : patients){
                    values.add(classify(p.calculateSixthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));

            }
        }else if(stage == 6){
            for(Patient p : patients){
                    values.add(classify(p.calculateSeventhWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays())));
            }
        }

        Number[] valuesToAdd = new Number[values.size()];
        for(int j = 0; j<valuesToAdd.length; j++){
            valuesToAdd[j] = values.get(j);
        }

        conf.setSeries(new ListSeries("", valuesToAdd));
        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.8f);

        chart.addPointClickListener(new PointClickListener() {
            @Override
            public void onClick(final PointClickEvent pointClickEvent) {
                final Window w = new Window();
                VerticalLayout v = new VerticalLayout();
                w.setContent(v);
                Button b = new Button("Go to specific view");
                Button b2 = new Button("Go to calendar view");
                v.addComponent(b);
                v.addComponent(b2);

                w.center();
                w.setWidth("300px");
                v.setSpacing(true);
                v.setMargin(true);

                b.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        w.close();
                        ((MainUI) getUI()).setCrtUser(pointClickEvent.getCategory());
                        getUI().getNavigator().navigateTo(PatientView.VIEW_NAME);
                    }
                });

                b2.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        w.close();
                        ((MainUI) getUI()).setCrtUser(pointClickEvent.getCategory());
                        getUI().getNavigator().navigateTo(CalendarView.VIEW_ID);
                    }
                });

                getUI().addWindow(w);
            }
        });
    }
    private String[] patientSerNums(int stage, Set<Patient> input){
        ArrayList<String> sers = new ArrayList<>();

        switch(stage){
            case 0 : for(Patient p : input){
                         sers.add("" + p.getPatientSerNum());
                     }
                     break;
            case 1 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
            case 2 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
            case 3 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
            case 4 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
            case 5 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
            case 6 : for(Patient p : input){
                    sers.add("" + p.getPatientSerNum());
            }
                break;
        }


        String[] serialToReturn = new String[sers.size()];

        for(int i = 0; i<sers.size(); i++){
            serialToReturn[i] = sers.get(i);
        }

        return serialToReturn;
    }


    private HashMap<String, List<Patient>> filterByDiagnosis(){
        HashMap<String, List<Patient>> newMap = new HashMap<>();

        String diag = diagnosis.getValue().toString().substring(0, diagnosis.getValue().toString().indexOf(" "));
        Set<DiagnosisModel> subDiagnosis = (HashSet) filterStageCodes.getBrokenSet().get(diag);

        for(Patient p : workingSet){
            if(subDiagnosis.contains(p.getDiagnosis())){
                if(newMap.containsKey(p.getDiagnosis().getCategory())){
                    newMap.get(p.getDiagnosis().getCategory()).add(p);
                }else{
                    ArrayList<Patient> list = new ArrayList<>();
                    list.add(p);
                    newMap.put(p.getDiagnosis().getCategory(), list);
                }
            }
        }

        return newMap;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init(((MainUI) getUI()).getPatientData());
    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        if(chart == null) removeComponent(CHOOSE_SOMETHING);
        if(chart != null) removeComponent(chart);

        if(selector.getValue().toString().equals(CHOICES[0])){
            setCharts(0, filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[1])){
            setCharts(1,  filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[2])){
            setCharts(2,  filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[3])){
            setCharts(3,  filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[4])){
            setCharts(4,  filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[5])){
            setCharts(5,  filterByDiagnosis());
        }else if(selector.getValue().toString().equals(CHOICES[6])){
            setCharts(6,  filterByDiagnosis());
        }
    }

    public Patient isInPatientData(int setNum){
        for(Patient p : workingSet){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
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

    private void addDescriptionsToChoices(){
        List<String> newDiagnosisChoices = new ArrayList<>();

        for(String s : diagnosisChoices){
            HashSet<DiagnosisModel> t = filterStageCodes.getBrokenSet().get(s);
            Iterator<DiagnosisModel> it = t.iterator();

            s += " (";
            while(it.hasNext()){
                DiagnosisModel d = it.next();
                if(!s.contains(d.getDescription())) s = s+" "+d.getDescription();
            }

            s += ")";

            newDiagnosisChoices.add(s);
        }

        diagnosisChoices.clear();
        diagnosisChoices.addAll(newDiagnosisChoices);
    }
}

