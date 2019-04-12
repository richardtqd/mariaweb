/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.DetalleSolicitudLogBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetCotizacionRepBean;
import pe.marista.sigma.dao.DetCotizacionDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDetalleDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author Administrador
 */
public class DetCotizacionService {

    private DetCotizacionDAO detCotizacionDAO;
    private SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO;

    //Getter and Setter
    public DetCotizacionDAO getDetCotizacionDAO() {
        return detCotizacionDAO;
    }

    public void setDetCotizacionDAO(DetCotizacionDAO detCotizacionDAO) {
        this.detCotizacionDAO = detCotizacionDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(Integer idCotizacion, Integer anio, List<DetCotizacionBean> listaDetalle, List<SolicitudLogDetalleBean> listaDet1, DetCotizacionBean detCotizacionBean, CotizacionBean cotizacionBean) throws Exception {
        SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
        List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean = new ArrayList<>();

        for (DetCotizacionBean listaDetalle1 : listaDetalle) {
            listaDetalle1.setIdCotizacion(idCotizacion);
            listaDetalle1.setIdRequerimiento(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
            listaDetalle1.setIdDetRequerimiento(listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento());
            listaDetalle1.getTipoMonedaBean().setIdCodigo(listaDetalle1.getSolicitudLogDetalleBean().getCatalogoBean().getTipoMonedaBean().getIdCodigo());
            listaDetalle1.setAnio(anio);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetalle1.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
            detCotizacionDAO.insertar(listaDetalle1);
        }
        Integer idCatalogo = 0, idDetRequerimiento = 0;
        Double montoRef = null;
        String ruc;
        for (DetCotizacionBean listaDetalle1 : listaDetalle) {
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorDetRequerimiento(listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento());
            for (SolicitudLogDetalleBean solicitud : listaSolicitudLogDetalleBean) {
                if (listaDetalle1.getCatalogoBean().getIdCatalogo().equals(solicitud.getCatalogoBean().getIdCatalogo())
                        && listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento().equals(solicitud.getIdDetRequerimiento())) {

                    /* Obteniendo Variables */
                    idCatalogo = listaDetalle1.getCatalogoBean().getIdCatalogo();
                    montoRef = listaDetalle1.getImporte();
                    idDetRequerimiento = listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento();

                    /* Seteando Variables */
                    solicitud.setIdDetRequerimiento(idDetRequerimiento);
                    solicitud.getCatalogoBean().setIdCatalogo(idCatalogo);
                    solicitud.setMontoRef(montoRef);

                    System.out.println("precio: " + listaDetalle1.getImporte());
                    System.out.println("precio: " + listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento());
                    System.out.println("precio: " + listaDetalle1.getCatalogoBean().getIdCatalogo());
                    solicitudLogisticoDetalleDAO.modificarPrecioDetalles(montoRef, idDetRequerimiento, idCatalogo);
                }
            }
        }
    }

    @Transactional
    public void eliminar(CotizacionBean cotizacionBean) throws Exception {
        detCotizacionDAO.eliminar(cotizacionBean);
    }

    @Transactional
    public void modificarPrecioDetalles(Double montoRef, Integer idDetRequerimiento, Integer idCatalogo) throws Exception {
        solicitudLogisticoDetalleDAO.modificarPrecioDetalles(montoRef, idDetRequerimiento, idCatalogo);
    }

    public Integer obtenerUltimo(Integer idCotizacion, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerUltimo(idCotizacion, uniNeg);
    }

    public List<DetCotizacionBean> obtenerListaPorId(Integer idDetCotizacion) throws Exception {
        return detCotizacionDAO.obtenerListaPorId(idDetCotizacion);
    }

    public List<DetCotizacionBean> obtenerTodos(String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerTodos(uniNeg);
    }

    public List<DetCotizacionBean> obtenerPorOrden(Integer idRequerimiento, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerPorOrden(idRequerimiento, uniNeg);
    }

    public List<DetCotizacionRepBean> obtenerPorOrdenPrimero(Integer idCotizacion, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerPorOrdenPrimero(idCotizacion, uniNeg);
    }

    public Integer obtenerUltimoCoti(Integer idCotizacion, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerUltimoCoti(idCotizacion, uniNeg);
    }

    public DetCotizacionBean obtenerPorId(DetCotizacionBean detCotizacionBean) throws Exception {
        return detCotizacionDAO.obtenerPorId(detCotizacionBean);
    }

    public SolicitudLogisticoDetalleDAO getSolicitudLogisticoDetalleDAO() {
        return solicitudLogisticoDetalleDAO;
    }

    public void setSolicitudLogisticoDetalleDAO(SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO) {
        this.solicitudLogisticoDetalleDAO = solicitudLogisticoDetalleDAO;
    }

    public List<DetCotizacionBean> obtenerListaPorIdSolicitud(Integer idRequerimiento, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerListaPorIdSolicitud(idRequerimiento, uniNeg);
    }

    public List<DetCotizacionBean> obtenerListaPorIdSolicitud2(DetCotizacionBean detCotizacionBean) throws Exception {
        return detCotizacionDAO.obtenerListaPorIdSolicitud2(detCotizacionBean);
    }

    public Integer obtenerRequerimiento(Integer idRequerimiento, String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerRequerimiento(idRequerimiento, uniNeg);
    }

    public List<DetCotizacionBean> obtenerTodosM(DetCotizacionBean detCotizacionBean) throws Exception {
        return detCotizacionDAO.obtenerTodosM(detCotizacionBean);
    }

    public List<DetCotizacionBean> obtenerTodosGeneral(DetCotizacionBean detCotizacionBean) throws Exception {
        return detCotizacionDAO.obtenerTodosGeneral(detCotizacionBean);
    }

    public List<DetCotizacionBean> obtenerPorOrdenSoli(Integer idCotizacion,String uniNeg) throws Exception {
        return detCotizacionDAO.obtenerPorOrdenSoli(idCotizacion,uniNeg);
    }

}
