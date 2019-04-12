package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BloqueoService;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class NuevoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void NuevoMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            //OBTENIENDO CLASES
            getCatalogoFiltroBean();
            getCatalogoFiltroBean().setIdtipoMoneda(MaristaConstantes.COD_SOLES);
            getCatalogoBean();
            getCatalogoBean().getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);

            //OBTENIENDO LISTAS
            getListaCatalogoFiltroBean();
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidadBean();
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCategoriaBean();
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            getListaTipoMonedaBean();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            getListaTipoUnidadMedidaBean();
            listaTipoUnidadMedidaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUnidadMedida"));

            //SETEANDO VALOR DE CATEGORIA
            setIdTipoCategoria(getTipoMaterial());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //CLASES
    private UsuarioBean usuarioBean;
    private CatalogoBean catalogoBean;
    private CatalogoBean catalogoFiltroBean;

    //LISTAS
    private List<CodigoBean> listaTipoUnidadMedidaBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<EntidadBean> listaEntidadBean;
    private List<CatalogoBean> listaCatalogoFiltroBean;

    //TIPOS DE CATEGORIA
    private Integer idTipoCategoria;
    private Integer tipoMaterial = MaristaConstantes.Id_Categoria_Almacen;
    private Integer tipoActivo = MaristaConstantes.Id_Categoria_Activo;
    private Integer tipoServicio = MaristaConstantes.Id_Categoria_Servicio;

    //METODOS
    public void obtenerPorFiltroCat() {
        try {
            Integer res = 0;
            getCatalogoFiltroBean();
            if (catalogoFiltroBean.getItem() != null && !catalogoFiltroBean.getItem().equals("")) {
                catalogoFiltroBean.setItem(catalogoFiltroBean.getItem());
                res = 1;
            }
            if (catalogoFiltroBean.getRuc() != null && !catalogoFiltroBean.getRuc().equals("")) {
                catalogoFiltroBean.setRuc(catalogoFiltroBean.getRuc());
                res = 1;
            }
            if (catalogoFiltroBean.getIdTipoCategoria() != null) {
                catalogoFiltroBean.setIdTipoCategoria(catalogoFiltroBean.getIdTipoCategoria());
                res = 1;
            }
            if (catalogoFiltroBean.getIdtipoMoneda() != null) {
                catalogoFiltroBean.setIdtipoMoneda(catalogoFiltroBean.getIdtipoMoneda());
                res = 1;
            }
            if (catalogoFiltroBean.getIdTipoUnidadMedida() != null) {
                catalogoFiltroBean.setIdTipoUnidadMedida(catalogoFiltroBean.getIdTipoUnidadMedida());
                res = 1;
            }
            if (res == 1) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                listaCatalogoFiltroBean = catalogoService.obtenerPorFiltroCategoria(catalogoFiltroBean);
                if (listaCatalogoFiltroBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaCatalogoFiltroBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaCatalogoFiltroBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroCatalogo() {
        try {
            catalogoFiltroBean = new CatalogoBean();
            catalogoFiltroBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
            listaCatalogoFiltroBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEntidadPorId(EntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidad = new EntidadBean();
            entidad.setRuc(bean.getRuc());
            entidad.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            entidad = entidadService.obtenerEntidadPorId(entidad);
            catalogoBean.setEntidadBean(entidad);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCatalogoPorId(Object obj) {
        try {
            catalogoBean = (CatalogoBean) obj;
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean = catalogoService.obtenerPorId(catalogoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void grabar() {
        try {
            if (catalogoBean.getIdCatalogo() == null) {
                insertar();
            } else {
                modificar();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                catalogoBean.setCreaPor(usuarioBean.getUsuario());
                catalogoService.insertar(catalogoBean);
                limpiarCatalogo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void modificar() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean.setModiPor(usuarioBean.getUsuario());
            catalogoService.actualizar(catalogoBean);
            limpiarCatalogo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCatalogo() {
        try {
            catalogoBean = new CatalogoBean();
            catalogoBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void eliminarItem() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoService.eliminar(catalogoBean);
            obtenerPorFiltroCat();
            limpiarCatalogo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void setearCatalogo() {
        try {
            if (catalogoFiltroBean.getEntidadBean().getRuc() == null) {
                catalogoFiltroBean.getEntidadBean().setRuc(catalogoFiltroBean.getEntidadBean().getRuc());
            }
            if (catalogoFiltroBean.getTipoMonedaBean().getIdCodigo() == null) {
                catalogoFiltroBean.getTipoMonedaBean().setIdCodigo(catalogoFiltroBean.getTipoMonedaBean().getIdCodigo());
            }
            if (catalogoFiltroBean.getTipoUnidadMedidaBean().getIdCodigo() == null) {
                catalogoFiltroBean.getTipoUnidadMedidaBean().setIdCodigo(catalogoFiltroBean.getTipoUnidadMedidaBean().getIdCodigo());
            }
            if (catalogoFiltroBean.getTipoCategoriaBean().getIdCodigo() == null) {
                catalogoFiltroBean.getTipoCategoriaBean().setIdCodigo(catalogoFiltroBean.getTipoCategoriaBean().getIdCodigo());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* METODOS SUBIDA DE ARCHIVO */
    public void cargarCsv(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            System.out.println(">>>" + destination);
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CatalogoBean catalogoBean = new CatalogoBean();
            catalogoBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            catalogoBean.setObjFile(fileName);
            catalogoService.execProInventarioCarga(catalogoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ponerTipoSolicitud() {
        try {
            if (getIdTipoCategoria() != null) {
                System.out.println(">>>" + getIdTipoCategoria());
                getCatalogoBean().getTipoCategoriaBean().setIdCodigo(getIdTipoCategoria());
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null) {
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public List<CodigoBean> getListaTipoUnidadMedidaBean() {
        if (listaTipoUnidadMedidaBean == null) {
            listaTipoUnidadMedidaBean = new ArrayList<>();
        }
        return listaTipoUnidadMedidaBean;
    }

    public void setListaTipoUnidadMedidaBean(List<CodigoBean> listaTipoUnidadMedidaBean) {
        this.listaTipoUnidadMedidaBean = listaTipoUnidadMedidaBean;
    }

    public List<CodigoBean> getListaTipoMonedaBean() {
        if (listaTipoMonedaBean == null) {
            listaTipoMonedaBean = new ArrayList<>();
        }
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public List<CodigoBean> getListaTipoCategoriaBean() {
        if (listaTipoCategoriaBean == null) {
            listaTipoCategoriaBean = new ArrayList<>();
        }
        return listaTipoCategoriaBean;
    }

    public void setListaTipoCategoriaBean(List<CodigoBean> listaTipoCategoriaBean) {
        this.listaTipoCategoriaBean = listaTipoCategoriaBean;
    }

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }

    public List<CatalogoBean> getListaCatalogoFiltroBean() {
        if (listaCatalogoFiltroBean == null) {
            listaCatalogoFiltroBean = new ArrayList<>();
        }
        return listaCatalogoFiltroBean;
    }

    public void setListaCatalogoFiltroBean(List<CatalogoBean> listaCatalogoFiltroBean) {
        this.listaCatalogoFiltroBean = listaCatalogoFiltroBean;
    }

    public CatalogoBean getCatalogoFiltroBean() {
        if (catalogoFiltroBean == null) {
            catalogoFiltroBean = new CatalogoBean();
        }
        return catalogoFiltroBean;
    }

    public void setCatalogoFiltroBean(CatalogoBean catalogoFiltroBean) {
        this.catalogoFiltroBean = catalogoFiltroBean;
    }

    public Integer getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public Integer getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(Integer tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Integer getTipoActivo() {
        return tipoActivo;
    }

    public void setTipoActivo(Integer tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public Integer getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(Integer tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

}
