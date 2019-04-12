
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ObjOperativoBean;
import pe.marista.sigma.dao.ObjetivoOperativoDAO;


public class ObjetivoOperativoService
{
    
    private ObjetivoOperativoDAO objetivoOperativoDAO;

    public void insertarObjetivoOperativo(ObjOperativoBean objOperativoBean) throws Exception {
        objetivoOperativoDAO.insertarObjetivoOperativo(objOperativoBean);
    }

    public void modificarObjetivoOperativo(ObjOperativoBean objOperativoBean) throws Exception {
        objetivoOperativoDAO.modificarObjetivoOperativo(objOperativoBean);
    }

    public void eliminarObjetivoOperativo(Integer idObjOperativo) throws Exception {
        objetivoOperativoDAO.eliminarObjetivoOperativo(idObjOperativo);
    }

    public ObjOperativoBean obtenerPorId(Integer idObjOperativo) throws Exception {
        return objetivoOperativoDAO.obtenerPorId(idObjOperativo);
    }

    public Integer obtenerUltimoCodigo(String uniNeg) throws Exception {
        return objetivoOperativoDAO.obtenerUltimoCodigo(uniNeg);
    }

    public Integer obtenerMaxIdObjOperativo(String uniNeg) throws Exception {
        return objetivoOperativoDAO.obtenerMaxIdObjOperativo(uniNeg);
    }

    public List<ObjOperativoBean> obtenerPorFiltroActividad(ObjOperativoBean objOperativoBean) throws Exception {
        return objetivoOperativoDAO.obtenerPorFiltroActividad(objOperativoBean);
    }

    public List<ObjOperativoBean> obtenerPorMaxId(Integer idObjOperativo, String uniNeg) throws Exception {
        return objetivoOperativoDAO.obtenerPorMaxId(idObjOperativo, uniNeg);
    }
    
    public List<ObjOperativoBean> obtenerActividad(CodigoBean codigoBean) throws Exception {
        return objetivoOperativoDAO.obtenerActividad(codigoBean);
    }

    public List<ObjOperativoBean> obtenerUniMed(CodigoBean codigoBean) throws Exception {
        return objetivoOperativoDAO.obtenerUniMed(codigoBean);
    }
    
    //Metodos Lógica de Negocio
    public List<ObjOperativoBean> obtenerTodos() throws Exception{    
        return objetivoOperativoDAO.obtenerTodos();
    }
    
    public List<ObjOperativoBean> obtenerPorPlanOpe(Integer idObjOperativo) throws Exception{    
        return objetivoOperativoDAO.obtenerPorPlanOpe(idObjOperativo);  
    }

    
    public List<ObjOperativoBean> obtenerPorPlanOperativo(Integer idUniOrg, String uniNeg) throws Exception {
        return objetivoOperativoDAO.obtenerPorPlanOperativo(idUniOrg, uniNeg);
    }
    
    public ObjOperativoBean obtenerPorIdObj(Integer idObjOperativo) throws Exception{    
        return objetivoOperativoDAO.obtenerPorId(idObjOperativo);
    }

    public ObjOperativoBean obtenerPorObjDet(Integer idObjEstrategicoDet) throws Exception {
        return objetivoOperativoDAO.obtenerPorObjDet(idObjEstrategicoDet);
    }
 
    public ObjetivoOperativoDAO getObjetivoOperativoDAO() {
        return objetivoOperativoDAO;
    }

    public void setObjetivoOperativoDAO(ObjetivoOperativoDAO objetivoOperativoDAO) {
        this.objetivoOperativoDAO = objetivoOperativoDAO;
    }
    
}
