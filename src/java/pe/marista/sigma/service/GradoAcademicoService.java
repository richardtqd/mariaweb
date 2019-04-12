package pe.marista.sigma.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.dao.GradoAcademicoDAO;

public class GradoAcademicoService {

    private GradoAcademicoDAO gradoAcademicoDAO;

    //Getter and Setter
    public GradoAcademicoDAO getGradoAcademicoDAO() {
        return gradoAcademicoDAO;
    }

    public void setGradoAcademicoDAO(GradoAcademicoDAO gradoAcademicoDAO) {
        this.gradoAcademicoDAO = gradoAcademicoDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        gradoAcademicoDAO.insertar(gradoAcademicoBean);
    }

    @Transactional
    public void modificar(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        gradoAcademicoDAO.actualizar(gradoAcademicoBean);
    }

    @Transactional
    public void eliminar(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        gradoAcademicoDAO.eliminar(gradoAcademicoBean);
    }

    public List<GradoAcademicoBean> obtenerTodos() throws Exception {
        return gradoAcademicoDAO.obtenerTodos();
    }

    public List<GradoAcademicoBean> obtenerPorFiltro(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        return gradoAcademicoDAO.obtenerPorFiltro(gradoAcademicoBean);
    }

//    public List<GradoAcademicoBean> obtenerGAPorCarreraArea(Integer idGradoAcademico)throws Exception{
//        return gradoAcademicoDAO.obtenerGAPorCarreraArea(idGradoAcademico);
//    }
    public GradoAcademicoBean obtenerPorId(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        return gradoAcademicoDAO.obtenerPorId(gradoAcademicoBean);
    }

    public GradoAcademicoBean obtenerPorIdNombre(String nombre) throws Exception {
        return gradoAcademicoDAO.obtenerPorIdNombre(nombre);
    }

    //Filtro para matricula

    public List<GradoAcademicoBean> obtenerTodosMatri() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("nivAcaIni", MaristaConstantes.NIV_ACA_INI);
        parms.put("nivAcaPri", MaristaConstantes.NIV_ACA_PRI);
        parms.put("nivAcaSec", MaristaConstantes.NIV_ACA_SEC);
        return gradoAcademicoDAO.obtenerTodosMatri(parms);
    }

    public List<GradoAcademicoBean> obtenerTodosMatriSinBachiller() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("nivAcaIni", MaristaConstantes.NIV_ACA_INI);
        parms.put("nivAcaPri", MaristaConstantes.NIV_ACA_PRI);
        parms.put("nivAcaSec", MaristaConstantes.NIV_ACA_SEC);
        parms.put("cuartoBach", MaristaConstantes.Cuarto_Bach_Secundaria);
        parms.put("quintoBach", MaristaConstantes.Quinto_Bach_Secundaria);
        return gradoAcademicoDAO.obtenerTodosMatriSinBachiller(parms);
    }

    public List<GradoAcademicoBean> obtenerTodosIniPriSec() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("nivAcaIni", MaristaConstantes.NIV_ACA_INI);
        parms.put("nivAcaPri", MaristaConstantes.NIV_ACA_PRI);
        parms.put("nivAcaSec", MaristaConstantes.NIV_ACA_SEC);
        parms.put("IniPriSec", MaristaConstantes.NIV_INI_PRI_SEC);
        return gradoAcademicoDAO.obtenerTodosIniPriSec(parms);
    }

    public List<GradoAcademicoBean> obtenerCuartoBachillerTercero() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("tercero", MaristaConstantes.Tercero_Secundaria); 
        parms.put("cuarto", MaristaConstantes.Cuarto_Secundaria);
        parms.put("cuartoBach", MaristaConstantes.Cuarto_Bach_Secundaria);
        return gradoAcademicoDAO.obtenerCuartoBachillerTercero(parms);
    }
    public List<GradoAcademicoBean> obtenerQuintoCuartoBachiller() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("cuarto", MaristaConstantes.Cuarto_Secundaria);
        parms.put("cuartoBach", MaristaConstantes.Cuarto_Bach_Secundaria);
        parms.put("quinto", MaristaConstantes.Quinto_Secundaria);
        parms.put("quintoBach", MaristaConstantes.Quinto_Bach_Secundaria);
        return gradoAcademicoDAO.obtenerQuintoCuartoBachiller(parms);
    }
    
    

    //Carrera
    public List<GradoAcademicoBean> obtenerGradoAcaPorNivelAca(Integer idNivelAcademico) throws Exception {
        return gradoAcademicoDAO.obtenerGradoAcaPorNivelAca(idNivelAcademico);
    }

    public List<GradoAcademicoBean> obtenerPorIdNombreLista(String nombre) throws Exception {
        return gradoAcademicoDAO.obtenerPorIdNombreLista(nombre);
    }
    
}
