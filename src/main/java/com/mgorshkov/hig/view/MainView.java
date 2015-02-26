package com.mgorshkov.hig.view;

import com.mgorshkov.hig.MainUI;
import com.mgorshkov.hig.Model.DataPoint;
import com.mgorshkov.hig.Model.DataPointType;
import com.mgorshkov.hig.Model.Patient;
import com.mgorshkov.hig.business.SessionBean;
import com.mgorshkov.hig.entities.Alias;
import com.mgorshkov.hig.entities.Appointment;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Created by mkgo on 25/02/15.
 */
@CDIView(value = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {

    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    public final static String VIEW_NAME = "";
    private Label statusLabel = new Label("Start.");

    public void init(){
        setSizeFull();
        statusLabel.setSizeFull();

        addComponent(statusLabel);
        setEntityManager();
        addAppointmentsToUsers();
    }

    private void setEntityManager(){
        entityManager = ((MainUI) UI.getCurrent()).getEntityManager();
    }

    private void addAppointmentsToUsers(){
        long start = System.currentTimeMillis();
        getAppointment();
        statusLabel.setValue(statusLabel.getValue() + "\nAppointments Added to Users. "+(System.currentTimeMillis() - start) + " ms.");
        System.out.println(((MainUI) UI.getCurrent()).getPatientData().size());
    }

    private void getAppointment(){
        TypedQuery<Appointment> query = entityManager.createNamedQuery("Appointment.findAll", Appointment.class);
        List<Appointment> appointmentList = query.getResultList();

        for(Appointment app : appointmentList){
            if(app.getAliasSerNum().equals(new BigInteger("3")) && !app.getStatus().equals("Cancelled") && !app.getStatus().equals("Open")){

                Patient crtPatient = ((MainUI) UI.getCurrent()).isInPatientData(app.getPatientSerNum());

                if(crtPatient == null){
                    Set<Patient> workingSet = ((MainUI) UI.getCurrent()).getPatientData();

                    Patient patient = new Patient(app.getPatientSerNum());
                    System.out.println(app.getScheduledStartTime());
                    patient.addDataPoint(new DataPoint(app.getScheduledStartTime(), DataPointType.APPOINTMENT));

                    workingSet.add(patient);

                    ((MainUI) UI.getCurrent()).setPatientData(workingSet);

                }else{

                    Set<Patient> workingSet = ((MainUI) UI.getCurrent()).getPatientData();

                    workingSet.remove(crtPatient);

                    crtPatient.addDataPoint(new DataPoint(app.getScheduledStartTime(), DataPointType.APPOINTMENT));

                    ((MainUI) UI.getCurrent()).setPatientData(workingSet);
                }
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();
        init();
    }
}
