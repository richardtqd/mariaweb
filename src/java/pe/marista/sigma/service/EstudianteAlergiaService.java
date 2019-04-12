package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteAlergiaBean;
import pe.marista.sigma.bean.reporte.EstudianteAlergiasRepBean;
import pe.marista.sigma.dao.EstudianteAlergiaDAO;

/**
 *
 * @author Administrador
 */
public class EstudianteAlergiaService {

    private EstudianteAlergiaDAO estudianteAlergiaDAO;

    @Transactional
    public void insertarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception {
        estudianteAlergiaDAO.insertarEstAlergia(estudianteAlergiaBean);
    }

    @Transactional
    public void modificarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception {
        estudianteAlergiaDAO.modificarEstAlergia(estudianteAlergiaBean);
    }

    @Transactional
    public void eliminarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception {
        estudianteAlergiaDAO.eliminarEstAlergia(estudianteAlergiaBean);
    }

    public EstudianteAlergiaBean obtenerEstAlergiaPorId(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception {
        return estudianteAlergiaDAO.obtenerEstAlergiaPorId(estudianteAlergiaBean);
    }

    public List<EstudianteAlergiaBean> obtenerEstAlergiaPorEst(String idEstudiante) throws Exception {
        return estudianteAlergiaDAO.obtenerEstAlergiaPorEst(idEstudiante);
    }

    public EstudianteAlergiaDAO getEstudianteAlergiaDAO() {
        return estudianteAlergiaDAO;
    }

    public void setEstudianteAlergiaDAO(EstudianteAlergiaDAO estudianteAlergiaDAO) {
        this.estudianteAlergiaDAO = estudianteAlergiaDAO;
    }

    public List<EstudianteAlergiasRepBean> obtenerAlergias(String idEstudiante, String uniNeg) throws Exception {
        return estudianteAlergiaDAO.obtenerAlergias(idEstudiante, uniNeg);
    }
     
}
