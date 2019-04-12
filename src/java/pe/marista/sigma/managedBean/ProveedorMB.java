/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.CentroCostoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ProveedorBean;
import pe.marista.sigma.bean.SolicitudBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroCostoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class ProveedorMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ProveedorMB
     */
    public ProveedorMB() throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");


//        ProveedorBean a = new ProveedorBean("P001", "GRUPO MEDIATECH S.A.C.", "10154854849", "CALLE FAURE 150 INT 402", 2475436);
//        ProveedorBean b = new ProveedorBean("P002", "FOX S.A.C.", "10985412549", "JR LOS ALMENAROS 196 INT 101 ", 2584815);
//        ProveedorBean c = new ProveedorBean("P003", "TAXI FAST S.A ", "10154854849", "CALLE BRAULIO SUAREZ 236 ZONA A", 2481584);
//        ProveedorBean d = new ProveedorBean("P004", "TEXTIMAX S.A", "10482519489", "AV. CAMINOS DEL INCA 3459-301", 2652684);
//
//        SolicitudBean e = new SolicitudBean("Cheque", "Compra", "603201", "SUMINISTROS DE UTILES DE ESCRITORIO", "ADM-Recursos Humanos", "Soles", 1200.0, 2014008, formato.parse("10/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean f = new SolicitudBean("Transferencia", "Compra", "603202", "SUMINISTROS DE ENSEÑANZA", "INI-Academico", "Soles", 450.20, 2014009, formato.parse("10/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10985412549");
//        SolicitudBean g = new SolicitudBean("-", "Compra", "603209", "SUMINISTROS DEPORTIVOS", "SEC-Deporte", "Soles", 300.50, 2014010, formato.parse("11/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Desaprobada", "10985412549");
//        SolicitudBean h = new SolicitudBean("Cheque", "Compra", "625112", "UNIFORMES DEL PERSONAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean i = new SolicitudBean("-", "Servicio", "631121", "MOVILIDAD LOCAL", "ADM-Recursos Humanos", "Soles", 35.0, 2014012, formato.parse("12/08/2014"), "TAXI FAST S.A.", "f", formato.parse("12/10/2014"), "Pendiente", "10482519489");
//        SolicitudBean j = new SolicitudBean("-", "Compra", "603201", "SUMINISTROS DEPORTIVOS", "SEC-Deporte", "Soles", 280.0, 2014013, formato.parse("13/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Pendiente", "10985412549");
//
//        //aprobadas
//        SolicitudBean e1 = new SolicitudBean("", "Compra", "603201", "SUMINISTROS DE UTILES DE ESCRITORIO", "ADM-Recursos Humanos", "Soles", 1200.0, 2014008, formato.parse("10/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean f2 = new SolicitudBean("", "Compra", "603202", "SUMINISTROS DE ENSEÑANZA", "INI-Academico", "Soles", 450.20, 2014009, formato.parse("10/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10985412549");
//        SolicitudBean h3 = new SolicitudBean("", "Compra", "625112", "UNIFORMES DEL PERSONAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("12/10/2014"), "Aprobada", "10554854849");
//        SolicitudBean j3 = new SolicitudBean("", "Servicio", "634311", "MANTENIMIENTO  DE LOCAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "EASY MAX S.A.", "f", formato.parse("13/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean k3 = new SolicitudBean("", "Compra", "603219", "OTRAS ADQUISICIONES", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("13/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean l3 = new SolicitudBean("", "Compra", "603208", "SUMNINISTROS DE LABORA. PASTORAL", "PAS-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("14/10/2014"), "Aprobada", "10554854849");
//
//        SolicitudBean k = new SolicitudBean("", "A Rendir", "141312", " Contratación de Vigilante", "ADM-Recursos Humanos", "Soles", 1200.0, 001, formato.parse("12/10/2014"), "Aprobada", "Parra Lopez Jesus", formato.parse("22/10/2014"), "Aprobada", "");
//        SolicitudBean l = new SolicitudBean("", "Contra Pago", "168111", "Copias de Exámenes Trimestrales", "INI-Recursos Humanos", "Soles", 90.0, 002, formato.parse("11/10/2014"), "Aprobada", "Cañari Huamani Johan", formato.parse("15/10/2014"), "Aprobada", "");
//        SolicitudBean m = new SolicitudBean("", "A Rendir", "141312", "Copias de Libros", "ADM-Recursos Humanos", "Soles", 60.0, 003, formato.parse("12/10/2014"), "Desaprobada", "Vera Tarazona Jesús ", formato.parse("15/10/2014"), "Desaprobada", "");
//        SolicitudBean n = new SolicitudBean("", "A Rendir", "141312", "Panfletos de Adminisión", "ADM-Recursos Humanos", "Soles", 110.0, 004, formato.parse("13/10/2014"), "Aprobada", "Jones Sanchez Luis", formato.parse("23/10/2014"), "Aprobada", "");
//        SolicitudBean o = new SolicitudBean("", "Contra Pago", "168111", "Evento del día de la Madre", "INI-Recursos Humanos", "Soles", 65.0, 005, formato.parse("14/10/2014"), "Pendiente", "Lam Torres Gilmar", formato.parse("16/10/2014"), "Pendiente", "");
//        SolicitudBean p = new SolicitudBean("", "A Rendir", "141312", "Comerciales Televisivos", "PRI-Recursos Humanos", "Soles", 45.0, 006, formato.parse("15/10/2014"), "Pendiente", "Cañari Huamani Johan", formato.parse("25/10/2014"), "Pendiente", "");
//
//        //aprobadas
//        SolicitudBean k1 = new SolicitudBean("", "A Rendir", "141312", " Contratación de Vigilante", "ADM-Recursos Humanos", "Soles", 120.0, 001, formato.parse("12/10/2014"), "Aprobada", "Parra Lopez Jesus", formato.parse("22/10/2014"), "", "");
//        SolicitudBean m2 = new SolicitudBean("", "Contra Pago", "168111", "Copias de Exámenes Trimestrales", "INI-Recursos Humanos", "Soles", 90.0, 002, formato.parse("11/10/2014"), "Aprobada", "Cañari Huamani Johan", formato.parse("15/10/2014"), "", "");
//        SolicitudBean n3 = new SolicitudBean("", "A Rendir", "141312", "Panfletos de Adminisión", "ADM-Recursos Humanos", "Soles", 110.0, 004, formato.parse("13/10/2014"), "Aprobada", "Jones Sanchez Luis", formato.parse("23/10/2014"), "", "");

