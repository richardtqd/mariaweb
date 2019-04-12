package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CheckListBean;
import pe.marista.sigma.bean.ProcesoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;

/**
 *
 * @author Administrador
 */
public interface ProcesoDAO {

    public void insertarProceso(ProcesoBean procesoBean) throws Exception;
    public void modificarProceso(ProcesoBean procesoBean) throws Exception;
    public void eliminarProceso(ProcesoBean ProcesoBean) throws Exception;
    public void cambiarEstado(ProcesoBean procesoBean) throws Exception;
    public List<ProcesoBean> obtenerProceso() throws Exception;
    public List<ProcesoBean> obtenerProcesoPorUniNeg(UnidadNegocioBean unidadNegocioBean) throws Exception;
    public List<ProcesoBean> obtenerProcesoActivos(UnidadNegocioBean unidadNegocioBean) throws Exception;
    public ProcesoBean obtenerProcPorId(ProcesoBean ProcesoBean) throws Exception;
    
    //checkList
    public void insertarCheckList(CheckListBean checkListBean) throws Exception;
    public void modificarCheckList(CheckListBean checkListBean) throws Exception;
    public void eliminarCheckList(CheckListBean checkListBean) throws Exception;  
    public List<CheckListBean> obtenerCheckListPorUniNeg(String uniNeg) throws Exception; //falta
    public List<CheckListBean> obtenerCheckListPorProceso(ProcesoBean procesoBean) throws Exception;
    public CheckListBean obtenerCheckListPorId(CheckListBean checkListBean) throws Exception;
}
