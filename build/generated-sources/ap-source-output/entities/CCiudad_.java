package entities;

import entities.HActivacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-19T17:34:59")
@StaticMetamodel(CCiudad.class)
public class CCiudad_ { 

    public static volatile SingularAttribute<CCiudad, String> descripcion;
    public static volatile SingularAttribute<CCiudad, Short> lada;
    public static volatile SingularAttribute<CCiudad, Date> fechaBaja;
    public static volatile SingularAttribute<CCiudad, String> codigo;
    public static volatile SingularAttribute<CCiudad, Date> fechaAlta;
    public static volatile CollectionAttribute<CCiudad, HActivacion> hActivacionCollection;
    public static volatile SingularAttribute<CCiudad, Long> idCiudad;
    public static volatile SingularAttribute<CCiudad, Date> fechaServidor;
    public static volatile SingularAttribute<CCiudad, Boolean> activo;

}