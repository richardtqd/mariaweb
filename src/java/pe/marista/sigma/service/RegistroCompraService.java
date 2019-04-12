package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.dao.RegistroCompraDAO;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentaFacturaBean;
import pe.marista.sigma.bean.DetRegistroCompraBean;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.DetRegistroCompraDAO;
import pe.marista.sigma.dao.MensajeDAO;
import pe.marista.sigma.dao.OrdenCompraDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.reporte.RegistroCompraRepBean;

public class RegistroCompraService {

    private RegistroCompraDAO registroCompraDAO;
    private MensajeDAO mensajeDAO;
    private DetRegistroCompraDAO detRegistroCompraDAO;
    OrdenCompraDAO ordenCompraDAO;

    public RegistroCompraDAO getRegistroCompraDAO() {
        return registroCompraDAO;
    }

    public void setRegistroCompraDAO(RegistroCompraDAO registroCompraDAO) {
        this.registroCompraDAO = registroCompraDAO;
    }
 
    @Transactional
    public void insertar(RegistroCompraBean registroCompra, List<OrdenCompraDetalleBean> listaOrdenCompraDetalleBean, DetRegistroCompraBean detRegistroCompraBean, List<FacturaCompraBean> listFacturaCompraBean, OrdenCompraBean ordenCompraBean, DetRegistroCompraCRBean detRegistroCompraCRBean, FacturaCompraBean facturaCompraBean,
            List<CuentaFacturaBean> listaCuentaFacturaBean, CuentaFacturaBean cuentaFacturaBean) throws Exception {
//        registroCompraBean.getTipoStatusRegCBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_RCL);
//        registroCompraDAO.insertar(registroCompraBean);
//        registroCompraBean.setObjeto(MaristaConstantes.OBJ_SOL_REG_COMPRA);
//        registroCompraDAO.llamarAutorizadores(registroCompraBean);

        //1.- SE INSERTA EL REGISTRO COMPRA
        registroCompraDAO.insertar(registroCompra);

        OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
        OrdenCompraBean ordenCompra = new OrdenCompraBean();
        if (registroCompra.getFlgRecibido() == true) {
            ordenCompra.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_REGISTRADO);
            ordenCompra.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        } else {
            ordenCompra.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
            ordenCompra.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        }
        ordenCompra.setIdOrdenCompra(ordenCompraBean.getIdOrdenCompra());
        ordenCompra.getUnidadNegocioBean().setUniNeg(registroCompra.getUnidadNegocioBean().getUniNeg());
        ordenCompraService.cambiarEstadoOrdenCompraRegistrado(ordenCompra);

        //2. SE INSERTA LAS FACTURA
//        for (FacturaCompraBean fact : listFacturaCompraBean) {
        for (int i = 0; i < listFacturaCompraBean.size(); i++) {
            FacturaCompraBean fact = listFacturaCompraBean.get(i);
            FacturaCompraBean factura = new FacturaCompraBean();
            // seteando valores por defecto
            factura.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_FACT_COMPRA);
            factura.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_PENDIENTE_FAC_COM);
            factura.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);

            factura.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
            factura.setSerieDoc(fact.getSerieDoc());
            factura.setNroDoc(fact.getNroDoc());
            factura.setDetraccionBean(fact.getDetraccionBean());
            factura.setImpuesto(fact.getImpuesto());
            factura.setIgv(fact.getIgv());
            factura.setImporte(fact.getImporte());
            factura.setMontoPago(fact.getMontoPago());
            factura.setTipoMonedaBean(fact.getTipoMonedaBean());
            factura.setTipoPrioridadBean(fact.getTipoPrioridadBean());
            factura.setFechaVenc(fact.getFechaVenc());
            factura.setGlosa(fact.getGlosa());
            factura.setFlgAdelanto(fact.getFlgAdelanto());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            factura.setCreaPor(usuarioBean.getUsuario());
            factura.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            registroCompraDAO.insertarFactura(factura);
            listFacturaCompraBean.get(i).setIdFacturaCompra(factura.getIdFacturaCompra());
            factura.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
            registroCompraDAO.llamarProGetAutorizadores(factura); 
            factura.getListaDetRequerimientoCRBean().add(detRegistroCompraCRBean); 
