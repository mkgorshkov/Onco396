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
        @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
        @NamedQuery(name = "Task.findByAliasAndStatus", query = "SELECT t FROM Task t where t.AliasSerNum=:aSerNum and t.Status not like :aStatus and t.Status not like :aStatus2")
})
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer TaskSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    private Integer TaskId;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DiagnosisSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PrioritySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 100)
    private BigInteger AliasSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AliasExpressionSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 50)
    private String Status;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date DueDateTime;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date CreationDate;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date CompletionDate;
    @Basic(optional = false)
    @Column(nullable = false)
    private Date LastUpdated;

    public Task() {}

    public Integer getTaskSerNum() {
        return TaskSerNum;
    }

    public void setTaskSerNum(Integer taskSerNum) {
        TaskSerNum = taskSerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer taskId) {
        TaskId = taskId;
    }

    public Integer getDiagnosisSerNum() {
        return DiagnosisSerNum;
    }

    public void setDiagnosisSerNum(Integer diagnosisSerNum) {
        DiagnosisSerNum = diagnosisSerNum;
    }

    public Integer getPrioritySerNum() {
        return PrioritySerNum;
    }

    public void setPrioritySerNum(Integer prioritySerNum) {
        PrioritySerNum = prioritySerNum;
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

    public Date getDueDateTime() {
        return DueDateTime;
    }

    public void setDueDateTime(Date dueDateTime) {
        DueDateTime = dueDateTime;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public Date getCompletionDate() {
        return CompletionDate;
    }

    public void setCompletionDate(Date completionDate) {
        CompletionDate = completionDate;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
