package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CheckListBean;
import pe.marista.sigma.bean.ProcesoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.dao.ProcesoDAO;

/**
 *
 * @author Administrador
 */
public class ProcesoService {

    private ProcesoDAO procesoDAO;

    //Lógica de Negocio
    public List<ProcesoBean> obtenerProceso() throws Exception {
        return procesoDAO.obtenerProceso();
    }

    public List<ProcesoBean> obtenerProcesoPorUniNeg(UnidadNegocioBean unidadNegocioBean) throws Exception {
        return procesoDAO.obtenerProcesoPorUniNeg(unidadNegocioBean);
    }

    public List<ProcesoBean> obtenerProcesoActivos(UnidadNegocioBean unidadNegocioBean) throws Exception {
        return procesoDAO.obtenerProcesoActivos(unidadNegocioBean);
    }

    public ProcesoBean obtenerProcPorId(ProcesoBean procesoBean) throws Exception {
        return procesoDAO.obtenerProcPorId(procesoBean);
    }

    @Transactional
    public void insertarProceso(ProcesoBean procesoBean) throws Exception {
        procesoDAO.insertarProceso(procesoBean);
    }

    @Transactional
    public void modificarProceso(ProcesoBean procesoBean) throws Exception {
        procesoDAO.modificarProceso(procesoBean);
    }

    @Transactional
    public void eliminarProceso(ProcesoBean procesoBean) throws Exception {
        procesoDAO.eliminarProceso(procesoBean);
    }

    @Transactional
    public void cambiarEstado(ProcesoBean procesoBean) throws Exception {
        procesoDAO.cambiarEstado(procesoBean);
    }

    
    //CheckList
    @Transactional
    public void insertarCheckList(CheckListBean checkListBean) throws Exception {
        procesoDAO.insertarCheckList(checkListBean);
    }
    @Transactional
    public void modificarCheckList(CheckListBean checkListBean) throws Exception {
        procesoDAO.modificarCheckList(checkListBean);
    }
    @Transactional
    public void eliminarCheckList(CheckListBean checkListBean) throws Exception {
        procesoDAO.eliminarCheckList(checkListBean);
    }
    public List<CheckListBean> obtenerCheckListPorUniNeg(String uniNeg) throws Exception {
         return procesoDAO.obtenerCheckListPorUniNeg(uniNeg);
    }
    public List<CheckListBean> obtenerCheckListPorProceso(ProcesoBean procesoBean) throws Exception {
         return procesoDAO.obtenerCheckListPorProceso(procesoBean);
    }
    public CheckListBean obtenerCheckListPorId(CheckListBean checkListBean) throws Exception {
         return procesoDAO.obtenerCheckListPorId(checkListBean);
    }    
    
    //GEtter y Setter
    public ProcesoDAO getProcesoDAO() {
        return procesoDAO;
    }
    public void setProcesoDAO(ProcesoDAO procesoDAO) {
        this.procesoDAO = procesoDAO;
    }

}
