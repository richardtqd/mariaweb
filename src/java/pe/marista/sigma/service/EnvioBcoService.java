/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EnvioBcoBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoEnvioUniNegBean;
import pe.marista.sigma.dao.EnvioBcoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS-001
 */
public class EnvioBcoService {

    private EnvioBcoDAO envioBcoDAO;

    public List<EnvioBcoBean> obtenerListaEnvioBcoPorId(String uniNeg, Integer idProcesoBancoIns) throws Exception {
        return envioBcoDAO.obtenerListaEnvioBcoPorId(uniNeg, idProcesoBancoIns);
    }

    @Transactional
    public void CURSOR_insertarLogEnvioBco(String uniNeg, Date fechaIni, Date fechaFin, TipoEnvioUniNegBean tipoEnvioUniNegBean, List<Integer> listAnios, List<Integer> listMeses, String usuario, ProcesoBancoBean procesoBancoBean) throws Exception {

        ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
        Integer registros = 0;
//        Integer registrosOK = 0;
        Integer registrosError = 0;
        Integer idProcesoBancoIns = 0;
        Double montoRecuperado = new Double("0.0");

        ProcesoBancoBean proceso = new ProcesoBancoBean();
        //INSERTANDO PROCESO BANCO
        Date fecha = new Date();
        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
        String date = fechaHora.format(fecha.getTime());
        Date date1 = fechaHora.parse(date);

        String nombreFile = envioBcoDAO.obtenerNombreFilePorFormula(uniNeg, null);
        System.out.println("nombreFile:" + nombreFile);
        proceso.setIdProcesoBanco(idProcesoBancoIns);
        proceso.getUnidadNegocioBean().setUniNeg(uniNeg);
        proceso.setFlgProceso(1);//0:recuperacion , 1:envio
        proceso.setTipoArchivo("C");
        proceso.setNombre(nombreFile);
        proceso.setRegEnv(registros);
        proceso.setRegError(registrosError);
        proceso.setMontoRecup(montoRecuperado.floatValue());
        proceso.setCreaPor(usuario);
        proceso.setFecha(new Date());
        procesoBancoService.insertarProcesoBanco(proceso);
        idProcesoBancoIns = (proceso.getIdProcesoBanco());
        System.out.println("idProcesoBancoIns " + idProcesoBancoIns);
        procesoBancoBean.setIdProcesoBanco(idProcesoBancoIns);
        procesoBancoBean.setNombre(nombreFile);
        //A. INSERTANDO DETALLE //Insertando Registros
        envioBcoDAO.CURSOR_insertarLogEnvioBco(uniNeg, fechaIni, fechaFin, tipoEnvioUniNegBean, listAnios, listMeses, usuario, idProcesoBancoIns);

        //List<EnvioBcoBean> lista = envioBcoDAO.obtenerListaEnvioBcoPorId(uniNeg, idProcesoBancoIns);
        Integer cantReg = envioBcoDAO.obtenerCantRegPorId(uniNeg, idProcesoBancoIns);

        //c. modificando PROCESO BANCO
        ProcesoBancoBean procesoBcoModi = new ProcesoBancoBean();
        procesoBcoModi.setIdProcesoBanco(idProcesoBancoIns);
        procesoBcoModi.getUnidadNegocioBean().setUniNeg(uniNeg);
        procesoBcoModi.setRegEnv(cantReg);
        procesoBcoModi.setRegError(registrosError);
        procesoBcoModi.setMontoRecup(montoRecuperado.floatValue());
        procesoBancoService.modificarProcesoBancoVer3(procesoBcoModi);

        //D: insertando cabecera
        ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
        List<ProcesoFilesBean> listaProcesoFilesCabeceraBean = procesoFilesService.obtenerFileProcesoVer2(uniNeg, null, 2, MaristaConstantes.FileCabecera);
        List<ProcesoFilesBean> listaProcesoFilesIntermetidoBean = procesoFilesService.obtenerFileProcesoVer2(uniNeg, null, 2, MaristaConstantes.FileIntermedio);
        if (!listaProcesoFilesCabeceraBean.isEmpty()) {
            if (listaProcesoFilesCabeceraBean.size() > 0) {
                System.out.println("tiene cabecera");
                envioBcoDAO.CURSOR_insertarLogEnvioBcoCabecera(uniNeg, fechaIni, fechaFin, tipoEnvioUniNegBean, listAnios, listMeses, usuario, idProcesoBancoIns);
            }
        }
        if (!listaProcesoFilesIntermetidoBean.isEmpty()) {
            if (listaProcesoFilesIntermetidoBean.size() > 0) {
                System.out.println("tiene intermedio");
                envioBcoDAO.CURSOR_insertarLogEnvioBcoIntermedio(uniNeg, fechaIni, fechaFin, tipoEnvioUniNegBean, listAnios, listMeses, usuario, idProcesoBancoIns);
            }
        }

        System.out.println("idProcesoBcoInst:" + procesoBancoBean.getIdProcesoBanco());

    }

    public List<EnvioBcoBean> obtenerListaIntermedioRegPorId(String uniNeg, Integer idProcesoBancoIns) throws Exception {
        return envioBcoDAO.obtenerListaIntermedioRegPorId(uniNeg, idProcesoBancoIns);
    }
    
    

    public Integer countCURSOR_insertarLogEnvioBco(String uniNeg, Date fechaIni, Date fechaFin, Integer idTipoStatusCtaCte, List<Integer> listAnios, List<Integer> listMeses) throws Exception {
        return envioBcoDAO.countCURSOR_insertarLogEnvioBco(uniNeg, fechaIni, fechaFin, idTipoStatusCtaCte, listAnios, listMeses);
    }

    public String obtenerCabeceraRegPorId(String uniNeg, Integer idProcesoBancoIns) throws Exception {
        return envioBcoDAO.obtenerCabeceraRegPorId(uniNeg, idProcesoBancoIns);
    }

    public String obtenerNombreFilePorFormula(String formula, Date fecha) throws Exception {
        return envioBcoDAO.obtenerNombreFilePorFormula(formula, fecha);
    }

    //metodos getter and setter
    public EnvioBcoDAO getEnvioBcoDAO() {
        return envioBcoDAO;
    }

    public void setEnvioBcoDAO(EnvioBcoDAO envioBcoDAO) {
        this.envioBcoDAO = envioBcoDAO;
    }

}
