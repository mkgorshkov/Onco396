package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.filters.AddAllData;
import com.mgorshkov.hig.filters.FilterByStage;
import com.mgorshkov.hig.filters.FilterExtremes;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
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

    Button charts = new Button("Charts by Stage", FontAwesome.BAR_CHART_O);
    Button patients = new Button("Charts by Patient", FontAwesome.USER);

    HorizontalLayout buttons = new HorizontalLayout();

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        charts.addClickListener(this);
        patients.addClickListener(this);

        buttons.addComponent(charts);
        buttons.addComponent(patients);
        buttons.setSpacing(true);

        addComponent(buttons);

        setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);

        initialData = new AddAllData();
        workingSet = initialData.getWorkingSet();

        filterStage = new FilterByStage(workingSet);
        workingSet = filterStage.getWorkingSet();

        filterExtremes = new FilterExtremes(workingSet);
        workingSet = filterExtremes.getWorkingSet();
        ((MainUI) getUI()).setPatientData(workingSet);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if(clickEvent.getSource().equals(charts)){
            getUI().getNavigator().navigateTo(ChartsView.VIEW_NAME);
        }else if(clickEvent.getSource().equals(patients)){
            getUI().getNavigator().navigateTo(PatientView.VIEW_NAME);
        }
    }
}
