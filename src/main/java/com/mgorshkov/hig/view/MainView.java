package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.business.exporter.ExportToCSV;
import com.mgorshkov.hig.filters.AddAllData;
import com.mgorshkov.hig.filters.FilterByStage;
import com.mgorshkov.hig.filters.FilterExtremes;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mkgo on 25/02/15.
 */
@UIScoped
@CDIView(value = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View, Button.ClickListener {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "";
    Set<Patient> workingSet = new HashSet<>();
    AddAllData initialData;
    FilterByStage filterStage;
    FilterExtremes filterExtremes;

    Button charts = new Button("Stage", FontAwesome.BAR_CHART_O);
    Button chartsByDiagnosis = new Button("Stage and Diagnosis", FontAwesome.EDIT);
    Button patients = new Button("Patient", FontAwesome.USER);
    Button chartsTimeline = new Button("Oncologists", FontAwesome.FILE_TEXT);
    Button exportExcel = new Button("Export as CSV", FontAwesome.FILE_EXCEL_O);
    Button globalOptionsButton = new Button("Settings", FontAwesome.GEARS);
    Button outlierAnalysis = new Button("Outlier Analysis", FontAwesome.SEARCH);
    Button timelineView = new Button("Patient Timeline", FontAwesome.CALENDAR);
    Button calendarView = new Button("Calendar View", FontAwesome.CALENDAR_O);

    Button save = new Button(FontAwesome.SAVE);
    Button cancel = new Button(FontAwesome.TIMES);


    HorizontalLayout buttons = new HorizontalLayout();
    HorizontalLayout buttons2 = new HorizontalLayout();

    VerticalLayout globalLayout = new VerticalLayout();
    Window options = new Window("Global Options", globalLayout);

    ComboBox timeUnit = new ComboBox("Time Unit");

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        charts.addClickListener(this);
        patients.addClickListener(this);
        chartsTimeline.addClickListener(this);
        exportExcel.addClickListener(this);
        globalOptionsButton.addClickListener(this);
        chartsByDiagnosis.addClickListener(this);
        save.addClickListener(this);
        cancel.addClickListener(this);
        outlierAnalysis.addClickListener(this);
        timelineView.addClickListener(this);
        calendarView.addClickListener(this);

        buttons.addComponent(charts);
        buttons.addComponent(chartsByDiagnosis);
        buttons.addComponent(chartsTimeline);
        buttons.addComponent(patients);
        buttons.addComponent(timelineView);
        buttons2.addComponent(calendarView);
        buttons2.addComponent(exportExcel);
        buttons2.addComponent(outlierAnalysis);
        buttons2.addComponent(globalOptionsButton);

        buttons.setSpacing(true);
        buttons2.setSpacing(true);

        Resource res = new ThemeResource("img/logo1.png");
        Image image = new Image(null, res);
        addComponent(image);

        VerticalLayout v = new VerticalLayout(buttons, buttons2);
        v.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        v.setComponentAlignment(buttons2, Alignment.MIDDLE_CENTER);
        v.setSpacing(true);
        v.setWidth("100%");

        addComponent(v);

        setComponentAlignment(v, Alignment.TOP_CENTER);
        setComponentAlignment(image, Alignment.MIDDLE_CENTER);


        setWindow();

        if(((MainUI) getUI()).getPatientData() == null || ((MainUI) getUI()).getPatientData().size() == 0){
            initialData = new AddAllData();
            workingSet = initialData.getWorkingSet();

            filterStage = new FilterByStage(workingSet);
            workingSet = filterStage.getWorkingSet();

            filterExtremes = new FilterExtremes(workingSet);
            workingSet = filterExtremes.getWorkingSet();

            ((MainUI) getUI()).setPatientData(workingSet);
            ((MainUI) getUI()).setExtremeUsers(filterExtremes.getExtremes());
        }
    }

    private void setWindow(){
        options.center();

        timeUnit.addItems(OncoTimeUnit.values());
        timeUnit.setNullSelectionAllowed(false);
        timeUnit.setValue(((MainUI) getUI()).getTimeUnit());



        globalLayout.setMargin(true);
        globalLayout.setSpacing(true);
        globalLayout.addComponent(timeUnit);
        globalLayout.addComponent(new HorizontalLayout(save, cancel));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        if(workingSet == null){
            getUI().getNavigator().navigateTo(MainView.VIEW_NAME);
        }
        init();
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if(clickEvent.getSource().equals(charts)){
            getUI().getNavigator().navigateTo(ChartsView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(chartsByDiagnosis)){
            getUI().getNavigator().navigateTo(ChartsDiagnosisView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(chartsTimeline)){
            getUI().getNavigator().navigateTo(OncologistView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(patients)){
            getUI().getNavigator().navigateTo(PatientView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(exportExcel)){
            ExportToCSV e = new ExportToCSV(((MainUI) getUI()).getPatientData(), (((MainUI) getUI()).getTimeUnit()));
        }else if(clickEvent.getSource().equals(globalOptionsButton)){
            UI.getCurrent().addWindow(options);
        }else if(clickEvent.getSource().equals(save)){
            ((MainUI) getUI()).setTimeUnit((OncoTimeUnit) timeUnit.getValue());
            options.close();
            Notification.show("Time Unit Updated", Notification.Type.TRAY_NOTIFICATION);
        }else if(clickEvent.getSource().equals(cancel)){
            options.close();
        }else if(clickEvent.getSource().equals(outlierAnalysis)){
            getUI().getNavigator().navigateTo(OutlierAnalysisView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(timelineView)){
            getUI().getNavigator().navigateTo(PatientSummaryView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(calendarView)){
            getUI().getNavigator().navigateTo(CalendarView.VIEW_ID);
        }
    }
}
