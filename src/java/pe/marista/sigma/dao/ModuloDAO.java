/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ModuloBean;

/**
 *
 * @author Administrador
 */
public interface ModuloDAO {

    public List<ModuloBean> obtenerTodos() throws Exception;

    public List<ModuloBean> obtenerModuloPadre(@Param("idModuloPadre") Integer idModuloPadre) throws Exception;

    public List<ModuloBean> obtenerModuloPadrePos(@Param("idModuloPadre") Integer idModuloPadre) throws Exception;

    public List<ModuloBean> obtenerModuloPadrePosicion(@Param("idModuloPadre") Integer idModuloPadre, @Param("posicionIni") Integer posicionIni, @Param("posicionFin") Integer posicionFin, @Param("idModulo") Integer idModulo) throws Exception;

    public Integer obtenerPosMin(@Param("idModuloPadre") Integer idModuloPadre, @Param("posicionIni") Integer posicionIni, @Param("posicionFin") Integer posicionFin, @Param("idModulo") Integer idModulo) throws Exception;

    public Integer obtenerPosMax(@Param("idModuloPadre") Integer idModuloPadre, @Param("posicionIni") Integer posicionIni, @Param("posicionFin") Integer posicionFin, @Param("idModulo") Integer idModulo) throws Exception;

    public List<ModuloBean> obtenerFiltro(Integer idTipoNodo) throws Exception;

    public ModuloBean obtenerPorId(ModuloBean moduloBean) throws Exception;

    public void insertar(ModuloBean moduloBean) throws Exception;

    public void actualizar(ModuloBean moduloBean) throws Exception;

    public void actualizarPadre(ModuloBean moduloBean) throws Exception;

    public void modificarPosicion(@Param("posicion") Integer posicion, @Param("idModulo") Integer idModulo, @Param("idModuloPadre") Integer idModuloPadre) throws Exception;

    public void eliminarLogicamente(ModuloBean moduloBean) throws Exception;

    public void eliminarHijos(ModuloBean moduloBean) throws Exception;

    public void eliminar(ModuloBean moduloBean) throws Exception;

    public void modificarOrdenamiento(ModuloBean moduloBean) throws Exception;

    public void modificarOrdenamientoPro(ModuloBean moduloBean) throws Exception;

    public Integer obtenerPosicionCero(Integer idModuloPadre) throws Exception;

    public void modificarPosicionPro(ModuloBean moduloBean) throws Exception;

}
