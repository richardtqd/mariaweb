package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDependienteBean;
import pe.marista.sigma.bean.reporte.LegajoRepBean;
import pe.marista.sigma.bean.reporte.PersonalDependienteRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalDependienteDAO {
        
    public List<PersonalDependienteBean> obtenerPersonalDependientePorPersonal(PersonalBean personalBean) throws Exception;
    
    public PersonalDependienteBean obtenerPersonalDependientePorId(PersonalDependienteBean personalDependienteBean) throws Exception;
        
    public void insertarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception;

    public void modificarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception;

    public void eliminarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception;
    
    public void cambiarEstadoDependiente(PersonalDependienteBean personalDependienteBean) throws Exception;
    
    public List<PersonalDependienteRepBean> obtenerDependientePorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;
    
    public String  obtenerDependienteFechaNacimiento(@Param("fechaNacimiento") Date fechaNacimiento) throws Exception;
            
}
