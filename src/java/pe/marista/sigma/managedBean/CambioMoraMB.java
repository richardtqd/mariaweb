/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author JC
 */
public class CambioMoraMB extends BaseMB implements Serializable {

    @PostConstruct
    public void CambioMoraMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            fechaActual = new GregorianCalendar();

            //OBTENIENDO LISTA DE MESES
            obtenerListaMeses();

            //PONIENDO MATRICULA
            getMatriculaBean();
            getMatriculaBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMatriculaBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            getMatriculaBean().setFechaInicio(fechaActual.getTime());

            //PONIENDO MATRICUL VIEW
            getMatriculaViewBean();
            getMatriculaViewBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //CLASES
    private UsuarioBean usuarioBean;
    private MatriculaBean matriculaBean;
    private MatriculaBean matriculaViewBean;

    //LISTAS
    private List<MesBean> listaMeses;
    private List<MatriculaBean> listaMatriculaBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;

    //VARIABLES
    private Calendar fechaActual;
    private Integer mes;

    //METODOS
    public void obtenerListaMeses() {
        try {
            getListaMeses();
            listaMeses.add(new MesBean(2, MaristaConstantes.FEBRERO));
            listaMeses.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMeses.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMeses.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMeses.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMeses.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMeses.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMeses.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMeses.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMeses.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMeses.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarDatos() {
        try {
            Integer res = 0;
            matriculaBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (matriculaBean.getEstudianteBean().getIdEstudiante() != null
                    && !matriculaBean.getEstudianteBean().getIdEstudiante().equals("")) {
                matriculaBean.getEstudianteBean().setIdEstudiante(matriculaBean.getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (matriculaBean.getEstudianteBean().getCodigo() != null
                    && !matriculaBean.getEstudianteBean().getCodigo().equals("")) {
                matriculaBean.getEstudianteBean().setCodigo(matriculaBean.getEstudianteBean().getCodigo());
                res = 1;
            }
            if (matriculaBean.getEstudianteBean().getPersonaBean().getApepat() != null
                    && !matriculaBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaBean.getEstudianteBean().getPersonaBean().setApepat(matriculaBean.getEstudianteBean().getPersonaBean().getApepat());
                res = 1;
            }
            if (matriculaBean.getEstudianteBean().getPersonaBean().getApemat() != null
                    && !matriculaBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaBean.getEstudianteBean().getPersonaBean().setApemat(matriculaBean.getEstudianteBean().getPersonaBean().getApemat());
                res = 1;
            }
            if (matriculaBean.getEstudianteBean().getPersonaBean().getNombre() != null
                    && matriculaBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaBean.getEstudianteBean().getPersonaBean().setNombre(matriculaBean.getEstudianteBean().getPersonaBean().getNombre());
                res = 1;
            }
            if (matriculaBean.getAnio() != null) {
                matriculaBean.setAnio(matriculaBean.getAnio());
                res = 1;
            }
            if (res == 1) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                listaMatriculaBean = matriculaService.obtenerFiltroMatriculados(matriculaBean);
                if (listaMatriculaBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaMatriculaBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaMatriculaBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltro() {
        try {
            matriculaBean = new MatriculaBean();
            getMatriculaBean();
            matriculaBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            matriculaBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuenta(Object obj) {
        try {
            MatriculaBean matricula = (MatriculaBean) obj;
            matriculaViewBean = matricula;
            CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().setIdPersona(matricula.getEstudianteBean().getIdEstudiante());
            cuentasPorCobrarBean.setAnio(matricula.getAnio());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCtaCtePorEstudiantePorAnio(cuentasPorCobrarBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarMora() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            for (MatriculaBean matricula : listaMatriculaBean) {
                matricula.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                matricula.setFechaInicio(matriculaBean.getFechaInicio());
                if (matriculaBean.getMes() != null) {
                    matricula.setMes(matriculaBean.getMes());
                }
                cuentasPorCobrarService.modificarMora(matricula);
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public List<MesBean> getListaMeses() {
        if (listaMeses == null) {
            listaMeses = new ArrayList<>();
        }
        return listaMeses;
    }

    public void setListaMeses(List<MesBean> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<MatriculaBean> getListaMatriculaBean() {
        if (listaMatriculaBean == null) {
            listaMatriculaBean = new ArrayList<>();
        }
        return listaMatriculaBean;
    }

    public void setListaMatriculaBean(List<MatriculaBean> listaMatriculaBean) {
        this.listaMatriculaBean = listaMatriculaBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
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

    public MatriculaBean getMatriculaViewBean() {
        if (matriculaViewBean == null) {
            matriculaViewBean = new MatriculaBean();
        }
        return matriculaViewBean;
    }

    public void setMatriculaViewBean(MatriculaBean matriculaViewBean) {
        this.matriculaViewBean = matriculaViewBean;
    }

}
