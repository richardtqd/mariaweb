/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.TipoSolicitudBean;

/**
 *
 * @author Administrador
 */
public interface TipoSolicitudDAO {

    public void insertarTipoSolicitud(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public void modificarTipoSolicitud(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public void eliminarTipoSolicitud(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public List<TipoSolicitudBean> obtenerTodosTipoSolicitudPorUniNeg(String uniNeg) throws Exception;

    public List<TipoSolicitudBean> obtenerPorAmbitoPorUniNeg(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public List<TipoSolicitudBean> obtenerSolGenCajaCH(@Param("general") String general, @Param("cajach") String cajach, @Param("uniNeg") String uniNeg) throws Exception;

    public List<TipoSolicitudBean> obtenerTipoSolPorTipoPorUniNeg(String uniNeg) throws Exception;

    public TipoSolicitudBean obtenerTipoSolicitudPorId(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public TipoSolicitudBean obtenerTipoSolicitudPorNombre(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public TipoSolicitudBean validarTipSolConAuto(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public List<TipoSolicitudBean> obtenerTipoSolicitudPorFiltro(TipoSolicitudBean tipoSolicitudBean) throws Exception;

    public String obtenerCorreoCorPorAutorizador(@Param("nombre") String nombre, @Param("uniNeg") String uniNeg) throws Exception;

}
