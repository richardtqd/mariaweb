package pe.marista.sigma.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.CajaDAO;
import pe.marista.sigma.dao.ImpresoraDAO;
import pe.marista.sigma.util.MaristaUtils;

public class CajaService {

    // Variables
    private CajaDAO cajaDAO;
////    private DocIngresoSerieDAO docIngresoSerieDAO;
    private ImpresoraDAO impresoraDAO;

    //Metodos Logica de Negocio
    public CajaBean buscarPorId(CajaBean cajaBean) throws Exception {
        return cajaDAO.buscarPorId(cajaBean);
    }

    public List<CajaBean> obtenerTodos(String uniNeg) throws Exception {
        return cajaDAO.obtenerTodos(uniNeg);
    }

    public List<CajaBean> obtenerTodosActivos(String uniNeg) throws Exception {
        return cajaDAO.obtenerTodosActivos(uniNeg);
    }

    public List<CajaBean> obtenerPorFiltro(CajaBean cajaBean) throws Exception {
        return cajaDAO.obtenerPorFiltro(cajaBean);
    }

    @Transactional
    public void insertarCaja(CajaBean cajaBean, List<ImpresoraBean> listaImpresoraBean, ImpresoraCajaBean impresoraCajaBean) throws Exception {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        cajaDAO.insertar(cajaBean);
        List<ImpresoraBean> listaImpreso = new ArrayList<>();
        for (Object objec : listaImpresoraBean) {
            ImpresoraBean impresora = new ImpresoraBean();
            impresora.setImpresora(objec.toString());
            impresora.setUnidadNegocioBean(cajaBean.getUnidadNegocioBean());
            listaImpreso = impresoraDAO.obtenerTodosTipoDoc(impresora);
            for (ImpresoraBean impreso : listaImpreso) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                ImpresoraBean impresoraBean = new ImpresoraBean();
                impresoraBean.setIdCompleto(impreso.getIdCompleto());
                String id = impresoraBean.getIdCompleto();
                String cadena[] = id.split("\\.");
                impresoraBean.setImpresora(cadena[0]);
                impresoraBean.getUnidadNegocioBean().setUniNeg(cadena[1]);
                impresoraBean.getIdTipoDoc().setIdCodigo(Integer.parseInt(cadena[2]));
                impresoraBean = impresoraDAO.buscarPorId(impresoraBean);
                impresoraCajaBean.setCajaBean(cajaBean);
                impresoraCajaBean.setUniNeg(impresoraBean);
                impresoraCajaBean.setIdTipoDoc(impresoraBean);
                impresoraCajaBean.setImpresora(impresoraBean);
                impresoraCajaBean.setCreaPor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                impresoraCajaBean.setCreaFecha(formato.parse(date));
////            DocIngresoSerieBean docIngresoSerieBean = new DocIngresoSerieBean();
////            docIngresoSerieBean.setIdCompleto(objecto.toString());
////            String id = docIngresoSerieBean.getIdCompleto();
////            String cadena[] = id.split("\\."); 
////            docIngresoSerieBean.setSerie(cadena[0]);
////            docIngresoSerieBean.getUnidadNegocioBean().setUniNeg(cadena[1]);
////            docIngresoSerieBean.getIdTipoDoc().setIdCodigo(Integer.parseInt(cadena[2]));
////            docIngresoSerieBean = impresoraDAO.buscarPorId(docIngresoSerieBean);
////            docIngresoSerieCajaBean.setCajaBean(cajaBean);
////            docIngresoSerieBean.setStatus(false);
////            docIngresoSerieCajaBean.setUniNeg(docIngresoSerieBean);
////            docIngresoSerieCajaBean.setIdTipoDoc(docIngresoSerieBean);
////            docIngresoSerieCajaBean.setSerie(docIngresoSerieBean);
////            docIngresoSerieCajaBean.setCreaPor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaDAO.insertarImpresoraCaja(impresoraCajaBean);
////            impresoraDAO.cambiarEstado(docIngresoSerieBean);
            }
        }
    }

    @Transactional
    public void modificarCaja(CajaBean cajaBean, List<ImpresoraBean> listaImpresoraBean) throws Exception {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
////        DocIngresoSerieCajaBean docIngresoSerieCajaBean = new DocIngresoSerieCajaBean();
        cajaDAO.actualizar(cajaBean);
        List<ImpresoraBean> lista = cajaDAO.obtenerImpresoraPorCaja(cajaBean);
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        String date = formato.format(new Date());
        for (ImpresoraBean impresora : lista) {

            ImpresoraBean impresoraBean = new ImpresoraBean();
            impresoraBean.setIdTipoDoc(impresora.getIdTipoDoc());
            impresoraBean.setUnidadNegocioBean(impresora.getUnidadNegocioBean());
            impresoraBean.setImpresora(impresora.getImpresora());
            impresoraCajaBean.setCajaBean(cajaBean);
            impresoraCajaBean.setImpresora(impresoraBean);
            impresoraCajaBean.setUniNeg(impresoraBean);
            impresoraCajaBean.setIdTipoDoc(impresoraBean);
            impresoraCajaBean.setCreaFecha(formato.parse(date));
            cajaDAO.eliminarImpresoraCajaAll(impresoraCajaBean);
////            DocIngresoSerieBean docIngresoSerie = new DocIngresoSerieBean();
////            docIngresoSerie.setIdTipoDoc(docIngreso.getIdTipoDoc());
////            docIngresoSerie.setUnidadNegocioBean(docIngreso.getUnidadNegocioBean());
////            docIngresoSerie.setSerie(docIngreso.getSerie());
////            docIngresoSerie.setStatus(true);            
////            impresoraDAO.cambiarEstado(docIngresoSerie);
////            docIngresoSerieCajaBean.setCajaBean(cajaBean);
////            DocIngresoSerieBean docIngresoSerieBean = new DocIngresoSerieBean();
////            docIngresoSerieBean.setIdCompleto(docIngreso.getIdTipoDoc().getIdCompleto());
////            String id = docIngresoSerieBean.getIdCompleto();
////            String cadena[] = id.split("\\."); 
////            docIngresoSerieBean.setSerie(cadena[0]);
////            docIngresoSerieBean.getUnidadNegocioBean().setUniNeg(cadena[1]);
////            docIngresoSerieBean.getIdTipoDoc().setIdCodigo(Integer.parseInt(cadena[2]));
////            docIngresoSerieCajaBean.setSerie(docIngresoSerie);
////            docIngresoSerieCajaBean.setUniNeg(docIngresoSerie);
////            docIngresoSerieCajaBean.setIdTipoDoc(docIngresoSerie);
////            cajaDAO.eliminarDocIngresoSerieCajaAll(docIngresoSerieCajaBean);
        }
        List<ImpresoraBean> listaImpreso = new ArrayList<>();
        for (Object objec : listaImpresoraBean) {
            ImpresoraBean impresora = new ImpresoraBean();
            impresora.setImpresora(objec.toString());
            impresora.setUnidadNegocioBean(cajaBean.getUnidadNegocioBean());
            listaImpreso = impresoraDAO.obtenerTodosTipoDoc(impresora);
            for (ImpresoraBean impreso : listaImpreso) {
                ImpresoraBean impresoraBean = new ImpresoraBean();
                ImpresoraCajaBean impresoraCaja = new ImpresoraCajaBean();
                impresoraBean.setIdCompleto(impreso.getIdCompleto());
                String id = impresoraBean.getIdCompleto();
                String cadena[] = id.split("\\.");
                impresoraBean.setImpresora(cadena[0]);
                impresoraBean.getUnidadNegocioBean().setUniNeg(cadena[1]);
                impresoraBean.getIdTipoDoc().setIdCodigo(Integer.parseInt(cadena[2]));
                impresoraBean = impresoraDAO.buscarPorId(impresoraBean);
                impresoraCaja.setImpresora(impresoraBean);
                impresoraCaja.setUniNeg(impresoraBean);
                impresoraCaja.setIdTipoDoc(impresoraBean);
                impresoraCaja.setCajaBean(cajaBean);
                impresoraCaja.setCreaPor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                impresoraCaja.setCreaFecha(formato.parse(date));
                cajaDAO.insertarImpresoraCaja(impresoraCaja);

////            DocIngresoSerieBean docIngresoSerieBean = new DocIngresoSerieBean();
////            DocIngresoSerieCajaBean docIngresoSerieCaja = new DocIngresoSerieCajaBean();
////            docIngresoSerieBean.setIdCompleto(objecto.toString());
////            String id = docIngresoSerieBean.getIdCompleto();
////            String cadena[] = id.split("\\."); 
////            docIngresoSerieBean.setSerie(cadena[0]);
////            docIngresoSerieBean.getUnidadNegocioBean().setUniNeg(cadena[1]);
////            docIngresoSerieBean.getIdTipoDoc().setIdCodigo(Integer.parseInt(cadena[2]));
////            docIngresoSerieBean.setSerie(objecto.getSerie());
////            docIngresoSerieBean.setIdTipoDoc(objecto.getIdTipoDoc());
////            docIngresoSerieBean.setUnidadNegocioBean(objecto.getUnidadNegocioBean());
////            docIngresoSerieBean = impresoraDAO.buscarPorId(docIngresoSerieBean);
////            docIngresoSerieCaja.setSerie(docIngresoSerieBean);
////            docIngresoSerieCaja.setUniNeg(docIngresoSerieBean);
////            docIngresoSerieCaja.setIdTipoDoc(docIngresoSerieBean);
////            docIngresoSerieCaja.setCajaBean(cajaBean);
////            docIngresoSerieCaja.setCreaPor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
////            docIngresoSerieBean.setStatus(false);
////            cajaDAO.insertarDocIngresoSerieCaja(docIngresoSerieCaja);
////            impresoraDAO.cambiarEstado(docIngresoSerieBean);
            }
        }
    }

    @Transactional
    public void eliminarCaja(CajaBean cajaBean) throws Exception {
        ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
        List<ImpresoraBean> listaImpresora = cajaDAO.obtenerImpresoraPorCaja(cajaBean);
//////        DocIngresoSerieCajaBean docIngresoSerieCajaBean = new DocIngresoSerieCajaBean();        
//////        List<DocIngresoSerieBean> listaDocIngresoSerie = cajaDAO.obtenerPorCaja(cajaBean);
        for (ImpresoraBean doc : listaImpresora) {
//////            doc.setStatus(true);
//////            impresoraDAO.cambiarEstado(doc);
            impresoraCajaBean.setImpresora(doc);
            impresoraCajaBean.setUniNeg(doc);
            impresoraCajaBean.setIdTipoDoc(doc);
            impresoraCajaBean.setCajaBean(cajaBean);
            cajaDAO.eliminarImpresoraCajaAll(impresoraCajaBean);
        }
        cajaDAO.eliminar(cajaBean);
    }

    @Transactional
    public void cambiarEstadoCaja(CajaBean cajaBean) throws Exception {
        cajaDAO.cambiarEstado(cajaBean);
    }