//        ProveedorBean a = new ProveedorBean("P001", "GRUPO MEDIATECH S.A.C.", "10154854849", "CALLE FAURE 150 INT 402", 2475436);
//        ProveedorBean b = new ProveedorBean("P002", "FOX S.A.C.", "10985412549", "JR LOS ALMENAROS 196 INT 101 ", 2584815);
//        ProveedorBean c = new ProveedorBean("P003", "TAXI FAST S.A ", "10154854849", "CALLE BRAULIO SUAREZ 236 ZONA A", 2481584);
//        ProveedorBean d = new ProveedorBean("P004", "TEXTIMAX S.A", "10482519489", "AV. CAMINOS DEL INCA 3459-301", 2652684);
//
//        SolicitudBean e = new SolicitudBean("Cheque", "Compra", "603201", "SUMINISTROS DE UTILES DE ESCRITORIO", "ADM-Recursos Humanos", "Soles", 1200.0, 2014008, formato.parse("10/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean f = new SolicitudBean("Transferencia", "Compra", "603202", "SUMINISTROS DE ENSEÑANZA", "INI-Academico", "Soles", 450.20, 2014009, formato.parse("10/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10985412549");
//        SolicitudBean g = new SolicitudBean("-", "Compra", "603209", "SUMINISTROS DEPORTIVOS", "SEC-Deporte", "Soles", 300.50, 2014010, formato.parse("11/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Desaprobada", "10985412549");
//        SolicitudBean h = new SolicitudBean("Cheque", "Compra", "625112", "UNIFORMES DEL PERSONAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean i = new SolicitudBean("-", "Servicio", "631121", "MOVILIDAD LOCAL", "ADM-Recursos Humanos", "Soles", 35.0, 2014012, formato.parse("12/08/2014"), "TAXI FAST S.A.", "f", formato.parse("12/10/2014"), "Pendiente", "10482519489");
//        SolicitudBean j = new SolicitudBean("-", "Compra", "603201", "SUMINISTROS DEPORTIVOS", "SEC-Deporte", "Soles", 280.0, 2014013, formato.parse("13/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Pendiente", "10985412549");
//
//        //aprobadas
//        SolicitudBean e1 = new SolicitudBean("", "Compra", "603201", "SUMINISTROS DE UTILES DE ESCRITORIO", "ADM-Recursos Humanos", "Soles", 1200.0, 2014008, formato.parse("10/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean f2 = new SolicitudBean("", "Compra", "603202", "SUMINISTROS DE ENSEÑANZA", "INI-Academico", "Soles", 450.20, 2014009, formato.parse("10/08/2014"), "FOX S.A.C.", "f", formato.parse("12/10/2014"), "Aprobada", "10985412549");
//        SolicitudBean h3 = new SolicitudBean("", "Compra", "625112", "UNIFORMES DEL PERSONAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("12/10/2014"), "Aprobada", "10554854849");
//        SolicitudBean j3 = new SolicitudBean("", "Servicio", "634311", "MANTENIMIENTO  DE LOCAL", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "EASY MAX S.A.", "f", formato.parse("13/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean k3 = new SolicitudBean("", "Compra", "603219", "OTRAS ADQUISICIONES", "ADM-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "GRUPO MEDIATECH S.A.C.", "f", formato.parse("13/10/2014"), "Aprobada", "10154854849");
//        SolicitudBean l3 = new SolicitudBean("", "Compra", "603208", "SUMNINISTROS DE LABORA. PASTORAL", "PAS-Recursos Humanos", "Soles", 1550.0, 2014011, formato.parse("12/08/2014"), "TEXTIMAX S.A.", "f", formato.parse("14/10/2014"), "Aprobada", "10554854849");
//
//        SolicitudBean k = new SolicitudBean("", "A Rendir", "141312", " Contratación de Vigilante", "ADM-Recursos Humanos", "Soles", 1200.0, 001, formato.parse("12/10/2014"), "Aprobada", "Parra Lopez Jesus", formato.parse("22/10/2014"), "Aprobada", "");
//        SolicitudBean l = new SolicitudBean("", "Contra Pago", "168111", "Copias de Exámenes Trimestrales", "INI-Recursos Humanos", "Soles", 90.0, 002, formato.parse("11/10/2014"), "Aprobada", "Cañari Huamani Johan", formato.parse("15/10/2014"), "Aprobada", "");
//        SolicitudBean m = new SolicitudBean("", "A Rendir", "141312", "Copias de Libros", "ADM-Recursos Humanos", "Soles", 60.0, 003, formato.parse("12/10/2014"), "Desaprobada", "Vera Tarazona Jesús ", formato.parse("15/10/2014"), "Desaprobada", "");
//        SolicitudBean n = new SolicitudBean("", "A Rendir", "141312", "Panfletos de Adminisión", "ADM-Recursos Humanos", "Soles", 110.0, 004, formato.parse("13/10/2014"), "Aprobada", "Jones Sanchez Luis", formato.parse("23/10/2014"), "Aprobada", "");
//        SolicitudBean o = new SolicitudBean("", "Contra Pago", "168111", "Evento del día de la Madre", "INI-Recursos Humanos", "Soles", 65.0, 005, formato.parse("14/10/2014"), "Pendiente", "Lam Torres Gilmar", formato.parse("16/10/2014"), "Pendiente", "");
//        SolicitudBean p = new SolicitudBean("", "A Rendir", "141312", "Comerciales Televisivos", "PRI-Recursos Humanos", "Soles", 45.0, 006, formato.parse("15/10/2014"), "Pendiente", "Cañari Huamani Johan", formato.parse("25/10/2014"), "Pendiente", "");
//
//        //aprobadas
//        SolicitudBean k1 = new SolicitudBean("", "A Rendir", "141312", " Contratación de Vigilante", "ADM-Recursos Humanos", "Soles", 120.0, 001, formato.parse("12/10/2014"), "Aprobada", "Parra Lopez Jesus", formato.parse("22/10/2014"), "", "");
//        SolicitudBean m2 = new SolicitudBean("", "Contra Pago", "168111", "Copias de Exámenes Trimestrales", "INI-Recursos Humanos", "Soles", 90.0, 002, formato.parse("11/10/2014"), "Aprobada", "Cañari Huamani Johan", formato.parse("15/10/2014"), "", "");
//        SolicitudBean n3 = new SolicitudBean("", "A Rendir", "141312", "Panfletos de Adminisión", "ADM-Recursos Humanos", "Soles", 110.0, 004, formato.parse("13/10/2014"), "Aprobada", "Jones Sanchez Luis", formato.parse("23/10/2014"), "", "");
//        
//        //doc
//         SolicitudBean v2 = new SolicitudBean("", "Copia Simple", "DNI", "Copias de Exámenes Trimestrales", "INI-Recursos Humanos", "Soles", 90.0, 002, formato.parse("11/10/2014"), "Aprobada", "Cañari Huamani Johan", formato.parse("15/10/2014"), "", "");
//        SolicitudBean v3 = new SolicitudBean("", "Original", "Ficha Postulante", "Panfletos de Adminisión", "ADM-Recursos Humanos", "Soles", 110.0, 004, formato.parse("13/10/2014"), "Aprobada", "Jones Sanchez Luis", formato.parse("23/10/2014"), "", "");
//        
//        
//        listProveedor = new ArrayList<>();
//        listProveedor.add(a);
//        listProveedor.add(b);
//        listProveedor.add(c);
//        listProveedor.add(d);
//
//        listsolicitudBean = new ArrayList<>();
//        listsolicitudBean.add(e);
//        listsolicitudBean.add(f);
//        listsolicitudBean.add(g);
//        listsolicitudBean.add(h);
//        listsolicitudBean.add(i);
//        listsolicitudBean.add(j);
//
//        listsolicitudBeanApro = new ArrayList<>();
//        listsolicitudBeanApro.add(e1);
//        listsolicitudBeanApro.add(f2);
//        listsolicitudBeanApro.add(h3);
//        listsolicitudBeanApro.add(j3);
//        listsolicitudBeanApro.add(k3);
//        listsolicitudBeanApro.add(l3);
//
////        listProveedor = new ArrayList<>();
////        listProveedor.add(a);
////        listProveedor.add(b);
////        listProveedor.add(c);
////        listProveedor.add(d);
////
//        listsolicitudBean = new ArrayList<>();
//        listsolicitudBean.add(e);
//        listsolicitudBean.add(f);
//        listsolicitudBean.add(g);
//        listsolicitudBean.add(h);
//        listsolicitudBean.add(i);
//        listsolicitudBean.add(j);
//
//        listsolicitudBeanApro = new ArrayList<>();
//        listsolicitudBeanApro.add(e1);
//        listsolicitudBeanApro.add(f2);
//        listsolicitudBeanApro.add(h3);
//        listsolicitudBeanApro.add(j3);
//        listsolicitudBeanApro.add(k3);
//        listsolicitudBeanApro.add(l3);
//
//        listSolicitudCajaChicaBean = new ArrayList<>();
//        listSolicitudCajaChicaBean.add(k);
//        listSolicitudCajaChicaBean.add(l);
//        listSolicitudCajaChicaBean.add(m);
//        listSolicitudCajaChicaBean.add(n);
//        listSolicitudCajaChicaBean.add(o);
//        listSolicitudCajaChicaBean.add(p);
////
//        listSolicitudCajaChicaBeanApro = new ArrayList<>();
//        listSolicitudCajaChicaBeanApro.add(k1);
//        listSolicitudCajaChicaBeanApro.add(m2);
//        listSolicitudCajaChicaBeanApro.add(n3);

