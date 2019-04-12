package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetRegistroCompraBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.dao.DetRegistroCompraDAO;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetRegistroCompraRepBean;
import pe.marista.sigma.util.MaristaUtils;

public class DetRegistroCompraService {

    private DetRegistroCompraDAO detRegistroCompraDAO;

    @Transactional
    public void insertar(RegistroCompraBean registroCompra, List<OrdenCompraDetalleBean> listaOrdenCompraDetalleBean, DetRegistroCompraBean detRegistroCompraBean) throws Exception {
        for (OrdenCompraDetalleBean ordCom : listaOrdenCompraDetalleBean) {
            //registro de compra
            DetRegistroCompraBean regComDet = new DetRegistroCompraBean();
            regComDet.getRegistroCompraBean().setIdRegistroCompra(registroCompra.getIdRegistroCompra());
            regComDet.getRegistroCompraBean().setAnio(registroCompra.getOrdenCompraBean().getAnio());
            regComDet.getRegistroCompraBean().setEntidadBean(registroCompra.getOrdenCompraBean().getEntidadBean());
            regComDet.setPrecio(registroCompra.getTotal());
            regComDet.setImporte(registroCompra.getImporte());
            regComDet.setDescripcion(registroCompra.getObsVenc());
//            regComDet.setCantidad(detRegistroCompraBean.getCantidad());
            // orden compra
            regComDet.getOrdenCompraBean().setIdOrdenCompra(ordCom.getOrdenCompraBean().getIdOrdenCompra());
            regComDet.getOrdenCompraDetalleBean().setIdDetOrdenCompra(ordCom.getIdDetOrdenCompra());
            regComDet.setCatalogoBean(ordCom.getCatalogoBean());

            regComDet.setTipoUniMedBean(ordCom.getTipoUniMedBean());

            regComDet.getUnidadNegocioBean().setUniNeg(registroCompra.getUnidadNegocioBean().getUniNeg());
            regComDet.getFacturaCompraBean().setIdFacturaCompra(ordCom.getFacturaCompraBean().getIdFacturaCompra());
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            regComDet.setCreaPor(usuarioBean.getUsuario());
            detRegistroCompraDAO.insertar(regComDet);
        }
    }

    @Transactional
    public void eliminar(Integer idRegistroCompra) throws Exception {
        detRegistroCompraDAO.eliminar(idRegistroCompra);
    }

    public List<DetRegistroCompraBean> obtenerPorOrden(Integer idRegistroCompra, String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerPorOrden(idRegistroCompra, uniNeg);
    }

    public List<DetRegistroCompraBean> obtenerPorFactura(FacturaCompraBean facturaCompraBean) throws Exception {
        return detRegistroCompraDAO.obtenerPorFactura(facturaCompraBean);
    }

    public List<DetRegistroCompraBean> obtenerDetalleDocEgreso2(Integer nroDocEgreso, String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerDetalleDocEgreso2(nroDocEgreso, uniNeg);
    }

    public DetRegistroCompraDAO getDetRegistroCompraDAO() {
        return detRegistroCompraDAO;
    }

    public void setDetRegistroCompraDAO(DetRegistroCompraDAO detRegistroCompraDAO) {
        this.detRegistroCompraDAO = detRegistroCompraDAO;
    }

    public List<DetRegistroCompraBean> obtenerPorRegistro(Integer idRegistroCompra, String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerPorRegistro(idRegistroCompra, uniNeg);
    }

    public List<DetRegistroCompraBean> obtenerPorRegistroDet(Integer idDetRegistroCompra) throws Exception {
        return detRegistroCompraDAO.obtenerPorRegistroDet(idDetRegistroCompra);
    }

    public Integer obtenerUltimo(Integer idRegistroCompra, String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerUltimo(idRegistroCompra, uniNeg);
    }

    public List<DetRegistroCompraBean> obtenerTodos(String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerTodos(uniNeg);
    }

    public List<DetRegistroCompraRepBean> obtenerDetFacturaCompra(Integer idFacturaCompra, String uniNeg) throws Exception {
        return detRegistroCompraDAO.obtenerDetFacturaCompra(idFacturaCompra, uniNeg);
    }
}
