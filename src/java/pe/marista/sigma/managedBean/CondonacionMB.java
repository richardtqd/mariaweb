/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.SlideEndEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class CondonacionMB extends BaseMB implements Serializable {

    /*
     *
     * Creates a new instance of CondonacionMB
     */
    @PostConstruct
    public void CondonacionMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cargarAno();
            getEstudianteBean();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");

            Calendar miCalendario = Calendar.getInstance();
            cargarAno();
            getListaCuentasPorCobrarBean();
            anio = miCalendario.get(Calendar.YEAR);
            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

        } catch (Exception ex) {
            Logger.getLogger(EstudianteBecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private List<Object> listaAnos;
    private EstudianteBean estudianteBean;
    private PersonaBean personaBean;
    private CuentasPorCobrarBean cuentasPorCobrarFiltro;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrar;
    private List<EstudianteBean> listaEstudianteBean;
    private MatriculaBean matriculaFiltroBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentasEstudianteBean;
    private List<CodigoBean> listaTipoDscto;
    private CuentasPorCobrarBean cuentas;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean; //lista de estudiantes matriculados en el historico 
    private BigDecimal montoPagar;
    private BigDecimal montoPagarCondonado = new BigDecimal("0.00");

    private Boolean flgCondParcial = true;

    private Integer anio;

    //AYUDA   
    private UsuarioBean usuarioLogin;

    public void cargarFormulario() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        try {
            if (parametro != null) {
//          
                EstudianteBean bean = new EstudianteBean();
                bean.getPersonaBean().setIdPersona(parametro);
                bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                obtenerEstudiantePorId(bean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListas() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoDscto();
            listaTipoDscto = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DSCTO));
            if (!listaTipoDscto.isEmpty()) {
                for (CodigoBean lista : listaTipoDscto) {
                    if (lista.getCodigo().equals("Mora")) {
                        lista.setCodigo(lista.getCodigo() + " 100%");
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
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

    public List<Object> getListaAnos() {
        return listaAnos;
    }

    public void setListaAnos(List<Object> listaAnos) {
        this.listaAnos = listaAnos;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarFiltro() {
        if (cuentasPorCobrarFiltro == null) {
            cuentasPorCobrarFiltro = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarFiltro;
    }

    public void setCuentasPorCobrarFiltro(CuentasPorCobrarBean cuentasPorCobrarFiltro) {
        this.cuentasPorCobrarFiltro = cuentasPorCobrarFiltro;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrar() {
        return listaCuentasPorCobrar;
    }

    public void setListaCuentasPorCobrar(List<CuentasPorCobrarBean> listaCuentasPorCobrar) {
        this.listaCuentasPorCobrar = listaCuentasPorCobrar;
    }

    public void obtenerEstudiantePorUniNeg() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            MatriculaBean matriculaBean = new MatriculaBean();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            String date = formato.format(new Date());
            matriculaBean.setAnio(Integer.parseInt(date));
            listaEstudianteBean = cuentasPorCobrarService.obtenerMatriculadosPorPeriodo(matriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiante() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().toUpperCase().trim());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().toUpperCase().trim());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase().trim());
            }
            if (anio != null) {
                matriculaFiltroBean.setAnio(anio);
            }
            matriculaFiltroBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaEstudianteBean = cuentasPorCobrarService.obtenerFiltroEstudianteMatriculado(matriculaFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroCtaCteEstudiante() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarFiltro.setAnio(anio);
            cuentasPorCobrarFiltro.setFlgDistinct(true);
            listaCuentasEstudianteBean = cuentasPorCobrarService.obtenerCtaCtePorEstudiantePorAnio(cuentasPorCobrarFiltro);
            if (listaCuentasEstudianteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaCuentasEstudianteBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

    }
    public void limpiarEstudianteMatricula() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
         listaEstudianteBean = new ArrayList<>();
    }

    public final void cargarAno() {
        try {
            int a = 2030;
            int b = 2010;
            listaAnos = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnos.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void consultar() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cuentasPorCobrar = new CuentasPorCobrarBean();
            cuentasPorCobrar.setAnio(anio);
            cuentasPorCobrarBean.setGrado(listaCuentasPorCobrar.get(0).getGrado());
            cuentasPorCobrarBean.setSeccion(listaCuentasPorCobrar.get(0).getSeccion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelect(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            obtenerEstudiantePorId(estudianteBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEstudiantePorId(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorIdMatricula(estudianteBean);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
//                if (cuenta.getAnio().equals(anio)) {
                if (cuenta.getFechaPago() != null) {
                    cuenta.setAlerta("/resources/images/verde.png");
                } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                    cuenta.setAlerta("/resources/images/rojo.png");
                } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                    cuenta.setAlerta("/resources/images/amarillo.png");
                }
                listaCuentasPorCobrarBean.add(cuenta);
//                } 
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorIdPorEst(EstudianteBean estudiante) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
//            System.out.println("id"+estudianteBean.getPersonaBean().getIdPersona());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
//                if (cuenta.getAnio().equals(anio)) {
                if (cuenta.getFechaPago() != null) {
                    cuenta.setAlerta("/resources/images/verde.png");
                } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                    cuenta.setAlerta("/resources/images/rojo.png");
                } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                    cuenta.setAlerta("/resources/images/amarillo.png");
                }
                listaCuentasPorCobrarBean.add(cuenta);
//                } 
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorId2(Object ctaCte) {
        try {
            cuentasPorCobrarBean = (CuentasPorCobrarBean) ctaCte;
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCtaCtePorEstudiante(cuentasPorCobrarBean);
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
//                if (cuenta.getAnio() == anio) {
                if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                    cuenta.setAlerta("/resources/images/rojo.png");
                } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() != null) {
                    cuenta.setAlerta("/resources/images/verde.png");
                } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                    cuenta.setAlerta("/resources/images/verde.png");
                }
                listaCuentasPorCobrarBean.add(cuenta);
//                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarDscto(RowEditEvent event) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cta = new CuentasPorCobrarBean();

            cta.setDscto(((CuentasPorCobrarBean) event.getObject()).getDscto());
            cta.getEstudianteBean().setIdEstudiante(((CuentasPorCobrarBean) event.getObject()).getEstudianteBean().getIdEstudiante());
            cta.setCreaDscto(usuarioLogin.getUsuario());
            cta.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            cta.setIdCtasXCobrar(((CuentasPorCobrarBean) event.getObject()).getIdCtasXCobrar());

            cta.setMonto(((CuentasPorCobrarBean) event.getObject()).getMonto());
            cta.setDsctoBeca(((CuentasPorCobrarBean) event.getObject()).getDsctoBeca());
            cta.setDscto(((CuentasPorCobrarBean) event.getObject()).getDscto());

            cta.setMonto(((CuentasPorCobrarBean) event.getObject()).getMonto());
            cta.setDsctoBeca(((CuentasPorCobrarBean) event.getObject()).getDsctoBeca());

//            cta.setDscto(((CuentasPorCobrarBean) event.getObject()).getDscto());
            BigDecimal montoMaxCond = (cta.getMonto().subtract(cta.getDsctoBeca())).add(cta.getMora());
            BigDecimal montoAcondonar = cta.getDscto();
            if (montoMaxCond.doubleValue() >= montoAcondonar.doubleValue()) {
                cta.setDscto(((CuentasPorCobrarBean) event.getObject()).getDscto());
                cuentasPorCobrarService.asignarDscto(cta);
                new MensajePrime().addInformativeMessagePer("msjMontoCodonacionExito");
            } else {
                new MensajePrime().addInformativeMessagePer("msjMontoCodonacionError");

            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void grabarDscto() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean.setCreaDscto(usuarioLogin.getUsuario());
            cuentasPorCobrarService.asignarDscto(cuentasPorCobrarBean);

            obtenerEstudiantePorIdPorEst(cuentasPorCobrarBean.getEstudianteBean());
//            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//            listaCuentasPorCobrarBean = new ArrayList<>();
//            Date fechaHoy = new Date();
//            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
//                if (cuenta.getAnio() == anio) {
//                    if (cuenta.getFechaPago() != null) {
//                        cuenta.setAlerta("/resources/images/verde.png");
//                    } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
//                        cuenta.setAlerta("/resources/images/rojo.png");
//                    } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
//                        cuenta.setAlerta("/resources/images/amarillo.png");
//                    }
//                    listaCuentasPorCobrarBean.add(cuenta);
//                }
//            }

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenrDscto() {
        try {
            Double porcentaje = 0.0;
            if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo() != null) {
                if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_MORA)) {
                    porcentaje = 100.0;
                    cuentasPorCobrarBean.setPorcentajeDscto(porcentaje);
                    cuentasPorCobrarBean.setDscto(cuentasPorCobrarBean.getMora());
                    this.flgCondParcial = true;
                    montoPagarCondonado = (new BigDecimal("0.00"));
                    obtnerMontoApagarCondonado(100.0);
                } else if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_PARCIAL)) {
                    if (cuentasPorCobrarBean.getDscto() == null) {
                        cuentasPorCobrarBean.setDscto(new BigDecimal("0.00"));
                    } 
                    porcentaje = 50.0; 
                    cuentasPorCobrarBean.setPorcentajeDscto(porcentaje);
                    this.flgCondParcial = false;
                    obtnerMontoApagarCondonado(porcentaje);
//                    montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //barra
    public void obtenerDsctoNew(Double porc) {
        try {
//            Double porcentaje = (Double) porc;
            if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo() != null) {
                if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_MORA)) {
                    cuentasPorCobrarBean.setPorcentajeDscto( 100.0);
                    cuentasPorCobrarBean.setDscto(cuentasPorCobrarBean.getMora());

                    this.flgCondParcial = true;
                    montoPagarCondonado = (new BigDecimal("0.00"));
                    obtnerMontoApagarCondonado(100.0);
                } else if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_PARCIAL)) {
                    if (cuentasPorCobrarBean.getDscto() == null) {
                        cuentasPorCobrarBean.setDscto(new BigDecimal("0.00"));
                    } 
                    cuentasPorCobrarBean.setPorcentajeDscto(porc);
                    this.flgCondParcial = false;
                    obtnerMontoApagarCondonado(porc);
//                    montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //barra
    public void obtenerDsctoNewInt(Integer porc) {
        try {
//            Double porcentaje = (Double) porc;
            if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo() != null) {
                if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_MORA)) {
                    cuentasPorCobrarBean.setPorcentajeDscto( 100.0);
                    cuentasPorCobrarBean.setDscto(cuentasPorCobrarBean.getMora());

                    this.flgCondParcial = true;
                    montoPagarCondonado = (new BigDecimal("0.00"));
                    obtnerMontoApagarCondonado(100.0);
                } else if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_PARCIAL)) {
                    if (cuentasPorCobrarBean.getDscto() == null) {
                        cuentasPorCobrarBean.setDscto(new BigDecimal("0.00"));
                    } 
                    cuentasPorCobrarBean.setPorcentajeDscto(porc.doubleValue());
                    this.flgCondParcial = false;
                    obtnerMontoApagarCondonado(porc.doubleValue());
//                    montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoDirect(Double monto) {
        try {
            System.out.println("monto: " + monto);
            System.out.println("mora: " + cuentasPorCobrarBean.getMora());
            if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo() != null) {
                if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_MORA)) {
                    Double porcentaje = 100.0;
                    cuentasPorCobrarBean.setPorcentajeDscto(porcentaje);
                    cuentasPorCobrarBean.setDscto(cuentasPorCobrarBean.getMora());

                    this.flgCondParcial = true;
                    montoPagarCondonado = (new BigDecimal("0.00"));
                    obtnerMontoApagarCondonado(porcentaje);
                } else if (cuentasPorCobrarBean.getIdTipoDscto().getIdCodigo().equals(MaristaConstantes.COD_PARCIAL)) {
                    if (cuentasPorCobrarBean.getDscto() == null) {
                        cuentasPorCobrarBean.setDscto(new BigDecimal("0.00"));
                    }
                    Double obtPor = (monto / cuentasPorCobrarBean.getMora().doubleValue()) * 100;                    
                    obtPor = (double) Math.round(obtPor * 100) / 100;
                    cuentasPorCobrarBean.setPorcentajeDscto(obtPor);
                    this.flgCondParcial = false;
                    obtnerMontoApagarCondonado(obtPor);
//                    montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
                }
            } else {
                Double obtPor = (monto / cuentasPorCobrarBean.getMora().doubleValue()) * 100;
                obtPor = (double) Math.round(obtPor * 100) / 100;
                cuentasPorCobrarBean.setPorcentajeDscto(obtPor);
                if (cuentasPorCobrarBean.getMora().equals(cuentasPorCobrarBean.getDscto())) {
                    cuentasPorCobrarBean.getIdTipoDscto().setIdCodigo(MaristaConstantes.COD_MORA);
                } else {
                    cuentasPorCobrarBean.getIdTipoDscto().setIdCodigo(MaristaConstantes.COD_PARCIAL);
                }
                obtnerMontoApagarCondonado(obtPor);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaCtePorId(Object objeto) {
        try {

            cuentasPorCobrarBean = (CuentasPorCobrarBean) objeto;
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaPorId(cuentasPorCobrarBean.getIdCtasXCobrar(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            cargarListas();
            montoPagar = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()));//monto + mora - dsctobeca 
            montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
            obtenrDscto();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onSlideEnd(SlideEndEvent event) {
//        System.out.println(event.getValue()+"%"); 
        obtenerDsctoNewInt(event.getValue());
//        FacesMessage message = new FacesMessage("Slide Ended", "Value: " + event.getValue());
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void obtnerMontoApagarCondonado(Double porcentaje) {
        try {
            if (porcentaje == null) {
                System.out.println("null");
            } else {
                BigDecimal valDouble = new BigDecimal(porcentaje);
                BigDecimal porc = new BigDecimal(100);
                valDouble = valDouble.divide(porc);
                cuentasPorCobrarBean.setPorcentajeDscto(porcentaje);
                System.out.println(porcentaje + " %");
                BigDecimal mora = cuentasPorCobrarBean.getMora();
                BigDecimal dsct = cuentasPorCobrarBean.getMora().multiply(valDouble);
//                System.out.println("dsc " + dsct);

                BigDecimal montoMaxCond = mora;
                BigDecimal montoAcondonar = dsct;
                if (montoAcondonar.doubleValue() >= 0.0) {
                    if (montoAcondonar.doubleValue() <= montoMaxCond.doubleValue()) {
                        cuentasPorCobrarBean.setDscto(dsct);
                        montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
//                cuentasPorCobrarService.asignarDscto(cuentasPorCobrarBean);
//                    new MensajePrime().addInformativeMessagePer("msjMontoCodonacionExito");
                    } else {
                        montoPagarCondonado = new BigDecimal("0.00");
                        cuentasPorCobrarBean.setDscto(new BigDecimal("0.00"));
                        cuentasPorCobrarBean.setPorcentajeDscto(0.0);
                        new MensajePrime().addInformativeMessagePer("msjMontoCodonacionError");
                    }
                }
            }

//            BigDecimal montoMaxCond = (cuentasPorCobrarBean.getMonto().subtract(cuentasPorCobrarBean.getDsctoBeca())).add(cuentasPorCobrarBean.getMora());
//            BigDecimal montoAcondonar = cuentasPorCobrarBean.getDscto(); 
//            if (montoAcondonar.doubleValue() >= 0.0) {
//                if (montoMaxCond.doubleValue() >= montoAcondonar.doubleValue()) {
//                    cuentasPorCobrarBean.setDscto(cuentasPorCobrarBean.getDscto());
//                    montoPagarCondonado = (cuentasPorCobrarBean.getMonto().add(cuentasPorCobrarBean.getMora()).subtract(cuentasPorCobrarBean.getDsctoBeca()).subtract(cuentasPorCobrarBean.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
////                cuentasPorCobrarService.asignarDscto(cuentasPorCobrarBean);
////                    new MensajePrime().addInformativeMessagePer("msjMontoCodonacionExito");
//                } else {
//                    montoPagarCondonado = new BigDecimal("0.00");
//                    new MensajePrime().addInformativeMessagePer("msjMontoCodonacionError");
//                }
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    ////////////////////////////////////
    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
    }

    public CuentasPorCobrarBean getCuentas() {
        if (cuentas == null) {
            cuentas = new CuentasPorCobrarBean();
        }
        return cuentas;
    }

    public void setCuentas(CuentasPorCobrarBean cuentas) {
        this.cuentas = cuentas;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
        if (listaMatriculaEstudiantesMasivosBean == null) {
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBean;
    }

    public void setListaMatriculaEstudiantesMasivosBean(List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) {
        this.listaMatriculaEstudiantesMasivosBean = listaMatriculaEstudiantesMasivosBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasEstudianteBean() {
        if (listaCuentasEstudianteBean == null) {
            listaCuentasEstudianteBean = new ArrayList<>();
        }
        return listaCuentasEstudianteBean;
    }

    public void setListaCuentasEstudianteBean(List<CuentasPorCobrarBean> listaCuentasEstudianteBean) {
        this.listaCuentasEstudianteBean = listaCuentasEstudianteBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public List<CodigoBean> getListaTipoDscto() {
        if (listaTipoDscto == null) {
            listaTipoDscto = new ArrayList<>();
        }
        return listaTipoDscto;
    }

    public void setListaTipoDscto(List<CodigoBean> listaTipoDscto) {
        this.listaTipoDscto = listaTipoDscto;
    }

    public BigDecimal getMontoPagar() {
        return montoPagar;
    }

    public void setMontoPagar(BigDecimal montoPagar) {
        this.montoPagar = montoPagar;
    }

    public BigDecimal getMontoPagarCondonado() {
        return montoPagarCondonado;
    }

    public void setMontoPagarCondonado(BigDecimal montoPagarCondonado) {
        this.montoPagarCondonado = montoPagarCondonado;
    }

    public Boolean getFlgCondParcial() {
        return flgCondParcial;
    }

    public void setFlgCondParcial(Boolean flgCondParcial) {
        this.flgCondParcial = flgCondParcial;
    }

}