//        listSolicitudCajaChicaBean = new ArrayList<>();
//        listSolicitudCajaChicaBean.add(k);
//        listSolicitudCajaChicaBean.add(l);
//        listSolicitudCajaChicaBean.add(m);
//        listSolicitudCajaChicaBean.add(n);
//        listSolicitudCajaChicaBean.add(o);
//        listSolicitudCajaChicaBean.add(p);
////
//        listSolicitudCajaChicaBeanApro = new ArrayList<>();
//        listSolicitudCajaChicaBeanApro.add(k1);
//        listSolicitudCajaChicaBeanApro.add(m2);
//        listSolicitudCajaChicaBeanApro.add(n3);
//        listDocAdm = new ArrayList<>();
//        listDocAdm.add(v2);
//        listDocAdm.add(v3); 
//        

    }

    private ProveedorBean proveedorBean;
    private SolicitudBean solicitudBean;
    private List<ProveedorBean> listProveedor;
    private List<ProveedorBean> listFiltroProveedor;
    private List<SolicitudBean> listsolicitudBean;
    private List<SolicitudBean> listsolicitudBeanApro;
    private List<SolicitudBean> listSolicitudCajaChicaBean;
    private List<SolicitudBean> listSolicitudCajaChicaBeanApro;
    private List<SolicitudBean> listDocAdm;
    private String prog;
    private List<String> listaDep;
    private String val;
    private String valRender;

    public String getValRender() {
        return valRender;
    }

    public void setValRender(String valRender) {
        this.valRender = valRender;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public List<ProveedorBean> getListFiltroProveedor() {
        return listFiltroProveedor;
    }

    public void setListFiltroProveedor(List<ProveedorBean> listFiltroProveedor) {
        this.listFiltroProveedor = listFiltroProveedor;
    }
    
    public ProveedorBean getProveedorBean() {
        if (proveedorBean == null) {
            proveedorBean = new ProveedorBean();
        }
        return proveedorBean;
    }

    public void setProveedorBean(ProveedorBean proveedorBean) {
        this.proveedorBean = proveedorBean;
    }

    public List<ProveedorBean> getListProveedor() {
        return listProveedor;
    }

    public void setListProveedor(List<ProveedorBean> listProveedor) {
        this.listProveedor = listProveedor;
    }

    public SolicitudBean getSolicitudBean() {
        if (solicitudBean == null) {
            solicitudBean = new SolicitudBean();
        }
        return solicitudBean;
    }

    public void setSolicitudBean(SolicitudBean solicitudBean) {
        this.solicitudBean = solicitudBean;
    }

    public List<SolicitudBean> getListsolicitudBean() {
        return listsolicitudBean;
    }

    public void setListsolicitudBean(List<SolicitudBean> listsolicitudBean) {
        this.listsolicitudBean = listsolicitudBean;
    }

    public List<SolicitudBean> getListSolicitudCajaChicaBean() {
        return listSolicitudCajaChicaBean;
    }

    public void setListSolicitudCajaChicaBean(List<SolicitudBean> listSolicitudCajaChicaBean) {
        this.listSolicitudCajaChicaBean = listSolicitudCajaChicaBean;
    }

    public void rowSelect(SelectEvent event) {
        try {

            solicitudBean = (SolicitudBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public List<SolicitudBean> getListSolicitudCajaChicaBeanApro() {
        return listSolicitudCajaChicaBeanApro;
    }

    public void setListSolicitudCajaChicaBeanApro(List<SolicitudBean> listSolicitudCajaChicaBeanApro) {
        this.listSolicitudCajaChicaBeanApro = listSolicitudCajaChicaBeanApro;
    }

    public List<SolicitudBean> getListsolicitudBeanApro() {
        return listsolicitudBeanApro;
    }

    public void setListsolicitudBeanApro(List<SolicitudBean> listsolicitudBeanApro) {
        this.listsolicitudBeanApro = listsolicitudBeanApro;
    }
    
    public List<ProveedorBean> completeProveedor(String query) {
        try 
        {
            listFiltroProveedor= new ArrayList<>();
            for (int i = 0; i < listProveedor.size(); i++) {
                ProveedorBean skin = listProveedor.get(i);
//                if (skin.getRazonSocial().toLowerCase().contains(query)) {
//                    listFiltroProveedor.add(skin);
//                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listFiltroProveedor;
    }

    public String getProg() {
        return prog;
    }

    public void setProg(String prog) {
        this.prog = prog;
    }

    public List<String> getListaDep() {
        return listaDep;
    }

    public void setListaDep(List<String> listaDep) {
        this.listaDep = listaDep;
    }
    
    public void actualizarLista(AjaxBehaviorEvent event) {
        try {
            switch (val) {
                case "Construcción":
                valRender = "1152.00";                    
                    break;
                case "Servicios":
                valRender = "1080.00";  
                    break;
                case "Otros":
                valRender = "1056.00"; 
                    break;
                case "Sin Detracción":
                valRender = "1200.00";
                    break;
            }
            
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<SolicitudBean> getListDocAdm() {
        if (listDocAdm==null)
            {listDocAdm= new ArrayList<>();}
        return listDocAdm;
    }

    public void setListDocAdm(List<SolicitudBean> listDocAdm) {
        this.listDocAdm = listDocAdm;
    }
    
}
