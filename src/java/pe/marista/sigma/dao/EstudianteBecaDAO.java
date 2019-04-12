/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBecaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.reporte.EstudianteBecaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteDetalleBecaRepBean;

/**
 *
 * @author MS002
 */
public interface EstudianteBecaDAO {
    
    public List<EstudianteBecaBean> obtenerTodosEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public List<EstudianteBecaBean> obtenerTodosBecaPorEstudiante(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public List<EstudianteBecaBean> obtenerTodosBecaPorEstudianteActivo(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public EstudianteBecaBean buscarPorIdEstudiantePeriodo(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public EstudianteBecaBean buscarPorIdEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public List<EstudianteBecaBean> buscarPorIdEstudianteBecaAnio(EstudianteBecaBean estudianteBecaBean) throws Exception;
    public List<EstudianteBecaBean> buscarBecadosAnio(EstudianteBecaBean estudianteBecaBean) throws Exception;

    public void insertarEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception;

    public void actualizarEstudianteBeca(EstudianteBecaBean EstudianteBecaBean) throws Exception;
    
    public void actualizarEstudianteBecaEstado(EstudianteBecaBean EstudianteBecaBean) throws Exception;
    
    public void actualizarEstudianteBecaEstadoOff(EstudianteBecaBean EstudianteBecaBean) throws Exception;

    public void eliminarEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception;
    
    public List<CronogramaPagoBean> obtenerCronograma() throws Exception;
    
    public List<EstudianteBean> obtenerMatriculadosPorPeriodo(MatriculaBean matriculaBean) throws Exception;
    
    public List<EstudianteBean> obtenerFiltroEstudianteMatriculado(MatriculaBean matriculaBean) throws Exception;
    
    public void actualizarCtaCte(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;
    
    public void actualizarCtaCteBecaTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;
    
    public void actualizarCtaCtecambioBecaTo(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;
    
    public void actualizarCtaCteMesDespues(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;
    
    public List<EstudianteBecaRepBean> obetenerTitulo(@Param("uniNeg") String uniNeg,@Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;
    
    public List<EstudianteBecaRepBean> obetenerNombreBeca(@Param("uniNeg") String uniNeg,@Param("mes") Integer mes,@Param("anio") Integer anio) throws Exception;
    
    public List<EstudianteBecaRepBean> obetenerNombreAlumno(@Param("uniNeg") String uniNeg,@Param("mes") Integer mes, @Param("anio") Integer anio,@Param("nombreBeca") String nombreBeca) throws Exception;
}
