package tecnoweb.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.ValoracionPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-17T11:13:51")
@StaticMetamodel(Valoracion.class)
public class Valoracion_ { 

    public static volatile SingularAttribute<Valoracion, ValoracionPK> valoracionPK;
    public static volatile SingularAttribute<Valoracion, Usuario> usuario;
    public static volatile SingularAttribute<Valoracion, Producto> producto;
    public static volatile SingularAttribute<Valoracion, String> comentario;
    public static volatile SingularAttribute<Valoracion, Date> fechaPublicacion;
    public static volatile SingularAttribute<Valoracion, Double> nota;

}