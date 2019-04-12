/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ChequeService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class ChequeMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocEgresoMB
     */
    @PostConstruct
    public void init() {
        try {
            //sesión del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            getListaCuentaBancoFiltroBean();
            listaCuentaBancoFiltroBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getChequeFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    private ChequeBean chequeBean;
    private ChequeBean chequeFiltroBean;
    private List<ChequeBean> listaChequeBean;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private List<CuentaBancoBean> listaCuentaBancoFiltroBean;
    private UsuarioBean usuarioLoginBean;

    public void obtenerFiltroCheque() {
        try {
            int estado = 0;
            ChequeService chequeService = BeanFactory.getChequeService();

            if (chequeFiltroBean.getCuentaBancoBean().getNumCuenta() != null && !chequeFiltroBean.getCuentaBancoBean().getNumCuenta().equals(0)) {
                chequeFiltroBean.getCuentaBancoBean().setNumCuenta(chequeFiltroBean.getCuentaBancoBean().getNumCuenta());
                estado = 1;
            }
            if (chequeFiltroBean.getNombre() != null && !chequeFiltroBean.getNombre().equals("")) {
                chequeFiltroBean.setNombre(chequeFiltroBean.getNombre().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaChequeBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaChequeBean = chequeService.obtenerChequePorFiltro(chequeFiltroBean);
                if (listaChequeBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListaCheques() {
        try {
            ChequeService chequeService = BeanFactory.getChequeService();
            listaChequeBean = chequeService.obtenerChequePorFiltro(chequeFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCheque() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                String iniString = chequeBean.getInicio();
                Integer iniInt = Integer.parseInt(iniString);
                String finString = chequeBean.getFin();
                Integer finInt = Integer.parseInt(finString);
                String actString = chequeBean.getActual();
                Integer actInt = Integer.parseInt(actString);
                if (iniInt < finInt
                        && iniInt <= actInt
                        && finInt >= actInt
                        && finInt > iniInt
                        && actInt >= iniInt
                        && actInt <= finInt) {
                    ChequeService chequeService = BeanFactory.getChequeService();
                    chequeBean.setCreaPor(usuarioLoginBean.getUsuario());
                    chequeBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    chequeService.insertarCheque(chequeBean);
                    limpiarCheque();
                    listaChequeBean = chequeService.obtenerChequePorFiltro(chequeFiltroBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    new MensajePrime().addInformativeMessagePer("mensajeChequeError");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCheque() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                String iniString = chequeBean.getInicio();
                Integer iniInt = Integer.parseInt(iniString);
                String finString = chequeBean.getFin();
                Integer finInt = Integer.parseInt(finString);
                String actString = chequeBean.getActual();
                Integer actInt = Integer.parseInt(actString);
                if (iniInt < finInt
                        && iniInt <= actInt
                        && finInt >= actInt
                        && finInt > iniInt
                        && actInt >= iniInt
                        && actInt <= finInt) {
                    ChequeService chequeService = BeanFactory.getChequeService();
                    chequeBean.setModiPor(usuarioLoginBean.getUsuario());
                    chequeService.modificarCheque(chequeBean);
                    limpiarCheque();
                    listaChequeBean = chequeService.obtenerChequePorFiltro(chequeFiltroBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    new MensajePrime().addInformativeMessagePer("mensajeChequeError");
                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCheque() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ChequeService chequeService = BeanFactory.getChequeService();
                chequeService.eliminarCheque(chequeBean);
                limpiarCheque();
                listaChequeBean = chequeService.obtenerChequePorFiltro(chequeFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerChequePorId(Object objeto) {
        try {
            chequeBean = (ChequeBean) objeto;
            ChequeService chequeService = BeanFactory.getChequeService();
            chequeBean = chequeService.obtenerChequePorId(chequeBean);

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            ChequeService chequeService = BeanFactory.getChequeService();
            chequeBean = (ChequeBean) event.getObject();
            chequeBean = chequeService.obtenerChequePorId(chequeBean);
            obtenerCtaBanco();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCtaBanco() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            CuentaBancoBean cta = new CuentaBancoBean();
            cta.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            cta.setNumCuenta(chequeBean.getCuentaBancoBean().getNumCuenta());
            cta = cuentaBancoService.obtenerCuentaBancoPorNumCta(cta);
            chequeBean.getCuentaBancoBean().getEntidadBancoBean().setRuc(cta.getEntidadBancoBean().getRuc());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCheque() {
        chequeBean = new ChequeBean();
    }

    public void limpiarChequeFiltroAnulado() {
        chequeFiltroBean = new ChequeBean();
        listaChequeBean = new ArrayList();
        getChequeFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

    }

    public void guardarCheque() {
        try {
            if (chequeBean.getIdCheque() == null) {
                insertarCheque();
            } else {
                modificarCheque();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    // métodos getter and setter
    public ChequeBean getChequeBean() {
        if (chequeBean == null) {
            chequeBean = new ChequeBean();
        }
        return chequeBean;
    }

    public void setChequeBean(ChequeBean chequeBean) {
        this.chequeBean = chequeBean;
    }

    public ChequeBean getChequeFiltroBean() {
        if (chequeFiltroBean == null) {
            chequeFiltroBean = new ChequeBean();
        }
        return chequeFiltroBean;
    }

    public void setChequeFiltroBean(ChequeBean chequeFiltroBean) {
        this.chequeFiltroBean = chequeFiltroBean;
    }

    public List<ChequeBean> getListaChequeBean() {
        if (listaChequeBean == null) {
            listaChequeBean = new ArrayList<>();
        }
        return listaChequeBean;
    }

    public void setListaChequeBean(List<ChequeBean> listaChequeBean) {
        this.listaChequeBean = listaChequeBean;
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
