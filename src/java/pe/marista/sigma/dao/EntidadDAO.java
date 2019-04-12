package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EntidadSedeBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ViewEntidadBean;

/**
 *
 * @author Administrador
 */
public interface EntidadDAO {

    public void insertarEntidad(EntidadBean entidadBean) throws Exception;

    public void modificarEntidad(EntidadBean entidadBean) throws Exception;

    public void eliminarEntidad(String ruc) throws Exception;

    public List<EntidadBean> obtenerEntidadPorUniNeg(String uniNeg) throws Exception;

    public List<EntidadBean> obtenerEntidadPorFiltroProveedor(EntidadBean entidadBean) throws Exception;

    public List<EntidadBean> obtenerEntidadPorRubro(CodigoBean CodigoBean) throws Exception;

    public EntidadBean obtenerEntidadPorId(EntidadBean entidadBean) throws Exception;

    public EntidadBean obtenerEntidadPorIdCot(@Param("ruc") String ruc, @Param("uniNeg") String uniNeg) throws Exception;

    public List<EntidadBean> obtenerEntidadPorFiltro(EntidadBean entidadBean) throws Exception;

    public List<EntidadBean> obtenerTodosSeguro(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<ViewEntidadBean> obtenerEntidadPorVistaPorUniNeg(@Param("value") String value, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ViewEntidadBean> obtenerEntidadPorVista(String entidadVista) throws Exception;
    
      public List<ViewEntidadBean> obtenerEntidadPorVistaLegajoNew(@Param("value") String value, @Param("idTipo") Integer idTipo) throws Exception;
//    //EntidadSede
//    public void insertarEntidadSede(EntidadSedeBean entidadSedeBean) throws Exception;
//    public void modificarEntidadSede(EntidadSedeBean entidadSedeBean) throws Exception;
//    public void eliminarEntidadSede(Integer idEntidadSede) throws Exception;
//    public List<EntidadSedeBean> obtenerEntidadSede() throws Exception;
//    public EntidadSedeBean obtenerEntidadSedePorId(EntidadSedeBean entidadSedeBean) throws Exception;
//    public List<EntidadSedeBean> obtenerEntidadSedePorEntidad(Integer idEntidad) throws Exception;
//    public List<EntidadSedeBean> obtenerProveedorPorUniNeg(String unineg) throws Exception;

    public List<EntidadBean> obtenerProveedorPorUniNeg(String unineg) throws Exception;

    public EntidadBean buscarPorId(String ruc) throws Exception;

    public List<EntidadBean> obtenerFlgFinanciero(String uniNeg) throws Exception;

    //Datos del Mensaje
    public EntidadBean obtenerInfoEntidad(@Param("uniNeg") String uniNeg) throws Exception;

    public String obtenerInfoCta(
            @Param("ruc") String ruc,
            @Param("uniNeg") String uniNeg,
            @Param("moneda") String moneda) throws Exception;

}