//            for (CuentaFacturaBean listaCr : fact.getListaCuentaFacturaBean()) {
////            for (DetRegistroCompraCRBean listaCr : fact.getListaDetRequerimientoCRBean()) {
//                DetRegistroCompraCRBean lista = new DetRegistroCompraCRBean();
//                lista.setFacturaCompraBean(factura);
//                lista.setRegistroCompraBean(registroCompra); 
////                lista.setTipoDistribucion(listaCr.getTipoDistribucion());
//                lista.setValor(listaCr.getValor());
//                lista.setCreaPor(usuarioBean.getUsuario());
//                lista.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                registroCompraDAO.insertarRegistroCR(lista);
//                
//            }
            
            //CUENTAfACTURA
                for (CuentaFacturaBean listCuenta : fact.getListaCuentaFacturaBean()) {
                    listCuenta.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 
                    listCuenta.getFacturaCompraBean().setIdFacturaCompra(factura.getIdFacturaCompra()); 
                    listCuenta.setCreaPor(usuarioBean.getUsuario());
//                    listCuenta.getTipoDistribucion().setIdCodigo(listCuenta.getFacturaCompraBean().getCodDistribucion());
                    System.out.println("cr" + listCuenta.getCentroResponsabilidadBean().getCr());
                    registroCompraDAO.insertarCuentaFact(listCuenta);
                }

        }

        //3. SE INSERTA EL DETALLE REGISTRA EL ID DE LA FACTURA
        for (OrdenCompraDetalleBean ordCom : listaOrdenCompraDetalleBean) {
            //registro de compra
            DetRegistroCompraBean regComDet = new DetRegistroCompraBean();
            regComDet.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
            regComDet.getRegistroCompraBean().setAnio(registroCompra.getOrdenCompraBean().getAnio());
            regComDet.getRegistroCompraBean().setEntidadBean(registroCompra.getOrdenCompraBean().getEntidadBean());
            regComDet.setImporte(ordCom.getCatalogoBean().getPrecioRef());//

//            regComDet.setCantidad(detRegistroCompraBean.getCantidad());
            // orden compra
            regComDet.getOrdenCompraBean().setIdOrdenCompra(ordCom.getOrdenCompraBean().getIdOrdenCompra());
            regComDet.getOrdenCompraDetalleBean().setIdDetOrdenCompra(ordCom.getIdDetOrdenCompra());
            regComDet.setCatalogoBean(ordCom.getCatalogoBean());
            regComDet.setTipoUniMedBean(ordCom.getTipoUniMedBean());
            regComDet.getUnidadNegocioBean().setUniNeg(registroCompra.getUnidadNegocioBean().getUniNeg());
            regComDet.setCantidadRecibida(ordCom.getCantidadRecibida());
            regComDet.setDescripcion(registroCompra.getObsVenc());//
            regComDet.setCantidad(ordCom.getCantidad());
            regComDet.setPrecio(ordCom.getCatalogoBean().getPrecioRef() * ordCom.getCantidadRecibida());//
            regComDet.setPlanContableCuentaDSoliBean(ordCom.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
            regComDet.setPlanContableCuentaHSoliBean(ordCom.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
            regComDet.setConceptoBean(ordCom.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean());
            regComDet.setConceptoUniNegBean(ordCom.getSolicitudLogDetalleBean().getConceptoUniNegBean());

//            
//            DetRegistroCompraCRBean detRegistroCR = new DetRegistroCompraCRBean();
//            detRegistroCR.getRegistroCompraBean().setIdRegistroCompra(regComDet.getRegistroCompraBean().getIdRegistroCompra());
//            detRegistroCR.getUnidadNegocioBean().setUniNeg(regComDet.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getDetRequerimientoCRBean().getUnidadNegocioBean().getUniNeg());
//            detRegistroCR.getCentroResponsabilidadBean().setCr(regComDet.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getDetRequerimientoCRBean().getCentroResponsabilidadBean().getCr());
//            detRegistroCR.getTipoDistribucion().setCodigo(regComDet.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getDetRequerimientoCRBean().getTipoDistribucion().getCodigo());
//            detRegistroCR.setValor(regComDet.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getDetRequerimientoCRBean().getValor());
//            detRegistroCR.setValorD(regComDet.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getDetRequerimientoCRBean().getValorD());
//      
            for (int i = 0; i < listFacturaCompraBean.size(); i++) {
                if (listFacturaCompraBean.get(i).getNroDoc().trim().equals(ordCom.getFacturaCompraBean().getNroDoc().trim())) {
                    if (listFacturaCompraBean.get(i).getSerieDoc().trim().equals(ordCom.getFacturaCompraBean().getSerieDoc().trim())) {
                        regComDet.getFacturaCompraBean().setIdFacturaCompra(listFacturaCompraBean.get(i).getIdFacturaCompra());
                        break;
                    }
                }
//                if (listFacturaCompraBean.get(i).getSerieDoc().equals(ordCom.getFacturaCompraBean().getSerieDoc())
//                        && listFacturaCompraBean.get(i).getNroDoc().equals(ordCom.getFacturaCompraBean().getNroDoc())) {
//                    regComDet.getFacturaCompraBean().setIdFacturaCompra(listFacturaCompraBean.get(i).getIdFacturaCompra());
//                    break;
//                } 
            }

            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            regComDet.setCreaPor(usuarioBean.getUsuario());
            detRegistroCompraDAO.insertar(regComDet);
        }

//        // CR
//        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//        List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
//        listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(registroCompra.getIdRegistroCompra(), registroCompra.getUnidadNegocioBean().getUniNeg());
//
////        Insertar RegistroCR
//        for (DetRequerimientoCRBean solLogDet : listaDetRequerimientoCRBean) {
//            //registro de compra
//            DetRegistroCompraCRBean registroCR = new DetRegistroCompraCRBean();
//
//            registroCR.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
//            registroCR.getCentroResponsabilidadBean().setCr(solLogDet.getCentroResponsabilidadBean().getCr());
//
//            registroCR.setTipoDistribucion(solLogDet.getTipoDistribucion());
//            registroCR.setValor(solLogDet.getValorD());
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            registroCR.setCreaPor(usuarioBean.getUsuario());
//            registroCR.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            registroCompraDAO.insertarRegistroCR(registroCR);
//            System.out.println("OK");
//        }
        // CR
////        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//        List<DetRegistroCompraCRBean> listaDetRegistroCompraCRBean = new ArrayList<>();
////        listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(registroCompra.getIdRegistroCompra(), registroCompra.getUnidadNegocioBean().getUniNeg());
//
////        Insertar RegistroCR
//        for (DetRegistroCompraCRBean RegComDet : listaDetRegistroCompraCRBean) {
//            //registro de compra
//            DetRegistroCompraCRBean registroCR = new DetRegistroCompraCRBean();
//
//            registroCR.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
//            registroCR.getCentroResponsabilidadBean().setCr(RegComDet.getCentroResponsabilidadBean().getCr());
//
//            registroCR.setTipoDistribucion(RegComDet.getTipoDistribucion());
//            registroCR.setValor(RegComDet.getValorD());
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            registroCR.setCreaPor(usuarioBean.getUsuario());
//            registroCR.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            registroCompraDAO.insertarRegistroCR(registroCR);
//            System.out.println("OK");
//        }
//         registroCompraDAO.llamarDuende();
    }
    @Transactional
    public void eliminarCuentaFact(Integer idFact, String uniNeg) throws Exception {
        registroCompraDAO.eliminarCuentaFact(idFact, uniNeg);
    }

    @Transactional
    public void modificar(RegistroCompraBean registroCompra, List<DetRegistroCompraBean> listaDetRegistroCompraBean, DetRegistroCompraBean detRegistroCompraBean, List<FacturaCompraBean> listFacturaCompraBean, OrdenCompraBean ordenCompraBean, DetRegistroCompraCRBean detRegistroCompraCRBean, FacturaCompraBean facturaCompraBean,
            List<CuentaFacturaBean> listaCuentaFacturaBean, CuentaFacturaBean cuentaFacturaBean) throws Exception {
        registroCompraDAO.modificar(registroCompra);
        
        OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
        OrdenCompraBean ordenCompra = new OrdenCompraBean();
        if (registroCompra.getFlgRecibido() == true) {
            ordenCompra.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_REGISTRADO);
            ordenCompra.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        } else {
            ordenCompra.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
            ordenCompra.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        }
        ordenCompra.setIdOrdenCompra(registroCompra.getOrdenCompraBean().getIdOrdenCompra());
        ordenCompra.getUnidadNegocioBean().setUniNeg(registroCompra.getUnidadNegocioBean().getUniNeg());
        ordenCompraService.cambiarEstadoOrdenCompraRegistrado(ordenCompra);

        //2. SE INSERTA LAS FACTURA
//        for (FacturaCompraBean fact : listFacturaCompraBean) {
        for (int i = 0; i < listFacturaCompraBean.size(); i++) {
            FacturaCompraBean fact = listFacturaCompraBean.get(i);
            FacturaCompraBean factura = new FacturaCompraBean();
            // seteando valores por defecto
            factura.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_FACT_COMPRA);
            factura.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_PENDIENTE_FAC_COM);
            factura.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);

            factura.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
            factura.setSerieDoc(fact.getSerieDoc());
            factura.setNroDoc(fact.getNroDoc());
            factura.setDetraccionBean(fact.getDetraccionBean());
            factura.setImpuesto(fact.getImpuesto());
            factura.setIgv(fact.getIgv());
            factura.setImporte(fact.getImporte());
            factura.setMontoPago(fact.getMontoPago());
            factura.setTipoMonedaBean(fact.getTipoMonedaBean());
            factura.setTipoPrioridadBean(fact.getTipoPrioridadBean());
            factura.setFechaVenc(fact.getFechaVenc());
            factura.setGlosa(fact.getGlosa());
            factura.setFlgAdelanto(fact.getFlgAdelanto());
            factura.setIdFacturaCompra(fact.getIdFacturaCompra());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            factura.setCreaPor(usuarioBean.getUsuario());
            factura.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            registroCompraDAO.modificarFactura(factura);
            listFacturaCompraBean.get(i).setIdFacturaCompra(factura.getIdFacturaCompra());
            factura.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
            registroCompraDAO.llamarProGetAutorizadores(factura); 
            factura.getListaDetRequerimientoCRBean().add(detRegistroCompraCRBean);  
            
            //CUENTAfACTURA
                for (CuentaFacturaBean listCuenta : fact.getListaCuentaFacturaBean()) {
                    listCuenta.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 
                    listCuenta.getFacturaCompraBean().setIdFacturaCompra(factura.getIdFacturaCompra()); 
                    listCuenta.setCreaPor(usuarioBean.getUsuario());
//                    listCuenta.getTipoDistribucion().setIdCodigo(listCuenta.getFacturaCompraBean().getCodDistribucion());
                    System.out.println("cr" + listCuenta.getCentroResponsabilidadBean().getCr());
                    registroCompraDAO.modificarCuentaFact(listCuenta);
                }

        }

        //3. SE INSERTA EL DETALLE REGISTRA EL ID DE LA FACTURA
        detRegistroCompraDAO.eliminar(registroCompra.getIdRegistroCompra());
        for (DetRegistroCompraBean ordCom : listaDetRegistroCompraBean) {
            //registro de compra
            DetRegistroCompraBean regComDet = new DetRegistroCompraBean();
            regComDet.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
            regComDet.getRegistroCompraBean().setAnio(registroCompra.getOrdenCompraBean().getAnio());
            regComDet.getRegistroCompraBean().setEntidadBean(registroCompra.getOrdenCompraBean().getEntidadBean());
            regComDet.setImporte(ordCom.getCatalogoBean().getPrecioRef());//

//            regComDet.setCantidad(detRegistroCompraBean.getCantidad());
            // orden compra
            regComDet.getOrdenCompraBean().setIdOrdenCompra(ordCom.getOrdenCompraBean().getIdOrdenCompra());
            regComDet.getOrdenCompraDetalleBean().setIdDetOrdenCompra(ordCom.getOrdenCompraDetalleBean().getIdDetOrdenCompra());
            regComDet.setCatalogoBean(ordCom.getCatalogoBean());
            regComDet.setTipoUniMedBean(ordCom.getTipoUniMedBean());
            regComDet.getUnidadNegocioBean().setUniNeg(registroCompra.getUnidadNegocioBean().getUniNeg());
            regComDet.setCantidadRecibida(ordCom.getCantidadRecibida());
            regComDet.setDescripcion(registroCompra.getObsVenc());//
            regComDet.setCantidad(ordCom.getCantidad());
            regComDet.setPrecio(ordCom.getCatalogoBean().getPrecioRef()* ordCom.getCantidadRecibida());//
            regComDet.setPlanContableCuentaDSoliBean(ordCom.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
            regComDet.setPlanContableCuentaHSoliBean(ordCom.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
            regComDet.setConceptoBean(ordCom.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean());
            regComDet.setConceptoUniNegBean(ordCom.getOrdenCompraDetalleBean().getSolicitudLogDetalleBean().getConceptoUniNegBean());
 
            for (int i = 0; i < listFacturaCompraBean.size(); i++) {
                if (listFacturaCompraBean.get(i).getNroDoc().trim().equals(ordCom.getFacturaCompraBean().getNroDoc().trim())) {
                    if (listFacturaCompraBean.get(i).getSerieDoc().trim().equals(ordCom.getFacturaCompraBean().getSerieDoc().trim())) {
                        regComDet.getFacturaCompraBean().setIdFacturaCompra(listFacturaCompraBean.get(i).getIdFacturaCompra());
                        break;
                    }
                } 
            }

            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            regComDet.setCreaPor(usuarioBean.getUsuario());
//            detRegistroCompraDAO.eliminar(registroCompra.getIdRegistroCompra());
            detRegistroCompraDAO.insertar(regComDet);
        }
    }

    public List<RegistroCompraBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception {
        return registroCompraDAO.obtenerTodosPorUniNeg(uniNeg);
    }

    public RegistroCompraBean obtenerPorId(RegistroCompraBean registroCompraBean) throws Exception {
        return registroCompraDAO.obtenerPorId(registroCompraBean);

    }

    // mis solicitudes, mensaje, lista de factura para obtener la lista de factura realizadas
    public List<FacturaCompraBean> obtenerPorIdFact(FacturaCompraBean bean) throws Exception {
        return registroCompraDAO.obtenerPorIdFact(bean);
    }

    public List<RegistroCompraBean> obtenerPorFiltroRC(RegistroCompraBean orden) throws Exception {
        return registroCompraDAO.obtenerPorFiltroRC(orden);
    }

    public List<RegistroCompraBean> obtenerPorFiltroRCAutorizados(RegistroCompraBean registroCompraBean) throws Exception {
        registroCompraBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        registroCompraBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_REG_AUTORIZADO);
        return registroCompraDAO.obtenerPorFiltroRCAutorizados(registroCompraBean);
    }

    public List<RegistroCompraBean> obtenerSolicitudPorFiltroGestor(RegistroCompraBean registroCompraBean) throws Exception {
        return registroCompraDAO.obtenerSolicitudPorFiltroGestor(registroCompraBean);
    }

    public List<RegistroCompraBean> obtenerSolicitudPorFiltroPersonal(RegistroCompraBean registroCompraBean) throws Exception {
        return registroCompraDAO.obtenerSolicitudPorFiltroPersonal(registroCompraBean);
    }

    public List<RegistroCompraBean> obtenerTodosAutorizados(RegistroCompraBean registroCompraBean) throws Exception {
        return registroCompraDAO.obtenerTodosAutorizados(registroCompraBean);
    }

    @Transactional
    public void modificarAprobacion(RegistroCompraBean registroCompraBean) throws Exception {
        registroCompraDAO.actualizarAprobacion(registroCompraBean);
    }

    public List<RegistroCompraBean> obtenerTodosAprob() throws Exception {
        return registroCompraDAO.obtenerTodosAprob();
    }

    public String obtenerUltimoRegistro(String uniNeg) throws Exception {
        return registroCompraDAO.obtenerUltimoRegistro(uniNeg);
    }

    @Transactional
    public void anularSolicitudRegistro(RegistroCompraBean registroCompraBean, MensajeBean mensajeBean) throws Exception {
        registroCompraBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
        registroCompraBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_ANULADO);
        registroCompraDAO.anularSolicitudRegistro(registroCompraBean);

        mensajeBean.setObjeto(MaristaConstantes.OBJ_SOL_REG_COMPRA);
        mensajeBean.setIdObjeto(registroCompraBean.getIdRegistroCompra());
        mensajeBean.getUnidadNegocioBean().setUniNeg(registroCompraBean.getUnidadNegocioBean().getUniNeg());
