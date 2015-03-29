package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.filters.FilterByStageCodes;
import com.mgorshkov.hig.model.Patient;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = OutlierAnalysisView.VIEW_NAME)
public class OutlierAnalysisView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "OutlierAnalysisView";
    Set<Patient> moreOneStd = new HashSet<>();
    Set<Patient> moreTwoStd = new HashSet<>();
    Set<Patient> moreThreeStd = new HashSet<>();

    private Set<Set<Patient>> extremeUsers = new HashSet<>();

    ComboBox selector = new ComboBox("Select: ");

    Table t = new Table();

    public void init(Set<Set<Patient>> extremeUsers){
        this.extremeUsers = extremeUsers;

        Iterator<Set<Patient>> iterator = extremeUsers.iterator();
        moreOneStd = iterator.next();
        moreTwoStd = iterator.next();
        moreThreeStd = iterator.next();

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        setTable();
        addComponent(t);
    }

    private void setTable(){
        t.setSizeFull();

        t.addContainerProperty("Patient Ser", Integer.class, 0);
        t.addContainerProperty("Standard Deviatons", String.class, null);

        int index = 0;
        for(Patient p : moreOneStd){
            t.addItem(new Object[]{p.getPatientSerNum(), "Between 1 and 2 Std."}, index);
            index++;
        }
        for(Patient p : moreTwoStd){
            t.addItem(new Object[]{p.getPatientSerNum(), "Between 2 and 3 Std."}, index);
            index++;
        }
        for(Patient p : moreThreeStd){
            t.addItem(new Object[]{p.getPatientSerNum(), "> 3 Std."}, index);
            index++;
        }

        t.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                if (itemClickEvent.isDoubleClick()) {
                    ((MainUI) getUI()).setCrtUser(""+itemClickEvent.getItem().getItemProperty("Patient Ser"));
                    getUI().getNavigator().navigateTo(PatientView.VIEW_NAME);
                }
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init(((MainUI) getUI()).getExtremeUsers());
    }
}

