package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.reporte.FamiliarRepBean;

/**
 *
 * @author Administrador
 */
public interface FamiliarDAO {

    //Familiar
    public void insertarFamiliar(FamiliarBean familiarBean) throws Exception;

    public void modificarFamiliar(FamiliarBean familiarBean) throws Exception;

    public void modificarFamiliarRapido(FamiliarBean familiarBean) throws Exception;

    public void eliminarFamiliar(String idFamiliar) throws Exception;

    public List<FamiliarBean> obtenerFamiliar() throws Exception;

    public List<FamiliarBean> obtenerFamiliarPorFiltro(FamiliarBean familiarBean) throws Exception;

    public List<PersonaBean> obtenerFamiliarPersonaPorFiltro(FamiliarBean familiarBean) throws Exception;

    public FamiliarBean obtenerFamiliarPorId(PersonaBean personaBean) throws Exception;

    public FamiliarBean obtenerResPagoPorId(PersonaBean personaBean) throws Exception;

    //FamiliarEstudiante
    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEst(String idEstudiante) throws Exception;

    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEstRapido(String idEstudiante) throws Exception;

    public FamiliarEstudianteBean obtenerFamiliarEstPorFamiliar(String idEstudiante) throws Exception;

    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEstSinPadres(String idEstudiante) throws Exception;

    public FamiliarEstudianteBean obtenerFamiliarEstPorId(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public void insertarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public void modificarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public void modificarFamiliarEstudianteRapido(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public void modificarFamiliarEstudianteStatus(FamiliarEstudianteBean familiarEstudianteBean) throws Exception; 

    public void eliminarFamiliarEst(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public void eliminarFamiliarEstudiantePorFam(String idFamiliar) throws Exception;

    public FamiliarEstudianteBean obtenerFamiliarPorParentesco(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;

    public List<FamiliarRepBean> obtenerFamiliarRep(@Param("idEstudiante") String idEstudiante, @Param("idFamiliar") String idFamiliar, @Param("tipo") String tipo, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarDniEstudianteFamEst(FamiliarEstudianteBean familiarEstudianteBean) throws Exception;
}