////        mensajeDAO.obtenerMensajePorIdTabla(mensajeBean);
//        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
//        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ELIMINADO);
//        mensajeDAO.cambiarStatusMsjeAnulado(mensajeBean);
        mensajeDAO.eliminarMensaje(mensajeBean);
    }

    @Transactional
    public void cambiarEstadoSolicitudRC(RegistroCompraBean registroCompraBean) throws Exception {
        Boolean flgStatusAutorizado = null; //solicitud autorizada
        Integer nivelAutoriza = null;

        //obteniendo el nivel de autorizadores de la solicitud
        if (registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() == null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 1;
        }
        if (registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 2;
        }
        if (registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() != null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && registroCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null) {
            nivelAutoriza = 3;
        }

        //comparando el nivel de autorizadores con su flag correspondiente
        if (nivelAutoriza == 1) {
            if (registroCompraBean.getFlgAutoriza1() != null) {
                if (registroCompraBean.getFlgAutoriza1() == true) {
                    flgStatusAutorizado = true;
                } else {
                    flgStatusAutorizado = false;
                }
            }
        }
        if (nivelAutoriza == 2) {
            if (registroCompraBean.getFlgAutoriza1() != null) {
                if (registroCompraBean.getFlgAutoriza1() == true) {
                    if (registroCompraBean.getFlgAutoriza2() != null) {
                        if (registroCompraBean.getFlgAutoriza2() == true) {
                            flgStatusAutorizado = true;
                        } else {
                            flgStatusAutorizado = false;
                        }
                    }
                }
            }
        }
        if (nivelAutoriza == 3) {
            if (registroCompraBean.getFlgAutoriza1() != null && registroCompraBean.getFlgAutoriza2() != null && registroCompraBean.getFlgAutoriza3() != null
                    && registroCompraBean.getFlgAutoriza1() == true && registroCompraBean.getFlgAutoriza2() == true && registroCompraBean.getFlgAutoriza3() == true) {
                flgStatusAutorizado = true;
            } else {
                flgStatusAutorizado = false;
            }
        }

        //cambiando el estado estado de la solicicitud, true=autorizado y flase=no autorizado
        if (flgStatusAutorizado != null) {
            if (flgStatusAutorizado == true) {
                registroCompraBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
                registroCompraBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
            }
            if (flgStatusAutorizado == false) {
                registroCompraBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
                registroCompraBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_NO_AUTORIZADO);
            }
        }
        if (flgStatusAutorizado == null) {
            registroCompraBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
            registroCompraBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_PENDIENTE);
        }
        registroCompraDAO.cambiarEstadoSolicitudRC(registroCompraBean);
    }

    public void cambiarEstadoPagadoSolicitudRC(RegistroCompraBean registroCompraBean) throws Exception {
        registroCompraDAO.cambiarEstadoSolicitudRC(registroCompraBean);
    }

    // facturas
    @Transactional
    public void insertarFactura(FacturaCompraBean facturaCompraBean) throws Exception {
        facturaCompraBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_FACT_COMPRA);
        facturaCompraBean.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_PENDIENTE_FAC_COM);
        facturaCompraBean.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);

        registroCompraDAO.insertarFactura(facturaCompraBean);
        facturaCompraBean.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
        registroCompraDAO.llamarProGetAutorizadores(facturaCompraBean);

    }

    @Transactional
    public void modificarFactura(FacturaCompraBean facturaCompraBean) throws Exception {
        registroCompraDAO.modificarFactura(facturaCompraBean);
    }

    @Transactional
    public void cambiarEstadoFacturaCompra(FacturaCompraBean facturaCompraBean) throws Exception {
        Boolean flgStatusAutorizado = null; //solicitud autorizada
        Integer nivelAutoriza = null;

        //obteniendo el nivel de autorizadores de la solicitud
        if (facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() == null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 1;
        }
        if (facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 2;
        }
        if (facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza3() != null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && facturaCompraBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null) {
            nivelAutoriza = 3;
        }

        //comparando el nivel de autorizadores con su flag correspondiente
        if (nivelAutoriza == 1) {
            if (facturaCompraBean.getFlgAutoriza1() != null) {
                if (facturaCompraBean.getFlgAutoriza1() == true) {
                    flgStatusAutorizado = true;
                } else {
                    flgStatusAutorizado = false;
                }
            }
        }
        if (nivelAutoriza == 2) {
            if (facturaCompraBean.getFlgAutoriza1() != null) {
                if (facturaCompraBean.getFlgAutoriza1() == true) {
                    if (facturaCompraBean.getFlgAutoriza2() != null) {
                        if (facturaCompraBean.getFlgAutoriza2() == true) {
                            flgStatusAutorizado = true;
                        } else {
                            flgStatusAutorizado = false;
                        }
                    }
                }
            }
        }
        if (nivelAutoriza == 3) {
            if (facturaCompraBean.getFlgAutoriza1() != null && facturaCompraBean.getFlgAutoriza2() != null && facturaCompraBean.getFlgAutoriza3() != null
                    && facturaCompraBean.getFlgAutoriza1() == true && facturaCompraBean.getFlgAutoriza2() == true && facturaCompraBean.getFlgAutoriza3() == true) {
                flgStatusAutorizado = true;
            } else {
                flgStatusAutorizado = false;
            }
        }

        //cambiando el estado estado de la solicicitud, true=autorizado y flase=no autorizado
        if (flgStatusAutorizado != null) {
            if (flgStatusAutorizado == true) {
                facturaCompraBean.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
                facturaCompraBean.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.COD_SOL_REG_AUTORIZADA);
            }
            if (flgStatusAutorizado == false) {
                facturaCompraBean.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
                facturaCompraBean.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.COD_SOL_REG_NO_AUTORIZADA);
            }
        }
        if (flgStatusAutorizado == null) {
            facturaCompraBean.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
            facturaCompraBean.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.COD_SOL_PENDIENTE);
        }
        registroCompraDAO.cambiarEstadoFacturaCompra(facturaCompraBean);
    }

    public List<FacturaCompraBean> obtenerTodosPorUniNegFact(String uniNeg) throws Exception {
        return registroCompraDAO.obtenerTodosPorUniNegFact(uniNeg);
    }

    public FacturaCompraBean obtenerPorIdFactura(FacturaCompraBean facturaCompraBean) throws Exception {
        return registroCompraDAO.obtenerPorIdFactura(facturaCompraBean);
    }
    public FacturaCompraBean obtenerPorIdFacturaVer2(FacturaCompraBean facturaCompraBean) throws Exception {
        return registroCompraDAO.obtenerPorIdFacturaVer2(facturaCompraBean);
    }

    public List<FacturaCompraBean> obtenerFacturaAutorizadoPorFiltro(FacturaCompraBean facturaCompraBean) throws Exception {
        facturaCompraBean.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
        facturaCompraBean.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.COD_SOL_REG_AUTORIZADA);
        return registroCompraDAO.obtenerFacturaAutorizadoPorFiltro(facturaCompraBean);
    }

    @Transactional
    public void modificarFacturaReg(FacturaCompraBean facturaCompraBean) throws Exception {
        registroCompraDAO.modificarFacturaReg(facturaCompraBean);
    }

    public MensajeDAO getMensajeDAO() {
        return mensajeDAO;
    }

    public void setMensajeDAO(MensajeDAO mensajeDAO) {
        this.mensajeDAO = mensajeDAO;
    }

    public DetRegistroCompraDAO getDetRegistroCompraDAO() {
        return detRegistroCompraDAO;
    }

    public void setDetRegistroCompraDAO(DetRegistroCompraDAO detRegistroCompraDAO) {
        this.detRegistroCompraDAO = detRegistroCompraDAO;
    }

    public void insertarDetRegistroCompraCR(DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception {
        registroCompraDAO.insertarDetRegistroCompraCR(detRegistroCompraCRBean);
    }

    //RegistroCompraCR
    public void insertarRegistroCR(DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception {
        registroCompraDAO.insertarRegistroCR(detRegistroCompraCRBean);
    }

    public Object llamarProGetAutorizadores(FacturaCompraBean FacturaCompraBean) throws Exception {
        return registroCompraDAO.llamarProGetAutorizadores(FacturaCompraBean);
    }

    public Object llamarDuende() throws Exception {
        return registroCompraDAO.llamarDuende();
    }

    public List<DetRequerimientoCRBean> ObtenerPorIdCR(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return registroCompraDAO.ObtenerPorIdCR(solicitudLogisticoBean);
    }

    public List<DetRequerimientoCRBean> ObtenerPorIdCRDis(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return registroCompraDAO.ObtenerPorIdCRDis(solicitudLogisticoBean);
    }

    public List<RegistroCompraRepBean> obtenerRegistroCompraCabecera(Integer idRegistroCompra, String uniNeg) throws Exception {
        return registroCompraDAO.obtenerRegistroCompraCabecera(idRegistroCompra, uniNeg);
    }

    //CuentaFactura
    public void insertarCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception {
        registroCompraDAO.insertarCuentaFact(cuentaFacturaBean);
    } 

//    public FacturaCompraBean obtenerPorIdFacturaPorIdCompra(Integer idRegistroCompra, String uniNeg) throws Exception {
//        return registroCompraDAO.obtenerPorIdFacturaPorIdCompra(idRegistroCompra, uniNeg);
//    } 

    public List<CuentaFacturaBean> obtenerCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception {
        return registroCompraDAO.obtenerCuentaFact(cuentaFacturaBean);
    }
    public List<CuentaFacturaBean> obtenerCuentaFactPorIdFactura(Integer idFact,String uniNeg) throws Exception {
        return registroCompraDAO.obtenerCuentaFactPorIdFactura(idFact,uniNeg);
    }
    public List<CuentaFacturaBean> obtenerCuentasPorIdFactura(Integer idFact,String uniNeg) throws Exception {
        return registroCompraDAO.obtenerCuentasPorIdFactura(idFact,uniNeg);
    }

    public void modificarCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception {
        registroCompraDAO.modificarCuentaFact(cuentaFacturaBean);
    }

    public void modificarCuentaFactDsctoNotaCred(Double monto, Integer idFact, String uniNeg) throws Exception {
        registroCompraDAO.modificarCuentaFactDsctoNotaCred(monto, idFact, uniNeg);
    }

    public List<FacturaCompraBean> obtenerFactura(FacturaCompraBean facturaCompraBean) throws Exception {
        return registroCompraDAO.obtenerFactura(facturaCompraBean);
    } 

    public List<CuentaFacturaBean> obtenerCuentaFactNota(Integer idFacturaCompra, String uniNeg) throws Exception {
        return registroCompraDAO.obtenerCuentaFactNota(idFacturaCompra, uniNeg);
    }

    public String obtenerCuentaDistribucion(Integer idFacturaCompra, String uniNeg) throws Exception {
       return registroCompraDAO.obtenerCuentaDistribucion(idFacturaCompra, uniNeg);
    }

    public List<CuentaFacturaBean> obtenerCuentaDistribucionCr(Integer idFacturaCompra, String uniNeg) throws Exception {
        return registroCompraDAO.obtenerCuentaDistribucionCr(idFacturaCompra, uniNeg);
    }

    public void modificarCuentaFactValorNoC(Double valor, Integer idFact, String uniNeg,Integer cr) throws Exception {
        registroCompraDAO.modificarCuentaFactValorNoC(valor, idFact, uniNeg,cr);
    }
 
    
}
