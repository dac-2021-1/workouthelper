/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "treino")
@NamedQueries({
    @NamedQuery(name = "Treino.findAll", query = "SELECT t FROM Treino t"),
    @NamedQuery(name = "Treino.findById", query = "SELECT t FROM Treino t WHERE t.id = :id"),
    @NamedQuery(name = "Treino.findByGrupamento", query = "SELECT t FROM Treino t WHERE t.grupamento = :grupamento")})
@XmlRootElement
public class Treino implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "grupamento")
    private String grupamento;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treinoA")
//    private Collection<FichaTreino> fichaTreinoCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treinoB")
//    private Collection<FichaTreino> fichaTreinoCollection1;
//    @OneToMany(mappedBy = "treinoC")
//    private Collection<FichaTreino> fichaTreinoCollection2;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treino")
//    private Collection<RlTreinoExercico> rlTreinoExercicoCollection;

    public Treino() {
    }

    public Treino(Integer id) {
        this.id = id;
    }

    public Treino(Integer id, String grupamento) {
        this.id = id;
        this.grupamento = grupamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


//    @XmlTransient
//    public Collection<FichaTreino> getFichaTreinoCollection() {
//        return fichaTreinoCollection;
//    }
//
//    public void setFichaTreinoCollection(Collection<FichaTreino> fichaTreinoCollection) {
//        this.fichaTreinoCollection = fichaTreinoCollection;
//    }
//
//    @XmlTransient
//    public Collection<FichaTreino> getFichaTreinoCollection1() {
//        return fichaTreinoCollection1;
//    }
//
//    public void setFichaTreinoCollection1(Collection<FichaTreino> fichaTreinoCollection1) {
//        this.fichaTreinoCollection1 = fichaTreinoCollection1;
//    }
//
//    @XmlTransient
//    public Collection<FichaTreino> getFichaTreinoCollection2() {
//        return fichaTreinoCollection2;
//    }
//
//    public void setFichaTreinoCollection2(Collection<FichaTreino> fichaTreinoCollection2) {
//        this.fichaTreinoCollection2 = fichaTreinoCollection2;
//    }
//
//    @XmlTransient
//    public Collection<RlTreinoExercico> getRlTreinoExercicoCollection() {
//        return rlTreinoExercicoCollection;
//    }
//
//    public void setRlTreinoExercicoCollection(Collection<RlTreinoExercico> rlTreinoExercicoCollection) {
//        this.rlTreinoExercicoCollection = rlTreinoExercicoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treino)) {
            return false;
        }
        Treino other = (Treino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Treino[ id=" + id + " ]";
    }

    public String getGrupamento() {
        return grupamento;
    }

    public void setGrupamento(String grupamento) {
        this.grupamento = grupamento;
    }
    
}
