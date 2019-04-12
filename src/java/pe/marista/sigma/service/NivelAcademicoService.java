package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.dao.NivelAcademicoDAO;

public class NivelAcademicoService {

    private NivelAcademicoDAO nivelAcademicoDAO;

    //Getter and Setter
    public NivelAcademicoDAO getNivelAcademicoDAO() {
        return nivelAcademicoDAO;
    }

    public void setNivelAcademicoDAO(NivelAcademicoDAO nivelAcademicoDAO) {
        this.nivelAcademicoDAO = nivelAcademicoDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(NivelAcademicoBean nivelAcademicoBean) throws Exception {
        nivelAcademicoDAO.insertar(nivelAcademicoBean);
    }
    @Transactional
    public void modificar(NivelAcademicoBean nivelAcademicoBean) throws Exception {
        nivelAcademicoDAO.actualizar(nivelAcademicoBean);
    }
    @Transactional
    public void eliminar(NivelAcademicoBean nivelAcademicoBean) throws Exception {
        nivelAcademicoDAO.eliminar(nivelAcademicoBean);
    }
    public List<NivelAcademicoBean> obtenerTodos() throws Exception {
        return nivelAcademicoDAO.obtenerTodos();
    }
    public List<NivelAcademicoBean> obtenerPorFiltro(NivelAcademicoBean nivelAcademicoBean) throws Exception {
        return nivelAcademicoDAO.obtenerPorFiltro(nivelAcademicoBean);
    }
    
    public List<NivelAcademicoBean> obtenerPorTipoFormacion(Integer idTipoForm) throws Exception {
        return nivelAcademicoDAO.obtenerPorTipoFormacion(idTipoForm);
    }
    public List<NivelAcademicoBean> obtenerPorTipoFormacionSinIniPriSec(Integer idTipoForm) throws Exception {
        return nivelAcademicoDAO.obtenerPorTipoFormacionSinIniPriSec(idTipoForm);
    }

    public NivelAcademicoBean obtenerPorId(NivelAcademicoBean nivelAcademicoBean) throws Exception {
        return nivelAcademicoDAO.obtenerPorId(nivelAcademicoBean);
    }

    //Carrera
    public List<NivelAcademicoBean> obtenerNivelAcaPorTipoFormacion(TipoFormacionBean tipoFormacionBean) throws Exception {
        return nivelAcademicoDAO.obtenerNivelAcaPorTipoFormacion(tipoFormacionBean);
    }
}
