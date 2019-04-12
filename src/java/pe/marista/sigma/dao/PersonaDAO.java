package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UnidadNegocioBean;

/**
 *
 * @author Administrador
 */
public interface PersonaDAO {

    public void insertarPersona(PersonaBean personaBean) throws Exception;

    public void modificarPersona(PersonaBean personaBean) throws Exception;

    public void modificarPersonaDatBasicos(PersonaBean personaBean) throws Exception;

    public void modificarResponsable(EstudianteBean estudianteBean) throws Exception;

    public void eliminarPersona(String idPersona) throws Exception;

    public PersonaBean obtenerPersPorId(PersonaBean personaBean) throws Exception;
    
    public PersonaBean obtenerPersPorIdRapido(PersonaBean personaBean) throws Exception;

    public String obtenerFoto(@Param("idPersona") String idPersona, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonaBean> obtenerPersona() throws Exception;

    public List<PersonaBean> obtenerPersonaPorUniNeg(UnidadNegocioBean unidadNegocioBean) throws Exception;

    //Filtro Admisión
    public List<PersonaBean> obtenerPersonaAdmPorUniNeg(String uniNeg) throws Exception;

    //Filtro Persona
    public List<PersonaBean> obtenerPersonaPorFiltro(PersonaBean personaBean) throws Exception;

    public List<PersonaBean> obtenerPersonaPorFiltroValorados(PersonaBean personaBean) throws Exception;

    public List<PersonaBean> obtenerPersonaProspectoPorFiltro(PersonaBean personaBean) throws Exception;

    public List<PersonaBean> obtenerTop10Persona(String uniNeg) throws Exception;

    public List<PersonaBean> SP_obtenerPersonaPorFiltro(PersonaBean personaBean) throws Exception;

    public PersonaBean obtenerPersonaPorFiltroProspecto(PersonaBean personaBean) throws Exception;

    public PersonaBean obtenerPersonaPorNombre(PersonaBean personaBean) throws Exception;

    public String generarCodigoPersona(@Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer verificarCodigoGenerado(@Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonaBean> obtenerPersonaPorCorreo(@Param("uniNeg") String uniNeg, @Param("correo") String correo) throws Exception;

}
