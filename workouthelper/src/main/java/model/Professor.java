/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "professor")
@NamedQueries({
    @NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor p"),
    @NamedQuery(name = "Professor.findById", query = "SELECT p FROM Professor p WHERE p.id = :id"),
    @NamedQuery(name = "Professor.findByCref", query = "SELECT p FROM Professor p WHERE p.cref = :cref"),
    @NamedQuery(name = "Professor.findBySalario", query = "SELECT p FROM Professor p WHERE p.salario = :salario")})
@XmlRootElement
public class Professor implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CREF")
    private String cref;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "salario")
    private String salario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfessor")
    private Collection<FichaTreino> fichaTreinoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfessor")
    private Collection<Avaliacao> avaliacaoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @OneToOne
    private Usuario idUsuario;

    public Professor() {
    }

    public Professor(Integer id) {
        this.id = id;
    }

    public Professor(Integer id, String cref, String salario) {
        this.id = id;
        this.cref = cref;
        this.salario = salario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Professor[ id=" + id + " ]";
    }


    @XmlTransient
    public Collection<FichaTreino> getFichaTreinoCollection() {
        return fichaTreinoCollection;
    }

    public void setFichaTreinoCollection(Collection<FichaTreino> fichaTreinoCollection) {
        this.fichaTreinoCollection = fichaTreinoCollection;
    }

    @XmlTransient
    public Collection<Avaliacao> getAvaliacaoCollection() {
        return avaliacaoCollection;
    }

    public void setAvaliacaoCollection(Collection<Avaliacao> avaliacaoCollection) {
        this.avaliacaoCollection = avaliacaoCollection;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
    
}
