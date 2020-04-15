package tecnoweb.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Valoracion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-15T12:49:04")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> apellidos;
    public static volatile SingularAttribute<Usuario, Date> fechaNac;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile SingularAttribute<Usuario, String> fotoUsuario;
    public static volatile ListAttribute<Usuario, Producto> productoList;
    public static volatile SingularAttribute<Usuario, Integer> idUsuario;
    public static volatile SingularAttribute<Usuario, String> emailUsuario;
    public static volatile SingularAttribute<Usuario, Boolean> isAdmin;
    public static volatile ListAttribute<Usuario, Valoracion> valoracionList;
    public static volatile SingularAttribute<Usuario, String> nombre;

}