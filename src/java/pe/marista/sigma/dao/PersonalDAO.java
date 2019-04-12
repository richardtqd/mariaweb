package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.reporte.ConceptoPlanillaRepBean;
import pe.marista.sigma.bean.reporte.PersonalRepBean;
import pe.marista.sigma.bean.reporte.PlanillaCuentasCRRepBean;
import pe.marista.sigma.bean.reporte.PlanillaPersonalCRRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalDAO {

    public List<PersonalBean> obtenerTodos() throws Exception;

    public List<PersonalBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception;

    //creacion de BuscarId del Personal para movimientos de activos fijos
    public PersonalBean buscarPorId(Integer idPersonal) throws Exception;
    
    public PersonalBean buscarPorCodPer(String codPer) throws Exception;

    //Para Logistica
    public PersonalBean buscarPorIdPersonalLogistico(@Param("idPersonal") Integer idPersonal, @Param("nombreUniOrg") String nombreUniOrg) throws Exception;

    //Para Cumplea�os
    public List<PersonalBean> obtenerPersonalCumpleanios(@Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonalBean> filtrarPersonal(PersonalBean personalBean) throws Exception;

    public List<Integer> filtrarPersonalConCentros(@Param("uniNeg") String uniNeg) throws Exception;

    public PersonalBean obtenerCentrosPlanilla(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarPlanillaCentros(PersonalBean personalBean) throws Exception;
   
    public void modificarSueldoBasico(PersonalBean personalBean) throws Exception;
    
    public void modificarSueldoBasicoEstatal(PersonalBean personalBean) throws Exception;

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCR(@Param("uniNeg") String uniNeg, @Param("status") Integer status, @Param("idTipoNivelIns") Integer idTipoNivelIns) throws Exception;

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRDetalle(@Param("uniNeg") String uniNeg, @Param("status") Integer status, @Param("idTipoNivelIns") Integer idTipoNivelIns) throws Exception;

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRDetalleSinNada(@Param("uniNeg") String uniNeg, @Param("status") Integer status, @Param("idTipoNivelIns") Integer idTipoNivelIns) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesCabecera(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesDetalle(@Param("uniNeg") String uniNeg, @Param("idNivelIns") Integer idNivelIns, @Param("idObjeto") Integer idObjeto, @Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCuenta(@Param("uniNeg") String uniNeg, @Param("idNivelIns") Integer idNivelIns, @Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesId(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("idNivelIns") Integer idNivelIns, @Param("anio") Integer anio) throws Exception;

    //TIPO PLANILLA CR
    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRCabecera(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("idNivelIns") Integer idNivelIns, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRDetalle(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("idNivelIns") Integer idNivelIns, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRSubDetalle(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("idNivelIns") Integer idNivelIns, @Param("anio") Integer anio, @Param("cr") Integer cr) throws Exception;

    //TRABAJADOR CR
    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRCabecera(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRDetalle(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("anio") Integer anio, @Param("idObjeto") Integer idObjeto) throws Exception;

    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRSubDetalle(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes, @Param("anio") Integer anio, @Param("cr") Integer cr, @Param("idObjeto") Integer idObjeto) throws Exception;

    //Validando DNI
    public String validarDni(@Param("nroDoc") String nroDoc, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonalBean> duplicadosPersonal() throws Exception;

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRTotales(@Param("uniNeg") String uniNeg, @Param("status") Integer status, @Param("idTipoNivelIns") Integer idTipoNivelIns) throws Exception;

    public List<PersonalBean> obtenerPersonalUsuarios(PersonalBean personalBean) throws Exception;
}
