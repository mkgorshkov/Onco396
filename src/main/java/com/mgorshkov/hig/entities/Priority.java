package com.mgorshkov.hig.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
        @NamedQuery(name = "Priority.findAll", query = "SELECT p FROM Priority p"),
        @NamedQuery(name = "Priority.findByPatient", query = "SELECT p FROM Priority p where p.PatientSerNum = :patientSerNum")
})
public class Priority implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PrioritySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Size(max = 25)
    @Column(length = 25, nullable = false)
    private String PriorityId;
    @Column(nullable = true)
    private Date PriorityDateTime;
    @Size(max = 25)
    @Column(length = 25, nullable = true)
    private String PriorityCode;
    @Column(nullable = false)
    private Date LastUpdated;

    public Priority(){

    }

    public Integer getPrioritySerNum() {
        return PrioritySerNum;
    }

    public void setPrioritySerNum(Integer prioritySerNum) {
        PrioritySerNum = prioritySerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public String getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(String priorityId) {
        PriorityId = priorityId;
    }

    public Date getPriorityDateTime() {
        return PriorityDateTime;
    }

    public void setPriorityDateTime(Date priorityDateTime) {
        PriorityDateTime = priorityDateTime;
    }

    public String getPriorityCode() {
        return PriorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        PriorityCode = priorityCode;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
