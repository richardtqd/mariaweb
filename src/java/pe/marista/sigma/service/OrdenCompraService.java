package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.OrdenCompraDAO;
import pe.marista.sigma.dao.RegistroCompraDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

public class OrdenCompraService {

    private OrdenCompraDAO ordenCompraDAO;
    private RegistroCompraDAO registroCompraDAO;

    //Getter and Setter
    public OrdenCompraDAO getOrdenCompraDAO() {
        return ordenCompraDAO;
    }

    public void setOrdenCompraDAO(OrdenCompraDAO ordenCompraDAO) {
        this.ordenCompraDAO = ordenCompraDAO;
    }

    public RegistroCompraDAO getRegistroCompraDAO() {
        return registroCompraDAO;
    }

    public void setRegistroCompraDAO(RegistroCompraDAO registroCompraDAO) {
        this.registroCompraDAO = registroCompraDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(OrdenCompraBean ordenCompraBean, SolicitudLogisticoBean solicitudLogisticoBean, List<FacturaCompraBean> listFacturaCompraBean, DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception {
        ordenCompraDAO.insertar(ordenCompraBean);
//        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//        SolicitudLogisticoBean solicitudLogistico = new SolicitudLogisticoBean();
//        solicitudLogistico.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
//        solicitudLogistico.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
//        solicitudLogistico.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
//        solicitudLogistico.getUnidadNegocioBean().setUniNeg(ordenCompraBean.getUnidadNegocioBean().getUniNeg());
//        System.out.println("estado: " + solicitudLogistico.getTipoEstadoBean().getCodigo());
//        System.out.println("tipo: " + solicitudLogistico.getTipoEstadoBean().getTipoCodigoBean().getDescripcion());
//        solicitudLogisticoService.cambiarEstadoSolicitudLogComprado(solicitudLogistico);

        //2. SE INSERTA LAS FACTURA
//        for (FacturaCompraBean fact : listFacturaCompraBean) {
        for (int i = 0; i < listFacturaCompraBean.size(); i++) {
            FacturaCompraBean fact = listFacturaCompraBean.get(i);
            FacturaCompraBean factura = new FacturaCompraBean();
            // seteando valores por defecto
            factura.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_FACT_COMPRA);
            factura.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_PENDIENTE_FAC_COM);
            factura.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);

            factura.getOrdenCompraBean().setIdOrdenCompra(ordenCompraBean.getIdOrdenCompra());
            factura.setSerieDoc(fact.getSerieDoc());
            factura.setNroDoc(fact.getNroDoc());
            factura.setDetraccionBean(fact.getDetraccionBean());
            factura.setIgv(fact.getIgv());
            factura.setImporte(fact.getImporte());
            factura.setMontoPago(fact.getMontoPago());
            factura.setTipoMonedaBean(fact.getTipoMonedaBean());
            factura.setTipoPrioridadBean(fact.getTipoPrioridadBean());
            factura.setFechaVenc(fact.getFechaVenc());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            factura.setCreaPor(usuarioBean.getUsuario());
            factura.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            registroCompraService.insertarFactura(factura);
            listFacturaCompraBean.get(i).setIdFacturaCompra(factura.getIdFacturaCompra());
            factura.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
            registroCompraService.llamarProGetAutorizadores(factura);
//            System.out.println("");
            factura.getListaDetRequerimientoCRBean().add(detRegistroCompraCRBean);

            for (DetRegistroCompraCRBean listaCr : fact.getListaDetRequerimientoCRBean()) {
//                DetRegistroCompraCRBean detRegistroCompraCRBean2 = new DetRegistroCompraCRBean();
                listaCr.setFacturaCompraBean(factura);
                listaCr.setOrdenCompraBean(ordenCompraBean);
//                detRegistroCompraCRBean2.setCentroResponsabilidadBean(facturaCompraBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
                listaCr.setTipoDistribucion(listaCr.getTipoDistribucion());
                listaCr.setValor(listaCr.getValorD());
                listaCr.setCreaPor(usuarioBean.getUsuario());
                listaCr.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                registroCompraService.insertarRegistroCR(listaCr);
            }
        }
    }

    @Transactional
    public void insertarParcial(OrdenCompraBean ordenCompraBean, SolicitudLogisticoBean solicitudLogisticoBean, List<FacturaCompraBean> listFacturaCompraBean, DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception {
        ordenCompraDAO.insertar(ordenCompraBean);
//        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//        SolicitudLogisticoBean solicitudLogistico = new SolicitudLogisticoBean();
//        solicitudLogistico.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_Comprado_Parcial);
//        solicitudLogistico.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
//        solicitudLogistico.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
//        solicitudLogistico.getUnidadNegocioBean().setUniNeg(ordenCompraBean.getUnidadNegocioBean().getUniNeg()); 
//        solicitudLogisticoService.cambiarEstadoSolicitudLogParcial(solicitudLogisticoBean); 

        //2. SE INSERTA LAS FACTURA
//        for (FacturaCompraBean fact : listFacturaCompraBean) {
        for (int i = 0; i < listFacturaCompraBean.size(); i++) {
            FacturaCompraBean fact = listFacturaCompraBean.get(i);
            FacturaCompraBean factura = new FacturaCompraBean();
            // seteando valores por defecto
            factura.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_FACT_COMPRA);
            factura.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_PENDIENTE_FAC_COM);
            factura.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);

            factura.getOrdenCompraBean().setIdOrdenCompra(ordenCompraBean.getIdOrdenCompra());
            factura.setSerieDoc(fact.getSerieDoc());
            factura.setNroDoc(fact.getNroDoc());
            factura.setDetraccionBean(fact.getDetraccionBean());
            factura.setIgv(fact.getIgv());
            factura.setImporte(fact.getImporte());
            factura.setMontoPago(fact.getMontoPago());
            factura.setTipoMonedaBean(fact.getTipoMonedaBean());
            factura.setTipoPrioridadBean(fact.getTipoPrioridadBean());
            factura.setFechaVenc(fact.getFechaVenc());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            factura.setCreaPor(usuarioBean.getUsuario());
            factura.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            registroCompraService.insertarFactura(factura);
            listFacturaCompraBean.get(i).setIdFacturaCompra(factura.getIdFacturaCompra());
            factura.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
            registroCompraService.llamarProGetAutorizadores(factura);
