/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetraccionBean;

/**
 *
 * @author Administrador
 */
public interface DetraccionDAO {

    public List<DetraccionBean> obtenerTodos() throws Exception;

    public List<DetraccionBean> obtenerTodosActivos() throws Exception;

    public DetraccionBean obtenerPorId(DetraccionBean detraccionBean) throws Exception;

    public Double redondearDetraccionAfavor(@Param("monto") Double monto) throws Exception;

    public void insertarDetraccion(DetraccionBean detraccionBean) throws Exception;

    public void modificarDetraccion(DetraccionBean detraccionBean) throws Exception;

    public void eliminarDetraccion(DetraccionBean detraccionBean) throws Exception;

    public void cambiarEstadoDetraccion(DetraccionBean detraccionBean) throws Exception;

}
