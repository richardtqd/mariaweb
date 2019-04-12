package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalContratoBean;
import pe.marista.sigma.bean.PersonalDesvinculacionBean;
import pe.marista.sigma.bean.PersonalInformacionBancariaBean;
import pe.marista.sigma.bean.PersonalVacacionesBean;
import pe.marista.sigma.bean.reporte.PersonalContratoRepBean;
import pe.marista.sigma.dao.PersonalContratoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS-005
 */
public class PersonalContratoService {

    private PersonalContratoDAO personalContratoDAO;

    public List<PersonalContratoBean> obtenerPersonalContratoPorPersonal(PersonalBean personalBean) throws Exception {
        CodigoService codigoService = BeanFactory.getCodigoService();
        CodigoBean cod = new CodigoBean();
        cod.setCodigo(MaristaConstantes.COD_VACACIONES);
        cod.getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_SITUACION_PER); //CAMBIO
        cod = codigoService.obtenerPorCodigo(cod);
        personalBean.setIdTipoVacaciones(cod.getIdCodigo());
        return personalContratoDAO.obtenerPersonalContratoPorPersonal(personalBean);
    }

//    public List<PersonalContratoBean> obtenerPersonalVacacionesPorPersonal(PersonalBean personalBean) throws Exception {
//        CodigoService codigoService = BeanFactory.getCodigoService();
//        CodigoBean cod = new CodigoBean();
//        cod.setCodigo(MaristaConstantes.COD_VACACIONES);
//        cod.getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_SITUACION_PER); //CAMBIO
//        cod = codigoService.obtenerPorCodigo(cod);
//        personalBean.setIdTipoVacaciones(cod.getIdCodigo());
//        return personalContratoDAO.obtenerPersonalVacacionesPorPersonal(personalBean);
//    }
    public List<PersonalContratoRepBean> obtenerContratoPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
           return personalContratoDAO.obtenerContratoPorPersonalRep(idPersonal,uniNeg);
    }
    public List<PersonalContratoRepBean> obtenerVacacionesPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
           return personalContratoDAO.obtenerVacacionesPorPersonalRep(idPersonal,uniNeg);
    }

    public PersonalContratoBean obtenerPersonalContratoPorId(PersonalContratoBean personalContratoBean) throws Exception {
        return personalContratoDAO.obtenerPersonalContratoPorId(personalContratoBean);
    }

    //Logica de Negocio
    @Transactional
    public void insertarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception {
        if (personalContratoBean.getTipoContratoBean().getIdCodigo() == null) {
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod.setCodigo(MaristaConstantes.COD_VACACIONES);
            cod.getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_SITUACION_PER); //CAMBIO
            cod = codigoService.obtenerPorCodigo(cod);
            personalContratoBean.getTipoContratoBean().setIdCodigo(cod.getIdCodigo());
        }
        personalContratoDAO.insertarPersonalContrato(personalContratoBean);
    }

    @Transactional
    public void modificarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception {
        personalContratoDAO.modificarPersonalContrato(personalContratoBean);
    }

    @Transactional
    public void eliminarPersonalContrato(PersonalContratoBean personalContratoBean) throws Exception {
        personalContratoDAO.eliminarPersonalContrato(personalContratoBean);
    }

    // metodos getter y setter
    public PersonalContratoDAO getPersonalContratoDAO() {
        return personalContratoDAO;
    }

    public void setPersonalContratoDAO(PersonalContratoDAO personalContratoDAO) {
        this.personalContratoDAO = personalContratoDAO;
    } 

    public void insertarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception {
        personalContratoDAO.insertarPersonalBancaria(informacionBancariaBean);
    }

    public PersonalInformacionBancariaBean obtenerPersonalBancariaPorPersonal(PersonalBean personalBean) throws Exception {
        return personalContratoDAO.obtenerPersonalBancariaPorPersonal(personalBean);
    }

    public void modificarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception {
        personalContratoDAO.modificarPersonalBancaria(informacionBancariaBean);
    }

    public void eliminarPersonalBancaria(PersonalInformacionBancariaBean informacionBancariaBean) throws Exception {
        personalContratoDAO.eliminarPersonalBancaria(informacionBancariaBean);
    }

    public List<PersonalDesvinculacionBean> obtenerPersonalDesvinculacionPorPersonal(PersonalBean personalBean) throws Exception {
        return personalContratoDAO.obtenerPersonalDesvinculacionPorPersonal(personalBean);
    }

    public PersonalDesvinculacionBean obtenerPersonalDesvinculacionPorId(PersonalDesvinculacionBean desvinculacionBean) throws Exception {
        return personalContratoDAO.obtenerPersonalDesvinculacionPorId(desvinculacionBean);
    }

    public void insertarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception {
        personalContratoDAO.insertarPersonalDesvinculacion(desvinculacionBean);
    }

    public void modificarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception {
        personalContratoDAO.modificarPersonalDesvinculacion(desvinculacionBean);
    }

    public void eliminarPersonalDesvinculacion(PersonalDesvinculacionBean desvinculacionBean) throws Exception {
        personalContratoDAO.eliminarPersonalDesvinculacion(desvinculacionBean);
    }

    public String obtenerPersonalHorasTrabajadas(String horaEntrada, String horaSalida) throws Exception {
        return personalContratoDAO.obtenerPersonalHorasTrabajadas(horaEntrada, horaSalida);
    }

    public String obtenerPersonalTotalHorasTrabajadas(Integer idPersonal,Integer idPersonalContrato) throws Exception {
        return personalContratoDAO.obtenerPersonalTotalHorasTrabajadas(idPersonal,idPersonalContrato);
    }

    public String obtenerPersonalTiempoContrato(Integer idPersonal, Integer periodo) throws Exception {
        return personalContratoDAO.obtenerPersonalTiempoContrato(idPersonal, periodo);
    }

    public List<PersonalVacacionesBean> obtenerPersonalVacacionesPorPersonal(PersonalBean personalBean) throws Exception {
        return personalContratoDAO.obtenerPersonalVacacionesPorPersonal(personalBean);
    }

    public PersonalVacacionesBean obtenerPersonalVacacionesPorId(PersonalVacacionesBean personalVacacionesBean) throws Exception {
        return personalContratoDAO.obtenerPersonalVacacionesPorId(personalVacacionesBean);
    }

    public void insertarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception {
        personalContratoDAO.insertarPersonalVacaciones(personalVacacionesBean);
    }

    public void modificarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception {
        personalContratoDAO.modificarPersonalVacaciones(personalVacacionesBean);
    }

    public void eliminarPersonalVacaciones(PersonalVacacionesBean personalVacacionesBean) throws Exception {
        personalContratoDAO.eliminarPersonalVacaciones(personalVacacionesBean);
    }

}
