package com.mgorshkov.hig.view;

import com.mgorshkov.hig.filters.AddAllData;
import com.mgorshkov.hig.filters.FilterByStage;
import com.mgorshkov.hig.filters.FilterExtremes;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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
public class MainView extends VerticalLayout implements View {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "";
    Set<Patient> workingSet = new HashSet<>();
    AddAllData initialData;
    FilterByStage filterStage;
    FilterExtremes filterExtremes;

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        initialData = new AddAllData();
        workingSet = initialData.getWorkingSet();

        filterStage = new FilterByStage(workingSet);
        workingSet = filterStage.getWorkingSet();

        filterExtremes = new FilterExtremes(workingSet);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
