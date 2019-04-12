/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.dao.PersonaDAO;

/**
 *
 * @author Administrador
 */
public class PersonaService {

    private PersonaDAO personaDAO;

    //Lógica de Negocio
    public List<PersonaBean> obtenerPersona() throws Exception {
        return personaDAO.obtenerPersona();
    }

    public List<PersonaBean> obtenerPersonaPorUniNeg(UnidadNegocioBean unidadNegocioBean) throws Exception {
        return personaDAO.obtenerPersonaPorUniNeg(unidadNegocioBean);
    }

    public List<PersonaBean> obtenerTop10Persona(String uniNeg) throws Exception {
        return personaDAO.obtenerTop10Persona(uniNeg);
    }

    public PersonaBean obtenerPersPorId(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersPorId(personaBean);
    }

    public String generarCodigoPersona(Integer anio, String uniNeg) throws Exception {
        System.out.println("generarCodigoPersona()  personservice");
        String codigo = personaDAO.generarCodigoPersona(anio, uniNeg);
        Integer estado = 0;
        estado = verificarCodigoGenerado(codigo, uniNeg);
        if (estado.equals(1)) {
            System.out.println("if (estado.equals(1))");
            codigo = personaDAO.generarCodigoPersona(anio, uniNeg);
        }
        System.out.println("codigo 111" + codigo);
        if (codigo == null) {
            System.out.println("nullnullnullnull");
            codigo=generarCodigoPersona(anio, uniNeg);
        }
         System.out.println("return codigo " + codigo);
        return codigo;
    }

    public PersonaBean obtenerPersonaPorNombre(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersonaPorNombre(personaBean);
    }
    
    public Integer verificarCodigoGenerado(String codigo, String uniNeg) throws Exception {
        return personaDAO.verificarCodigoGenerado(codigo, uniNeg);
    }

    public String obtenerFoto(String idPersona, String uniNeg) throws Exception {
        return personaDAO.obtenerFoto(idPersona, uniNeg);
    }

    public List<PersonaBean> obtenerPersonaPorCorreo(String uniNeg, String correo) throws Exception {
        return personaDAO.obtenerPersonaPorCorreo(uniNeg, correo);
    }

    @Transactional
    public void insertarPersona(PersonaBean personaBean) throws Exception {
        personaDAO.insertarPersona(personaBean);
    }

    @Transactional
    public void modificarPersona(PersonaBean personaBean) throws Exception {
        personaDAO.modificarPersona(personaBean);
    }

    @Transactional
    public void modificarPersonaDatBasicos(PersonaBean personaBean) throws Exception {
        personaDAO.modificarPersonaDatBasicos(personaBean);
    }

    @Transactional
    public void eliminarPersona(String idPersona) throws Exception {
        personaDAO.eliminarPersona(idPersona);
    }

    //Filtro Postulante
    public List<PersonaBean> obtenerPersonaAdmPorUniNeg(String uniNeg) throws Exception {
        return personaDAO.obtenerPersonaAdmPorUniNeg(uniNeg);
    }

    public List<PersonaBean> SP_obtenerPersonaPorFiltro(PersonaBean personaBean) throws Exception {
        return personaDAO.SP_obtenerPersonaPorFiltro(personaBean);
    }

    public List<PersonaBean> obtenerPersonaPorFiltro(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersonaPorFiltro(personaBean);
    }

    public List<PersonaBean> obtenerPersonaProspectoPorFiltro(PersonaBean personaBean) throws Exception {
        personaBean.setIdCodigo(MaristaConstantes.COD_INT_POSTULANTE);
        return personaDAO.obtenerPersonaProspectoPorFiltro(personaBean);
    }

    public List<PersonaBean> obtenerPersonaPorFiltroValorados(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersonaPorFiltroValorados(personaBean);
    }

    //Metodos Getter y Setter
    public PersonaDAO getPersonaDAO() {
        return personaDAO;
    }

    public void setPersonaDAO(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    public PersonaBean obtenerPersonaPorFiltroProspecto(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersonaPorFiltroProspecto(personaBean);
    }

    public PersonaBean obtenerPersPorIdRapido(PersonaBean personaBean) throws Exception {
        return personaDAO.obtenerPersPorIdRapido(personaBean);
    }

    public void modificarResponsable(EstudianteBean estudianteBean) throws Exception {
        personaDAO.modificarResponsable(estudianteBean);
    }
    
    
}
