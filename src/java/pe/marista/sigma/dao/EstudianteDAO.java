package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.BloqueoBean;
import pe.marista.sigma.bean.CodigoColegioBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteInfoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadPadresRepBean;
import pe.marista.sigma.bean.reporte.EstudianteMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteRepBean;
import pe.marista.sigma.bean.reporte.FamiliarEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FichaClinicaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FichaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.ResponsableEconomicoRepBean;

/**
 *
 * @author Administrador
 */
public interface EstudianteDAO {

    public void insertarEstudiante(EstudianteBean estudianteBean) throws Exception;

    public void modificarEstudiante(EstudianteBean estudianteBean) throws Exception;

    public void modificarEstudianteRespMat(EstudianteBean estudianteBean) throws Exception;

    public void modificarSoloDniRespPago(EstudianteBean estudianteBean) throws Exception;

    public void cambiarDatosIngresoEstudiante(EstudianteBean estudianteBean) throws Exception;

    public void cambiarDatosEgresoEstudiante(EstudianteBean estudianteBean) throws Exception;

    public void cambiarGradoAcademico(EstudianteBean estudianteBean) throws Exception;

    public void modificarTipoStatusEst(EstudianteBean estudianteBean) throws Exception;

    public void eliminarEstudiante(String idEstudiante) throws Exception;

    public List<EstudianteBean> obtenerEstudiante() throws Exception;

    public List<EstudianteBean> obtenerEstudiantePost() throws Exception;

    public List<EstudianteBean> obtenerEstudiantePorUniNeg(String uniNeg) throws Exception;

    public List<EstudianteBean> obtenerEstudiantePostPorUniNeg(String uniNeg) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudiante(EstudianteBean estudianteBean) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudianteFicha(EstudianteBean estudianteBean) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudianteActivo(EstudianteBean estudianteBean) throws Exception;

    public EstudianteBean obtenerEstudianteGradoAca(EstudianteBean estudianteBean) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudiantePros(EstudianteBean estudianteBean) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudiantePost(EstudianteBean estudianteBean) throws Exception;

    public EstudianteBean obtenerEstPorId(EstudianteBean estudianteBean) throws Exception;

    public EstudianteBean obtenerEstPorIdMatricula(EstudianteBean estudianteBean) throws Exception;

    public EstudianteBean obtenerEstudiantePorCodigo(@Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;

    public void actualizarGrupoSanguineo(EstudianteBean estudianteBean) throws Exception;

    public void actualizarDatosNacimiento(EstudianteBean estudianteBean) throws Exception;

    public void modificarEstFam(EstudianteBean estudianteBean) throws Exception;

    //EstudianteInfo
    public List<EstudianteInfoBean> obtenerEstudianteInfo() throws Exception;

    public EstudianteInfoBean obtenerEstudianteInfoPorId(String idEstudiante) throws Exception;

    public void insertarEstudianteInfo(EstudianteInfoBean estudianteInfoBean) throws Exception;

    public void modificarEstudianteInfo(EstudianteInfoBean estudianteInfoBean) throws Exception;
    
    public void modificarDNIEstudianteInfo(EstudianteInfoBean estudianteInfoBean) throws Exception;

    public void eliminarEstudianteInfo(String idEstudiante) throws Exception;

    //Filtros de Admsion
    public List<EstudianteBean> obtenerEstudianteAminPorUniNeg(String uniNeg) throws Exception;

    //Filtros de Matricula
    public List<EstudianteBean> obtenerEstudianteMatPorUniNeg(@Param("parms") Map<String, Object> parms) throws Exception;

    //filtros masivos
//    public List<EstudianteBean> obtenerFiltroEstudianteMasivo(EstudianteBean estudianteBean) throws Exception;
    //Generacion de Codigos
    public Integer generarCodigoChamp(EstudianteBean estudianteBean) throws Exception;

    public Integer generarCodigoSanjoH(EstudianteBean estudianteBean) throws Exception;

    public Integer generarCodigoOtros(EstudianteBean estudianteBean) throws Exception;

    public void insertarCodigo(EstudianteBean estudianteBean) throws Exception;

    public String existeCodigo(EstudianteBean estudianteBean) throws Exception;

    //Reportes
    public List<EstudianteRepBean> obtenerEstudianteRepPorId(EstudianteBean estudianteBean) throws Exception;
    
    public Boolean obtenerEstudianteActivoProspecto(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public List<FichaEstudianteRepBean> obtenerFichaEstudianteCabecera(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public List<FamiliarEstudianteRepBean> obtenerDetPadres(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ResponsableEconomicoRepBean> obtenerResponEconomico(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    //FichaClinica
    public List<FichaClinicaEstudianteRepBean> obtenerFichaClinicaEstudiante(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public List<EstudianteEnfermedadPadresRepBean> obtenerEstudianteInfoEnfPadres(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public EstudianteBean obtenerEstPorCodigo(EstudianteBean estudianteBean) throws Exception;

    //BLOQUEO DE ESTUDIANTE
    public Object execProBloqueo(BloqueoBean bloqueoBean) throws Exception;

    //LIBERA BLOQUEO DE ESTUDIANTE
    public Object execProBloqueoLibera(BloqueoBean bloqueoBean) throws Exception;

    public EstudianteBean SP_actualizarEstadoEst(EstudianteBean estudianteBean) throws Exception;

    //AyudaExcel
    public List<EstudianteBean> obtenerFiltroMatriculadosPorUsuario(EstudianteBean estudianteBean) throws Exception;

    public Object execProUnirFamilia(String uniNeg) throws Exception;

    //codigocolegio
    public List<CodigoColegioBean> obtenerCodigoPorFiltro(CodigoColegioBean codigoColegioBean) throws Exception;

    public void insertarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception; 

    public void modificarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception;

    public void eliminarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception;

    public CodigoColegioBean obtenerCodigoColegioPorId(CodigoColegioBean codigoColegioBean) throws Exception;

    public CodigoColegioBean obtenerCodigoColegioPorCodigo(CodigoColegioBean codigoColegioBean) throws Exception;

    public CodigoColegioBean obtenerCodigoColegioPorCodigoSanjoh(CodigoColegioBean codigoColegioBean) throws Exception;

    public void modificarEstudianteSeccion(@Param("idEstudiante") String idEstudiante, @Param("seccion") String seccion) throws Exception;

    public void modificarEstudianteSeccionyRetiro(@Param("idEstudiante") String idEstudiante, @Param("seccion") String seccion, @Param("fechaStatusEst") Date fechaStatusEst, @Param("motivoStatusEst") String motivoStatusEst) throws Exception;

    public List<EstudianteMatriculaRepBean> obtenerAlumnoMatricula(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Integer obtenerIdTipoResp(@Param("idEstudiante") String idEstudiante) throws Exception;

    public String obtenerIdResPago(@Param("idEstudiante") String idEstudiante, @Param("idTipoParentesco") Integer idTipoParentesco) throws Exception;

    public Integer obtenerSiAlumnoEsMatriculado(@Param("idEstudiante") String idEstudiante, @Param("anio") Integer anio) throws Exception;
    
    public Integer obtenerEstPorIdFamilia(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg);
    
    public void modificarFamiliaEstudiante(EstudianteBean estudianteBean) throws Exception;
}
