package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.EstudianteVacunaBean;
import pe.marista.sigma.bean.reporte.EstudianteVacunasRepBean;
import pe.marista.sigma.dao.EstudianteVacunaDAO;

public class EstudianteVacunaService {
    
    private EstudianteVacunaDAO estudianteVacunaDAO;

    public EstudianteVacunaDAO getEstudianteVacunaDAO() {
        return estudianteVacunaDAO;
    }

    public void setEstudianteVacunaDAO(EstudianteVacunaDAO estudianteVacunaDAO) {
        this.estudianteVacunaDAO = estudianteVacunaDAO;
    }

    public void insertarEstudianteVacuna(EstudianteVacunaBean estudianteNacimientoBean) throws Exception {
        estudianteVacunaDAO.insertarEstudianteVacuna(estudianteNacimientoBean);
    }

    public void modificarEstudianteVacuna(EstudianteVacunaBean estudianteNacimientoBean) throws Exception {
        estudianteVacunaDAO.modificarEstudianteVacuna(estudianteNacimientoBean);
    }

    public List<EstudianteVacunaBean> obtenerEstVacunaPorEst(String idEstudiante) throws Exception {
        return estudianteVacunaDAO.obtenerEstVacunaPorEst(idEstudiante);
    }

    public EstudianteVacunaBean obtenerEstEnfermedadPorId(EstudianteVacunaBean estudianteNacimientoBean) throws Exception {
        return estudianteVacunaDAO.obtenerEstEnfermedadPorId(estudianteNacimientoBean);
    }

    public void eliminarEstMedicamento(EstudianteVacunaBean estudianteVacunaBean) throws Exception {
        estudianteVacunaDAO.eliminarEstMedicamento(estudianteVacunaBean);
    }

    public List<EstudianteVacunasRepBean> obtenerVacunas(String idEstudiante, String uniNeg) throws Exception {
        return estudianteVacunaDAO.obtenerVacunas(idEstudiante, uniNeg);
    }
    
    
    
    
}
