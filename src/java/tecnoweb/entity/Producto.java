/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;

/**
 *
 * @author Jesús
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByTitulo", query = "SELECT p FROM Producto p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByFechaSubida", query = "SELECT p FROM Producto p WHERE p.fechaSubida = :fechaSubida")
    , @NamedQuery(name = "Producto.findByIdUsuario", query = "SELECT p FROM Producto p WHERE p.idUsuario.idUsuario = :idUsuario")})
public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProducto")
    private Integer idProducto;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 50)
    @Column(name = "titulo", nullable = false, length = 50)
    private String titulo;
    @Lob
    //@Size(max = 65535)
    @Column(name = "descripcion", length = 65535)
    private String descripcion;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "precio", nullable = false)
    private double precio;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "fechaSubida", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSubida;
    @Lob
    //@Size(max = 65535)
    @Column(name = "fotoProducto", length = 65535)
    private String fotoProducto;
    @JoinTable(name = "rel_prod_clave", joinColumns = {
        @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")}, inverseJoinColumns = {
        @JoinColumn(name = "idPalabraClave", referencedColumnName = "idPalabraClave")})
    @OneToMany
    private List<Palabraclave> palabraclaveList;
    @OneToMany( mappedBy = "producto")
    private List<Valoracion> valoracionList;
    @JoinColumn(name = "idSubcategoria", referencedColumnName = "idSubcategoria")
    @ManyToOne(optional = false)
    private Subcategoria idSubcategoria;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String titulo, double precio, Date fechaSubida) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.precio = precio;
        this.fechaSubida = fechaSubida;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    @XmlTransient
    public List<Palabraclave> getPalabraclaveList() {
        return palabraclaveList;
    }

    public void setPalabraclaveList(List<Palabraclave> palabraclaveList) {
        this.palabraclaveList = palabraclaveList;
    }

    @XmlTransient
    public List<Valoracion> getValoracionList() {
        return valoracionList;
    }

    public void setValoracionList(List<Valoracion> valoracionList) {
        this.valoracionList = valoracionList;
    }

    public Subcategoria getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Subcategoria idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
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
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tecnoweb.entity.Producto[ idProducto=" + idProducto + " ]";
    }
  
    
    public Double getNotaMedia(){
        Double result = 0.0;
        List<Valoracion> lista = this.getValoracionList();
        for(Valoracion v: lista){
            result += v.getNota();
        }
        if(!lista.isEmpty()){
            result = result / lista.size();
        }
        
        return result;
    }
    
    public ProductoDTO getDTO () {
        ProductoDTO prodDTO = new ProductoDTO();
        prodDTO.setIdProducto(this.idProducto);
        prodDTO.setTitulo(this.titulo);
        prodDTO.setDescripcion(this.descripcion);
        prodDTO.setFotoProducto(this.fotoProducto);
        prodDTO.setFechaSubida(this.fechaSubida);
        prodDTO.setIdSubcategoria(this.idSubcategoria.getDTO());
        return prodDTO;
    }
    
    public ProductoMenuDTO getMenuDTO () {
        ProductoMenuDTO prodDTO = new ProductoMenuDTO();
        prodDTO.setIdProducto(this.idProducto);
        prodDTO.setTitulo(this.titulo);
        prodDTO.setDescripcion(this.descripcion);
        prodDTO.setFotoProducto(this.fotoProducto);
        prodDTO.setPrecio(this.precio);
        prodDTO.setFechaSubida(this.fechaSubida);
        prodDTO.setIdSubcategoria(this.idSubcategoria.getDTO());
        if(this.idUsuario!=null) prodDTO.setIdUsuario(this.idUsuario.getDTO());
        //prodDTO.setNotaMedia(this.getNotaMedia());        //Usamos mejor la operacion en productosService
        List<PalabraclaveDTO> clavesDTO = new ArrayList<>();
        for(Palabraclave p: this.palabraclaveList){
            clavesDTO.add(p.getDTO());
        }
        prodDTO.setPalabraclaveList(clavesDTO);
        prodDTO.setNumValoraciones(this.valoracionList.size());
        return prodDTO;
    }
    
}
