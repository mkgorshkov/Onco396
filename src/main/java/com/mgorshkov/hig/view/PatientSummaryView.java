package com.mgorshkov.hig.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = PatientSummaryView.VIEW_NAME)
public class PatientSummaryView extends VerticalLayout implements View{
    public final static String VIEW_NAME = "PatientSummary";

    private void init() {
        setSizeFull();
        setMargin(true);
        addDoctorChart();
    }

    private void addDoctorChart() {
        Chart doctorChart = new Chart(ChartType.BAR);

        Configuration conf = doctorChart.getConfiguration();

        conf.setTitle("Treatment Planning Progress");

        XAxis x = new XAxis();
        x.setCategories("Patient 1", "Patient 2", "Patient 3", "Patient 4");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Total Waiting Time (Days)");

        conf.addyAxis(y);

        Legend legend = new Legend();
        legend.setBackgroundColor("#FFFFFF");
        legend.setReversed(true);

        PlotLine plotLine = new PlotLine();
        plotLine.setColor(new SolidColor("red"));
        plotLine.setValue(9);
        plotLine.setWidth(3);
        plotLine.setzIndex(0);
        plotLine.setDashStyle(DashStyle.SOLID);
        PlotBandLabel label = new PlotBandLabel("Today");
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

        conf.addSeries(new ListSeries("Ready For Treatment", 1, 2, 2, 1));
        conf.addSeries(new ListSeries("Physics QA", 3, 4, 2, 2));
        conf.addSeries(new ListSeries("MD Approve", 1, 2, 3, 1));
        conf.addSeries(new ListSeries("Dose Calculation", 1, 2, 1, 0.5));
        conf.addSeries(new ListSeries("CT Planning Sheet", 1, 3, 2, 1.5));
        conf.addSeries(new ListSeries("MD Contour", 1, 3, 4, 2));
        conf.addSeries(new ListSeries("Initial Contour", 1, 2, 1, 3));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.y +' days estimated remaining for '+ this.series.name +' 6 days estimated total.'");

        conf.setTooltip(tooltip);

//        PlotOptionsColumn plot2 = new PlotOptionsColumn();
//        plot2.setStacking(Stacking.NORMAL);
//        plot2.set

        doctorChart.drawChart(conf);
        doctorChart.setSizeFull();

        addComponent(doctorChart);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
