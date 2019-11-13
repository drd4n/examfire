/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exam.findAll", query = "SELECT e FROM Exam e")
    , @NamedQuery(name = "Exam.findByExamid", query = "SELECT e FROM Exam e WHERE e.examid = :examid")
    , @NamedQuery(name = "Exam.findByExamtitle", query = "SELECT e FROM Exam e WHERE e.examtitle = :examtitle")})
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer examid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String examtitle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examid")
    private List<Choiceset> choicesetList;
    @JoinColumn(name = "UESRID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users uesrid;

    public Exam() {
    }

    public Exam(Integer examid) {
        this.examid = examid;
    }

    public Exam(Integer examid, String examtitle) {
        this.examid = examid;
        this.examtitle = examtitle;
    }

    public Integer getExamid() {
        return examid;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public String getExamtitle() {
        return examtitle;
    }

    public void setExamtitle(String examtitle) {
        this.examtitle = examtitle;
    }

    @XmlTransient
    public List<Choiceset> getChoicesetList() {
        return choicesetList;
    }

    public void setChoicesetList(List<Choiceset> choicesetList) {
        this.choicesetList = choicesetList;
    }

    public Users getUesrid() {
        return uesrid;
    }

    public void setUesrid(Users uesrid) {
        this.uesrid = uesrid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examid != null ? examid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) object;
        if ((this.examid == null && other.examid != null) || (this.examid != null && !this.examid.equals(other.examid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Exam[ examid=" + examid + " ]";
    }
    
}
