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
        @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DocumentSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PatientSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 30)
    private String DocumentId;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer DiagnosisSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer PrioritySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 11)
    private String ApprovedStatus;
    @Basic(optional = true)
    @Column(nullable = true)
    private Integer ApprovedBySerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date ApprovedTimeStamp;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AuthoredBySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Date DateOfService;
    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 30)
    private BigInteger AliasSerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AliasExpressionSerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    @Size(max = 5)
    private String Printed;
    @Basic(optional = true)
    @Column(nullable = true)
    private Integer SignedBySerNum;
    @Basic(optional = true)
    @Column(nullable = true)
    private Date SignedTimeStamp;
    @Basic(optional = true)
    @Column(nullable = true)
    private Integer SupervisedBySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer CreatedBySerNum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Date CreatedTimeStamp;
    @Basic(optional = false)
    @Column(nullable = false)
    private Date LastUpdated;

    public Document(){}

    public Integer getDocumentSerNum() {
        return DocumentSerNum;
    }

    public void setDocumentSerNum(Integer documentSerNum) {
        DocumentSerNum = documentSerNum;
    }

    public Integer getPatientSerNum() {
        return PatientSerNum;
    }

    public void setPatientSerNum(Integer patientSerNum) {
        PatientSerNum = patientSerNum;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
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

    public String getApprovedStatus() {
        return ApprovedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        ApprovedStatus = approvedStatus;
    }

    public Integer getApprovedBySerNum() {
        return ApprovedBySerNum;
    }

    public void setApprovedBySerNum(Integer approvedBySerNum) {
        ApprovedBySerNum = approvedBySerNum;
    }

    public Date getApprovedTimeStamp() {
        return ApprovedTimeStamp;
    }

    public void setApprovedTimeStamp(Date approvedTimeStamp) {
        ApprovedTimeStamp = approvedTimeStamp;
    }

    public Integer getAuthoredBySerNum() {
        return AuthoredBySerNum;
    }

    public void setAuthoredBySerNum(Integer authoredBySerNum) {
        AuthoredBySerNum = authoredBySerNum;
    }

    public Date getDateOfService() {
        return DateOfService;
    }

    public void setDateOfService(Date dateOfService) {
        DateOfService = dateOfService;
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

    public String getPrinted() {
        return Printed;
    }

    public void setPrinted(String printed) {
        Printed = printed;
    }

    public Integer getSignedBySerNum() {
        return SignedBySerNum;
    }

    public void setSignedBySerNum(Integer signedBySerNum) {
        SignedBySerNum = signedBySerNum;
    }

    public Date getSignedTimeStamp() {
        return SignedTimeStamp;
    }

    public void setSignedTimeStamp(Date signedTimeStamp) {
        SignedTimeStamp = signedTimeStamp;
    }

    public Integer getSupervisedBySerNum() {
        return SupervisedBySerNum;
    }

    public void setSupervisedBySerNum(Integer supervisedBySerNum) {
        SupervisedBySerNum = supervisedBySerNum;
    }

    public Integer getCreatedBySerNum() {
        return CreatedBySerNum;
    }

    public void setCreatedBySerNum(Integer createdBySerNum) {
        CreatedBySerNum = createdBySerNum;
    }

    public Date getCreatedTimeStamp() {
        return CreatedTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        CreatedTimeStamp = createdTimeStamp;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
