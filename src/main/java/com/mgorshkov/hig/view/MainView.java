package com.mgorshkov.hig.view;

import com.mgorshkov.hig.filters.AddAllData;
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
    AddAllData addDataFilter;

    public void init(){
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        addDataFilter = new AddAllData();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
