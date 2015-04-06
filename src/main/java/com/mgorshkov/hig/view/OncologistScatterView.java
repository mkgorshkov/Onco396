package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.business.utils.ChronologicalOrderSorter;
import com.mgorshkov.hig.business.utils.WaitingTimeSorter;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = OncologistScatterView.VIEW_NAME)
public class OncologistScatterView extends VerticalLayout implements View, ComboBox.ValueChangeListener {

    public final static String VIEW_NAME = "OncologistScatterView";
    Set<Patient> workingSet = new HashSet<>();
    List<Integer> oncologistSet = new ArrayList<>();

    final static Label CHOOSE_SOMETHING = new Label("Make selection above to see graphs.");
    final static String[] LABELS = {"Stage 1: CT Scan - Initial Contour", "Stage 2: Initial Contour - MD Contour", "Stage 3: MD Contour - CT Planning Sheet", "Stage 4: CT Planning Sheet - Dose Calculation", "Stage 5: Dose Calculation - MD Approve", "Stage 6: MD Approve - Physics QA", "Stage 7: Physics QA - Ready for Treatement"};

    ComboBox selector = new ComboBox("Oncologist Select: ");
    Chart chart;

    HorizontalLayout topBar = new HorizontalLayout();

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;

        sortOncologistSet(((MainUI) getUI().getCurrent()).getOncologists());
        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setCombo();
        setTopBar();

        addComponent(CHOOSE_SOMETHING);
        CHOOSE_SOMETHING.setWidthUndefined();
        setExpandRatio(CHOOSE_SOMETHING, 0.9f);
        setComponentAlignment(CHOOSE_SOMETHING, Alignment.MIDDLE_CENTER);
    }


    private void setTopBar() {
        topBar.addComponent(selector);
        topBar.setSizeFull();
        topBar.setSpacing(true);
        addComponent(topBar);
        setExpandRatio(topBar, 0.1f);
    }

    private void setCombo(){
        selector.addItems(oncologistSet);
        selector.setNullSelectionAllowed(false);
        selector.setValue(null);
        selector.setWidth("100%");
        selector.addValueChangeListener(this);
    }

    private void sortOncologistSet(Set<Integer> input){
        oncologistSet.addAll(input);
        Collections.sort(oncologistSet);
    }

    private void setCharts(int oncologistSer){
        chart = new Chart(ChartType.LINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Patients by Oncologist: " + oncologistSer);

        List<Patient> toDisplay = new ArrayList<>();

        for(Patient p : workingSet){
            if(p.getOncologist() == oncologistSer){
                toDisplay.add(p);
            }
        }


        YAxis y = new YAxis();
        y.setTitle("Waiting Time " + (((MainUI) getUI()).getTimeUnit()));
        conf.addyAxis(y);

        PlotOptionsSeries plot = new PlotOptionsSeries();
        plot.setStacking(Stacking.NORMAL);
        conf.setPlotOptions(plot);

        Legend legend = new Legend();
        legend.setBackgroundColor("#FFFFFF");
        legend.setReversed(true);
        conf.setLegend(legend);

        ArrayList<Patient> sortedpatients = putInChronologicalOrder(toDisplay);

        String[] patientSerNums = new String[toDisplay.size()];
        for(int i = 0; i<sortedpatients.size(); i++){
            patientSerNums[i] = ""+sortedpatients.get(i).getPatientSerNum();
        }

        XAxis x = new XAxis();
        x.setCategories(patientSerNums);
        Labels yLabel = new Labels();
        yLabel.setEnabled(false);
        x.setLabels(yLabel);
        conf.addxAxis(x);

        Double[] allSeven = new Double[sortedpatients.size()];
        Double[] allSix = new Double[sortedpatients.size()];
        Double[] allFive = new Double[sortedpatients.size()];
        Double[] allFour = new Double[sortedpatients.size()];
        Double[] allThree = new Double[sortedpatients.size()];
        Double[] allTwo = new Double[sortedpatients.size()];
        Double[] allOne = new Double[sortedpatients.size()];

        for(int i = 0; i<sortedpatients.size(); i++) {
            allSeven[i] = sortedpatients.get(i).calculateSeventhWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allSix[i] = sortedpatients.get(i).calculateSixthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allFive[i] = sortedpatients.get(i).calculateFifthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allFour[i] = sortedpatients.get(i).calculateFourthWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allThree[i] = sortedpatients.get(i).calculateThirdWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allTwo[i] = sortedpatients.get(i).calculateSecondWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());
            allOne[i] = sortedpatients.get(i).calculateFirstWait(((MainUI) getUI()).getTimeUnit(), ((MainUI) getUI()).isRemoveWeekendHolidays());

        }
        conf.addSeries(new ListSeries(LABELS[6], allSeven));
        conf.addSeries(new ListSeries(LABELS[5], allSix));
        conf.addSeries(new ListSeries(LABELS[4], allFive));
        conf.addSeries(new ListSeries(LABELS[3], allFour));
        conf.addSeries(new ListSeries(LABELS[2], allThree));
        conf.addSeries(new ListSeries(LABELS[1], allTwo));
        conf.addSeries(new ListSeries(LABELS[0], allOne));

        chart.addPointClickListener(new PointClickListener() {
            @Override
            public void onClick(PointClickEvent pointClickEvent) {
                ((MainUI) getUI()).setCrtUser(pointClickEvent.getCategory());
                getUI().getNavigator().navigateTo(PatientView.VIEW_NAME);
            }
        });

        chart.drawChart(conf);

        chart.setSizeFull();
        addComponent(chart);

        setExpandRatio(chart, 0.9f);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        if(workingSet == null){
            getUI().getNavigator().navigateTo(MainView.VIEW_NAME);
        }
        init(((MainUI) getUI()).getPatientData());
    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        if(chart == null) removeComponent(CHOOSE_SOMETHING);
        if(chart != null) removeComponent(chart);
        setCharts((Integer) selector.getValue());
    }

    private ArrayList<Patient> putInChronologicalOrder(List<Patient> input){
        ArrayList<Patient> sorted = new ArrayList<>();
        sorted.addAll(input);
        Collections.sort(sorted, new ChronologicalOrderSorter());

        return sorted;
    }
}