//            System.out.println("");
            factura.getListaDetRequerimientoCRBean().add(detRegistroCompraCRBean);

            for (DetRegistroCompraCRBean listaCr : fact.getListaDetRequerimientoCRBean()) {
//                DetRegistroCompraCRBean detRegistroCompraCRBean2 = new DetRegistroCompraCRBean();
                listaCr.setFacturaCompraBean(factura);
                listaCr.setOrdenCompraBean(ordenCompraBean);
//                detRegistroCompraCRBean2.setCentroResponsabilidadBean(facturaCompraBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
                listaCr.setTipoDistribucion(listaCr.getTipoDistribucion());
                listaCr.setValor(listaCr.getValorD());
                listaCr.setCreaPor(usuarioBean.getUsuario());
                listaCr.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                registroCompraService.insertarRegistroCR(listaCr);
            }
        }
    }

    @Transactional
    public void insertarOC(OrdenCompraBean ordenCompraBean, SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        ordenCompraDAO.insertar(ordenCompraBean);
        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
        SolicitudLogisticoBean solicitudLogistico = new SolicitudLogisticoBean();
        solicitudLogistico.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_REGISTRADO);
        solicitudLogistico.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
        solicitudLogistico.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
        solicitudLogistico.getUnidadNegocioBean().setUniNeg(ordenCompraBean.getUnidadNegocioBean().getUniNeg());
        solicitudLogisticoService.cambiarEstadoSolicitudLogComprado(solicitudLogisticoBean);

    }

    //cotizacion para Orden
    @Transactional
    public void insertarCotizacionOrden(OrdenCompraBean ordenCompraBean, List<DetCotizacionBean> listaDetalle) throws Exception {
        ordenCompraDAO.insertar(ordenCompraBean);
        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
        SolicitudLogisticoBean solicitudLogistico = new SolicitudLogisticoBean();
        solicitudLogistico.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_REGISTRADO);
        solicitudLogistico.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
        solicitudLogistico.getUnidadNegocioBean().setUniNeg(ordenCompraBean.getUnidadNegocioBean().getUniNeg());
        for (DetCotizacionBean listaDetalle1 : listaDetalle) {
            solicitudLogistico.setIdRequerimiento(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
            solicitudLogisticoService.cambiarEstadoSolicitudLogComprado(listaDetalle1.getSolicitudLogisticoBean());
        }
    }

    @Transactional
    public void modificar(OrdenCompraBean ordenCompraBean) throws Exception {
        ordenCompraDAO.actualizar(ordenCompraBean);
    }

    @Transactional
    public void cambiarEstadoOrdenCompraRegistrado(OrdenCompraBean ordenCompraBean) throws Exception {
        ordenCompraDAO.cambiarEstadoOrdenCompraRegistrado(ordenCompraBean);
    }

    public List<OrdenCompraBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception {
        return ordenCompraDAO.obtenerTodosPorUniNeg(uniNeg);
    }

    public List<OrdenCompraBean> obtenerTodosSolDes(OrdenCompraBean bean) throws Exception {
        return ordenCompraDAO.obtenerTodosSolDes(bean);
    }

    public List<OrdenCompraBean> obtenerTodosM(OrdenCompraBean bean) throws Exception {
        return ordenCompraDAO.obtenerTodosM(bean);
    }

    public List<OrdenCompraBean> obtenerPorFiltro(OrdenCompraBean orden) throws Exception {
        return ordenCompraDAO.obtenerPorFiltro(orden);
    }

    public List<OrdenCompraBean> obtenerListaPorId(Integer idordenCompra) throws Exception {
        return ordenCompraDAO.obtenerListaPorId(idordenCompra);
    }

    public Integer obtenerUltimo(String uniNeg) throws Exception {
        return ordenCompraDAO.obtenerUltimo(uniNeg);
    }

    public OrdenCompraBean obtenerPorId(OrdenCompraBean ordenCompraBean) throws Exception {
        return ordenCompraDAO.obtenerPorId(ordenCompraBean);
    }

    public SolicitudLogisticoBean obtenerPorSol(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return ordenCompraDAO.obtenerPorSol(solicitudLogisticoBean);
    }

    public String obtenerUltimoOrden(String uniNeg) throws Exception {
        return ordenCompraDAO.obtenerUltimoOrden(uniNeg);
    }
}
