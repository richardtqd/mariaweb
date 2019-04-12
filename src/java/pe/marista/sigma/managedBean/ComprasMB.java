/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ComprasBean;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.InventarioAlmacenService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class ComprasMB extends BaseMB implements Serializable {

    private ComprasBean comprasBean;
    private ComprasBean docEgresoBean;
    private List<ComprasBean> listDocEgreso;
    private List<ComprasBean> listCompras;
    private List<ComprasBean> listFiltroComprasBean;
    private List<ComprasBean> listAlmacen;
    private List<ComprasBean> listKardex;
    private List<ComprasBean> listActivoFijo;
    private ComprasBean compraBean;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private List<InventarioAlmacenBean> listaInventarioAlmacenBean;
    
    /**
     * Creates a new instance of ComprasMB
     */
    @PostConstruct
    public void ComprasMB() {

        ComprasBean a = new ComprasBean("001", "Plumones", "100", 1.00, "Unidad", "19.00", "119.00");
        ComprasBean b = new ComprasBean("002", "Pelotas", "100", 2.00, "Unidad", "38.00", "138.00");

        listDocEgreso = new ArrayList<>();
        listDocEgreso.add(a);
        listDocEgreso.add(b);

        ComprasBean c = new ComprasBean("201400001", "25/10/14", "VINIBALL", "257.00");
        ComprasBean d = new ComprasBean("201400002", "25/10/14", "FOX S.A.C.", "500.00");
        ComprasBean e = new ComprasBean("201400003", "26/10/14", "HP", "854.00");
        ComprasBean e1 = new ComprasBean("201400004", "26/10/14", "", "544.00");  

        listCompras = new ArrayList<>();
        listCompras.add(c);
        listCompras.add(d);
        listCompras.add(e);
        listCompras.add(e1);

        ComprasBean f = new ComprasBean("001", "25/10/14", "Plumones", 2.5, "Unidad", "VINIBALL", "20", "500", "400");
        ComprasBean g = new ComprasBean("002", "25/10/14", "Pelotas", 35.50, "Unidad", "VINIBALL", "15", "150", "200");
        ComprasBean g1 = new ComprasBean("003", "26/10/14", "Colchonetas", 46.23, "Unidad", "FOX S.A.C.", "20", "100", "50");
        ComprasBean g2 = new ComprasBean("004", "27/10/14", "Pizarras", 80.00, "Unidad", "FOX S.A.C.", "15", "250", "200");
        ComprasBean g3 = new ComprasBean("005", "27/10/14", "Carpetas", 54.00, "Unidad", "FOX S.A.C.", "15", "1000", "900");
        listAlmacen = new ArrayList<>();
        listAlmacen.add(f);
        listAlmacen.add(g);
        listAlmacen.add(g1);
        listAlmacen.add(g2);
        listAlmacen.add(g3);

        ComprasBean h = new ComprasBean("001", "25/10/14", "Plumnes", "Secundaria", "Ord. Compra Nro 0055", "100", "1", "100", "", "", "100", "400", "1", "400");
        ComprasBean i = new ComprasBean("002", "25/10/14", "Pelotas", "Almacén", "Doc. Nro 0056", "", "", "", "50", "1", "50", "250", "1", "250");

        listKardex = new ArrayList<>();
        listKardex.add(h);
        listKardex.add(i);

        ComprasBean j = new ComprasBean("001", "30/10/12", "250.00", "EPSON", "PA-5454X", "Equipo Cómputo", "Impresoras", "Colegio San Luis de Barranco", "Impresora Matriz");
        ComprasBean k = new ComprasBean("002", "25/10/13", "1550.00", "HP", "IQ500", "Equipo Cómputo", "PC's", "Colegio San Luis de Barranco", "Desktop PC");

        listActivoFijo = new ArrayList<>();
        listActivoFijo.add(j);
        listActivoFijo.add(k);
        try {
            obtenerInventarioAlmacen();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    

    public ComprasBean getCompraBean() {
        if (compraBean == null) {
            compraBean = new ComprasBean();
        }
        return compraBean;
    }

    public void setCompraBean(ComprasBean compraBean) {
        this.compraBean = compraBean;
    }

    public List<ComprasBean> getListFiltroComprasBean() {
        return listFiltroComprasBean;
    }

    public void setListFiltroComprasBean(List<ComprasBean> listFiltroComprasBean) {
        this.listFiltroComprasBean = listFiltroComprasBean;
    }

    public ComprasBean getComprasBean() {

        if (docEgresoBean == null) {
            docEgresoBean = new ComprasBean();
        }

        return docEgresoBean;
    }

    public void setComprasBean(ComprasBean docEgresoBean) {
        this.docEgresoBean = docEgresoBean;
    }

    public List<ComprasBean> getListDocEgreso() {
        return listDocEgreso;
    }

    public void setListDocEgreso(List<ComprasBean> listDocEgreso) {
        this.listDocEgreso = listDocEgreso;
    }

    public ComprasBean getDocEgresoBean() {
        return docEgresoBean;
    }

    public void setDocEgresoBean(ComprasBean docEgresoBean) {
        this.docEgresoBean = docEgresoBean;
    }

    public List<ComprasBean> getListCompras() {
        return listCompras;
    }

    public void setListCompras(List<ComprasBean> listCompras) {
        this.listCompras = listCompras;
    }

    public List<ComprasBean> getListAlmacen() {
        return listAlmacen;
    }

    public void setListAlmacen(List<ComprasBean> listAlmacen) {
        this.listAlmacen = listAlmacen;
    }

    public List<ComprasBean> getListKardex() {
        return listKardex;
    }

    public void setListKardex(List<ComprasBean> listKardex) {
        this.listKardex = listKardex;
    }

    public List<ComprasBean> getListActivoFijo() {
        return listActivoFijo;
    }

    public void setListActivoFijo(List<ComprasBean> listActivoFijo) {
        this.listActivoFijo = listActivoFijo;
    }

    public void rowSelect(SelectEvent event) {
        try {
            compraBean = (ComprasBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public List<ComprasBean> completeTheme(String query) {
        try {
            listFiltroComprasBean = new ArrayList<>();
            for (int i = 0; i < listCompras.size(); i++) {
                ComprasBean skin = listCompras.get(i);
                if (skin.getNroOrden().toLowerCase().contains(query)) {
                    listFiltroComprasBean.add(skin);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listFiltroComprasBean;
    }

    public void limpiarInventarioAlmacen() {
        try {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void obtenerInventarioAlmacen(){
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario(inventarioAlmacenBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public String insertarInventarioAlmacen(){
        String pagina = null;
        try {
            if(pagina == null){
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                inventarioAlmacenBean.setCreaPor(beanUsuarioSesion.getUsuario());
//                inventarioAlmacenService.insertar(inventarioAlmacenBean);
//                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario(inventarioAlmacenBean);
                limpiarInventarioAlmacen();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
    
    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if(inventarioAlmacenBean == null){
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
    }

    public List<InventarioAlmacenBean> getListaInventarioAlmacenBean() {
        if(listaInventarioAlmacenBean == null){
            listaInventarioAlmacenBean = new ArrayList<>();
        }
        return listaInventarioAlmacenBean;
    }

    public void setListaInventarioAlmacenBean(List<InventarioAlmacenBean> listaInventarioAlmacenBean) {
        this.listaInventarioAlmacenBean = listaInventarioAlmacenBean;
    }
     
}
