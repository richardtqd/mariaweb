package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.AlumnosIngresantesRetiradosRepBean;
import pe.marista.sigma.bean.reporte.DeclaracionJuradaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaNivelRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccionRepBean;
import pe.marista.sigma.bean.reporte.EstudianteGeneralRepBean;
import pe.marista.sigma.bean.reporte.ListaAlumnosRepBean;

/**
 *
 * @author Administrador
 */
public interface MatriculaDAO {

    public void insertarMatricula(MatriculaBean matriculaBean) throws Exception;

    public void insertarMatriculaAll(MatriculaBean matriculaBean) throws Exception;

    public void insertarMatriculaEspecial(MatriculaBean matriculaBean) throws Exception;

    public void modificarMatricula(MatriculaBean matriculaBean) throws Exception;

    public void corregirMatriculaOff(MatriculaBean matriculaBean) throws Exception;

    public void eliminarMatricula(Integer idMatricula) throws Exception;

    public List<MatriculaBean> obtenerMatricula() throws Exception;

    public List<MatriculaBean> obtenerMatriculaActivos() throws Exception;

    public MatriculaBean obtenerMatriculaPorId(MatriculaBean matriculaBean) throws Exception;

//    public MatriculaBean obtenerBachiller()throws Exception;
    public MatriculaBean obtenerMatriculaPorIdPeriodo(MatriculaBean matriculaBean) throws Exception;

    public Object llamarGenerarCtasxCobrar(MatriculaBean matriculaBean) throws Exception;

    public Object llamarGenerarMatricula(MatriculaBean matriculaBean) throws Exception;

    public void eliminarCtasxcobrar(MatriculaBean matriculaBean) throws Exception;
//    public Object llamarGenerarCtasxCobrar(@Param("param") Map<String,Object> param) throws Exception;

