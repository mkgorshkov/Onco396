package com.mgorshkov.hig.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by mkgo on 23/02/15.
 */
@Entity
@Table(catalog = "hig20150218", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Diagnosis.findAll", query = "SELECT d FROM Diagnosis d"),
        @NamedQuery(name = "Diagnosis.findBySer", query = "SELECT d FROM Diagnosis d where d.PatientSerNum = :patientSerNum")
})
public class Diagnosis implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DiagnosisSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    @Size(max = 25)
    private BigInteger DiagnosisId;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date DiagnosisCreationDate;
    @Basic(optional = true)
    @Column(nullable = true)
    @Size(max = 25)
    private String DiagnosisCode;
    @Basic(optional = false)
    @Column(nullable = false)
    private String Description;
    @Basic(optional = false)
    @Column(nullable = false)
    private Date LastUpdated;

    public Diagnosis(){}

    public Integer getDiagnosisSerNum() {
        return DiagnosisSerNum;
    }

    public void setDiagnosisSerNum(Integer diagnosisSerNum) {
        DiagnosisSerNum = diagnosisSerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public BigInteger getDiagnosisId() {
        return DiagnosisId;
    }

    public void setDiagnosisId(BigInteger diagnosisId) {
        DiagnosisId = diagnosisId;
    }

    public Date getDiagnosisCreationDate() {
        return DiagnosisCreationDate;
    }

    public void setDiagnosisCreationDate(Date diagnosisCreationDate) {
        DiagnosisCreationDate = diagnosisCreationDate;
    }

    public String getDiagnosisCode() {
        return DiagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        DiagnosisCode = diagnosisCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
