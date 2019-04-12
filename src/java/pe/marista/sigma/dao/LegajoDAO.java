/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDatosHistoricoBean;
import pe.marista.sigma.bean.PersonalVoluntariadoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.reporte.LegajoRepBean;
import pe.marista.sigma.bean.reporte.PersonalRepBean;

public interface LegajoDAO {

    public void insertarPersonal(PersonalBean personalBean) throws Exception;

    public void modificarPersonal(PersonalBean personalBean) throws Exception;
    
    public void modificarPersonalSangre(PersonalBean personalBean) throws Exception;

    public void eliminarPersonal(PersonalBean personalBean) throws Exception;

    public List<PersonalBean> obtenerTodos() throws Exception;

    public List<PersonalBean> obtenerTodosActivos() throws Exception;

    public List<PersonalBean> obtenerPersonalPorUnidadNegocio(String uniNeg) throws Exception;

    public List<PersonalBean> obtenerPersonalPorUnidadNegocioPorId(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg) throws Exception;

    public PersonalDatosHistoricoBean obtenerUltimoDirecEstadoCivil(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonalBean> obtenerFiltroPersonal(PersonalBean personalBean) throws Exception;

    public PersonalBean obtenerPersonalPorId(PersonalBean personalBean) throws Exception;

    public PersonalBean obtenerUniOrgPersonalPorId(PersonalBean personalBean) throws Exception;

    public void cambiarEstado(PersonalBean PersonalBean) throws Exception;

    public List<PersonalBean> obtenerCumpleaniosPersonal(PersonalBean personalBean) throws Exception;

    //reporte
    public List<PersonalBean> obtenerPersonalPorFiltro(PersonalBean personalBean) throws Exception;

    public List<PersonalRepBean> obtenerPersonalRepPorFiltro(PersonalRepBean personalRepBean) throws Exception;

    public List<LegajoRepBean> obtenerFichaPersonal(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public String obtenerCorreoCorPorPersonal(@Param("unidad") String unidad, @Param("uniNeg") String uniNeg) throws Exception;

    public String obtenerFotoPersonal(@Param("codPer") String codPer, @Param("uniNeg") String uniNeg) throws Exception;

    //Personal voluntariado
    public void insertarPersonalVoluntariado(PersonalVoluntariadoBean personalVoluntariadoBean) throws Exception;

    public List<PersonalVoluntariadoBean> obtenerPersonalVoluntariadoLista(PersonalVoluntariadoBean personalVoluntariadoBean) throws Exception;

    //Personal Historico
    public void insertarPersonalHistorico(PersonalDatosHistoricoBean personalDatosHistoricoBean) throws Exception;

    public List<PersonalDatosHistoricoBean> obtenerPersonalHistoricoLista(PersonalDatosHistoricoBean personalDatosHistoricoBean) throws Exception;
}
