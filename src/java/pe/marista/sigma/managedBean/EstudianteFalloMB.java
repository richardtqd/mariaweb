package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.bean.FalloBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.FalloService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class EstudianteFalloMB extends BaseMB implements Serializable {

    @PostConstruct
    public void EstudianteFalloMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            fechaActual = new GregorianCalendar();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //CLASES
    private UsuarioBean usuarioBean;
    private FalloBean falloBean;

    //LISTAS
    private List<FalloBean> listaFalloBean;

    //VARIABLES
    private Calendar fechaActual;

    //METODOS DE CLASE
    public void cargarDatos() {
        try {
            getFalloBean();
            getFalloBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFalloBean().setCreaFecha(fechaActual.getTime());
            FalloService falloService = BeanFactory.getFalloService();
            getListaFalloBean();
            listaFalloBean = falloService.obtenerPorUniNeg(falloBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarDatos() {
        try {
            Integer res = 0;
            if (falloBean.getCodigo() != null && !falloBean.getCodigo().equals("")) {
                falloBean.setCodigo(falloBean.getCodigo());
                res = 1;
            }
            if (falloBean.getEstudianteBean().getIdEstudiante() != null && !falloBean.getEstudianteBean().getIdEstudiante().equals("")) {
                falloBean.getEstudianteBean().setIdEstudiante(falloBean.getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (falloBean.getDiscente() != null && !falloBean.getDiscente().equals("")) {
                falloBean.setDiscente(falloBean.getDiscente());
                res = 1;
            }
            if (res == 1) {
                FalloService falloService = BeanFactory.getFalloService();
                listaFalloBean = falloService.filtrarFallo(falloBean);
                if (listaFalloBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFalloBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaFalloBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltro() {
        try {
            falloBean = new FalloBean();
            cargarDatos();
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

    public FalloBean getFalloBean() {
        if (falloBean == null) {
            falloBean = new FalloBean();
        }
        return falloBean;
    }

    public void setFalloBean(FalloBean falloBean) {
        this.falloBean = falloBean;
    }

    public List<FalloBean> getListaFalloBean() {
        if (listaFalloBean == null) {
            listaFalloBean = new ArrayList<>();
        }
        return listaFalloBean;
    }

    public void setListaFalloBean(List<FalloBean> listaFalloBean) {
        this.listaFalloBean = listaFalloBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

}
