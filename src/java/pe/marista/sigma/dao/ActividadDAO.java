package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ActividadBean;

public interface ActividadDAO {

    public List<ActividadBean> obtenerTodos(String uniNeg) throws Exception;

    public List<ActividadBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception;

    public List<ActividadBean> obtenerPorUnidadNegocioUniOrg(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ActividadBean> obtenerPorObjOpe(Integer idobjoperativo) throws Exception;

    public ActividadBean obtenerPorId(Integer idActividad) throws Exception;

    public List<ActividadBean> obtenerPorPlanOperativo(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerMaxId(String uniNeg) throws Exception;

    public String obtenerActExec(@Param("idActividad") Integer idActividad, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ActividadBean> obtenerListaPorId(@Param("idActividad") Integer idActividad, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ActividadBean> obtenerPorUnidadOrganica(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public ActividadBean obtenerPorObjOperativo(@Param("idObjOperativo") Integer idObjOperativo) throws Exception;

    public void insertarActividad(ActividadBean actividadBean) throws Exception;

    public void actualizarActividad(ActividadBean actividadBean) throws Exception;

    public void eliminarActividad(Integer idActividad) throws Exception;

    //OBTENER ACTIVIDAD OBJ POR ID
    public ActividadBean obtenerObjPorId(ActividadBean actividadBean) throws Exception;

    //OBTENER MONTO DE ACTIVIDAD
    public BigDecimal obtenerImporteActividad(ActividadBean actividadBean) throws Exception;

    //OBTENER POR ANIO
    public List<ActividadBean> obtenerObjPorAnio(ActividadBean actividadBean) throws Exception;

}
