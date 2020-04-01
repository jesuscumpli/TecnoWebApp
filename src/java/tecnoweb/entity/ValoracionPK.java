/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jesús
 */
@Embeddable
public class ValoracionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idValoracion")
    private int idValoracion;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "idProducto", nullable = false)
    private int idProducto;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "idUsuario", nullable = false)
    private int idUsuario;

    public ValoracionPK() {
    }

    public ValoracionPK(int idValoracion, int idProducto, int idUsuario) {
        this.idValoracion = idValoracion;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
    }

    public int getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(int idValoracion) {
        this.idValoracion = idValoracion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idValoracion;
        hash += (int) idProducto;
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValoracionPK)) {
            return false;
        }
        ValoracionPK other = (ValoracionPK) object;
        if (this.idValoracion != other.idValoracion) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tecnoweb.entity.ValoracionPK[ idValoracion=" + idValoracion + ", idProducto=" + idProducto + ", idUsuario=" + idUsuario + " ]";
    }
    
}
