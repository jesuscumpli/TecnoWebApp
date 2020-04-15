package tecnoweb.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-15T12:49:04")
@StaticMetamodel(Subcategoria.class)
public class Subcategoria_ { 

    public static volatile ListAttribute<Subcategoria, Producto> productoList;
    public static volatile SingularAttribute<Subcategoria, Integer> idSubcategoria;
    public static volatile SingularAttribute<Subcategoria, String> nombreSubcategoria;
    public static volatile SingularAttribute<Subcategoria, Categoria> idCategoria;

}