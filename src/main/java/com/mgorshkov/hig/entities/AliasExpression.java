package com.mgorshkov.hig.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mkgo on 22/02/15.
 */

@Entity
@Table(catalog = "hig20150218", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AliasExpression.findAll", query = "SELECT a FROM AliasExpression a"),
})
public class AliasExpression implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer AliasExpressionSerNum;
    @Column(nullable = false)
    private Integer AliasSerNum;
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String ExpressionName;

    public AliasExpression() {}

    public Integer getAliasSerNum() {
        return AliasSerNum;
    }

    public void setAliasSerNum(Integer aliasSerNum) {
        AliasSerNum = aliasSerNum;
    }

    public String getExpressionName() {
        return ExpressionName;
    }

    public Integer getAliasExpressionSerNum() {
        return AliasExpressionSerNum;
    }

    public void setAliasExpressionSerNum(Integer aliasExpressionSerNum) {
        AliasExpressionSerNum = aliasExpressionSerNum;
    }

    public void setExpressionName(String expressionName) {
        ExpressionName = expressionName;
    }
}
