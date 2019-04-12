package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.dao.TipoConceptoDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class TipoConceptoService {

    private TipoConceptoDAO tipoConceptoDAO;

    //Metodos Lógica de Negocio
    @Transactional
    public void insertarTipoConcepto(TipoConceptoBean tipoConceptoBean) throws Exception {
        tipoConceptoDAO.insertarTipoConcepto(tipoConceptoBean);
    }

    @Transactional
    public void modificarTipoConcepto(TipoConceptoBean tipoConceptoBean) throws Exception {
        tipoConceptoDAO.modificarTipoConcepto(tipoConceptoBean);
    }

    @Transactional
    public void eliminarTipoConcepto(Integer idTipoConcepto) throws Exception {
        tipoConceptoDAO.eliminarTipoConcepto(idTipoConcepto);
    }

    public List<TipoConceptoBean> obtenerTipoConcepto() throws Exception {
        return tipoConceptoDAO.obtenerTipoConcepto();
    }

    public TipoConceptoBean obtenerTipoConceptoPorId(Integer idTipoConcepto) throws Exception {
        return tipoConceptoDAO.obtenerTipoConceptoPorId(idTipoConcepto);
    }

    public List<TipoConceptoBean> obtenerPorTipoProcesoBanco() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("matricula", MaristaConstantes.TIP_CON_MAT);
        parms.put("pension", MaristaConstantes.TIP_CON_PEN);
        return tipoConceptoDAO.obtenerPorTipoProcesoBanco(parms);
    }

    public List<TipoConceptoBean> obtenerTipoConceptoCurso() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("matricula", MaristaConstantes.TIP_CON_MAT);
        parms.put("pension", MaristaConstantes.TIP_CON_PEN);
        parms.put("curso", MaristaConstantes.TIP_CON_CUR);
        return tipoConceptoDAO.obtenerPorTipoProcesoBancoCur(parms);
    }

    public List<TipoConceptoBean> obtenerPorTipoProcesoBancoCur(Map<String, Object> parms) throws Exception {

        return tipoConceptoDAO.obtenerPorTipoProcesoBancoCur(parms);
    }

    public TreeNode mappearTiposConceptos(List<TipoConceptoBean> listaTipoConceptoBean, String uniNeg) throws Exception {
//        TreeNode treeNode = new DefaultTreeNode(MensajesBackEnd.getValueOfKey("appSiglas", null), null);
        TreeNode root = new CheckboxTreeNode(MensajesBackEnd.getValueOfKey("appSiglas", null), null);

        for (TipoConceptoBean listaTipoConcepto : listaTipoConceptoBean) {
            if (listaTipoConcepto.getIdConceptoVista() == null) {
                TreeNode treeNodeChild = new CheckboxTreeNode(listaTipoConcepto, root);
//                mappearConceptos(listaTipoConcepto.getIdTipoConceptoVista(), treeNodeChild, uniNeg);
                treeNodeChild.setExpanded(false);

                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                List<ConceptoUniNegBean> listaConcepto = new ArrayList<>();
                listaConcepto = conceptoUniNegService.obtenerConceptoUniNegPorTip(listaTipoConcepto.getIdTipoConceptoVista(), uniNeg);

                for (ConceptoUniNegBean listaConcepto1 : listaConcepto) {
                    if (listaConcepto1.getConceptoBean().getTipoConceptoBean().getIdConceptoVista() != null) {
                        if (listaTipoConcepto.getIdTipoConceptoVista().toString().equals(listaConcepto1.getConceptoBean().getTipoConceptoBean().getIdTipoConceptoVista().toString())) {
                            TreeNode treeNodeChild2 = new CheckboxTreeNode(listaConcepto1, treeNodeChild);

                        }
                    }
                }

            }
        }
        return root;

    }

//    public TreeNode mappearConceptos(Integer idTipoConcepto, TreeNode treeNode, String uniNeg) throws Exception {
//        ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
//        List<ConceptoUniNegBean> listaConcepto = new ArrayList<>();
//        listaConcepto = conceptoUniNegService.obtenerConceptoUniNegPorTip(idTipoConcepto, uniNeg);
//
//        for (ConceptoUniNegBean listaConcepto1 : listaConcepto) {
//            if (listaConcepto1.getConceptoBean().getTipoConceptoBean().getIdConceptoVista() != null) {
//                if (idTipoConcepto.toString().equals(listaConcepto1.getConceptoBean().getTipoConceptoBean().getIdTipoConceptoVista().toString())) {
//                    TreeNode treeNodeChild = new CheckboxTreeNode(listaConcepto1, treeNode);
////                    treeNodeChild.setExpanded(true); 
////                    mappearConceptos(listaConcepto1.getIdTipoConceptoVista(), treeNodeChild, uniNeg);
//                }
//            }
//        }
//
//        if (treeNode.getChildren().isEmpty()) {
//            return null;
//        }
//        return treeNode;
//    }
    public List<TipoConceptoBean> obtenerTipoConceptoIngreso() throws Exception {
        return tipoConceptoDAO.obtenerTipoConceptoIngreso();
    }

    public List<TipoConceptoBean> obtenerTipoConceptoSalida() throws Exception {
        return tipoConceptoDAO.obtenerTipoConceptoSalida();
    }

    public List<TipoConceptoBean> obtenerPorTipoCronograma(Integer idTipoConcepto1, Integer idTipoConcepto2) throws Exception {
        return tipoConceptoDAO.obtenerPorTipoCronograma(idTipoConcepto1, idTipoConcepto2);
    }

    public List<TipoConceptoBean> obtenerListaTipoConceptoPorId(Integer idTipoConcepto) throws Exception {
        return tipoConceptoDAO.obtenerListaTipoConceptoPorId(idTipoConcepto);
    }

    //Getter and SEtter
    public TipoConceptoDAO getTipoConceptoDAO() {
        return tipoConceptoDAO;
    }

    public void setTipoConceptoDAO(TipoConceptoDAO tipoConceptoDAO) {
        this.tipoConceptoDAO = tipoConceptoDAO;
    }

    public List<TipoConceptoBean> obtenerConceptosTree(String uniNeg) throws Exception {
        return tipoConceptoDAO.obtenerConceptosTree(uniNeg);
    }
}
