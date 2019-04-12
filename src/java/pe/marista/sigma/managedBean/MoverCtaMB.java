package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class MoverCtaMB implements Serializable {

    @PostConstruct
    public void MoverCtaMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            setDato(1);
            obtenerFiltro();
            obtenerListaMeses();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES
    private UsuarioBean usuarioBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private CuentasPorCobrarBean cuentaOrigen;
    private CuentasPorCobrarBean cuentaDestino;

    //LISTAS
    private List<CuentasPorCobrarBean> listaCuentaOrigen;
    private List<CuentasPorCobrarBean> listaCuentaDestino;
    private List<MesBean> listaMesAll;

    //VARIABLES
    private Integer dato;

    //METODOS DE CLASE
    public void cargarDatos() {
        try {
            getCuentaOrigen();
            getCuentaOrigen().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentaOrigen().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            getCuentaDestino();
            getCuentaDestino().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentaDestino().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentasPorCobrarBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerListaMeses() {
        try {
            listaMesAll = new ArrayList<>();
            listaMesAll.add(new MesBean(2, MaristaConstantes.FEBRERO));
            listaMesAll.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMesAll.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMesAll.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMesAll.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMesAll.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMesAll.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMesAll.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMesAll.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMesAll.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMesAll.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltro() {
        try {
            if (dato != null) {
                if (dato.equals(1)) {
                    listaCuentaDestino = new ArrayList<>();
                } else if (dato.equals(2)) {
                    listaCuentaOrigen = new ArrayList<>();
                }
                limpiarFiltro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltro() {
        try {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentasPorCobrarBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            listaCuentaOrigen = new ArrayList<>();
            listaCuentaDestino = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //FILTRO DE ESTUDIANTE
    public void filtrarEstudiante() {
        try {
            Integer res = 0;
            if (cuentasPorCobrarBean.getEstudianteBean().getCodigo() != null && !cuentasPorCobrarBean.getEstudianteBean().getCodigo().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setCodigo(cuentasPorCobrarBean.getEstudianteBean().getCodigo());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante() != null && !cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setIdEstudiante(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApepat() != null && !cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().setApepat(cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApepat());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApemat() != null && !cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().setApemat(cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getApemat());
                res = 1;
            }
            if (cuentasPorCobrarBean.getAnio() != null) {
                cuentasPorCobrarBean.setAnio(cuentasPorCobrarBean.getAnio());
                res = 1;
            }
            if (cuentasPorCobrarBean.getMes() != null) {
                cuentasPorCobrarBean.setMes(cuentasPorCobrarBean.getMes());
                res = 1;
            }
            if (res == 1) {
                ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                if (dato.equals(1)) { //ORIGEN
                    cuentasPorCobrarBean.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PAG);
                    listaCuentaOrigen = procesoEnvioService.buscarCuentaCorriente(cuentasPorCobrarBean);
                    if (listaCuentaOrigen.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaCuentaOrigen = new ArrayList<>();
                    }
                } else if (dato.equals(2)) { //DESTINO
                    cuentasPorCobrarBean.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PEN);
                    listaCuentaDestino = procesoEnvioService.buscarCuentaCorriente(cuentasPorCobrarBean);
                    if (listaCuentaDestino.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaCuentaDestino = new ArrayList<>();
                    }
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaCuentaOrigen = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerEstudianteCuenta(Object obj, Integer dato) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            if (dato.equals(1)) { //ORIGEN
                cuentaOrigen = new CuentasPorCobrarBean();
                cuentaOrigen = (CuentasPorCobrarBean) obj;
                cuentaOrigen = cuentasPorCobrarService.obtenerCuentaPorId(cuentaOrigen.getIdCtasXCobrar(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            } else if (dato.equals(2)) { //DESTINO
                cuentaDestino = new CuentasPorCobrarBean();
                cuentaDestino = (CuentasPorCobrarBean) obj;
                cuentaDestino = cuentasPorCobrarService.obtenerCuentaPorId(cuentaDestino.getIdCtasXCobrar(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            limpiarFiltro();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerObjCuenta() {
        try {
            getCuentaOrigen();
            getCuentaDestino();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarMover() {
        try {
            cuentaOrigen = new CuentasPorCobrarBean();
            cuentaDestino = new CuentasPorCobrarBean();
            cuentaOrigen.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentaDestino.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void moverCuenta() {
        try {
            if (cuentaOrigen != null && cuentaDestino != null) {
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                /* MODIFICANDO ORIGEN */
                CuentasPorCobrarBean cuenta = new CuentasPorCobrarBean();
                cuenta.setMontoPagado(cuentaOrigen.getMontoPagado());
                cuenta.setFechaPago(cuentaOrigen.getFechaPago());
                cuenta.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PAG);
                cuenta.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cuenta.setIdCtasXCobrar(cuentaDestino.getIdCtasXCobrar());
                cuentasPorCobrarService.modificarCuentaMovimiento(cuenta);
                /* MODIFICANDO DESTINO */
                CuentasPorCobrarBean cuentaDes = new CuentasPorCobrarBean();
                cuentaDes.setMontoPagado(null);
                cuentaDes.setFechaPago(null);
                cuentaDes.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PEN);
                cuentaDes.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cuentaDes.setIdCtasXCobrar(cuentaOrigen.getIdCtasXCobrar());
                cuentasPorCobrarService.modificarCuentaMovimiento(cuentaDes);

                //LIMPIANDO
                limpiarMover();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public CuentasPorCobrarBean getCuentaOrigen() {
        if (cuentaOrigen == null) {
            cuentaOrigen = new CuentasPorCobrarBean();
        }
        return cuentaOrigen;
    }

    public void setCuentaOrigen(CuentasPorCobrarBean cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public CuentasPorCobrarBean getCuentaDestino() {
        if (cuentaDestino == null) {
            cuentaDestino = new CuentasPorCobrarBean();
        }
        return cuentaDestino;
    }

    public void setCuentaDestino(CuentasPorCobrarBean cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public List<CuentasPorCobrarBean> getListaCuentaOrigen() {
        if (listaCuentaOrigen == null) {
            listaCuentaOrigen = new ArrayList<>();
        }
        return listaCuentaOrigen;
    }

    public void setListaCuentaOrigen(List<CuentasPorCobrarBean> listaCuentaOrigen) {
        this.listaCuentaOrigen = listaCuentaOrigen;
    }

    public List<CuentasPorCobrarBean> getListaCuentaDestino() {
        if (listaCuentaDestino == null) {
            listaCuentaDestino = new ArrayList<>();
        }
        return listaCuentaDestino;
    }

    public void setListaCuentaDestino(List<CuentasPorCobrarBean> listaCuentaDestino) {
        this.listaCuentaDestino = listaCuentaDestino;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public List<MesBean> getListaMesAll() {
        if (listaMesAll == null) {
            listaMesAll = new ArrayList<>();
        }
        return listaMesAll;
    }

    public void setListaMesAll(List<MesBean> listaMesAll) {
        this.listaMesAll = listaMesAll;
    }

}