//////    DocIngresoSerieCaja
//////    public List<DocIngresoSerieCajaBean> obtenerDocumentoPorCaja(CajaBean cajaBean) throws Exception {
//////        return cajaDAO.obtenerDocumentoPorCaja(cajaBean);
//////    }
//////    public List<DocIngresoSerieBean> obtenerPorCaja(CajaBean CajaBean) throws Exception {
//////        return cajaDAO.obtenerPorCaja(CajaBean);
//////    }
//////    public void insertarDocIngresoSerieCaja(DocIngresoSerieCajaBean docIngresoSerieCajaBean) throws Exception {
//////        cajaDAO.insertarDocIngresoSerieCaja(docIngresoSerieCajaBean);
//////    }
//////    public void eliminarDocIngresoSerieCajaAll(DocIngresoSerieCajaBean docIngresoSerieCajaBean) throws Exception {
//////        cajaDAO.eliminarDocIngresoSerieCajaAll(docIngresoSerieCajaBean);
//////    }
    //ImpresoraCaja
    public List<ImpresoraBean> obtenerImpresoraPorCaja(CajaBean cajaBean) throws Exception {
        return cajaDAO.obtenerImpresoraPorCaja(cajaBean);
    }

    public void insertarImpresoraCaja(ImpresoraCajaBean impresoraCajaBean) throws Exception {
        cajaDAO.insertarImpresoraCaja(impresoraCajaBean);
    }

    public void eliminarImpresoraCajaAll(ImpresoraCajaBean impresoraCajaBean) throws Exception {
        cajaDAO.eliminarImpresoraCajaAll(impresoraCajaBean);
    }

    // Métodos
    public CajaDAO getCajaDAO() {
        return cajaDAO;
    }

    public void setCajaDAO(CajaDAO cajaDAO) {
        this.cajaDAO = cajaDAO;
    }
////
////    public DocIngresoSerieDAO getDocIngresoSerieDAO() {
////        return docIngresoSerieDAO;
////    }
////
////    public void setDocIngresoSerieDAO(DocIngresoSerieDAO docIngresoSerieDAO) {
////        this.docIngresoSerieDAO = docIngresoSerieDAO;
////    }

    public ImpresoraDAO getImpresoraDAO() {
        return impresoraDAO;
    }

    public void setImpresoraDAO(ImpresoraDAO impresoraDAO) {
        this.impresoraDAO = impresoraDAO;
    }

}
