package tecnoweb.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tecnoweb.entity.Subcategoria;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-17T11:13:51")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile ListAttribute<Categoria, Subcategoria> subcategoriaList;
    public static volatile SingularAttribute<Categoria, Integer> idCategoria;
    public static volatile SingularAttribute<Categoria, String> nombreCategoria;

}