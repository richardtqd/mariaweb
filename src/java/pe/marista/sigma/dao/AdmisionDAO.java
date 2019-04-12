package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.reporte.AdmisionEstudiantesRepBean;

/**
 *
 * @author Administrador
 */
public interface AdmisionDAO {

    public void insertarAdmision(AdmisionBean admisionBean) throws Exception;

    public void modificarAdmision(AdmisionBean admisionBean) throws Exception;

    public void cambiarEstadoPostulante(AdmisionBean admisionBean) throws Exception;

    public void eliminarAdmision(AdmisionBean admisionBean) throws Exception;

    public List<AdmisionBean> obtenerAdmision() throws Exception;

    public AdmisionBean obtenerAdmisionPorId(AdmisionBean admisionBean) throws Exception;

    public AdmisionBean obtenerAdmisionPorPostu(AdmisionBean admisionBean) throws Exception;

    public AdmisionBean validarPostuEnAdmision(EstudianteBean estudianteBean) throws Exception;

    public List<AdmisionBean> obtenerAdmisionPorUniNeg(String uniNeg) throws Exception;

    public AdmisionBean obtenerAdmisionPorIdPeriodo(AdmisionBean admisionBean) throws Exception;

    //Aprobacion de Postulantes
    public List<AdmisionBean> obtenerAdmisionFiltro(AdmisionBean admisionBean) throws Exception;

    public void aprobarPostulante(AdmisionBean admisionBean) throws Exception;

    public Object llamarGenerarDocPorAdmision(AdmisionBean admisionBean) throws Exception;

    //lista distinct - grupo
    public List<AdmisionBean> obtenerListaDistinctAdm(String value) throws Exception;

    public void insertarAdmisionEspecial(AdmisionBean admisionBean) throws Exception;
    
    public List<AdmisionEstudiantesRepBean> obtenerAlumnosPorAnio(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

}
