/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@Table(name = "aluno", schema = "workouthelper")
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findById", query = "SELECT a FROM Aluno a WHERE a.id = :id"),
//    @NamedQuery(name = "Aluno.findByListaAvaliacao", query = "SELECT a FROM Aluno a WHERE a.listaAvaliacao = :listaAvaliacao"),
    @NamedQuery(name = "Aluno.findByAtivo", query = "SELECT a FROM Aluno a WHERE a.ativo = :ativo")})
@XmlRootElement
public class Aluno implements Serializable {

    @Size(max = 45)
    @Column(name = "lista_avaliacao")
    private String listaAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ativo")
    private short ativo;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAluno")
//    private Collection<FichaTreino> fichaTreinoCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAluno")
//    private Collection<Avaliacao> avaliacaoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_plano", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Plano idPlano;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @OneToOne(optional = true)
    @JsonbTransient
    private Usuario usuario;

    public Aluno() {
    }

    public Aluno(Integer id) {
        this.id = id;
    }

    public Aluno(Integer id, short ativo) {
        this.id = id;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListaAvaliacao() {
        return listaAvaliacao;
    }

    public void setListaAvaliacao(String listaAvaliacao) {
        this.listaAvaliacao = listaAvaliacao;
    }

    public Plano getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Plano idPlano) {
        this.idPlano = idPlano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Aluno[ id=" + id + " ]";
    }

//    @XmlTransient
//    public Collection<FichaTreino> getFichaTreinoCollection() {
//        return fichaTreinoCollection;
//    }
//
//    public void setFichaTreinoCollection(Collection<FichaTreino> fichaTreinoCollection) {
//        this.fichaTreinoCollection = fichaTreinoCollection;
//    }

//    @XmlTransient
//    public Collection<Avaliacao> getAvaliacaoCollection() {
//        return avaliacaoCollection;
//    }
//
//    public void setAvaliacaoCollection(Collection<Avaliacao> avaliacaoCollection) {
//        this.avaliacaoCollection = avaliacaoCollection;
//    }

    public short getAtivo() {
        return ativo;
    }

    public void setAtivo(short ativo) {
        this.ativo = ativo;
    }

}
