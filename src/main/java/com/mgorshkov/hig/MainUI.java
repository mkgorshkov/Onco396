package com.mgorshkov.hig;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mgorshkov.hig.model.Patient;
import com.mgorshkov.hig.business.services.SessionBean;
import com.mgorshkov.hig.model.enums.OncoTimeUnit;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Theme("mytheme")
@CDIUI(value = "")
@Widgetset("com.mgorshkov.hig.MyAppWidgetset")
@PreserveOnRefresh
public class MainUI extends UI {

    @Inject
    CDIViewProvider viewProvider;
    @PersistenceContext(unitName = "hig20150218")
    EntityManager entityManager;
    @EJB
    SessionBean bean;
    Navigator navigator;
    final VerticalLayout layout = new VerticalLayout();
    private Set<Patient> patientData;
    private OncoTimeUnit globalTimeUnit;
    private Set<String> diagnosis;
    private String crtUser;
    private Set<Integer> oncologists;
    private Set<Set<Patient>> extremeUsers;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        patientData = new HashSet<>();
        globalTimeUnit = OncoTimeUnit.MINUTES;
        diagnosis = new HashSet<>();

        layout.setMargin(true);
        setContent(layout);

        setNavigation();
    }

    private void setNavigation() {
        navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public Set<Patient> getPatientData() {
        return patientData;
    }

    public Patient isInPatientData(int setNum){
        for(Patient p : patientData){
            if(p.getPatientSerNum() == setNum){
                return p;
            }
        }
        return null;
    }

    public OncoTimeUnit getTimeUnit(){
        return globalTimeUnit;
    }

    public void setTimeUnit(OncoTimeUnit newUnit){
        globalTimeUnit = newUnit;
    }

    public void setPatientData(Set<Patient> patientData) {
        this.patientData = patientData;
    }

    public Set<String> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(HashSet<String> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public Set<Integer> getOncologists() {
        return oncologists;
    }

    public void setOncologists(Set<Integer> oncologists) {
        this.oncologists = oncologists;
    }

    public Set<Set<Patient>> getExtremeUsers() {
        return extremeUsers;
    }

    public void setExtremeUsers(Set<Set<Patient>> extremeUsers) {
        this.extremeUsers = extremeUsers;
    }
}
