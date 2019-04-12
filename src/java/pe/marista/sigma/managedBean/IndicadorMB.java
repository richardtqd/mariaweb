/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.IndicadorBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.IndicadorService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class IndicadorMB extends BaseMB implements Serializable {

    @PostConstruct
    public void IndicadorMB() {
        try {
            //MEtodos de Indicador
            obtenerTodos();
            obtenerCodigo();

            CodigoService codigoService1 = BeanFactory.getCodigoService();
            //Tipo Indicador
            getListaTipoIndicador();
            listaTipoIndicador = codigoService1.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Indicador));

            //Tipo Uso Indicador
            getListaTipoUsoIndicador();
            listaTipoUsoIndicador = codigoService1.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_UsoIndicador));

            getListaTipoValor();
            listaTipoValor = codigoService1.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));

//            listaTipoIndicador;
//            listaTipoUsoIndicador;
            //====================
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoIndicadorBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_INDICADOR));
            listaTipoIndicadorMap = new LinkedHashMap<String, Integer>();
            for (CodigoBean codigoBean : listaTipoIndicadorBean) {
                listaTipoIndicadorMap.put(codigoBean.getCodigo(), new Integer(codigoBean.getIdCodigo()));
            }
            listaTipoUsoIndicadorBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_USO_INDICADOR));
            listaTipoUsoIndicadorMap = new LinkedHashMap<String, Integer>();
            for (CodigoBean codigoBean : listaTipoUsoIndicadorBean) {
                listaTipoUsoIndicadorMap.put(codigoBean.getCodigo(), new Integer(codigoBean.getIdCodigo()));
            }
        } catch (Exception ex) {
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    private IndicadorBean indicadorBean;
    private IndicadorBean indicadorFiltroBean;
    private List<IndicadorBean> listaIndicadorBean;
    private List<CodigoBean> listaTipoIndicadorBean;
    private Map<String, Integer> listaTipoIndicadorMap;
    private List<CodigoBean> listaTipoUsoIndicadorBean;
    private Map<String, Integer> listaTipoUsoIndicadorMap;
    private List<CodigoBean> listaTipoIndicador;
    private List<CodigoBean> listaTipoUsoIndicador;
    private List<CodigoBean> listaTipoValor;

    //Metodos Get yu Set
    public IndicadorBean getIndicadorBean() {
        if (indicadorBean == null) {
            indicadorBean = new IndicadorBean();
        }
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
    }

    public IndicadorBean getIndicadorFiltroBean() {
        if (indicadorFiltroBean == null) {
            indicadorFiltroBean = new IndicadorBean();
        }
        return indicadorFiltroBean;
    }

    public void setIndicadorFiltroBean(IndicadorBean indicadorFiltroBean) {
        this.indicadorFiltroBean = indicadorFiltroBean;
    }

    public List<IndicadorBean> getListaIndicadorBean() {
        return listaIndicadorBean;
    }

    public void setListaIndicadorBean(List<IndicadorBean> listaIndicadorBean) {
        this.listaIndicadorBean = listaIndicadorBean;
    }

    public List<CodigoBean> getListaTipoIndicadorBean() {
        return listaTipoIndicadorBean;
    }

    public void setListaTipoIndicadorBean(List<CodigoBean> listaTipoIndicadorBean) {
        this.listaTipoIndicadorBean = listaTipoIndicadorBean;
    }

    public Map<String, Integer> getListaTipoIndicadorMap() {
        return listaTipoIndicadorMap;
    }

    public void setListaTipoIndicadorMap(Map<String, Integer> listaTipoIndicadorMap) {
        this.listaTipoIndicadorMap = listaTipoIndicadorMap;
    }

    public List<CodigoBean> getListaTipoUsoIndicadorBean() {
        return listaTipoUsoIndicadorBean;
    }

    public void setListaTipoUsoIndicadorBean(List<CodigoBean> listaTipoUsoIndicadorBean) {
        this.listaTipoUsoIndicadorBean = listaTipoUsoIndicadorBean;
    }

    public Map<String, Integer> getListaTipoUsoIndicadorMap() {
        return listaTipoUsoIndicadorMap;
    }

    public void setListaTipoUsoIndicadorMap(Map<String, Integer> listaTipoUsoIndicadorMap) {
        this.listaTipoUsoIndicadorMap = listaTipoUsoIndicadorMap;
    }

    //Codigo Indicador
    public List<CodigoBean> getListaTipoIndicador() {
        if (listaTipoIndicador == null) {
            listaTipoIndicador = new ArrayList<>();
        }
        return listaTipoIndicador;
    }

    public void setListaTipoIndicador(List<CodigoBean> listaTipoIndicador) {
        this.listaTipoIndicador = listaTipoIndicador;
    }

    public List<CodigoBean> getListaTipoUsoIndicador() {
        if (listaTipoUsoIndicador == null) {
            listaTipoUsoIndicador = new ArrayList<>();
        }
        return listaTipoUsoIndicador;
    }

    public void setListaTipoUsoIndicador(List<CodigoBean> listaTipoUsoIndicador) {
        this.listaTipoUsoIndicador = listaTipoUsoIndicador;
    }

    public List<CodigoBean> getListaTipoValor() {
        if(listaTipoValor == null){
            listaTipoValor = new ArrayList<>();
        }
        return listaTipoValor;
    }

    public void setListaTipoValor(List<CodigoBean> listaTipoValor) {
        this.listaTipoValor = listaTipoValor;
    }
 
    //=============================Metodos Indicador=================================//
    public void limpiarIndicadorBean() {
        try {
            indicadorBean = new IndicadorBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodos() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            listaIndicadorBean = indicadorService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            if (indicadorFiltroBean.getNombre() != null) {
                indicadorFiltroBean.setNombre(indicadorFiltroBean.getNombre().toUpperCase().trim());
            }
            if (indicadorFiltroBean.getCodigo() != null) {
                indicadorFiltroBean.setCodigo(indicadorFiltroBean.getCodigo().toUpperCase().trim());
            }
            if (indicadorFiltroBean.getFormula() != null) {
                indicadorFiltroBean.setFormula(indicadorFiltroBean.getFormula().toUpperCase().trim());
            }
            listaIndicadorBean = indicadorService.obtenerPorFiltro(indicadorFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            indicadorBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorBean = indicadorService.buscarPorId(indicadorBean.getIdIndicador());
            System.out.println(indicadorBean.getIdIndicador());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarIndicador() {
        try {
            if (indicadorBean.getIdIndicador() == null) {
                insertarIndicador();
            } else {
                modificarIndicador();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setCreaPor(user.getUsuario());
                indicadorBean.setFechaCrea(formato.parse(date));
                indicadorService.insertar(indicadorBean);
                listaIndicadorBean = indicadorService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setModiPor(user.getUsuario());
                indicadorService.actualizar(indicadorBean);
                listaIndicadorBean = indicadorService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarIndicador() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorService.eliminar(indicadorBean.getIdIndicador());
                listaIndicadorBean = indicadorService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPoId(Object object) {
        try {
            indicadorBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorService.buscarPorId(indicadorBean.getIdIndicador());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void obtenerLineaPorId(Object object) {//Faltante Linea estrategica Service
//        try {
//            lineaEstrategicaBean = (LineaEstrategicaBean) object;
//            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
//            lineaEstrategicaBean = lineaEstrategicaService.obtenerPorId(getLineaEstrategicaBean().getIdLinea());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerCodigo() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            String codigo = "IND-";
            if (indicadorBean == null) {
                indicadorBean = new IndicadorBean();
            }
            String cod = indicadorService.obtenerCodigo(indicadorBean.getCodigo());
            Integer var = Integer.parseInt(cod) + 1;
            String base = String.format("%03d", var);
            indicadorBean.setCodigo(codigo.concat(base));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            indicadorBean = (IndicadorBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

}
