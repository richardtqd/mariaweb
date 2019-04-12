package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteSeguroBean;
import pe.marista.sigma.dao.EstudianteSeguroDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class EstudianteSeguroService {

    private EstudianteSeguroDAO estudianteSeguroDAO;

    @Transactional
    public void insertarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.insertarEstudianteSeguro(estudianteSeguroBean);
    }

    @Transactional
    public void insertarEstudianteSeguroRapido(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.insertarEstudianteSeguroRapido(estudianteSeguroBean);
    }

    @Transactional
    public void modificarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.modificarEstudianteSeguro(estudianteSeguroBean);
    }

    @Transactional
    public void modificarEstudianteSeguroRapido(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.modificarEstudianteSeguroRapido(estudianteSeguroBean);
    }

    @Transactional
    public void insertarEstudianteSeguroRapidoVer2(EstudianteSeguroBean estudianteSeguroBean, String usuario) throws Exception {

        //1.-pongo en estado false todos los seguros del estudiante
        estudianteSeguroBean.setStatus(Boolean.FALSE);
        estudianteSeguroBean.setModiPor(usuario);
        estudianteSeguroDAO.cambiarEstadoAllEstudianteSeguroRapido(estudianteSeguroBean);
        //2.- inserto el nuevo seguro con estado true 
        estudianteSeguroBean.setStatus(Boolean.TRUE);
        estudianteSeguroBean.setCreaPor(usuario);

        estudianteSeguroDAO.insertarEstudianteSeguroRapido(estudianteSeguroBean);
    }

    public List<EstudianteSeguroBean> obtenerEstudianteSeguroActivoPorEst(EstudianteBean estudianteBean) throws Exception {
        return estudianteSeguroDAO.obtenerEstudianteSeguroActivoPorEst(estudianteBean);
    }

    @Transactional
    public void eliminarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.eliminarEstudianteSeguro(estudianteSeguroBean);
    }

    @Transactional
    public void cambiarEstadoEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        estudianteSeguroDAO.cambiarEstadoEstudianteSeguro(estudianteSeguroBean);
    }

    public EstudianteSeguroBean obtenerEstudianteSeguroPorId(EstudianteSeguroBean estudianteSeguroBean) throws Exception {
        return estudianteSeguroDAO.obtenerEstudianteSeguroPorId(estudianteSeguroBean);
    }

    public List<EstudianteSeguroBean> obtenerEstudianteSeguro() throws Exception {
        return estudianteSeguroDAO.obtenerEstudianteSeguro();
    }

    public List<EstudianteSeguroBean> obtenerEstudianteSeguroPorEst(EstudianteBean estudianteBean) throws Exception {
        return estudianteSeguroDAO.obtenerEstudianteSeguroPorEst(estudianteBean);
    }

    public EstudianteSeguroBean obtenerEstudianteSeguroSelect(EstudianteBean estudianteBean) throws Exception {
        return estudianteSeguroDAO.obtenerEstudianteSeguroSelect(estudianteBean);
    }
    //GEtter y Setter

    public EstudianteSeguroDAO getEstudianteSeguroDAO() {
        return estudianteSeguroDAO;
    }

    public void setEstudianteSeguroDAO(EstudianteSeguroDAO estudianteSeguroDAO) {
        this.estudianteSeguroDAO = estudianteSeguroDAO;
    }

}
