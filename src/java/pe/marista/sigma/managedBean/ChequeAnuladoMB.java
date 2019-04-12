/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.ChequeAnuladoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ChequeAnuladoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class ChequeAnuladoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocEgresoMB
     */
    @PostConstruct
    public void init() {
        try {
            //sesión del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            CodigoService codigoService = BeanFactory.getCodigoService();

            //filtros
            fechaActual = new GregorianCalendar();
//            getDocEgresoFiltroBean().setFechaInicio(fechaActual.getTime());
//            getDocEgresoFiltroBean().setFechaFin(fechaActual.getTime());
//            getDocEgresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            getListaCuentaBancoFiltroBean();
            listaCuentaBancoFiltroBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getChequeAnuladoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            fechaActual = new GregorianCalendar();
            getChequeAnuladoFiltroBean().setFechaInicio(fechaActual.getTime());
            getChequeAnuladoFiltroBean().setFechaFin(fechaActual.getTime());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    private ChequeAnuladoBean chequeAnuladoBean;
    private ChequeAnuladoBean chequeAnuladoFiltroBean;
    private List<ChequeAnuladoBean> listaChequeAnuladoBean;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private List<CuentaBancoBean> listaCuentaBancoFiltroBean;
    private UsuarioBean usuarioLoginBean;
    private Calendar fechaActual;

    public void obtenerFiltroChequeAnulado() {
        try {
            int estado = 0;
            ChequeAnuladoService chequeAnuladoService = BeanFactory.getChequeAnuladoService();
            if (chequeAnuladoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(chequeAnuladoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                chequeAnuladoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (chequeAnuladoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(chequeAnuladoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                chequeAnuladoFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (chequeAnuladoFiltroBean.getDocEgresoBean().getNumCheque() != null && !chequeAnuladoFiltroBean.getDocEgresoBean().getNumCheque().equals("")) {
                chequeAnuladoFiltroBean.getDocEgresoBean().setNumCheque(chequeAnuladoFiltroBean.getDocEgresoBean().getNumCheque());
                estado = 1;
            }
            if (chequeAnuladoFiltroBean.getCuentaBancoBean().getNumCuenta() != null && !chequeAnuladoFiltroBean.getCuentaBancoBean().getNumCuenta().equals("")) {
                chequeAnuladoFiltroBean.getCuentaBancoBean().setNumCuenta(chequeAnuladoFiltroBean.getCuentaBancoBean().getNumCuenta());
                estado = 1;
            }
            if (chequeAnuladoFiltroBean.getObs() != null && !chequeAnuladoFiltroBean.getObs().equals("")) {
                chequeAnuladoFiltroBean.setObs(chequeAnuladoFiltroBean.getObs().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaChequeAnuladoBean = new ArrayList<>();
            }
            if (estado == 1) {

                listaChequeAnuladoBean = chequeAnuladoService.obtenerChequeAnuladoPorFiltro(chequeAnuladoFiltroBean);
                if (listaChequeAnuladoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarChequeAnulado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (chequeAnuladoBean.getDocEgresoBean().getNumCheque() != null && !chequeAnuladoBean.getDocEgresoBean().getNumCheque().equals(0)) {
                    ChequeAnuladoService chequeAnuladoService = BeanFactory.getChequeAnuladoService();
                    chequeAnuladoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");
//                    docEgresoMB.getDoc.setDocEgresoBean(docEgresoBean);
                     chequeAnuladoService.insertarChequeAnulado(chequeAnuladoBean,usuarioLoginBean,docEgresoMB.getListaDocEgresoBean(),chequeAnuladoBean.getDocEgresoBean());
                    limpiarChequeAnulado();
                    listaChequeAnuladoBean = chequeAnuladoService.obtenerChequeAnuladoPorFiltro(chequeAnuladoFiltroBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
                else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionIncorrecta", true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarChequeAnulado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ChequeAnuladoService chequeAnuladoService = BeanFactory.getChequeAnuladoService();
                chequeAnuladoBean.setModiPor(usuarioLoginBean.getUsuario());
                chequeAnuladoService.modificarChequeAnulado(chequeAnuladoBean);
                limpiarChequeAnulado();
                listaChequeAnuladoBean = chequeAnuladoService.obtenerChequeAnuladoPorFiltro(chequeAnuladoFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarChequeAnulado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ChequeAnuladoService chequeAnuladoService = BeanFactory.getChequeAnuladoService();
                chequeAnuladoService.eliminarChequeAnulado(chequeAnuladoBean);
                limpiarChequeAnulado();
                listaChequeAnuladoBean = chequeAnuladoService.obtenerChequeAnuladoPorFiltro(chequeAnuladoFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerDocEgresoPorId(Object objeto) {
        try {
            chequeAnuladoBean = (ChequeAnuladoBean) objeto;
            ChequeAnuladoService chequeAnuladoService = BeanFactory.getChequeAnuladoService();
            chequeAnuladoBean = chequeAnuladoService.obtenerChequeAnuladoPorId(chequeAnuladoBean);

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarChequeAnulado() {
        chequeAnuladoBean = new ChequeAnuladoBean();
    }

    public void limpiarChequeFiltroAnulado() {
        chequeAnuladoFiltroBean = new ChequeAnuladoBean();
        listaChequeAnuladoBean = new ArrayList();
        getChequeAnuladoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        fechaActual = new GregorianCalendar();
        getChequeAnuladoFiltroBean().setFechaInicio(fechaActual.getTime());
        getChequeAnuladoFiltroBean().setFechaFin(fechaActual.getTime());
    }

    // métodos getter and setter
    public ChequeAnuladoBean getChequeAnuladoBean() {
        if (chequeAnuladoBean == null) {
            chequeAnuladoBean = new ChequeAnuladoBean();
        }
        return chequeAnuladoBean;
    }

    public void setChequeAnuladoBean(ChequeAnuladoBean chequeAnuladoBean) {
        this.chequeAnuladoBean = chequeAnuladoBean;
    }

    public ChequeAnuladoBean getChequeAnuladoFiltroBean() {
        if (chequeAnuladoFiltroBean == null) {
            chequeAnuladoFiltroBean = new ChequeAnuladoBean();
        }
        return chequeAnuladoFiltroBean;
    }

    public void setChequeAnuladoFiltroBean(ChequeAnuladoBean chequeAnuladoFiltroBean) {
        this.chequeAnuladoFiltroBean = chequeAnuladoFiltroBean;
    }

    public List<ChequeAnuladoBean> getListaChequeAnuladoBean() {
        if (listaChequeAnuladoBean == null) {
            listaChequeAnuladoBean = new ArrayList<>();
        }
        return listaChequeAnuladoBean;
    }

    public void setListaChequeAnuladoBean(List<ChequeAnuladoBean> listaChequeAnuladoBean) {
        this.listaChequeAnuladoBean = listaChequeAnuladoBean;
    }

    public List<CuentaBancoBean> getListaCuentaBancoBean() {
        if (listaCuentaBancoBean == null) {
            listaCuentaBancoBean = new ArrayList<>();
        }
        return listaCuentaBancoBean;
    }

    public void setListaCuentaBancoBean(List<CuentaBancoBean> listaCuentaBancoBean) {
        this.listaCuentaBancoBean = listaCuentaBancoBean;
    }

    public List<CuentaBancoBean> getListaCuentaBancoFiltroBean() {
        if (listaCuentaBancoFiltroBean == null) {
            listaCuentaBancoFiltroBean = new ArrayList<>();
        }
        return listaCuentaBancoFiltroBean;
    }

    public void setListaCuentaBancoFiltroBean(List<CuentaBancoBean> listaCuentaBancoFiltroBean) {
        this.listaCuentaBancoFiltroBean = listaCuentaBancoFiltroBean;
    }

}
