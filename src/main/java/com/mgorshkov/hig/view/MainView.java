package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.filters.AddAllData;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.DataPointType;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.entities.Appointment;
import com.mgorshkov.hig.entities.Task;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
