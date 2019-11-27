/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ZolyKana
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choice.findAll", query = "SELECT c FROM Choice c")
    , @NamedQuery(name = "Choice.findByChoiceid", query = "SELECT c FROM Choice c WHERE c.choiceid = :choiceid")
    , @NamedQuery(name = "Choice.findByQuestion", query = "SELECT c FROM Choice c WHERE c.question = :question")
    , @NamedQuery(name = "Choice.findByAnswer", query = "SELECT c FROM Choice c WHERE c.answer = :answer")})
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer choiceid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String answer;
    @JoinColumn(name = "CHOICESETID", referencedColumnName = "CHOICESETID")
    @ManyToOne(optional = false)
    private Choiceset choicesetid;

    public Choice() {
    }

    public Choice(Integer choiceid) {
        this.choiceid = choiceid;
    }

    public Choice(Integer choiceid, String question, String answer) {
        this.choiceid = choiceid;
        this.question = question;
        this.answer = answer;
    }

    public Integer getChoiceid() {
        return choiceid;
    }

    public void setChoiceid(Integer choiceid) {
        this.choiceid = choiceid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Choiceset getChoicesetid() {
        return choicesetid;
    }

    public void setChoicesetid(Choiceset choicesetid) {
        this.choicesetid = choicesetid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choiceid != null ? choiceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choice)) {
            return false;
        }
        Choice other = (Choice) object;
        if ((this.choiceid == null && other.choiceid != null) || (this.choiceid != null && !this.choiceid.equals(other.choiceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Choice[ choiceid=" + choiceid + " ]";
    }
    
}
