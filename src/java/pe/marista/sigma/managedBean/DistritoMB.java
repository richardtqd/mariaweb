package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.ProvinciaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.DistritoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class DistritoMB implements Serializable {

    /**
     * Creates a new instance of DistritoMB
     */
    @PostConstruct
    public void DistritoMB() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDepartamentoBean = distritoService.obtenerDepartamentos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private List<DepartamentoBean> listaDepartamentoBean;
    private List<ProvinciaBean> listaProvinciaBean;
    private List<DistritoBean> listaDistritoBean;
    private DistritoBean distritoBean;
    private ProvinciaBean provinciaBean;
    private DepartamentoBean departamentoBean;

    public void obtenerProvincia() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaBean = distritoService.obtenerProvinciaPorDep(departamentoBean);
            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistrito() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoBean = distritoService.obtenerDistritoPorProv(provinciaBean);
            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<DepartamentoBean> getListaDepartamentoBean() {
        if (listaDepartamentoBean == null) {
            listaDepartamentoBean = new ArrayList<>();
        }
        return listaDepartamentoBean;
    }

    public void setListaDepartamentoBean(List<DepartamentoBean> listaDepartamentoBean) {
        this.listaDepartamentoBean = listaDepartamentoBean;
    }

    public List<ProvinciaBean> getListaProvinciaBean() {
        if (listaProvinciaBean == null) {
            listaProvinciaBean = new ArrayList<>();
        }
        return listaProvinciaBean;
    }

    public void setListaProvinciaBean(List<ProvinciaBean> listaProvinciaBean) {
        this.listaProvinciaBean = listaProvinciaBean;
    }

    public List<DistritoBean> getListaDistritoBean() {
        if (listaDistritoBean == null) {
            listaDistritoBean = new ArrayList<>();
        }
        return listaDistritoBean;
    }

    public void setListaDistritoBean(List<DistritoBean> listaDistritoBean) {
        this.listaDistritoBean = listaDistritoBean;
    }

    public DistritoBean getDistritoBean() {
        if (distritoBean == null) {
            distritoBean = new DistritoBean();
        }
        return distritoBean;
    }

    public void setDistritoBean(DistritoBean distritoBean) {
        this.distritoBean = distritoBean;
    }

    public ProvinciaBean getProvinciaBean() {
        if (provinciaBean == null) {
            provinciaBean = new ProvinciaBean();
        }
        return provinciaBean;
    }

    public void setProvinciaBean(ProvinciaBean provinciaBean) {
        this.provinciaBean = provinciaBean;
    }

    public DepartamentoBean getDepartamentoBean() {
        if (departamentoBean == null) {
            departamentoBean = new DepartamentoBean();
        }
        return departamentoBean;
    }

    public void setDepartamentoBean(DepartamentoBean departamentoBean) {
        this.departamentoBean = departamentoBean;
    }

}
