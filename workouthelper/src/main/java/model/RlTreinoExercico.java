/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "rl_treino_exercico")
@NamedQueries({
    @NamedQuery(name = "RlTreinoExercico.findAll", query = "SELECT r FROM RlTreinoExercico r"),
    @NamedQuery(name = "RlTreinoExercico.findByIdTreino", query = "SELECT r FROM RlTreinoExercico r WHERE r.rlTreinoExercicoPK.idTreino = :idTreino"),
    @NamedQuery(name = "RlTreinoExercico.findByIdExercicio", query = "SELECT r FROM RlTreinoExercico r WHERE r.rlTreinoExercicoPK.idExercicio = :idExercicio"),
    @NamedQuery(name = "RlTreinoExercico.findBySerie", query = "SELECT r FROM RlTreinoExercico r WHERE r.serie = :serie"),
    @NamedQuery(name = "RlTreinoExercico.findByRepeticao", query = "SELECT r FROM RlTreinoExercico r WHERE r.repeticao = :repeticao"),
    @NamedQuery(name = "RlTreinoExercico.findByIntervalo", query = "SELECT r FROM RlTreinoExercico r WHERE r.intervalo = :intervalo"),
    @NamedQuery(name = "RlTreinoExercico.findByRow", query = "SELECT r FROM RlTreinoExercico r WHERE r.row = :row")})
@XmlRootElement
public class RlTreinoExercico implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "repeticao")
    private String repeticao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "intervalo")
    private String intervalo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "row")
    private String row;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RlTreinoExercicoPK rlTreinoExercicoPK;
    @JoinColumn(name = "id_exercicio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Exercicio exercicio;
    @JoinColumn(name = "id_treino", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Treino treino;

    public RlTreinoExercico() {
    }

    public RlTreinoExercico(int idTreino, int idExercicio, String serie, String repeticao, String intervalo, String row) {
        
        this.serie = serie;
        this.repeticao = repeticao;
        this.intervalo = intervalo;
        this.row = row;
    }

    public RlTreinoExercico(int idTreino, int idExercicio) {
        this.rlTreinoExercicoPK = new RlTreinoExercicoPK(idTreino, idExercicio);
    }

    public RlTreinoExercicoPK getRlTreinoExercicoPK() {
        return rlTreinoExercicoPK;
    }

    public void setRlTreinoExercicoPK(RlTreinoExercicoPK rlTreinoExercicoPK) {
        this.rlTreinoExercicoPK = rlTreinoExercicoPK;
    }


    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rlTreinoExercicoPK != null ? rlTreinoExercicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RlTreinoExercico)) {
            return false;
        }
        RlTreinoExercico other = (RlTreinoExercico) object;
        if ((this.rlTreinoExercicoPK == null && other.rlTreinoExercicoPK != null) || (this.rlTreinoExercicoPK != null && !this.rlTreinoExercicoPK.equals(other.rlTreinoExercicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RlTreinoExercico[ rlTreinoExercicoPK=" + rlTreinoExercicoPK + " ]";
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
    
}
