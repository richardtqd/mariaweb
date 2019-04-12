package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.DocIngresoSerieBean;
import pe.marista.sigma.bean.DocIngresoSerieCajaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;

public interface CajaDAO {

    public List<CajaBean> obtenerTodos(String uniNeg) throws Exception;
        public List<CajaBean> obtenerTodosActivos(String uniNeg) throws Exception;
    public List<CajaBean> obtenerPorFiltro(CajaBean usuarioBean) throws Exception;
    public CajaBean buscarPorId(CajaBean  cajaBean) throws Exception;
    public void insertar(CajaBean usuarioBean) throws Exception;
    public void actualizar(CajaBean usuarioBean) throws Exception;
    public void eliminar(CajaBean usuarioBean) throws Exception;
    public void cambiarEstado(CajaBean usuarioBean) throws Exception;
   
    //Documento de Ingreso Serie
//    public List<DocIngresoSerieCajaBean> obtenerDocumentoPorCaja(CajaBean cajaBean) throws Exception;
    public List<DocIngresoSerieBean> obtenerPorCaja(CajaBean CajaBean) throws Exception;
    public void insertarDocIngresoSerieCaja(DocIngresoSerieCajaBean docIngresoSerieCajaBean) throws Exception;
    public void eliminarDocIngresoSerieCajaAll(DocIngresoSerieCajaBean docIngresoSerieCajaBean) throws Exception;
    
    //Impresora
    public List<ImpresoraBean> obtenerImpresoraPorCaja(CajaBean cajaBean) throws Exception;
    public void insertarImpresoraCaja(ImpresoraCajaBean impresoraCajaBean) throws Exception;
    public void eliminarImpresoraCajaAll(ImpresoraCajaBean impresoraCajaBean) throws Exception;
    
  }
