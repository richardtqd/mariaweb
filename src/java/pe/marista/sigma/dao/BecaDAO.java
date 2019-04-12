/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.BecaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBecaBean;

/**
 *
 * @author MS002
 */
public interface BecaDAO {

    public List<BecaBean> obtenerTodos() throws Exception;

    public List<BecaBean> obtenerTodosActivos() throws Exception;

    public BecaBean buscarPorId(BecaBean becaBean) throws Exception;

    public void insertar(BecaBean becaBean) throws Exception;

    public void actualizar(BecaBean becaBean) throws Exception;

    public void eliminar(BecaBean becaBean) throws Exception;

    public void cambiarEstado(BecaBean becaBean) throws Exception;

    public Double obtenerDsctoPorId(@Param("id") Integer id, @Param("monto") Double monto) throws Exception;
}
