/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.ProcesoFilesDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class ProcesoFilesService {

    private ProcesoFilesDAO procesoFilesDAO;

    @Transactional
    public void insertarProcesosFilesBanco(ProcesoFilesBean procesoFilesBean) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        procesoFilesBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        procesoFilesBean.setIdFilePadre(0);
        procesoFilesBean.setEstado(1);
        procesoFilesBean.getTipoFile().setIdCodigo(0);
        procesoFilesBean.setLongitud(0);
        procesoFilesBean.setPosicionIni(0);
        procesoFilesBean.setPosicionFin(0);
        procesoFilesBean.setCreaPor(beanUsuarioSesion.getUsuario());
        procesoFilesDAO.insertarProcesoFiles(procesoFilesBean);
    }

    @Transactional
    public void insertarProcesosFilesDetaBanco(ProcesoFilesBean procesoFilesDetaBean, ProcesoFilesBean procesoFilesBean, Integer flgPro) throws Exception {
        //Verificando Codigos
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        Integer posicionCabecera = 0;
        Integer posicionDetalle = 0;
        Integer posicionIntermedio = 0;

        //Obteniendo Codigos 
        CodigoService codigoService = BeanFactory.getCodigoService();
        CodigoBean codigoTipoFileCab = new CodigoBean();
        CodigoBean codigoTipoIntCab = new CodigoBean();
        codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
        codigoTipoIntCab = codigoService.obtenerPorCodigo(new CodigoBean(20003, "Intermedio", new TipoCodigoBean(MaristaConstantes.file_Intermedio)));

        List<ProcesoFilesBean> listaProcesoFilesDetBean = new ArrayList<>();
        List<ProcesoFilesBean> listaProcesoFilesDetDetBean = new ArrayList<>();
        List<ProcesoFilesBean> listaProcesoFilesIntDetBean = new ArrayList<>();

        ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
        listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
        listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
        listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);

        Integer longitud = 0;
        if (procesoFilesDetaBean.getPosicionFin() > procesoFilesDetaBean.getPosicionIni() || procesoFilesDetaBean.getPosicionFin().equals(procesoFilesDetaBean.getPosicionIni())) {
            longitud = procesoFilesDetaBean.getPosicionFin() - procesoFilesDetaBean.getPosicionIni();
            procesoFilesDetaBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoFilesDetaBean.setLongitud(longitud + 1);
            procesoFilesDetaBean.setEstado(1);
            //Validando Posiciones
            if (procesoFilesDetaBean.getTipoFile().getIdCodigo().equals(codigoTipoFileCab.getIdCodigo())) {
                posicionCabecera = listaProcesoFilesDetBean.size() + 1;
                procesoFilesDetaBean.setPosicionItem(posicionCabecera);
            } else {
                if (procesoFilesDetaBean.getTipoFile().getIdCodigo().equals(codigoTipoIntCab.getIdCodigo())) {
                    posicionIntermedio = listaProcesoFilesIntDetBean.size() + 1;
                    procesoFilesDetaBean.setPosicionItem(posicionIntermedio);
                } else {
                    posicionDetalle = listaProcesoFilesDetDetBean.size() + 1;
                    procesoFilesDetaBean.setPosicionItem(posicionDetalle);
                }
            }
            procesoFilesDetaBean.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoFilesDAO.insertarProcesoFiles(procesoFilesDetaBean);
        } else {
        }
    }

    public List<ProcesoFilesBean> obtenerProcesosFiles(String uniNeg) throws Exception {
        return procesoFilesDAO.obtenerProcesosFiles(uniNeg);
    }

    public List<ProcesoFilesBean> obtenerProcesosFilesDeta(String uniNeg, Integer idFile) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesDeta(uniNeg, idFile);
    }

//Obteniendo Listas
    public List<ProcesoFilesBean> obtenerProcesosFilesDetaCab(String uniNeg, Integer idFile) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesDetaCab(uniNeg, idFile);
    }

    public List<ProcesoFilesBean> obtenerProcesosFilesDetaDet(String uniNeg, Integer idFile) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesDetaDet(uniNeg, idFile);
    }
//End 

