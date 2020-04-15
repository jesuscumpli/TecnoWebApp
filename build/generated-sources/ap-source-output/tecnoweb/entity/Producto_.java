package tecnoweb.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.Valoracion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-15T12:49:04")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, Double> precio;
    public static volatile ListAttribute<Producto, Palabraclave> palabraclaveList;
    public static volatile SingularAttribute<Producto, Date> fechaSubida;
    public static volatile SingularAttribute<Producto, Usuario> idUsuario;
    public static volatile SingularAttribute<Producto, Subcategoria> idSubcategoria;
    public static volatile SingularAttribute<Producto, String> titulo;
    public static volatile SingularAttribute<Producto, Integer> idProducto;
    public static volatile ListAttribute<Producto, Valoracion> valoracionList;
    public static volatile SingularAttribute<Producto, String> fotoProducto;

}