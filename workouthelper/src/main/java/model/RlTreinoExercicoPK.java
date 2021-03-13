/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marco
 */
@Embeddable
public class RlTreinoExercicoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_treino")
    private int idTreino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_exercicio")
    private int idExercicio;

    public RlTreinoExercicoPK() {
    }

    public RlTreinoExercicoPK(int idTreino, int idExercicio) {
        this.idTreino = idTreino;
        this.idExercicio = idExercicio;
    }

    public int getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(int idTreino) {
        this.idTreino = idTreino;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTreino;
        hash += (int) idExercicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RlTreinoExercicoPK)) {
            return false;
        }
        RlTreinoExercicoPK other = (RlTreinoExercicoPK) object;
        if (this.idTreino != other.idTreino) {
            return false;
        }
        if (this.idExercicio != other.idExercicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RlTreinoExercicoPK[ idTreino=" + idTreino + ", idExercicio=" + idExercicio + " ]";
    }
    
}