    public List<MatriculaBean> obtenerFiltroNoMatriculados(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroMatriculadosPorUsuario(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroMatriculados(MatriculaBean matriculaBean) throws Exception;

    //filtro masivo
    public List<MatriculaBean> obtenerFiltroMatriculaMasivo(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivo(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoLista(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoDeudor(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoDeudorMes(@Param("matriculaBean") MatriculaBean matriculaBean, @Param("listaMeses") Integer[] listaMeses) throws Exception;

    public List<MatriculaBean> obtenerAlumnoEsquela(@Param("creaFecha") String creaFecha, @Param("idEsquela") Integer idEsquela, @Param("uniNeg") String uniNeg, @Param("status") Integer status) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoDepuracionMatri(MatriculaBean matriculaBean) throws Exception;//depuracion de no matriculados

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoCorreccionGrado(MatriculaBean matriculaBean) throws Exception;//cambiar de grado academico

    public List<MatriculaBean> obtenerFiltroEstudianteCtasPorCobrarMasivo(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroSaldoPensiones(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("anio") Integer anio, @Param("idNivelAcademico") Integer idNivelAcademico, @Param("idGradoAcademico") Integer idGradoAcademico) throws Exception;

    public List<ViewMatriculaBean> verEstadisticasMatriculaPorAnio(ViewMatriculaBean viewMatriculaBean) throws Exception;
//    public List<ViewMatriculaBean> verEstadisticasMatriculaPorAnio(@Param("anio")Integer value,@Param("uniNeg")String uniNeg) throws Exception;

    //correcion de grado academico
    public void modificarMatriculaGradoAca(MatriculaBean matriculaBean) throws Exception;

    //Impresion Masiva de Comp de pago
    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivo(MatriculaBean matriculaBean) throws Exception;

    //Exclusivo para generar Recibos
    public List<MatriculaBean> obtenerFiltroEstudianteGenerarRecibo(MatriculaBean matriculaBean) throws Exception;
    
    public List<MatriculaBean> obtenerFiltroEstudianteGenerarSinRecibo(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoDeuda(@Param("matriculaBean") MatriculaBean matriculaBean, @Param("listaMeses") Integer[] listaMeses) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoDeudaCole(@Param("matriculaBean") MatriculaBean matriculaBean, @Param("listaMeses") Integer[] listaMeses) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoAfter(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerMatriculaUniNeg(String uniNeg) throws Exception;

    public List<MatriculaBean> obtenerMatriculaUniNegAnio(MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMatriculadosPorUsu(MatriculaBean matriculaBean) throws Exception;

    public Integer obtenerMaxId(MatriculaBean matriculaBean) throws Exception;

    //FichaEstudiante
    public MatriculaBean obtenerEstudiantePorMatricula(MatriculaBean matriculaBean) throws Exception;

    //estadistica matricula por anio
    public List<EstMatriculaRepBean> obtenerEstadisticaMatriculaCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipFor") String tipFor, @Param("flg") Integer flg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<EstMatriculaNivelRepBean> obtenerNiveles(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipFor") String tipFor, @Param("flg") Integer flg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<EstMatriculaGradoAcaRepBean> obtenerGradosAcademicoPorNivel(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipFor") String tipFor, @Param("nivel") String nivel, @Param("flg") Integer flg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<EstMatriculaSeccGradoAcaRepBean> obtenerSeccionesPorGradoAca(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipFor") String tipFor, @Param("nivel") String nivel, @Param("idGradoAcademico") Integer idGradoAcademico, @Param("flg") Integer flg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<EstMatriculaSeccionRepBean> obtenerDetSeccionPorGradoAca(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipFor") String tipFor, @Param("nivel") String nivel, @Param("idGradoAcademico") Integer idGradoAcademico, @Param("secc") String secc, @Param("flg") Integer flg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<EstudianteGeneralRepBean> obtenerImpresionPorDeUsuarioMat(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("creaPor") String creaPor, @Param("fechaMatricula") Date fechaMatricula) throws Exception;

    public List<MatriculaBean> obtenerTablaParaExcel(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("matricula") Integer matricula) throws Exception;

    public List<ListaAlumnosRepBean> obtenerListaEstudiantesMatr(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("flg") Integer flg, @Param("idGrado") Integer idGrado, @Param("idNivel") Integer idNivel, @Param("seccion") String seccion) throws Exception;

    public List<ListaAlumnosRepBean> obtenerListaEstudiantesMatrDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("flg") Integer flg, @Param("idGrado") Integer idGrado, @Param("idNivel") Integer idNivel, @Param("seccion") String seccion) throws Exception;

    //METODO NUEVO
    public List<EstudianteGeneralRepBean> obtenerImpresionPorDeUsuarioMatPorGrado(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("creaPor") String creaPor, @Param("fechaMatriculaInicio") Date fechaMatriculaInicio, @Param("fechaMatriculaFin") Date fechaMatriculaFin, @Param("matriculaBean") MatriculaBean matriculaBean) throws Exception;

    public List<MatriculaBean> obtenerSeccionPorGrado(
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoSeccion(MatriculaBean matriculaBean) throws Exception;

    public void modificarMatriculaSeccion(MatriculaBean matriculaBean) throws Exception;

    public List<AlumnosIngresantesRetiradosRepBean> obtenerCabeceraIngresantesRetiradosTotal(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaInicioClases") Date fechaInicioClases, @Param("fechaFinFiltro") Date fechaFinFiltro) throws Exception;

    public List<AlumnosIngresantesRetiradosRepBean> obtenerNivelesIngresantesRetiradosTotal(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaInicioClases") Date fechaInicioClases, @Param("fechaFinFiltro") Date fechaFinFiltro) throws Exception;

    public List<AlumnosIngresantesRetiradosRepBean> obtenerDetalleIngresantesRetiradosTotal(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaInicioClases") Date fechaInicioClases, @Param("fechaFinFiltro") Date fechaFinFiltro, @Param("idNivel") Integer idNivel) throws Exception;

    public List<AlumnosIngresantesRetiradosRepBean> obtenerSubDetalleIngresantesRetiradosTotal(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaInicioClases") Date fechaInicioClases, @Param("fechaFinFiltro") Date fechaFinFiltro, @Param("idGrado") Integer idGrado) throws Exception;

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaPrimera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaSegunda(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaTercera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

}
