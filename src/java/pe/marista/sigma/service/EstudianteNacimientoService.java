package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.EstudianteNacimientoBean;
import pe.marista.sigma.bean.reporte.EstudianteNacimientoRepBean;
import pe.marista.sigma.dao.EstudianteNacimientoDAO;

public class EstudianteNacimientoService {
    
     private EstudianteNacimientoDAO estudianteNacimientoDAO;

    public EstudianteNacimientoDAO getEstudianteNacimientoDAO() {
        return estudianteNacimientoDAO;
    }

    public void setEstudianteNacimientoDAO(EstudianteNacimientoDAO estudianteNacimientoDAO) {
        this.estudianteNacimientoDAO = estudianteNacimientoDAO;
    }

    public void insertarEstudianteNacimiento(EstudianteNacimientoBean estudianteNacimientoBean) throws Exception {
        estudianteNacimientoDAO.insertarEstudianteNacimiento(estudianteNacimientoBean);
    }

    public void modificarEstudianteNacimiento(EstudianteNacimientoBean estudianteNacimientoBean) throws Exception {
        estudianteNacimientoDAO.modificarEstudianteNacimiento(estudianteNacimientoBean);
    }

    public EstudianteNacimientoBean obtenerEstNacimientoPorEst(String idEstudiante) throws Exception {
        return estudianteNacimientoDAO.obtenerEstNacimientoPorEst(idEstudiante);
    }

    public List<EstudianteNacimientoRepBean> obtenerEstudianteNacimiento(String idEstudiante, String uniNeg) throws Exception {
        return estudianteNacimientoDAO.obtenerEstudianteNacimiento(idEstudiante, uniNeg);
    }
         
}
