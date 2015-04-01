package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.model.DataPoint;
import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.model.enums.Stage;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import java.util.*;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@CDIView(value = CalendarView.VIEW_ID)
public class CalendarView extends VerticalLayout implements View, ComboBox.ValueChangeListener{
    public final static String VIEW_ID = "CalendarView";

    Set<Patient> workingSet = new HashSet<>();
    Calendar cal = new Calendar();

    int[] choices;
    ComboBox selector = new ComboBox("Patient Select: ");

    CalendarEvent event;
    CalendarEvent event2;
    CalendarEvent event3;
    CalendarEvent event4;
    CalendarEvent event5;
    CalendarEvent event6;
    CalendarEvent event7;

    public void init(Set<Patient> workingSet){
        this.workingSet = workingSet;

        setSizeFull();
        setMargin(true);

        setCombo();
        setCalendar(1);
    }

    private void setCalendar(int patientID){

        Patient crt = isInPatientData(patientID);
        Iterator<DataPoint> it = crt.getDataPoints().iterator();

        DataPoint[] sorted = new DataPoint[Stage.values().length];

        while(it.hasNext()){
            DataPoint d = it.next();
            if(d.getStage() == Stage.CT_SCAN){
                sorted[0] = d;
            }else if(d.getStage() == Stage.INITIAL_CONTOUR){
                sorted[1] = d;

            }else if(d.getStage() == Stage.MD_CONTOUR){
                sorted[2] = d;

            }else if(d.getStage() == Stage.CT_PLANNING_SHEET){
                sorted[3] = d;

            }else if(d.getStage() == Stage.DOSE_CALCULATION){
                sorted[4] = d;

            }else if(d.getStage() == Stage.MD_APPROVE){
                sorted[5] = d;

            }else if(d.getStage() == Stage.PHYSICS_QA){
                sorted[6] = d;

            }else if(d.getStage() == Stage.READY_FOR_TREATMENT){
                sorted[7] = d;

            }
        }

        event = new BasicEvent(Stage.CT_SCAN.toString(), "Wait from CT Scan to Initial Contour", sorted[0].getTimePoint(), sorted[1].getTimePoint());
        event2 = new BasicEvent(Stage.INITIAL_CONTOUR.toString(), "Wait Initial Contour to MD Contour", sorted[1].getTimePoint(), sorted[2].getTimePoint());
        event3 = new BasicEvent(Stage.MD_CONTOUR.toString(), "Wait from MD Contour to CT Planning Sheet", sorted[2].getTimePoint(), sorted[3].getTimePoint());
        event4 = new BasicEvent(Stage.CT_PLANNING_SHEET.toString(), "Wait from CT Planning Sheet to Dose Calculation", sorted[3].getTimePoint(), sorted[4].getTimePoint());
        event5 = new BasicEvent(Stage.DOSE_CALCULATION.toString(), "Wait from Dose Calculation to MD Approval", sorted[4].getTimePoint(), sorted[5].getTimePoint());
        event6 = new BasicEvent(Stage.MD_APPROVE.toString(), "Wait from MD Approval to Physics QA", sorted[5].getTimePoint(), sorted[6].getTimePoint());
        event7 = new BasicEvent(Stage.PHYSICS_QA.toString(), "Wait from Physics QA to Ready for Treatment", sorted[6].getTimePoint(), sorted[7].getTimePoint());

        cal.addEvent(event);
        cal.addEvent(event2);
        cal.addEvent(event3);
        cal.addEvent(event4);
        cal.addEvent(event5);
        cal.addEvent(event6);
        cal.addEvent(event7);

        cal.setStartDate(sorted[0].getTimePoint());
        cal.setEndDate(sorted[7].getTimePoint());

        cal.setSizeFull();

        addComponent(cal);
        setExpandRatio(cal, 0.9f);
    }

    private void setCombo(){
        selector = patientSerNums(selector);

        selector.setWidth("100%");
        selector.addValueChangeListener(this);

        addComponent(selector);
        setExpandRatio(selector, 0.1f);
    }

    private ComboBox patientSerNums(ComboBox selector){

        int[] serialToReturn = new int[workingSet.size()];

        ArrayList<Integer> names = new ArrayList<>();
        for(Patient p : workingSet){
            names.add(p.getPatientSerNum());
        }

        Collections.sort(names);

        int i = 0;
        for(int serNum : names){
            serialToReturn[i] = serNum;
            selector.addItem(serNum);
        }

        selector.setNullSelectionAllowed(false);
        selector.setValue(names.get(0));

        choices = serialToReturn;

        return selector;
    }

    public Patient isInPatientData(int setNum){
        for(Patient p : workingSet){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init(((MainUI) getUI()).getPatientData());
    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        cal.removeEvent(event);
        cal.removeEvent(event2);
        cal.removeEvent(event3);
        cal.removeEvent(event4);
        cal.removeEvent(event5);
        cal.removeEvent(event6);
        cal.removeEvent(event7);

        removeComponent(cal);
        setCalendar((Integer) selector.getValue());
    }
}
