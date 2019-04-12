/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetRegistroCompraBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.bean.reporte.DetRegistroCompraRepBean;

/**
 *
 * @author MS001
 */
public interface DetRegistroCompraDAO {

    public List<DetRegistroCompraBean> obtenerPorOrden(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertar(DetRegistroCompraBean detRegistroCompraBean) throws Exception;

    public void eliminar(Integer idRegistroCompra) throws Exception;

    public void validarRecepcionCompra(DetRegistroCompraBean detRegistroCompraBean) throws Exception;

    public List<DetRegistroCompraBean> obtenerPorRegistro(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetRegistroCompraBean> obtenerPorRegistroDet(Integer idDetRegistroCompra) throws Exception;

    public Integer obtenerUltimo(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetRegistroCompraBean> obtenerPorFactura(FacturaCompraBean facturaCompraBean) throws Exception;

    public List<DetRegistroCompraBean> obtenerTodos(String uniNeg) throws Exception;

    public List<DetRegistroCompraBean> obtenerDetalleDocEgreso2(@Param("nroDocEgreso") Integer nroDocEgreso, @Param("uniNeg") String uniNeg) throws Exception;

    //CR Registro de Compra
    public void obtenerCRRegistro(DetRequerimientoCRBean detRequerimientoCRBean) throws Exception;

    //reporte
    public List<DetRegistroCompraRepBean> obtenerDetFacturaCompra(@Param("idFacturaCompra") Integer idFacturaCompra, @Param("uniNeg") String uniNeg) throws Exception;

}
