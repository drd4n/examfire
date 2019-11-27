/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author ZolyKana
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choiceset.findAll", query = "SELECT c FROM Choiceset c")
    , @NamedQuery(name = "Choiceset.findByChoicesetid", query = "SELECT c FROM Choiceset c WHERE c.choicesetid = :choicesetid")
    , @NamedQuery(name = "Choiceset.findByTitle", query = "SELECT c FROM Choiceset c WHERE c.title = :title")})
public class Choiceset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer choicesetid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String title;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "choicesetid")
    private List<Choice> choiceList;
    @JoinColumn(name = "EXAMID", referencedColumnName = "EXAMID")
    @ManyToOne(optional = false)
    private Exam examid;

    public Choiceset() {
    }

    public Choiceset(Integer choicesetid) {
        this.choicesetid = choicesetid;
    }

    public Choiceset(Integer choicesetid, String title) {
        this.choicesetid = choicesetid;
        this.title = title;
    }

    public Integer getChoicesetid() {
        return choicesetid;
    }

    public void setChoicesetid(Integer choicesetid) {
        this.choicesetid = choicesetid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public List<Choice> getChoiceList() {
        return choiceList;
    }
    
    public List<Choice> getShuffleChoice() {
        ArrayList<Choice> shuffle = new ArrayList<>();
        for (Choice choiceList1 : this.choiceList) {
            shuffle.add(choiceList1);
        }
        Collections.shuffle(shuffle);
        return shuffle;
    }

    public void setChoiceList(List<Choice> choiceList) {
        this.choiceList = choiceList;
    }

    public Exam getExamid() {
        return examid;
    }

    public void setExamid(Exam examid) {
        this.examid = examid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choicesetid != null ? choicesetid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choiceset)) {
            return false;
        }
        Choiceset other = (Choiceset) object;
        if ((this.choicesetid == null && other.choicesetid != null) || (this.choicesetid != null && !this.choicesetid.equals(other.choicesetid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Choiceset[ choicesetid=" + choicesetid + " ]";
    }
    
}
