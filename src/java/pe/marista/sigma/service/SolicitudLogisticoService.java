package pe.marista.sigma.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.MensajeDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDetalleDAO;
import pe.marista.sigma.dao.InventarioAlmacenDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.util.Mailing;

public class SolicitudLogisticoService {

    private SolicitudLogisticoDAO solicitudLogisticoDAO;
    private SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO;
    private InventarioAlmacenDAO InventarioAlmacenDAO;
    private MensajeDAO mensajeDAO;

    //Getter and Setter
    public SolicitudLogisticoDAO getSolicitudLogisticoDAO() {
        return solicitudLogisticoDAO;
    }

    public void setSolicitudLogisticoDAO(SolicitudLogisticoDAO solicitudLogisticoDAO) {
        this.solicitudLogisticoDAO = solicitudLogisticoDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(SolicitudLogisticoBean solicitudLogisticoBean, List<SolicitudLogDetalleBean> listaDetalle, SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        //1.- insertando solicitud logistico
        solicitudLogisticoDAO.insertar(solicitudLogisticoBean);
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //2.- insertando detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
//            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaDetalle1.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
            listaDetalle1.setFlgComprado(Boolean.TRUE); 
            listaDetalle1.getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
//            listaDetalle1.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
//            listaDetalle1.getCatalogoBean().getInventarioActivoBean().setStockActual(solicitudLogDetalleBean.getCatalogoBean().getInventarioActivoBean().getStockActual());

//            if (listaDetalle1.getHoraSalida().equals("")) {
//                listaDetalle1.setHoraSalida(null);
//            }
//            if (listaDetalle1.getHoraRegreso().equals("")) {
//                listaDetalle1.setHoraRegreso(null);
//            }
            solicitudLogisticoDetalleDAO.insertar(listaDetalle1);
            catalogoService.actualizarPrecioRef(listaDetalle1.getCatalogoBean());

            //3.- insertando detalle CR
        }
//        for (int i = 0; i < solicitudLogisticoBean.getListaDetRequerimientoCRBean().size(); i++) {
//            DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
//            detRequerimientoCRBean.setSolicitudLogisticoBean(solicitudLogisticoBean);
//            detRequerimientoCRBean.setCentroResponsabilidadBean(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
//            detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(solicitudLogisticoBean.getCodDistribucion()));
//            detRequerimientoCRBean.setValor(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getValorD());
//            detRequerimientoCRBean.setCreaPor(solicitudLogisticoBean.getCreaPor());
//            solicitudLogisticoDAO.insertarDetRequerimientoCR(detRequerimientoCRBean);
//        }

        solicitudLogisticoBean.setObjeto(MaristaConstantes.OBJ_SOL_LOG);
        solicitudLogisticoDAO.llamarAutorizadores(solicitudLogisticoBean);

        //MENSAJE LOGISTICO
        TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
        Mailing mailing = new Mailing();

        TipoSolicitudBean tipo = new TipoSolicitudBean();
        tipo.setIdTipoSolicitud(solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoSolicitud());
        tipo.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
        tipo = tipoSolicitudService.obtenerTipoSolicitudPorId(tipo);

            if (tipo.getNombre().equals(MaristaConstantes.TIPO_SOL_REQ_AUTO)) {
                String mensaje = "meylinsuyin@gmail.com";
                String mensaje2 = tipoSolicitudService.obtenerCorreoCorPorAutorizador(MaristaConstantes.TIPO_SOL_REQ_AUTO, usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println("correo "+mensaje2);
                mailing.enviarMensajeAutoriza(mensaje2, solicitudLogisticoBean);
            }

    }

    public void insertarServicio(SolicitudLogisticoBean solicitudLogisticoBean, List<SolicitudLogDetalleBean> listaDetalle, SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        solicitudLogisticoDAO.insertarServicio(solicitudLogisticoBean);
        //2.- insertando detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
            listaDetalle1.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetalle1.getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
            listaDetalle1.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
            solicitudLogisticoDetalleDAO.insertarServicio(listaDetalle1);

            //3.- insertando detalle CR
        }
        for (int i = 0; i < solicitudLogisticoBean.getListaDetRequerimientoCRBean().size(); i++) {
            DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
            detRequerimientoCRBean.setSolicitudLogisticoBean(solicitudLogisticoBean);
            detRequerimientoCRBean.setCentroResponsabilidadBean(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
            detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(solicitudLogisticoBean.getCodDistribucion()));
            detRequerimientoCRBean.setValor(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getValorD());
            detRequerimientoCRBean.setCreaPor(solicitudLogisticoBean.getCreaPor());
            solicitudLogisticoDAO.insertarDetRequerimientoCR(detRequerimientoCRBean);
        }

        solicitudLogisticoBean.setObjeto(MaristaConstantes.OBJ_SOL_LOG);
        solicitudLogisticoDAO.llamarAutorizadores(solicitudLogisticoBean);
    }

