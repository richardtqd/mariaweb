package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.EstudianteRetiroBean;
import pe.marista.sigma.dao.EstudianteRetiroDAO;

public class EstudianteRetiroService {

    private EstudianteRetiroDAO estudianteRetiroDAO;

    public EstudianteRetiroDAO getEstudianteRetiroDAO() {
        return estudianteRetiroDAO;
    }

    public void setEstudianteRetiroDAO(EstudianteRetiroDAO estudianteRetiroDAO) {
        this.estudianteRetiroDAO = estudianteRetiroDAO;
    }

    public List<EstudianteRetiroBean> obtenerPorEstudiante(String uniNeg, Integer anio, String codigo) throws Exception {
        return estudianteRetiroDAO.obtenerPorEstudiante(uniNeg, anio, codigo);
    }

    public void insertarEstudianteRetiro(EstudianteRetiroBean estudianteRetiroBean) throws Exception {
        estudianteRetiroDAO.insertarEstudianteRetiro(estudianteRetiroBean);
    }

}
