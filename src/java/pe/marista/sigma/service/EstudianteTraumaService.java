package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteTraumaBean;
import pe.marista.sigma.bean.reporte.EstudianteTraumaRepBean;
import pe.marista.sigma.dao.EstudianteTraumaDAO;

/**
 *
 * @author Administrador
 */
public class EstudianteTraumaService {

    private EstudianteTraumaDAO estudianteTraumaDAO;

    @Transactional
    public void insertarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception {
        estudianteTraumaDAO.insertarEstTrauma(estudianteTraumaBean);
    }

    @Transactional
    public void modificarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception {
        estudianteTraumaDAO.modificarEstTrauma(estudianteTraumaBean);
    }

    @Transactional
    public void eliminarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception {
        estudianteTraumaDAO.eliminarEstTrauma(estudianteTraumaBean);
    }

    public EstudianteTraumaBean obtenerEstTraumaPorId(EstudianteTraumaBean estudianteTraumaBean) throws Exception {
        return estudianteTraumaDAO.obtenerEstTraumaPorId(estudianteTraumaBean);
    }

    public List<EstudianteTraumaBean> obtenerEstTraumaPorEst(String idEstudiante) throws Exception {
        return estudianteTraumaDAO.obtenerEstTraumaPorEst(idEstudiante);
    }

    public EstudianteTraumaDAO getEstudianteTraumaDAO() {
        return estudianteTraumaDAO;
    }

    public void setEstudianteTraumaDAO(EstudianteTraumaDAO estudianteTraumaDAO) {
        this.estudianteTraumaDAO = estudianteTraumaDAO;
    }

    public List<EstudianteTraumaRepBean> obtenerTraumas(String idEstudiante, String uniNeg) throws Exception {
        return estudianteTraumaDAO.obtenerTraumas(idEstudiante, uniNeg);
    }

}
