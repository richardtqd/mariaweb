package pe.marista.sigma.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.dao.ConceptoUniNegDAO;

/**
 *
 * @author Administrador
 */
public class ConceptoUniNegService {

    private ConceptoUniNegDAO conceptoUniNegDAO;

    //Metodos de Logica de negocio
    public ConceptoUniNegBean obtenerConceptoPorId(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoPorId(conceptoUniNegBean);
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNeg() throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNeg();
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorTip(Integer idTipoConcepto, String uniNeg) throws Exception {
        Map<Object, Object> parms = new HashMap<>();
        parms.put("idTipoConcepto", idTipoConcepto);
        parms.put("uniNeg", uniNeg);
        return conceptoUniNegDAO.obtenerConceptoUniNegPorTip(parms);
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorUni(String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegPorUni(uniNeg);
    }
    
    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorUniArendir(String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegPorUniArendir(uniNeg);
    }

    public Integer obtenerTipoPorIdConcepto(Integer idConcepto, String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerTipoPorIdConcepto(idConcepto, uniNeg);
    }

    public Integer obtenerPorIdConceptoMontoCero(Integer idConcepto, String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerPorIdConceptoMontoCero(idConcepto, uniNeg);
    }

    public Integer obtenerTipoPorProgramacion(Integer idConcepto, String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerTipoPorProgramacion(idConcepto, uniNeg);
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegActivos() throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegActivos();
    }

    @Transactional
    public void insertarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        conceptoUniNegDAO.insertarConceptoUniNeg(conceptoUniNegBean);
    }

    @Transactional
    public void modificarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        conceptoUniNegDAO.modificarConceptoUniNeg(conceptoUniNegBean);
    }

    @Transactional
    public void modificarMontoConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        conceptoUniNegDAO.modificarMontoConceptoUniNeg(conceptoUniNegBean);
    }

    @Transactional
    public void eliminarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        conceptoUniNegDAO.eliminarConceptoUniNeg(conceptoUniNegBean);
    }

    @Transactional
    public void cambiarEstadoConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        conceptoUniNegDAO.cambiarEstadoConceptoUniNeg(conceptoUniNegBean);
    } 
    
    public List<ConceptoUniNegBean> obtenerConceptoEstMatri(String uniNeg, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg);
        parm.put("matri", MaristaConstantes.NOM_CAT_MAT);
        parm.put("pensiones", MaristaConstantes.NOM_CAT_PEN);
//        parm.put("exa", MaristaConstantes.NOM_CAT_EXA);
//        parm.put("curVac", MaristaConstantes.NOM_CAT_CUR_VAC);
//        parm.put("derIns", MaristaConstantes.NOM_CAT_DER_INS);
//        parm.put("actCom", MaristaConstantes.NOM_CAT_ACT_COM);
//        parm.put("desc", MaristaConstantes.NOM_CAT_DES_REB_BON);
//        parm.put("otro", MaristaConstantes.NOM_CAT_OTR_ING);
//        parm.put("otroGest", MaristaConstantes.NOM_CAT_OTR_ING_GEST);
//        parm.put("alquileres", MaristaConstantes.TIP_CON_ALQUILERES);
//        parm.put("expe", MaristaConstantes.NOM_CAT_EXPED);
//        parm.put("reclaMN", MaristaConstantes.NOM_CAT_RECLA_MN);
//        parm.put("reclaME", MaristaConstantes.NOM_CAT_RECLA_ME);
//        parm.put("talleres", MaristaConstantes.NOM_CAT_TALLERES);
//        parm.put("alquileres", MaristaConstantes.NOM_CAT_ALQUILERES);
        return conceptoUniNegDAO.obtenerConceptoEstMatri(parm);
    }

    public List<ConceptoUniNegBean> obtenerConceptoInscr(String uniNeg, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg);
        parm.put("matri", MaristaConstantes.NOM_CAT_MAT);
        parm.put("pensiones", MaristaConstantes.NOM_CAT_PEN);
//        parm.put("curVac", MaristaConstantes.NOM_CAT_CUR_VAC);
//        parm.put("derIns", MaristaConstantes.NOM_CAT_DER_INS);
//        parm.put("actCom", MaristaConstantes.NOM_CAT_ACT_COM);
//        parm.put("otro", MaristaConstantes.NOM_CAT_OTR_ING);
//        parm.put("otroGest", MaristaConstantes.NOM_CAT_OTR_ING_GEST);
//        parm.put("alquileres", MaristaConstantes.TIP_CON_ALQUILERES);
//        parm.put("expe", MaristaConstantes.NOM_CAT_EXPED);
//        parm.put("reclaMN", MaristaConstantes.NOM_CAT_RECLA_MN);
//        parm.put("reclaME", MaristaConstantes.NOM_CAT_RECLA_ME);
//        parm.put("talleres", MaristaConstantes.NOM_CAT_TALLERES);
//        parm.put("alquileres", MaristaConstantes.NOM_CAT_ALQUILERES);
        return conceptoUniNegDAO.obtenerConceptoInscr(parm);
    }

    public List<ConceptoUniNegBean> obtenerConceptoPost(String uniNeg, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg);
        parm.put("matri", MaristaConstantes.NOM_CAT_MAT);
        parm.put("pensiones", MaristaConstantes.NOM_CAT_PEN);
