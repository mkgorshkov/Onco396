package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.mgorshkov.hig.model.enums.Stage;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = PatientSummaryView.VIEW_NAME)
public class PatientSummaryView extends VerticalLayout implements View{
    public final static String VIEW_NAME = "PatientSummary";
    Set<Patient> workingSet = new HashSet<>();
    HashSet<Patient> inScope = new HashSet<>();

    Calendar c = Calendar.getInstance();

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;
        c.set(2014, Calendar.AUGUST, 13, 10, 10, 0);

        setSizeFull();
        setMargin(true);

        findAllUndergoingTreatment();
        addDoctorChart();
    }

    private void findAllUndergoingTreatment(){
        for(Patient p : workingSet){
            if(p.isUndergoingTreatment(c.getTime())){
                inScope.add(p);
            }
        }

        System.out.println("Scope: "+inScope.size());
    }

    private void addDoctorChart() {
        Patient p = inScope.iterator().next();
        Chart doctorChart = new Chart(ChartType.BAR);

        Configuration conf = doctorChart.getConfiguration();
        conf.setTitle("Treatment Planning Progress");

        XAxis x = new XAxis();
        x.setCategories(addPatientSers(inScope));
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Total Waiting Time ("+((MainUI) getUI()).getTimeUnit().toString()+")");

        conf.addyAxis(y);

        Legend legend = new Legend();
        legend.setBackgroundColor("#FFFFFF");
        legend.setReversed(false);

        PlotLine plotLine = new PlotLine();
        plotLine.setColor(new SolidColor("black"));
        plotLine.setValue(0);
        plotLine.setWidth(3);
        plotLine.setzIndex(0);
        plotLine.setDashStyle(DashStyle.SOLID);
        PlotBandLabel label = new PlotBandLabel("Today: "+c.getTime());
        label.setAlign(HorizontalAlign.RIGHT);
        label.setVerticalAlign(VerticalAlign.TOP);
        label.setRotation(360);
        Style style = new Style();
        style.setColor(new SolidColor("gray"));
        label.setStyle(style);
        plotLine.setLabel(label);
        y.setPlotLines(plotLine);

        PlotOptionsSeries plot = new PlotOptionsSeries();
        plot.setStacking(Stacking.NORMAL);

        conf.setLegend(legend);

        conf.setPlotOptions(plot);
        conf.addSeries(new ListSeries("Initial Contour", addValueByStage(Stage.CT_SCAN)));
        conf.addSeries(new ListSeries("MD Contour", addValueByStage(Stage.INITIAL_CONTOUR)));
        conf.addSeries(new ListSeries("CT Planning Sheet", addValueByStage(Stage.MD_CONTOUR)));
        conf.addSeries(new ListSeries("Dose Calculation", addValueByStage(Stage.CT_PLANNING_SHEET)));
        conf.addSeries(new ListSeries("MD Approve", addValueByStage(Stage.DOSE_CALCULATION)));
        conf.addSeries(new ListSeries("Physics QA", addValueByStage(Stage.MD_APPROVE)));
        conf.addSeries(new ListSeries("Ready For Treatment", addValueByStage(Stage.PHYSICS_QA)));
//
//        Tooltip tooltip = new Tooltip();
//        tooltip.setFormatter("this.y +' days estimated remaining for '+ this.series.name +' 6 days estimated total.'");
//
//        conf.setTooltip(tooltip);


        doctorChart.drawChart(conf);
        doctorChart.setSizeFull();

        addComponent(doctorChart);
    }

    private Double[] addValueByStage(Stage s){
        Double[] third = new Double[inScope.size()];
        Iterator<Patient> it = inScope.iterator();

        int i = 0;
        while(it.hasNext()){
            Patient p = it.next();
            third[i] = -valueOrElapsedSoFar(p, s);
            i++;
        }

        return third;
    }

    public String[] addPatientSers(HashSet<Patient> input){
        String[] patientSers = new String[input.size()];
        Iterator<Patient> it = input.iterator();

        int i = 0;
        while(it.hasNext()){
            patientSers[i] = ""+it.next().getPatientSerNum();
            i++;
        }

        return patientSers;
    }

    public Double valueOrElapsedSoFar(Patient p, Stage s){

        if(p.getCurrentStage(c.getTime()).compareTo(s) > 1){
            if(s.equals(Stage.CT_SCAN)){
                return p.calculateFirstWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.INITIAL_CONTOUR)){
                return p.calculateSecondWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.MD_CONTOUR)){
                return p.calculateThirdWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.CT_PLANNING_SHEET)){
                return p.calculateFourthWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.DOSE_CALCULATION)){
                return p.calculateFifthWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.MD_APPROVE)){
                return p.calculateSixthWait(((MainUI) getUI()).getTimeUnit());
            }if(s.equals(Stage.PHYSICS_QA)){
                return p.calculateSeventhWait(((MainUI) getUI()).getTimeUnit());
            }
        }else if(p.getCurrentStage(c.getTime()).compareTo(s) == 0){
            Iterator<DataPoint> it = p.getDataPoints().iterator();
            DataPoint crt = null;

            while(it.hasNext()){
                DataPoint next = it.next();
                if(next.getStage().equals(s)){
                    crt = next;
                }
            }
            long duration = c.getTime().getTime() - crt.getTimePoint().getTime();

            double minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

            if(((MainUI) getUI()).getTimeUnit() == OncoTimeUnit.HOURS){
                return minutes / 60;
            }else if(((MainUI) getUI()).getTimeUnit() == OncoTimeUnit.DAYS){
                return minutes / 1440;
            }

            return minutes;
        }
            return 0.0;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init(((MainUI) getUI()).getPatientData());
    }
}
