package pe.marista.sigma.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.dao.EntidadDAO;

/**
 *
 * @author Administrador
 */
public class EntidadService {

    private EntidadDAO entidadDAO;

    //Logica de Negocio
    @Transactional
    public void insertarEntidad(EntidadBean entidadBean) throws Exception {
        entidadDAO.insertarEntidad(entidadBean);
    }

    @Transactional
    public void modificarEntidad(EntidadBean entidadBean) throws Exception {
        entidadDAO.modificarEntidad(entidadBean);
    }

    @Transactional
    public void eliminarEntidad(String ruc) throws Exception {
        entidadDAO.eliminarEntidad(ruc);
    }

    public List<EntidadBean> obtenerEntidadPorUniNeg(String uniNeg) throws Exception {
        return entidadDAO.obtenerEntidadPorUniNeg(uniNeg);
    }

    public List<EntidadBean> obtenerEntidadPorFiltroProveedor(EntidadBean entidadBean) throws Exception {
        return entidadDAO.obtenerEntidadPorFiltroProveedor(entidadBean);
    }

    public List<EntidadBean> obtenerEntidadPorFiltro(EntidadBean entidadBean) throws Exception {
        return entidadDAO.obtenerEntidadPorFiltro(entidadBean);
    }

    public EntidadBean obtenerEntidadPorId(EntidadBean entidadBean) throws Exception {
        return entidadDAO.obtenerEntidadPorId(entidadBean);
    }

    public List<EntidadBean> obtenerEntidadPorRubro(CodigoBean codigoBean) throws Exception {
        return entidadDAO.obtenerEntidadPorRubro(codigoBean);
    }

    public List<EntidadBean> obtenerTodosSeguro() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("SalAsiSoc", MaristaConstantes.TIP_RUBRO_AFP);
        parms.put("FinSeg", MaristaConstantes.TIP_RUBRO_EPS);
        return entidadDAO.obtenerTodosSeguro(parms);
    }

    public List<ViewEntidadBean> obtenerEntidadPorVista(String entidadVista) throws Exception {
        return entidadDAO.obtenerEntidadPorVista(entidadVista);
    }

    public List<ViewEntidadBean> obtenerEntidadPorVistaLegajoNew(String value, Integer idTipo) throws Exception {
        return entidadDAO.obtenerEntidadPorVistaLegajoNew(value, idTipo);
    }

    public List<ViewEntidadBean> obtenerEntidadPorVistaPorUniNeg(String value, String uniNeg) throws Exception {
        return entidadDAO.obtenerEntidadPorVistaPorUniNeg(value, uniNeg);
    }

    public EntidadBean obtenerInfoEntidad(String uniNeg) throws Exception {
        return entidadDAO.obtenerInfoEntidad(uniNeg);
    }

    //EntidadSede
//    @Transactional
//    public void insertarEntidadSede(EntidadSedeBean entidadSedeBean) throws Exception {
//        entidadDAO.insertarEntidadSede(entidadSedeBean);
//    }
//    @Transactional
//    public void modificarEntidadSede(EntidadSedeBean entidadSedeBean) throws Exception {
//        entidadDAO.modificarEntidadSede(entidadSedeBean);
//    }
//    @Transactional
//    public void eliminarEntidadSede(Integer idEntidadSede) throws Exception {
//        entidadDAO.eliminarEntidadSede(idEntidadSede);    
//    }
//    public EntidadSedeBean obtenerEntidadSedePorId(EntidadSedeBean entidadSedeBean) throws Exception {
//        return entidadDAO.obtenerEntidadSedePorId(entidadSedeBean);
//    }
//    public List<EntidadSedeBean> obtenerEntidadSedePorEntidad(Integer idEntidad) throws Exception {
//        return entidadDAO.obtenerEntidadSedePorEntidad(idEntidad);
//    }
//    public List<EntidadSedeBean> obtenerProveedorPorUniNeg(String unineg) throws Exception {
//        return entidadDAO.obtenerProveedorPorUniNeg(unineg);
//    }
//    public List<EntidadSedeBean> obtenerEntidadSede() throws Exception {
//        return entidadDAO.obtenerEntidadSede();
//    }
    public List<EntidadBean> obtenerProveedorPorUniNeg(String unineg) throws Exception {
        return entidadDAO.obtenerProveedorPorUniNeg(unineg);
    }

    public List<EntidadBean> obtenerFlgFinanciero(String uniNeg) throws Exception {
        return entidadDAO.obtenerFlgFinanciero(uniNeg);
    }

    public EntidadBean obtenerEntidadPorIdCot(String ruc, String uniNeg) throws Exception {
        return entidadDAO.obtenerEntidadPorIdCot(ruc, uniNeg);
    }

    public String obtenerInfoCta(String ruc, String uniNeg, String moneda) throws Exception {
        return entidadDAO.obtenerInfoCta(ruc, uniNeg, moneda);
    }

    //Getter y Setter
    public EntidadDAO getEntidadDAO() {
        return entidadDAO;
    }

    public void setEntidadDAO(EntidadDAO entidadDAO) {
        this.entidadDAO = entidadDAO;
    }

    public EntidadBean buscarPorId(String ruc) throws Exception {
        return entidadDAO.buscarPorId(ruc);
    }

}
