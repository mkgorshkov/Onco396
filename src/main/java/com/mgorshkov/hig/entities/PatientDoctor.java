package com.mgorshkov.hig.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
@Entity
@Table(catalog = "hig20150218", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PatientDoctor.findAll", query = "SELECT p FROM PatientDoctor p"),
        @NamedQuery(name = "PatientDoctor.findBySerialAndOncologist", query = "SELECT p FROM PatientDoctor p where p.PatientSerNum = :patientSerNum and p.OncologistFlag = 1")
})
public class PatientDoctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientDoctorSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DoctorSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer OncologistFlag;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PrimaryFlag;
    @Column(nullable = false)
    private Date LastUpdated;

    public PatientDoctor(){

    }

    public Integer getPatientDoctorSerNum() {
        return PatientDoctorSerNum;
    }

    public void setPatientDoctorSerNum(Integer patientDoctorSerNum) {
        PatientDoctorSerNum = patientDoctorSerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public Integer getDoctorSerNum() {
        return DoctorSerNum;
    }

    public void setDoctorSerNum(Integer doctorSerNum) {
        DoctorSerNum = doctorSerNum;
    }

    public Integer getOncologistFlag() {
        return OncologistFlag;
    }

    public void setOncologistFlag(Integer oncologistFlag) {
        OncologistFlag = oncologistFlag;
    }

    public Integer getPrimaryFlag() {
        return PrimaryFlag;
    }

    public void setPrimaryFlag(Integer primaryFlag) {
        PrimaryFlag = primaryFlag;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
