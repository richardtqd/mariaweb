package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteMedicamentoBean;
import pe.marista.sigma.bean.reporte.EstudianteMedicamentosRepBean;
import pe.marista.sigma.dao.EstudianteMedicamentoDAO;

/**
 *
 * @author Administrador
 */
public class EstudianteMedicamentoService {

    private EstudianteMedicamentoDAO estudianteMedicamentoDAO;

    @Transactional
    public void insertarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception {
        estudianteMedicamentoDAO.insertarEstMedicamento(estudianteMedicamentoBean);
    }

    @Transactional
    public void modificarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception {
        estudianteMedicamentoDAO.modificarEstMedicamento(estudianteMedicamentoBean);
    }

    @Transactional
    public void eliminarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception {
        estudianteMedicamentoDAO.eliminarEstMedicamento(estudianteMedicamentoBean);
    }

    public EstudianteMedicamentoBean obtenerEstMedicamentoPorId(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception {
        return estudianteMedicamentoDAO.obtenerEstMedicamentoPorId(estudianteMedicamentoBean);
    }

    public List<EstudianteMedicamentoBean> obtenerEstMedicamentoPorEst(String idEstudiante) throws Exception {
        return estudianteMedicamentoDAO.obtenerEstMedicamentoPorEst(idEstudiante);
    }

    public EstudianteMedicamentoDAO getEstudianteMedicamentoDAO() {
        return estudianteMedicamentoDAO;
    }

    public void setEstudianteMedicamentoDAO(EstudianteMedicamentoDAO estudianteMedicamentoDAO) {
        this.estudianteMedicamentoDAO = estudianteMedicamentoDAO;
    }

    public List<EstudianteMedicamentosRepBean> obtenerMedicamentos(String idEstudiante, String uniNeg) throws Exception {
        return estudianteMedicamentoDAO.obtenerMedicamentos(idEstudiante, uniNeg);
    }

    
}
