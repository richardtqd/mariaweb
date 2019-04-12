/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;
//
//import java.util.List;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDatosHistoricoBean;
import pe.marista.sigma.bean.PersonalVoluntariadoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.reporte.LegajoRepBean;
import pe.marista.sigma.bean.reporte.PersonalRepBean;
import pe.marista.sigma.dao.LegajoDAO;

/**
 *
 * @author Administrador
 */
public class LegajoService {

    private LegajoDAO legajoDAO;

    //Logica Negocio
    public List<PersonalBean> obtenerTodos() throws Exception {
        return legajoDAO.obtenerTodos();
    }

    public List<PersonalBean> obtenerPersonalPorUnidadNegocio(String uniNeg) throws Exception {
        return legajoDAO.obtenerPersonalPorUnidadNegocio(uniNeg);
    }
    public List<PersonalBean> obtenerPersonalPorUnidadNegocioPorId(String usuario,String uniNeg) throws Exception {
        return legajoDAO.obtenerPersonalPorUnidadNegocioPorId(usuario,uniNeg);
    }

    public List<PersonalBean> obtenerTodosActivos() throws Exception {
        return legajoDAO.obtenerTodosActivos();
    }

    public List<PersonalBean> obtenerFiltroPersonal(PersonalBean personalBean) throws Exception {
        return legajoDAO.obtenerFiltroPersonal(personalBean);
    }

    public List<PersonalBean> obtenerPersonalPorFiltro(PersonalBean personalBean) throws Exception {
        return legajoDAO.obtenerPersonalPorFiltro(personalBean);
    }

    public List<PersonalBean> obtenerCumpleaniosPersonal(PersonalBean personalBean) throws Exception {
        return legajoDAO.obtenerCumpleaniosPersonal(personalBean);
    }

    public List<PersonalRepBean> obtenerPersonalRepPorFiltro(PersonalRepBean personalRepBean) throws Exception {
        return legajoDAO.obtenerPersonalRepPorFiltro(personalRepBean);
    }

    public PersonalBean obtenerLegajoId(PersonalBean personalBean) throws Exception {
        return legajoDAO.obtenerPersonalPorId(personalBean);
    }

    public PersonalBean obtenerUniOrgPersonalPorId(PersonalBean personalBean) throws Exception {
        return legajoDAO.obtenerUniOrgPersonalPorId(personalBean);
    }

    public List<LegajoRepBean> obtenerFichaPersonal(Integer idPersonal, String uniNeg) throws Exception {
        return legajoDAO.obtenerFichaPersonal(idPersonal, uniNeg);
    }

    //CRUD
    @Transactional
    public void insertarPersonal(PersonalBean personalBean) throws Exception {
        legajoDAO.insertarPersonal(personalBean);
    }

    @Transactional
    public void modificarPersonal(PersonalBean personalBean) throws Exception {
        legajoDAO.modificarPersonal(personalBean);
    }

    @Transactional
    public void cambiarEstado(PersonalBean personalBean) throws Exception {
        legajoDAO.cambiarEstado(personalBean);
    }

    @Transactional
    public void eliminarPersonal(PersonalBean personalBean) throws Exception {
        legajoDAO.eliminarPersonal(personalBean);
    }

    public String obtenerCorreoCorPorPersonal(String unidad, String uniNeg) throws Exception {
        return legajoDAO.obtenerCorreoCorPorPersonal(unidad, uniNeg);
    }

    //Metodos Getter y Setter  //metodos
    public LegajoDAO getLegajoDAO() {
        return legajoDAO;
    }

    public void setLegajoDAO(LegajoDAO legajoDAO) {
        this.legajoDAO = legajoDAO;
    }

    public String obtenerFotoPersonal(String codPer, String uniNeg) throws Exception {
        return legajoDAO.obtenerFotoPersonal(codPer, uniNeg);
    }

    public void insertarPersonalVoluntariado(PersonalVoluntariadoBean personalVoluntariadoBean) throws Exception {
        legajoDAO.insertarPersonalVoluntariado(personalVoluntariadoBean);
    }

    public List<PersonalVoluntariadoBean> obtenerPersonalVoluntariadoLista(PersonalVoluntariadoBean personalVoluntariadoBean) throws Exception {
        return legajoDAO.obtenerPersonalVoluntariadoLista(personalVoluntariadoBean);
    }

    public void insertarPersonalHistorico(PersonalDatosHistoricoBean personalDatosHistoricoBean) throws Exception {
        legajoDAO.insertarPersonalHistorico(personalDatosHistoricoBean);
    }

    public List<PersonalDatosHistoricoBean> obtenerPersonalHistoricoLista(PersonalDatosHistoricoBean personalDatosHistoricoBean) throws Exception {
        return legajoDAO.obtenerPersonalHistoricoLista(personalDatosHistoricoBean);
    }

    public PersonalDatosHistoricoBean obtenerUltimoDirecEstadoCivil(Integer idPersonal, String uniNeg) throws Exception {
        return legajoDAO.obtenerUltimoDirecEstadoCivil(idPersonal, uniNeg);
    } 

    public void modificarPersonalSangre(PersonalBean personalBean) throws Exception {
        legajoDAO.modificarPersonalSangre(personalBean);
    }
    
}
