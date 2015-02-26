package com.mgorshkov.hig.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by mkgo on 22/02/15.
 */
@Entity
@Table(catalog = "hig20150218", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
})
public class Appointment implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AppointmentSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    private Integer AppointmentId;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DiagnosisSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 100)
    private BigInteger AliasSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AliasExpressionSerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    @Size(max = 50)
    private String Status;
    @Column(nullable = true)
    @Basic(optional = true)
    private Date ScheduledStartTime;
    @Column(nullable = true)
    @Basic(optional = true)
    private Date ScheduledEndTime;
    @Column(nullable = false)
    private Date LastUpdated;

    public Appointment(){}

    public Integer getAppointmentSerNum() {
        return AppointmentSerNum;
    }

    public void setAppointmentSerNum(Integer appointmentSerNum) {
        AppointmentSerNum = appointmentSerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public Integer getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        AppointmentId = appointmentId;
    }

    public Integer getDiagnosisSerNum() {
        return DiagnosisSerNum;
    }

    public void setDiagnosisSerNum(Integer diagnosisSerNum) {
        DiagnosisSerNum = diagnosisSerNum;
    }

    public BigInteger getAliasSerNum() {
        return AliasSerNum;
    }

    public void setAliasSerNum(BigInteger aliasSerNum) {
        AliasSerNum = aliasSerNum;
    }

    public Integer getAliasExpressionSerNum() {
        return AliasExpressionSerNum;
    }

    public void setAliasExpressionSerNum(Integer aliasExpressionSerNum) {
        AliasExpressionSerNum = aliasExpressionSerNum;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getScheduledStartTime() {
        return ScheduledStartTime;
    }

    public void setScheduledStartTime(Date scheduledStartTime) {
        ScheduledStartTime = scheduledStartTime;
    }

    public Date getScheduledEndTime() {
        return ScheduledEndTime;
    }

    public void setScheduledEndTime(Date scheduledEndTime) {
        ScheduledEndTime = scheduledEndTime;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
