package com.mgorshkov.hig.business.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mkgo on 22/02/15.
 */
@Entity
@Table(catalog = "hig20150218", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Alias.findAll", query = "SELECT a FROM Alias a"),
})
public class Alias implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AliasSerNum;
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String AliasName;
    @Size(max = 25)
    @Column(length = 25, nullable = false)
    private String AliasType;
    @Size(max = 10)
    @Column(length = 10, nullable = false)
    private Integer AliasUpdate;
    @Column(nullable = false)
    private Date LastUpdated;

    public Alias(){}

    public Integer getAliasSerNum() {
        return AliasSerNum;
    }

    public void setAliasSerNum(Integer aliasSerNum) {
        AliasSerNum = aliasSerNum;
    }

    public String getAliasType() {
        return AliasType;
    }

    public void setAliasType(String aliasType) {
        AliasType = aliasType;
    }

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String aliasName) {
        AliasName = aliasName;
    }

    public Integer getAliasUpdate() {
        return AliasUpdate;
    }

    public void setAliasUpdate(Integer aliasUpdate) {
        AliasUpdate = aliasUpdate;
    }

    public Date getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        LastUpdated = lastUpdated;
    }
}
