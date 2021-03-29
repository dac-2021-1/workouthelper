/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "ficha_treino")
@NamedQueries({
    @NamedQuery(name = "FichaTreino.findAll", query = "SELECT f FROM FichaTreino f"),
    @NamedQuery(name = "FichaTreino.findById", query = "SELECT f FROM FichaTreino f WHERE f.id = :id"),
    @NamedQuery(name = "FichaTreino.findByDataInicio", query = "SELECT f FROM FichaTreino f WHERE f.dataInicio = :dataInicio"),
    @NamedQuery(name = "FichaTreino.findByDataFinal", query = "SELECT f FROM FichaTreino f WHERE f.dataFinal = :dataFinal")})
@XmlRootElement
public class FichaTreino implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "data_inicio")
    private String dataInicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "data_final")
    private String dataFinal;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Aluno idAluno;
    @JoinColumn(name = "id_professor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Professor idProfessor;
    @JoinColumn(name = "treino_a", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Treino treinoA;
    @JoinColumn(name = "treino_b", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Treino treinoB;
    @JoinColumn(name = "treino_c", referencedColumnName = "id")
    @ManyToOne
    private Treino treinoC;

    public FichaTreino() {
    }

    public FichaTreino(Integer id) {
        this.id = id;
    }

    public FichaTreino(Integer id, String dataInicio, String dataFinal) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Aluno getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Aluno idAluno) {
        this.idAluno = idAluno;
    }

    public Professor getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Professor idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Treino getTreinoA() {
        return treinoA;
    }

    public void setTreinoA(Treino treinoA) {
        this.treinoA = treinoA;
    }

    public Treino getTreinoB() {
        return treinoB;
    }

    public void setTreinoB(Treino treinoB) {
        this.treinoB = treinoB;
    }

    public Treino getTreinoC() {
        return treinoC;
    }

    public void setTreinoC(Treino treinoC) {
        this.treinoC = treinoC;
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
        if (!(object instanceof FichaTreino)) {
            return false;
        }
        FichaTreino other = (FichaTreino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FichaTreino[ id=" + id + " ]";
    }

}
