package entities;

import entities.SPerfilesAccesos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-07T13:49:01")
@StaticMetamodel(SPerfiles.class)
public class SPerfiles_ { 

    public static volatile SingularAttribute<SPerfiles, String> descripcion;
    public static volatile SingularAttribute<SPerfiles, Date> fechaBaja;
    public static volatile SingularAttribute<SPerfiles, Date> fechaAlta;
    public static volatile SingularAttribute<SPerfiles, Integer> idPerfil;
    public static volatile CollectionAttribute<SPerfiles, SPerfilesAccesos> sPerfilesAccesosCollection;
    public static volatile SingularAttribute<SPerfiles, Integer> idUsuarioModifica;
    public static volatile SingularAttribute<SPerfiles, String> nombrePerfil;
    public static volatile SingularAttribute<SPerfiles, Date> fechaServidor;
    public static volatile SingularAttribute<SPerfiles, Boolean> activo;

}