//Obteniendo Listas/Proceso 
    public List<ProcesoFilesBean> obtenerProcesosFilesDetaCabPro(String uniNeg, Integer idFile, Integer flgProceso) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesDetaCabPro(uniNeg, idFile, flgProceso);
    }

    public List<ProcesoFilesBean> obtenerProcesosFilesDetaDetPro(String uniNeg, Integer idFile, Integer flgProceso) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesDetaDetPro(uniNeg, idFile, flgProceso);
    }

    public List<ProcesoFilesBean> obtenerProcesosFilesConFormula(String uniNeg, Integer idFile, String txt) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesConFormula(uniNeg, idFile, txt);
    }

    public String obtenerValor(String txt) throws Exception {
        if (txt != null) {
            txt = procesoFilesDAO.obtenerValor(txt);
        } else {
            txt = "xxx";
        }
        return txt;
    }

    public List<ProcesoFilesBean> obtenerProcesosFilesIntDetPro(String uniNeg, Integer idFile, Integer flgProceso) throws Exception {
        return procesoFilesDAO.obtenerProcesosFilesIntDetPro(uniNeg, idFile, flgProceso);
    }

//End
//Obteniendo Lista para Procesos de Bancos
    public List<ProcesoFilesBean> obtenerFileProceso(String uniNeg, String ruc, Integer flgProceso, Integer idTipoFile) throws Exception {
        return procesoFilesDAO.obtenerFileProceso(uniNeg, ruc, flgProceso, idTipoFile);
    }
    public List<ProcesoFilesBean> obtenerFileProcesoVer2(String uniNeg, String ruc, Integer flgProceso, Integer idTipoFile) throws Exception {
        return procesoFilesDAO.obtenerFileProcesoVer2(uniNeg, ruc, flgProceso, idTipoFile);
    }
//End

    public ProcesoFilesBean obtenerFilesId(Integer idFile) throws Exception {
        return procesoFilesDAO.obtenerFilesId(idFile);
    }

    public ProcesoFilesBean obtenerDetaFilesId(String uniNeg, Integer idFile) throws Exception {
        return procesoFilesDAO.obtenerDetaFilesId(uniNeg, idFile);
    }

    public Integer obtenerMaxPosFin(String uniNeg, String ruc) throws Exception {
        return procesoFilesDAO.obtenerMaxPosFin(uniNeg, ruc);
    }

    public Integer obtenerNumFiles(String uniNeg, Integer flg, Integer idtipofile, String ruc) throws Exception {
        return procesoFilesDAO.obtenerNumFiles(uniNeg, flg, idtipofile, ruc);
    }

    @Transactional
    public void modificarProcesosFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarProcesosFiles(procesoFilesBean);
    }

    @Transactional
    public void modificarPosicionFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarPosicionFiles(procesoFilesBean);
    }

    @Transactional
    public void modificarCabeceraFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarCabeceraFiles(procesoFilesBean);
    }

    @Transactional
    public void modificarDetallesFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarDetallesFiles(procesoFilesBean);
    }

    @Transactional
    public void modificarSuperFile(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarSuperFile(procesoFilesBean);
    }

    @Transactional
    public void modificarPosicionesFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarPosicionesFiles(procesoFilesBean);
    }

    @Transactional
    public void eliminarProcesosFile(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.eliminarProcesosFile(procesoFilesBean);
    }

    @Transactional
    public void insertarProcesoFiles(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.insertarProcesoFiles(procesoFilesBean);
    }

    @Transactional
    public void modificarDisabled(ProcesoFilesBean procesoFilesBean) throws Exception {
        procesoFilesDAO.modificarDisabled(procesoFilesBean);
    }

    public Integer obtenerUltimaPosicion(String uniNeg, Integer idTipoFile, String ruc, Integer flgProceso) throws Exception {
        return procesoFilesDAO.obtenerUltimaPosicion(uniNeg, idTipoFile, ruc, flgProceso);
    }

    public Integer obtenerPosTipoFile(ProcesoFilesBean procesoFilesBean) throws Exception {
        return procesoFilesDAO.obtenerPosTipoFile(procesoFilesBean);
    }

    public ProcesoFilesDAO getProcesoFilesDAO() {
        return procesoFilesDAO;
    }

    public void setProcesoFilesDAO(ProcesoFilesDAO procesoFilesDAO) {
        this.procesoFilesDAO = procesoFilesDAO;
    }

}
