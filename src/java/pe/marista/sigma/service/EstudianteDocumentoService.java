package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteDocumentoBean;
import pe.marista.sigma.dao.AdmisionDAO;
import pe.marista.sigma.dao.EstudianteDocumentoDAO;

/**
 *
 * @author Administrador
 */
public class EstudianteDocumentoService {

    private EstudianteDocumentoDAO estudianteDocumentoDAO;
    private AdmisionDAO admisionDAO;

    @Transactional
    public void darCheckDocumentoAdmision(List<EstudianteDocumentoBean> listaEstudianteDocumentoBean, EstudianteDocumentoBean estudianteDocBean, AdmisionBean admisionBean) throws Exception {
        Boolean docObliEntre = true;
        for (EstudianteDocumentoBean estudianteDocumentoBean : listaEstudianteDocumentoBean) {
            if (estudianteDocumentoBean.isStatus() == true) {
                estudianteDocumentoBean.setStatus(true);
            } else {
                estudianteDocumentoBean.setStatus(false);
                if (estudianteDocumentoBean.getCheckListBean().getFlgObligatorio() == true) {
                    docObliEntre = false;
                }
            }
            estudianteDocumentoDAO.darCheckDocumentoAdmision(estudianteDocumentoBean);
        }
        if (docObliEntre == true) {
            admisionBean.getCodigoBean().setCodigo(MaristaConstantes.COD_EVALUACION);
        } else {
            admisionBean.getCodigoBean().setCodigo(MaristaConstantes.COD_INSCRITO);
        }
        admisionDAO.cambiarEstadoPostulante(admisionBean);
    }

    @Transactional
    public void eliminarEstDocumento(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception {
        estudianteDocumentoDAO.eliminarEstDocumento(estudianteDocumentoBean);
    }

    public EstudianteDocumentoBean obtenerEstDocumentoPorId(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception {
        return estudianteDocumentoDAO.obtenerEstDocumentoPorId(estudianteDocumentoBean);
    }

    public List<EstudianteDocumentoBean> obtenerEstDocumentoPorEst(String idEstudiante,String uniNeg,Integer anio) throws Exception {
        return estudianteDocumentoDAO.obtenerEstDocumentoPorEst(idEstudiante,uniNeg,anio);
    }

    public EstudianteDocumentoDAO getEstudianteDocumentoDAO() {
        return estudianteDocumentoDAO;
    }

    public void setEstudianteDocumentoDAO(EstudianteDocumentoDAO estudianteDocumentoDAO) {
        this.estudianteDocumentoDAO = estudianteDocumentoDAO;
    }

    public AdmisionDAO getAdmisionDAO() {
        return admisionDAO;
    }

    public void setAdmisionDAO(AdmisionDAO admisionDAO) {
        this.admisionDAO = admisionDAO;
    }

}
