/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.RecuperacionBcoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.RecuperacionBcoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public class RecuperacionBcoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of RecuperacionBcoMB
     */
    @PostConstruct
    public void RecuperacionBcoMB() {
        usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        getFlgEstructura();
        try {
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private UsuarioBean usuarioSessionBean;
    private ProcesoBancoBean procesoBancoBean;
    private ProcesoBancoBean procesoBancoListaBean;
    private List<RecuperacionBcoBean> listaRecuperacionBcoBean;
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private Integer flgEstructura = 0;

    private Integer progress;

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        }
//        else {
//            progress = progress + (int) (Math.random() * 90);
//            if (progress > 100) {
//                progress = 100;
//            }
//        }

        return progress;
    }

    public void obtenerFileRecuperacion(FileUploadEvent event) {
        try {

            String uniNeg = usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            //1 = recuperacion 
            if (flgEstructura.equals(1)) {
                CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
                CuentaBancoBean cuenta = new CuentaBancoBean();
                cuenta = cuentaBancoService.obtenerPorRucRecaudaVer2(uniNeg, MaristaConstantes.COD_SOLES);
                getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(uniNeg);
                getProcesoBancoBean().setFlgProceso(0);
                getProcesoBancoBean().getEntidadBean().setNombre(cuenta.getEntidadBancoBean().getNombre());
                getProcesoBancoBean().getEntidadBean().setRuc(cuenta.getEntidadBancoBean().getRuc());
                getProcesoBancoBean().setRuc(cuenta.getEntidadBancoBean().getRuc());
                getProcesoBancoBean().setCodUniNeg(cuenta.getCodUniNeg());
                getProcesoBancoBean().setNumCuenta(cuenta.getNumCuenta());

                ///////inico proceso 
                InputStream miArchivo = null;
                UploadedFile file = event.getFile();
                miArchivo = file.getInputstream();
                byte[] b = new byte[(int) file.getSize()];
                miArchivo.read(b);
                miArchivo.close();
                String resultado = new String(b);
                String[] arreglo = resultado.split("\n");
                listaRecuperacionBcoBean = new ArrayList<>();
                RecuperacionBcoService recuperacionBcoService = BeanFactory.getRecuperacionBcoService();
//            Integer idProcesoBanco = 1178;  
                Integer anio = 0;
                Calendar miCalendario = Calendar.getInstance();
                anio = miCalendario.get(Calendar.YEAR);
                Integer flgDuplicado = 0;
                flgDuplicado = procesoBancoService.obtenerNombreDuplicado(file.getFileName(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                if (flgDuplicado.equals(0)) {
                    procesoBancoBean.setNombre(file.getFileName().toString());
//                    System.out.println("file.getFileName().toString() " + file.getFileName().toString());

                    //nuevos cambios
                    Integer flgTieneCab = 0;
                    Integer flgTienePie = 0;
                    flgTieneCab = recuperacionBcoService.validarCabeceraRecepcion(uniNeg);
                    flgTienePie = recuperacionBcoService.validarCabeceraRecepcion(uniNeg);

                    Integer ini = 0;
                    if (flgTieneCab.equals(1)) {                         
                        ini = 1;
                    }
                    Integer fin = arreglo.length;
                    if (flgTienePie.equals(1)) {                         
                        fin = fin - 1;
                    }
                    //fin nuevos cambios

                    for (int i = ini; i < fin; i++) {
                        System.out.print(i + ">>" + arreglo[i]);
                        RecuperacionBcoBean bean = new RecuperacionBcoBean();
                        bean = recuperacionBcoService.SP_obtenerCamposRecuperacion(
                                usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), arreglo[i]);
                        if (bean != null) {
                            bean.setTxt(arreglo[i]);
                            System.out.println("-------------INICIO-------------");
//                            System.out.println("getUniNeg->" + bean.getUniNeg());
                            System.out.println("getNombre->" + bean.getNombre());
                            System.out.println("getCodigo->" + bean.getCodigo());
//                            System.out.println("getFechaPago->" + bean.getFechaPago());
//                            System.out.println("getFechaVenc->" + bean.getFechaVenc());
                            System.out.println("getMora->" + bean.getMora());
                            System.out.println("getMontoPension->" + bean.getMontoPension());
                            System.out.println("getIdCtasXcobrar->" + bean.getIdCtasXcobrar());

//                            System.out.println("getFechaPagoCta->" + bean.getFechaPagoCta());
//                            System.out.println("getMontoPensionCta->" + bean.getMontoPensionCta());
//                            System.out.println("getMesPens->" + bean.getMesPens());
//                            System.out.println("getIdCtaPensAnt->" + bean.getIdCtaPensAnt());
//                            System.out.println("getPagoMesAnt->" + bean.getPagoMesAnt());
//                            System.out.println("getIdDocIngreso->" + bean.getIdDocIngreso());
//                            System.out.println("-------------FIN-------------");
                            //si es cabecera o detalle
                            if (bean.getMontoPension() > 0) {
                                //detalle
                                listaRecuperacionBcoBean.add(bean);
                            } else {
                                //cabecera
                                System.out.println("Es cabecera");
                            }
                        }
                    }
//                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                for (int i = this.progress; i <= 100; i=i+4) {
//                    this.progress = i;
//                }
                    recuperacionBcoService.modificarCuentasPorCobrar(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaRecuperacionBcoBean, usuarioSessionBean.getUsuario(), procesoBancoBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    Integer idUltProceso = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    obtenerListaPorProcesoBanco(idUltProceso);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjNombreArchivoExiste");
                }
                ///fin proceso
            } else {
                new MensajePrime().addInformativeMessagePer("msjErrorEstructura");
                System.out.println("estructura... ERROR");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatos() {
        try {
            String uniNeg = usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            //1 = recuperacion
            this.flgEstructura = procesoBancoService.verificarEstructuraProcesoBanco(uniNeg, 1);
            if (flgEstructura.equals(1)) {
                System.out.println("estructura... OK");
            } else {
                new MensajePrime().addInformativeMessagePer("msjErrorEstructura");
                System.out.println("estructura... ERROR");
            }
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean();
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoBancoBean().setFecha(fechaActual.getTime());
            getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            getProcesoBancoBean().setFlgProceso(0);
            CuentaBancoBean cuenta = new CuentaBancoBean();
            cuenta = cuentaBancoService.obtenerPorRucRecaudaVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.COD_SOLES);
            getProcesoBancoBean().getEntidadBean().setNombre(cuenta.getEntidadBancoBean().getNombre());
            getProcesoBancoBean().getEntidadBean().setRuc(cuenta.getEntidadBancoBean().getRuc());
            getProcesoBancoBean().setRuc(cuenta.getEntidadBancoBean().getRuc());
            getProcesoBancoBean().setCodUniNeg(cuenta.getCodUniNeg());
            getProcesoBancoBean().setNumCuenta(cuenta.getNumCuenta());
            getProcesoBancoBean().setTipoMoneda(MaristaConstantes.COD_SOLES);
//            obtenerFiltroProceso();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroProceso() {
        try {
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (procesoBancoBean.getFlgProceso() != null) {
                procesoBancoBean.setFlgProceso(procesoBancoBean.getFlgProceso());
                res = 1;
            }
            if (procesoBancoBean.getFechaInicio() != null) {
                procesoBancoBean.setFechaInicio(procesoBancoBean.getFechaInicio());
                res = 1;
            }
            if (procesoBancoBean.getFechaFin() != null) {
                procesoBancoBean.setFechaFin(procesoBancoBean.getFechaFin());
                res = 1;
            }
            if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProcesoBancoBean = new ArrayList<>();
            } else if (res == 1) {
                listaProcesoBancoBean = procesoBancoService.filtrarProcesoVer2(procesoBancoBean);
                if (listaProcesoBancoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProcesoBancoBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerListaPorProcesoBanco(Integer id) {
        try {
            RecuperacionBcoService recuperacionBcoService = BeanFactory.getRecuperacionBcoService();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoListaBean = new ProcesoBancoBean();
            procesoBancoListaBean = procesoBancoService.obtenerProcesoBancoIdVer2(id, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaRecuperacionBcoBean = recuperacionBcoService.obtenerListaRecuperacionPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), id);
            System.out.println("lista size " + listaRecuperacionBcoBean.size());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onCompleteCero() {
        this.progress = 0;
        obtenerFiltroProceso();
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));
    }

    public List<RecuperacionBcoBean> getListaRecuperacionBcoBean() {
        if (listaRecuperacionBcoBean == null) {
            listaRecuperacionBcoBean = new ArrayList<>();
        }
        return listaRecuperacionBcoBean;
    }

    public void setListaRecuperacionBcoBean(List<RecuperacionBcoBean> listaRecuperacionBcoBean) {
        this.listaRecuperacionBcoBean = listaRecuperacionBcoBean;
    }

    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
    }

    public ProcesoBancoBean getProcesoBancoListaBean() {
        if (procesoBancoListaBean == null) {
            procesoBancoListaBean = new ProcesoBancoBean();
        }
        return procesoBancoListaBean;
    }

    public void setProcesoBancoListaBean(ProcesoBancoBean procesoBancoListaBean) {
        this.procesoBancoListaBean = procesoBancoListaBean;
    }

    public Integer getFlgEstructura() {
        return flgEstructura;
    }

    public void setFlgEstructura(Integer flgEstructura) {
        this.flgEstructura = flgEstructura;
    }

    public UsuarioBean getUsuarioSessionBean() {
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

}
