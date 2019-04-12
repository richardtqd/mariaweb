package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalContratoBean;
import pe.marista.sigma.bean.PersonalDesvinculacionBean;
import pe.marista.sigma.bean.PersonalInformacionBancariaBean;
import pe.marista.sigma.bean.PersonalVacacionesBean;
import pe.marista.sigma.bean.reporte.PersonalContratoRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalContratoDAO {
  
//    public List<PersonalContratoBean> obtenerPersonalVacacionesPorPersonal(PersonalBean personalBean) throws Exception;

    public List<PersonalContratoBean> obtenerPersonalContratoPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalContratoBean obtenerPersonalContratoPorId(PersonalContratoBean personalContratoBean) throws Exception;

    public void insertarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception;

    public void modificarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception;

    public void eliminarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception;

    public List<PersonalContratoRepBean> obtenerContratoPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonalContratoRepBean> obtenerVacacionesPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    //PersonalInformacionBancaria 
    public PersonalInformacionBancariaBean obtenerPersonalBancariaPorPersonal(PersonalBean personalBean) throws Exception;

    public void insertarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception;

    public void modificarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception;

    public void eliminarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception;

    //PersonalDesvinculacion
    public List<PersonalDesvinculacionBean> obtenerPersonalDesvinculacionPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalDesvinculacionBean obtenerPersonalDesvinculacionPorId(PersonalDesvinculacionBean desvinculacionBean) throws Exception;

    public void insertarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception;

    public void modificarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception;

    public void eliminarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception;

    public String obtenerPersonalHorasTrabajadas(@Param("horaEntrada") String horaEntrada, @Param("horaSalida") String horaSalida) throws Exception;

    public String obtenerPersonalTotalHorasTrabajadas(@Param("idPersonal") Integer idPersonal, @Param("idPersonalContrato") Integer idPersonalContrato) throws Exception;

    public String obtenerPersonalTiempoContrato(@Param("idPersonal") Integer idPersonal, @Param("periodo") Integer periodo) throws Exception;
    
    //personal vacaciones 
    public List<PersonalVacacionesBean> obtenerPersonalVacacionesPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalVacacionesBean obtenerPersonalVacacionesPorId(PersonalVacacionesBean personalVacacionesBean) throws Exception;

    public void insertarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception;

    public void modificarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception;

    public void eliminarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception;
}