//        parm.put("curVac", MaristaConstantes.NOM_CAT_CUR_VAC);
//        parm.put("derIns", MaristaConstantes.NOM_CAT_DER_INS);
//        parm.put("actCom", MaristaConstantes.NOM_CAT_ACT_COM);
//        parm.put("alquileres", MaristaConstantes.TIP_CON_ALQUILERES);
//        parm.put("expe", MaristaConstantes.NOM_CAT_EXPED);
//        parm.put("otro", MaristaConstantes.NOM_CAT_OTR_ING);
//        parm.put("otroGest", MaristaConstantes.NOM_CAT_OTR_ING_GEST);
//        parm.put("reclaMN", MaristaConstantes.NOM_CAT_RECLA_MN);
//        parm.put("reclaME", MaristaConstantes.NOM_CAT_RECLA_ME);
//        parm.put("talleres", MaristaConstantes.NOM_CAT_TALLERES);
//        parm.put("alquileres", MaristaConstantes.NOM_CAT_ALQUILERES);
        return conceptoUniNegDAO.obtenerConceptoPost(parm);
    }

    public List<ConceptoUniNegBean> obtenerConceptoExterno(String uniNeg, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg);
        parm.put("matri", MaristaConstantes.NOM_CAT_MAT);
        parm.put("pensiones", MaristaConstantes.NOM_CAT_PEN);
//        parm.put("exa", MaristaConstantes.NOM_CAT_EXA);
//        parm.put("curVac", MaristaConstantes.NOM_CAT_CUR_VAC);
//        parm.put("derIns", MaristaConstantes.NOM_CAT_DER_INS);
//        parm.put("actCom", MaristaConstantes.NOM_CAT_ACT_COM);
//        parm.put("alquileres", MaristaConstantes.TIP_CON_ALQUILERES);
//        parm.put("otro", MaristaConstantes.NOM_CAT_OTR_ING);
//        parm.put("otroGest", MaristaConstantes.NOM_CAT_OTR_ING_GEST);
//        parm.put("expe", MaristaConstantes.NOM_CAT_EXPED);
//        parm.put("reclaMN", MaristaConstantes.NOM_CAT_RECLA_MN);
//        parm.put("reclaME", MaristaConstantes.NOM_CAT_RECLA_ME);
//        parm.put("talleres", MaristaConstantes.NOM_CAT_TALLERES);
//        parm.put("alquileres", MaristaConstantes.NOM_CAT_ALQUILERES);
        return conceptoUniNegDAO.obtenerConceptoExterno(parm);
    }
    public List<ConceptoUniNegBean> obtenerConceptoConProgramacion(String uniNeg, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg); 
        return conceptoUniNegDAO.obtenerConceptoConProgramacion(parm);
    }

    public List<ConceptoUniNegBean> obtenerConceptoConProgramacionPorFiltro(String uniNeg, Object dscto,String nombreConcepto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", uniNeg); 
        parm.put("nombreConcepto", nombreConcepto);
        return conceptoUniNegDAO.obtenerConceptoConProgramacionPorFiltro(parm);
    }

    public ConceptoUniNegBean obtenerConceptoPorIdConDscto(ConceptoUniNegBean conceptoUniNegBean, Object dscto) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("dscto", dscto);
        parm.put("uniNeg", conceptoUniNegBean.getUnidadNegocioBean().getUniNeg());
        parm.put("idConcepto", conceptoUniNegBean.getConceptoBean().getIdConcepto());
        return conceptoUniNegDAO.obtenerConceptoPorIdConDscto(parm);
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorIng(String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegPorIng(uniNeg);
    }

    //Getter y Setter
    public ConceptoUniNegDAO getConceptoUniNegDAO() {
        return conceptoUniNegDAO;
    }

    public void setConceptoUniNegDAO(ConceptoUniNegDAO conceptoUniNegDAO) {
        this.conceptoUniNegDAO = conceptoUniNegDAO;
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorEgr(String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegPorEgr(uniNeg);
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegActivosIngresos() throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegActivosIngresos();
    }

    public List<ConceptoUniNegBean> obtenerConceptoUniNegCuotaIng(String uniNeg) throws Exception {
        return conceptoUniNegDAO.obtenerConceptoUniNegCuotaIng(uniNeg);
    } 
    
    public ConceptoUniNegBean obtenerConceptoPorIdConCuotaIng(ConceptoUniNegBean conceptoUniNegBean) throws Exception {
        Map<String, Object> parm = new HashMap<>();
        parm.put("uniNeg", conceptoUniNegBean.getUnidadNegocioBean().getUniNeg());
        parm.put("idConcepto", conceptoUniNegBean.getConceptoBean().getIdConcepto());
        return conceptoUniNegDAO.obtenerConceptoPorIdConCuotaIng(parm);
    }
    
}
