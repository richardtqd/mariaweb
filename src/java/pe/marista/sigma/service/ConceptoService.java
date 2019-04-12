

package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.ConceptoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class ConceptoService {
    private ConceptoDAO conceptoDAO;
    //Logica de Negocio
    @Transactional
    public void insertarConcepto(ConceptoBean conceptoBean) throws Exception{
        conceptoDAO.insertarConcepto(conceptoBean);  
    }
    @Transactional
    public void insertarConceptoUniNeg(ConceptoBean conceptoBean,UsuarioBean usuario) throws Exception{
        conceptoDAO.insertarConcepto(conceptoBean);
        ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
        ConceptoUniNegBean conceptoUniNegBean =  new ConceptoUniNegBean();
        conceptoUniNegBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
        conceptoUniNegBean.setConceptoBean(conceptoBean);
        conceptoUniNegBean.setImporte(new BigDecimal(0.0));
        conceptoUniNegBean.setStatus(Boolean.FALSE);
        conceptoUniNegBean.setCreaPor(usuario.getUsuario()); 
        conceptoUniNegService.insertarConceptoUniNeg(conceptoUniNegBean);
    }
    @Transactional
    public void eliminarConcepto(ConceptoBean conceptoBean,UsuarioBean beanUsuarioSesion) throws Exception{
        conceptoDAO.eliminarConcepto(conceptoBean);
        ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
        ConceptoUniNegBean conceptoUniNegBean =  new ConceptoUniNegBean();
        conceptoUniNegBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
        conceptoUniNegBean.setConceptoBean(conceptoBean); 
        conceptoUniNegService.eliminarConceptoUniNeg(conceptoUniNegBean);
    }
    @Transactional
    public void eliminarConcepto(ConceptoBean conceptoBean) throws Exception{
        conceptoDAO.eliminarConcepto(conceptoBean);
    }
    @Transactional
    public void cambiarEstado(ConceptoBean conceptoBean) throws Exception{
        conceptoDAO.cambiarEstado(conceptoBean);
    }
    @Transactional
    public void modificarConcepto(ConceptoBean conceptoBean) throws Exception{
        conceptoDAO.modificarConcepto(conceptoBean);
    }
    @Transactional
    public void eliminarPorTipo(TipoConceptoBean tipoConceptoBean) throws Exception{
        conceptoDAO.eliminarPorTipo(tipoConceptoBean);
    }
    public List<ConceptoBean> obtenerPorTipo(TipoConceptoBean tipoConceptoBean) throws Exception{
       return conceptoDAO.obtenerPorTipo(tipoConceptoBean);
    }
    public ConceptoBean obtenerConceptoPorId(ConceptoBean conceptoBean) throws Exception{
       return conceptoDAO.obtenerConceptoPorId(conceptoBean);
    }
    public ConceptoBean obtenerConceptoCuentasPorId(ConceptoBean conceptoBean) throws Exception{
       return conceptoDAO.obtenerConceptoCuentasPorId(conceptoBean);
    }
    public List<ConceptoBean> obtenerConcepto() throws Exception{
       return conceptoDAO.obtenerConcepto();
    }
    public List<ConceptoBean> obtenerTodosEgresos() throws Exception{
       return conceptoDAO.obtenerTodosEgresos();
    }
    public List<ConceptoBean> obtenerConceptoNotIn(String uniNeg) throws Exception{
       return conceptoDAO.obtenerConceptoNotIn(uniNeg);
    }

    public Integer obtenerGradoAcaPorIdConcepto(Integer idConcepto) throws Exception {
        return conceptoDAO.obtenerGradoAcaPorIdConcepto(idConcepto);
    }

    public String validarSiTieneCr(Integer idConcepto) throws Exception {
        return conceptoDAO.validarSiTieneCr(idConcepto);
    }
    
    //Ayuda 
    public List<ConceptoBean> obtenerPresupuestoTipo(String mod) throws Exception {
        return conceptoDAO.obtenerPresupuestoTipo(mod);
    }
     
    //Getter y Setter

    public ConceptoDAO getConceptoDAO() {
        return conceptoDAO;
    }

    public void setConceptoDAO(ConceptoDAO conceptoDAO) {
        this.conceptoDAO = conceptoDAO;
    }

    public Integer obtenerConceptoDesc(Integer idConcepto) throws Exception {
        return conceptoDAO.obtenerConceptoDesc(idConcepto);
    }
    
}
