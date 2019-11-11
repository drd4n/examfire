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
import javax.persistence.Id;
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
    @NamedQuery(name = "Subjects.findAll", query = "SELECT s FROM Subjects s")
    , @NamedQuery(name = "Subjects.findBySubjectid", query = "SELECT s FROM Subjects s WHERE s.subjectid = :subjectid")
    , @NamedQuery(name = "Subjects.findBySubjectname", query = "SELECT s FROM Subjects s WHERE s.subjectname = :subjectname")})
public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer subjectid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    private String subjectname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectid")
    private List<Exam> examList;

    public Subjects() {
    }

    public Subjects(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Subjects(Integer subjectid, String subjectname) {
        this.subjectid = subjectid;
        this.subjectname = subjectname;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    @XmlTransient
    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectid != null ? subjectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.subjectid == null && other.subjectid != null) || (this.subjectid != null && !this.subjectid.equals(other.subjectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Subjects[ subjectid=" + subjectid + " ]";
    }
    
}
