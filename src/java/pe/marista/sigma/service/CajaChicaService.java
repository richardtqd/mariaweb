package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaSaldoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovRepBean;
import pe.marista.sigma.dao.CajaChicaDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class CajaChicaService {

    private CajaChicaDAO cajaChicaDAO;

    @Transactional
    public void insertarCajaChica(CajaChicaBean cajaChicaBean) throws Exception {
        cajaChicaBean.setSaldoSol(cajaChicaBean.getAperturaSol());
        cajaChicaBean.setSaldoDol(cajaChicaBean.getAperturaDol());
        cajaChicaDAO.insertarCajaChica(cajaChicaBean);
        System.out.println("idCajaChica: " + cajaChicaBean.getIdCajaChica());
        CajaChicaSaldoBean cajaChicaSaldoBean = new CajaChicaSaldoBean();
        CodigoService codigoService = BeanFactory.getCodigoService();
        List<CodigoBean> listaTipoMoneda = new ArrayList<>();
        listaTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
        cajaChicaSaldoBean.setCajaChicaBean(cajaChicaBean);
        cajaChicaSaldoBean.setCreaPor(cajaChicaBean.getCreaPor());
        for (CodigoBean tipoMoneda : listaTipoMoneda) {
            switch (tipoMoneda.getCodigo()) {
                case MaristaConstantes.PAGO_MON_SOL:
                    cajaChicaSaldoBean.setTipoMonedaBean(tipoMoneda);
                    cajaChicaSaldoBean.setImporte(cajaChicaBean.getAperturaSol());
                    cajaChicaDAO.insertarCajaChicaSaldo(cajaChicaSaldoBean);
                    break;
                case MaristaConstantes.PAGO_MON_DOL:
                    cajaChicaSaldoBean.setTipoMonedaBean(tipoMoneda);
                    cajaChicaSaldoBean.setImporte(cajaChicaBean.getAperturaDol());
                    cajaChicaDAO.insertarCajaChicaSaldo(cajaChicaSaldoBean);
                    break;
            }
        }
    }

    @Transactional
    public void modificarCajaChica(CajaChicaBean cajaChicaBean) throws Exception {
        cajaChicaDAO.modificarCajaChica(cajaChicaBean);
    }

    @Transactional
    public void eliminarCajaChica(CajaChicaBean cajaChicaBean) throws Exception {
        cajaChicaDAO.eliminarCajaChica(cajaChicaBean);
    }

    public CajaChicaBean obtenerCajaChicaPorId(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaDAO.obtenerCajaChicaPorId(cajaChicaBean);
    }

    public List<CajaChicaBean> obtenerCajaChica(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaDAO.obtenerCajaChica(cajaChicaBean);
    }

    public List<CajaChicaBean> obtenerCajaChicaAbierto(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaDAO.obtenerCajaChicaAbierto(cajaChicaBean);
    }

    public List<CajaChicaBean> obtenerCajaChicaPorFiltro(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaDAO.obtenerCajaChicaPorFiltro(cajaChicaBean);
    }

    public List<CajaChicaBean> obtenerUltimaCajaChicaCerrada(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaDAO.obtenerUltimaCajaChicaCerrada(cajaChicaBean);
    }

//    @Transactional
//    public void abrirCaja(CajaChicaBean cajaChicaBean) throws Exception {
//        cajaChicaDAO.abrirCaja(cajaChicaBean);
//    }
    @Transactional
    public void cerrarCaja(CajaChicaBean cajaChicaBean) throws Exception {
        cajaChicaDAO.cerrarCaja(cajaChicaBean);
    }

    //Getter y Setter
    public CajaChicaDAO getCajaChicaDAO() {
        return cajaChicaDAO;
    }

    public void setCajaChicaDAO(CajaChicaDAO cajaChicaDAO) {
        this.cajaChicaDAO = cajaChicaDAO;
    }

    public void modificarCajaChicaAnulacionDolares(String modiPor, String uniNeg, Integer idCajaChica, Double utilizado, Double saldo) throws Exception {
        cajaChicaDAO.modificarCajaChicaAnulacionDolares(modiPor, uniNeg, idCajaChica, utilizado, saldo);
    }

    public void modificarCajaChicaAnulacionSoles(String modiPor, String uniNeg, Integer idCajaChica, Double utilizado, Double saldo) throws Exception {
        cajaChicaDAO.modificarCajaChicaAnulacionSoles(modiPor, uniNeg, idCajaChica, utilizado, saldo);
    }

    public void modificarCajaChicaSaldoAnulacion(String modiPor, String uniNeg, Integer idCajaChica, Double importe, Integer idTipoMoneda) throws Exception {
        cajaChicaDAO.modificarCajaChicaSaldoAnulacion(modiPor, uniNeg, idCajaChica, importe, idTipoMoneda);
    } 
}
