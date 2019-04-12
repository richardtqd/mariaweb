/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EsquelaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class EsquelaMB implements Serializable {

    /**
     * Creates a new instance of EsquelaMB
     */
    @PostConstruct
    public void EsquelaMB() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEsquelaBean();
            listaTipoEsquelaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ESQUELA));

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            //Lista Esquela
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            getListaEsquelaBean();
            listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaMesesForSup();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Esquela
    private EsquelaBean esquelaBean;
    private List<EsquelaBean> listaEsquelaBean;

    private Boolean flgDeuda;
    private Boolean flgPension;
    private String texto;
    private Map<String, String> listaMeses;

    //TipoEsquela
    private CodigoBean tipoEsquelaBean;
    private List<CodigoBean> listaTipoEsquelaBean;

    //Ayuda
    private String mensaje;

    public EsquelaBean getEsquelaBean() {
        if (esquelaBean == null) {
            esquelaBean = new EsquelaBean();
        }
        return esquelaBean;
    }

    public void setEsquelaBean(EsquelaBean esquelaBean) {
        this.esquelaBean = esquelaBean;
    }

    public List<EsquelaBean> getListaEsquelaBean() {
        if (listaEsquelaBean == null) {
            listaEsquelaBean = new ArrayList<>();
        }
        return listaEsquelaBean;
    }

    public void setListaEsquelaBean(List<EsquelaBean> listaEsquelaBean) {
        this.listaEsquelaBean = listaEsquelaBean;
    }

    public CodigoBean getTipoEsquelaBean() {
        if (tipoEsquelaBean == null) {
            tipoEsquelaBean = new CodigoBean();
        }
        return tipoEsquelaBean;
    }

    public void setTipoEsquelaBean(CodigoBean tipoEsquelaBean) {
        this.tipoEsquelaBean = tipoEsquelaBean;
    }

    public List<CodigoBean> getListaTipoEsquelaBean() {
        if (listaTipoEsquelaBean == null) {
            listaTipoEsquelaBean = new ArrayList<>();
        }
        return listaTipoEsquelaBean;
    }

    public void setListaTipoEsquelaBean(List<CodigoBean> listaTipoEsquelaBean) {
        this.listaTipoEsquelaBean = listaTipoEsquelaBean;
    }

    public Boolean getFlgDeuda() {
        return flgDeuda;
    }

    public void setFlgDeuda(Boolean flgDeuda) {
        this.flgDeuda = flgDeuda;
    }

    public Boolean getFlgPension() {
        return flgPension;
    }

    public void setFlgPension(Boolean flgPension) {
        this.flgPension = flgPension;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Map<String, String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    /*=========================================================================================================================*/
    public String insertarEsquela() {
        String pagina = null;
        String dato = "";
        Integer dato2 = 0;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                esquelaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                esquelaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                if (flgPension.equals(true)) {
                    texto = "<B>Monto total del mes <span style=\"color:red\">$00.00</span></B>\n"
                            + "		<br/>\n"
                            + "		<B style=\"position:relative;left:70%;\"><U>Gracias por su puntualidad</U></B>";
                    dato = esquelaBean.getMensaje().concat(texto);
                    dato2 = 19701;
                } else {
                    if (flgDeuda.equals(true)) {
                        texto = "<ul>Hasta la fecha usted tiene deudas de:\n"
                                + "			<li>\n"
                                + "			Pension de \"     \" monto a pagar de\n"
                                + "				<span style=\"color:red\">$00.00</span>\n"
                                + "			</li>\n"
                                + "			<li>\n"
                                + "			Pension de \"     \" monto a pagar de\n"
                                + "				<span style=\"color:red\">$00.00</span>\n"
                                + "			</li>\n"
                                + "			<li>\n"
                                + "			Pension de \"     \" monto a pagar de\n"
                                + "				<span style=\"color:red\">$00.00</span>\n"
                                + "			</li>\n"
                                + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                                + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                                + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                                + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                                + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                                + "			<li>\n"
                                + "			Pension de \"     \" monto a pagar de\n"
                                + "				<span style=\"color:red\">$00.00</span>\n"
                                + "			</li>\n"
                                + "		</ul>\n"
                                + "		<B>Monto total a pagar de <span style=\"color:red\">$00.00</span></B>";
                        dato = esquelaBean.getMensaje().concat(texto);
                        dato2 = 19702;
                    }
                }
                esquelaBean.setMensaje(dato);
                esquelaBean.getTipoEsquelaBean().setIdCodigo(dato2);
                esquelaService.insertarEsquela(esquelaBean);
                listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarEsquela();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarMenEsquela() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                esquelaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                esquelaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                esquelaService.insertarEsquela(esquelaBean);
                listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarEsquela();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEsquela() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                esquelaBean.setModiPor(beanUsuarioSesion.getUsuario());
                esquelaService.modificarEsquela(esquelaBean);
                listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarEsquela();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardar() {
        try {
            if (esquelaBean.getIdEsquela() != null) {
                modificarEsquela();
            } else {
                insertarMenEsquela();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEsquela() {
        try {
            esquelaBean = new EsquelaBean();
            flgPension = false;
            flgDeuda = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(Object object) {
        try {
            EsquelaBean esq = (EsquelaBean) object;
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaBean = esquelaService.obtenerPorId(esq.getIdEsquela());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            EsquelaBean esque = (EsquelaBean) object;
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaBean = esquelaService.obtenerPorId(esque.getIdEsquela());
            mensaje = esquelaBean.getTitulo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarEsquela() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                esquelaService.eliminarEsquela(esquelaBean.getIdEsquela());
                listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarEsquela();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void cambiarPension() {
        try {
            flgDeuda = false;
            flgPension = true;
//            texto = "<br><p>Pension de. monto:</p>&nbsp;"
//                    + "<div style='margin:0 auto;'>"
//                    + "<ul>"
//                    + "<li><span style=\"font-size: 10pt;\">&nbsp;Pension de&nbsp;</span></li>"
//                    + "</ul>"
//                    + "</div>";
            texto = "<B>Monto total del mes <span style=\"color:red\">$00.00</span></B>\n"
                    + "		<br/>\n"
                    + "		<B><U>Gracias por su puntualidad</U></B>";
            esquelaBean.setMensaje(texto);
            esquelaBean.getTipoEsquelaBean().setIdCodigo(19701);
            System.out.println(flgDeuda);
            System.out.println(flgPension);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarDeuda() {
        try {
            flgDeuda = true;
            flgPension = false;
//            texto = "<br><p>Su deuda atrazada es de:</p>&nbsp;"
//                    + "<div style='margin:0 auto;'>"
//                        + "<ul>"
//                            + "<li><span style=\"font-size: 10pt;\">&nbsp;Pension de&nbsp;</span></li>"
//                        + "</ul>"
//                    + "</div>";
            texto = "<ul>Hasta la fecha usted tiene deudas de:\n"
                    + "			<li>\n"
                    + "			Pension de \"     \" monto a pagar de\n"
                    + "				<span style=\"color:red\">$00.00</span>\n"
                    + "			</li>\n"
                    + "			<li>\n"
                    + "			Pension de \"     \" monto a pagar de\n"
                    + "				<span style=\"color:red\">$00.00</span>\n"
                    + "			</li>\n"
                    + "			<li>\n"
                    + "			Pension de \"     \" monto a pagar de\n"
                    + "				<span style=\"color:red\">$00.00</span>\n"
                    + "			</li>\n"
                    + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                    + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                    + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                    + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                    + "			<li style=\"list-style-type:none;color:red;font-size:15px;\">......</li>\n"
                    + "			<li>\n"
                    + "			Pension de \"     \" monto a pagar de\n"
                    + "				<span style=\"color:red\">$00.00</span>\n"
                    + "			</li>\n"
                    + "		</ul>\n"
                    + "		<B>Monto total a pagar de <span style=\"color:red\">$00.00</span></B>";
            esquelaBean.setMensaje(texto);
            esquelaBean.getTipoEsquelaBean().setIdCodigo(19702);
            System.out.println(flgDeuda);
            System.out.println(flgPension);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMesesForSup() {
        listaMeses = new LinkedHashMap<>();
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), "1");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), "2");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), "3");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), "4");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), "5");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), "6");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), "7");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), "8");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), "9");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), "10");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), "11");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), "12");
        listaMeses = Collections.unmodifiableMap(listaMeses);
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Cambio cancelado", ((EsquelaBean) event.getObject()).getTitulo());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

}
