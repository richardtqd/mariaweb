package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteEnfermedadBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadRepBean;
import pe.marista.sigma.dao.EstudianteEnfermedadDAO;

/**
 *
 * @author Administrador
 */
public class EstudianteEnfermedadService {

    private EstudianteEnfermedadDAO estudianteEnfermedadDAO;

    @Transactional
    public void insertarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception {
        estudianteEnfermedadDAO.insertarEstEnfermedad(estudianteEnfermedadBean);
    }

    @Transactional
    public void modificarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception {
        estudianteEnfermedadDAO.modificarEstEnfermedad(estudianteEnfermedadBean);
    }

    @Transactional
    public void eliminarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception {
        estudianteEnfermedadDAO.eliminarEstEnfermedad(estudianteEnfermedadBean);
    }

    public EstudianteEnfermedadBean obtenerEstEnfermedadPorId(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception {
        return estudianteEnfermedadDAO.obtenerEstEnfermedadPorId(estudianteEnfermedadBean);
    }

    public List<EstudianteEnfermedadBean> obtenerEstEnfermedadPorEst(String idEstudiante) throws Exception {
        return estudianteEnfermedadDAO.obtenerEstEnfermedadPorEst(idEstudiante);
    }

    public EstudianteEnfermedadDAO getEstudianteEnfermedadDAO() {
        return estudianteEnfermedadDAO;
    }

    public void setEstudianteEnfermedadDAO(EstudianteEnfermedadDAO estudianteEnfermedadDAO) {
        this.estudianteEnfermedadDAO = estudianteEnfermedadDAO;
    }

    public List<EstudianteEnfermedadRepBean> obtenerEstudianteEnfermedad(String idEstudiante, String uniNeg) throws Exception {
        return estudianteEnfermedadDAO.obtenerEstudianteEnfermedad(idEstudiante, uniNeg);
    }

}