    @Transactional
    public void modificar(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        solicitudLogisticoDAO.actualizar(solicitudLogisticoBean);
        solicitudLogisticoBean.setObjeto(MaristaConstantes.OBJ_SOL_LOG);
        solicitudLogisticoDAO.llamarAutorizadores(solicitudLogisticoBean);
    }

    @Transactional
    public void actualizarFechaAprobacion(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        solicitudLogisticoDAO.actualizarFechaAprobacion(solicitudLogisticoBean);
    }

    @Transactional
    public void modificarSOL(SolicitudLogisticoBean solicitudLogisticoBean, List<SolicitudLogDetalleBean> listaDetalle, SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //1.- eliminacion detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
            solicitudLogisticoDetalleDAO.eliminar(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
        }

        //2.- insertando detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
//            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaDetalle1.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());

            listaDetalle1.getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
//            listaDetalle1.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
//            listaDetalle1.getCatalogoBean().getInventarioActivoBean().setStockActual(solicitudLogDetalleBean.getCatalogoBean().getInventarioActivoBean().getStockActual());
            solicitudLogisticoDetalleDAO.insertar(listaDetalle1);

             if (listaDetalle1.getSolicitudLogisticoBean().getTipoCategoriaBean().getCodigo()=="Activo Fijo") {
                InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
                listaDetalle1.getCatalogoBean().getInventarioActivoBean().setStockActual(listaDetalle1.getStockAyuda());
                inventarioActivoService.modificarStockActual(listaDetalle1.getCatalogoBean().getInventarioActivoBean());
            } else {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().setStockActual(listaDetalle1.getStockAyuda());
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(listaDetalle1.getSolicitudLogisticoBean().getUnidadNegocioBean().getUniNeg());
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().getCatalogoBean().setIdCatalogo(listaDetalle1.getCatalogoBean().getIdCatalogo());
                
                inventarioAlmacenService.modificarStockActual(listaDetalle1.getCatalogoBean().getInventarioAlmacenBean());
            }

            //eliminando lista cr
//            List<DetRequerimientoCRBean> listaReqCr = new ArrayList<>();
//            solicitudLogisticoDAO.elimnarRequerimientoCrPorReq(solicitudLogisticoBean.getIdRequerimiento(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            for (int i = 0; i < solicitudLogisticoBean.getListaDetRequerimientoCRBean().size(); i++) {
//                DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
//                detRequerimientoCRBean.setSolicitudLogisticoBean(solicitudLogisticoBean);
//                detRequerimientoCRBean.setCentroResponsabilidadBean(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
//                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(solicitudLogisticoBean.getCodDistribucion()));
//                detRequerimientoCRBean.setValor(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getValorD());
//                detRequerimientoCRBean.setCreaPor(usuarioBean.getUsuario());
//                detRequerimientoCRBean.setModiPor(usuarioBean.getUsuario());
//                solicitudLogisticoDAO.insertarDetRequerimientoCR(detRequerimientoCRBean);
//            }
//            movimientoAlmacenBean.getCatalogoBean().setIdCatalogo(listaDetalle1.getCatalogoBean().getIdCatalogo());
//            movimientoAlmacenBean.getSolicitudLogisticoBean().setIdRequerimiento(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
//            movimientoAlmacenBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(listaDetalle1.getIdDetRequerimiento());
//            movimientoAlmacenBean.setFechaMov(listaDetalle1.getSolicitudLogisticoBean().getFechaSolicitud());
//            movimientoAlmacenBean.setCantidad(listaDetalle1.get);
            this.modificar(solicitudLogisticoBean);
        }
    }

    @Transactional
    public void modificarDES(SolicitudLogisticoBean solicitudLogisticoBean, List<SolicitudLogDetalleBean> listaDetalle, SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //1.- eliminacion detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
            solicitudLogisticoDetalleDAO.eliminar(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
        }

        //2.- insertando detalle SL
        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaDetalle1.setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());

            listaDetalle1.getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
            listaDetalle1.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
            solicitudLogisticoDetalleDAO.insertar(listaDetalle1);

            if (listaDetalle1.getSolicitudLogisticoBean().getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
                listaDetalle1.getCatalogoBean().getInventarioActivoBean().setStockActual(listaDetalle1.getStockAyuda());
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().getCatalogoBean().setIdCatalogo(listaDetalle1.getCatalogoBean().getIdCatalogo());
                inventarioActivoService.modificarStockActual(listaDetalle1.getCatalogoBean().getInventarioActivoBean());
            } else {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().setStockActual(listaDetalle1.getStockAyuda());
                listaDetalle1.getCatalogoBean().getInventarioAlmacenBean().getCatalogoBean().setIdCatalogo(listaDetalle1.getCatalogoBean().getIdCatalogo());
                inventarioAlmacenService.modificarStockActual(listaDetalle1.getCatalogoBean().getInventarioAlmacenBean());
            }

            //eliminando lista cr
            solicitudLogisticoDAO.elimnarRequerimientoCrPorReq(solicitudLogisticoBean.getIdRequerimiento(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < solicitudLogisticoBean.getListaDetRequerimientoCRBean().size(); i++) {
                DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
                detRequerimientoCRBean.setSolicitudLogisticoBean(solicitudLogisticoBean);
                detRequerimientoCRBean.setCentroResponsabilidadBean(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getCentroResponsabilidadBean());
                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(solicitudLogisticoBean.getCodDistribucion()));
                detRequerimientoCRBean.setValor(solicitudLogisticoBean.getListaDetRequerimientoCRBean().get(i).getValorD());
                detRequerimientoCRBean.setCreaPor(usuarioBean.getUsuario());
                detRequerimientoCRBean.setModiPor(usuarioBean.getUsuario());
                solicitudLogisticoDAO.insertarDetRequerimientoCR(detRequerimientoCRBean);
            }
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            Integer idInventario = null;
            idInventario = inventarioAlmacenService.obtenerUltimoPDF(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (SolicitudLogDetalleBean listaMov : listaDetalle) {
                MovimientoAlmacenBean mov = new MovimientoAlmacenBean();
                mov.getUnidadNegocioBean().setUniNeg(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
                mov.getCatalogoBean().setIdCatalogo(listaMov.getCatalogoBean().getIdCatalogo());
                mov.getSolicitudLogisticoBean().setIdRequerimiento(listaMov.getIdRequerimiento());
                mov.getSolicitudLogDetalleBean().setIdDetRequerimiento(listaMov.getIdDetRequerimiento());
                mov.setCantidad(listaMov.getCantidadMovi());
                mov.getTipoUniMedBean().setIdCodigo(listaMov.getCatalogoBean().getTipoUnidadMedidaBean().getIdCodigo());
                mov.setEntregadoPor(usuarioBean.getPersonalBean().getNombreCompleto());
                mov.setRecibidopor(solicitudLogisticoBean.getPersonalBean().getNombreCompleto());
                mov.setCreaPor(usuarioBean.getUsuario());
                mov.setFechaMov(new Date());
                mov.setNroMovimiento(idInventario);
                inventarioAlmacenService.insertarMovimiento(mov);
                if (mov.getCantidad() == null || mov.getSolicitudLogisticoBean().getIdRequerimiento() == null) {
                    inventarioAlmacenService.eliminar(mov);
                }
            }
            this.modificar(solicitudLogisticoBean);
        }
    }

    @Transactional
    public void anularSolicitudCCH(SolicitudLogisticoBean solicitudLogisticoBean, MensajeBean mensajeBean) throws Exception {
        solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
        solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_SOL_ANULADO);
        solicitudLogisticoDAO.anularSolicitudLog(solicitudLogisticoBean);

        mensajeBean.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
        mensajeBean.setIdObjeto(solicitudLogisticoBean.getIdRequerimiento());
        mensajeBean.getUnidadNegocioBean().setUniNeg(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
////        mensajeDAO.obtenerMensajePorIdTabla(mensajeBean);
//        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
//        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ELIMINADO);
//        mensajeDAO.cambiarStatusMsjeAnulado(mensajeBean);
        mensajeDAO.eliminarMensaje(mensajeBean);
    }

    @Transactional
    public void cambiarEstadoSolicitudLog(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        Boolean flgStatusAutorizado = null; //solicitud autorizada
        Integer nivelAutoriza = null;

        //obteniendo el nivel de autorizadores de la solicitud
        if (solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza2() == null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 1;
        }
        if (solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 2;
        }
        if (solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza3() != null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null) {
            nivelAutoriza = 3;
        }

        //comparando el nivel de autorizadores con su flag correspondiente
        if (nivelAutoriza == 1) {
            if (solicitudLogisticoBean.getFlgAutoriza1() != null) {
                if (solicitudLogisticoBean.getFlgAutoriza1() == true) {
                    flgStatusAutorizado = true;
                } else {
                    flgStatusAutorizado = false;
                }
            }
        }
        if (nivelAutoriza == 2) {
            if (solicitudLogisticoBean.getFlgAutoriza1() != null) {
                if (solicitudLogisticoBean.getFlgAutoriza1() == true) {
                    if (solicitudLogisticoBean.getFlgAutoriza2() != null) {
                        if (solicitudLogisticoBean.getFlgAutoriza2() == true) {
                            flgStatusAutorizado = true;
                        } else {
                            flgStatusAutorizado = false;
                        }
                    }
                }
            }
        }
        if (nivelAutoriza == 3) {
            if (solicitudLogisticoBean.getFlgAutoriza1() != null && solicitudLogisticoBean.getFlgAutoriza2() != null && solicitudLogisticoBean.getFlgAutoriza3() != null
                    && solicitudLogisticoBean.getFlgAutoriza1() == true && solicitudLogisticoBean.getFlgAutoriza2() == true && solicitudLogisticoBean.getFlgAutoriza3() == true) {
                flgStatusAutorizado = true;
            } else {
                flgStatusAutorizado = false;
            }
        }

        //cambiando el estado estado de la solicicitud, true=autorizado y flase=no autorizado
        if (flgStatusAutorizado != null) {
            if (flgStatusAutorizado == true) {
                solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
                solicitudLogisticoDAO.actualizarFechaAprobacion(solicitudLogisticoBean);
            }
            if (flgStatusAutorizado == false) {
                solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_SOL_NO_AUTORIZADO);
            }
        }
        if (flgStatusAutorizado == null) {
            solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
            solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_SOL_PENDIENTE);
        }
        solicitudLogisticoDAO.cambiarEstadoSolicitudLog(solicitudLogisticoBean);
    }

    @Transactional
    public void cambiarEstadoSolicitudLogComprado(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
        solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
        solicitudLogisticoDAO.cambiarEstadoSolicitudLogComprado(solicitudLogisticoBean);
    }

    public List<SolicitudLogisticoBean> obtenerPorFiltro(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerPorFiltro(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosSol() throws Exception {
        return solicitudLogisticoDAO.obtenerTodosSol();
    }

    public List<SolicitudLogisticoBean> obtenerTodosSolDes(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosSolDes(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosM(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosM(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosMDespacho(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosMDespacho(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosSolicitud(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosSolicitud(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosCat(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosCat(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodosAprob(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosAprob(bean);
    }

    public List<SolicitudLogisticoBean> obtenerTodos() throws Exception {
        return solicitudLogisticoDAO.obtenerTodos();
    }

    public List<SolicitudLogisticoBean> obtenerTodosCompra(SolicitudLogisticoBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerTodosCompra(bean);
    }

    public SolicitudLogisticoBean obtenerPorId(Integer idRequerimiento, String uniNeg) throws Exception {        
        return solicitudLogisticoDAO.obtenerPorId(idRequerimiento, uniNeg);
    }

    public List<DetRequerimientoCRBean> ObtenerPorIdCR(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return solicitudLogisticoDAO.ObtenerPorIdCR(solicitudLogisticoBean);
    }

    public List<DetRequerimientoCRBean> ObtenerPorIdCRDis(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return solicitudLogisticoDAO.ObtenerPorIdCRDis(solicitudLogisticoBean);
    }

    public List<DetRequerimientoCRBean> obtenerCRRegistro(Integer idRegistroCompra, String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerCRRegistro(idRegistroCompra, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerCRInRegistro(Integer idRegistroCompra, String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerCRInRegistro(idRegistroCompra, uniNeg);
    }

    public List<DetRequerimientoCRBean> obtenerCROrden(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerCROrden(idRequerimiento, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerCRInOrden(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerCRInOrden(idRequerimiento, uniNeg);
    }

    @Transactional
    public void elimnarRequerimientoCrPorReq(Integer idRequerimiento, String uniNeg) throws Exception {
        solicitudLogisticoDAO.elimnarRequerimientoCrPorReq(idRequerimiento, uniNeg);
    }

    // mis solicitudes mensaje, lista para obtener sol. por Id
    public List<SolicitudLogisticoBean> obtenerPorIdSoli(PersonalBean bean) throws Exception {
        return solicitudLogisticoDAO.obtenerPorIdSoli(bean);
    }

    public void ObtenerPorIdCRes(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        solicitudLogisticoDAO.ObtenerPorIdCRes(solicitudLogisticoBean);
    }

    public SolicitudLogisticoDetalleDAO getSolicitudLogisticoDetalleDAO() {
        return solicitudLogisticoDetalleDAO;
    }

    public void setSolicitudLogisticoDetalleDAO(SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO) {
        this.solicitudLogisticoDetalleDAO = solicitudLogisticoDetalleDAO;
    }

    public MensajeDAO getMensajeDAO() {
        return mensajeDAO;
    }

    public void setMensajeDAO(MensajeDAO mensajeDAO) {
        this.mensajeDAO = mensajeDAO;
    }

    public String obtenerUltimoSoli(String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerUltimoSoli(uniNeg);
    }

    public List<SolicitudLogisticoBean> obtenerCerosSol(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDAO.obtenerCerosSol(idRequerimiento, uniNeg);
    }

    public void cambiarEstadoSolicitudLogParcial(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        solicitudLogisticoDAO.cambiarEstadoSolicitudLogParcial(solicitudLogisticoBean);
    }

    
}
