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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "exercicio")
@NamedQueries({
    @NamedQuery(name = "Exercicio.findAll", query = "SELECT e FROM Exercicio e"),
    @NamedQuery(name = "Exercicio.findById", query = "SELECT e FROM Exercicio e WHERE e.id = :id"),
    @NamedQuery(name = "Exercicio.findByNome", query = "SELECT e FROM Exercicio e WHERE e.nome = :nome"),
    @NamedQuery(name = "Exercicio.findByDescricao", query = "SELECT e FROM Exercicio e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Exercicio.findByLinkVideo", query = "SELECT e FROM Exercicio e WHERE e.linkVideo = :linkVideo"),
    @NamedQuery(name = "Exercicio.findByRecomendacao", query = "SELECT e FROM Exercicio e WHERE e.recomendacao = :recomendacao")})
public class Exercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 100)
    @Column(name = "link_video")
    private String linkVideo;
    @Size(max = 45)
    @Column(name = "recomendacao")
    private String recomendacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exercicio")
    private Collection<RlTreinoExercico> rlTreinoExercicoCollection;

    public Exercicio() {
    }

    public Exercicio(Integer id) {
        this.id = id;
    }

    public Exercicio(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    public Collection<RlTreinoExercico> getRlTreinoExercicoCollection() {
        return rlTreinoExercicoCollection;
    }

    public void setRlTreinoExercicoCollection(Collection<RlTreinoExercico> rlTreinoExercicoCollection) {
        this.rlTreinoExercicoCollection = rlTreinoExercicoCollection;
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
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Exercicio[ id=" + id + " ]";
    }
    
}
