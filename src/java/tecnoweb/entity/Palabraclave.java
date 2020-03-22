/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jesús
 */
@Entity
@Table(name = "palabraclave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palabraclave.findAll", query = "SELECT p FROM Palabraclave p")
    , @NamedQuery(name = "Palabraclave.findByIdPalabraClave", query = "SELECT p FROM Palabraclave p WHERE p.idPalabraClave = :idPalabraClave")
    , @NamedQuery(name = "Palabraclave.findByValor", query = "SELECT p FROM Palabraclave p WHERE p.valor = :valor")})
public class Palabraclave implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPalabraClave")
    private Integer idPalabraClave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "valor")
    private String valor;
    @JoinTable(name = "rel_prod_clave", joinColumns = {
        @JoinColumn(name = "idPalabraClave", referencedColumnName = "idPalabraClave")}, inverseJoinColumns = {
        @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")})
    @ManyToMany
    private List<Producto> productoList;

    public Palabraclave() {
    }

    public Palabraclave(Integer idPalabraClave) {
        this.idPalabraClave = idPalabraClave;
    }

    public Palabraclave(Integer idPalabraClave, String valor) {
        this.idPalabraClave = idPalabraClave;
        this.valor = valor;
    }

    public Integer getIdPalabraClave() {
        return idPalabraClave;
    }

    public void setIdPalabraClave(Integer idPalabraClave) {
        this.idPalabraClave = idPalabraClave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPalabraClave != null ? idPalabraClave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palabraclave)) {
            return false;
        }
        Palabraclave other = (Palabraclave) object;
        if ((this.idPalabraClave == null && other.idPalabraClave != null) || (this.idPalabraClave != null && !this.idPalabraClave.equals(other.idPalabraClave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tecnoweb.entity.Palabraclave[ idPalabraClave=" + idPalabraClave + " ]";
    }
    
}
