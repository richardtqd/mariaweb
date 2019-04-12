package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.RecEnvBean;
import pe.marista.sigma.bean.reporte.IngresoCajaPensionesRepBean;
import pe.marista.sigma.dao.RecEnvDAO;
import pe.marista.sigma.factory.BeanFactory;

public class RecEnvService {

    private RecEnvDAO recEnvDAO;

    public List<RecEnvBean> buscarProcesoRecEnv(String uniNeg, Date fecha) throws Exception {
        return recEnvDAO.buscarProcesoRecEnv(uniNeg, fecha);
    }

    public List<IngresoCajaPensionesRepBean> buscarPensionesEnCajaPorFecha(String uniNeg, Date fechaIni, Date fechaFin) throws Exception {
        return recEnvDAO.buscarPensionesEnCajaPorFecha(uniNeg, fechaIni, fechaFin);
    }

    @Transactional
    public Object execProEnvioCol(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        return recEnvDAO.execProEnvioCol(procesoEnvioBean);
    }

    public List<Contenedor> obtenerFiltroOperaciones(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        List<Contenedor> listaContenedor = new ArrayList<>();
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        Object valor = execProEnvioCol(procesoEnvioBean);
        if (valor != null) {
            System.out.println(">>>>> OK");
            listaContenedor = procesoFinalService.execProListaBancoCol(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle);
        }
        return listaContenedor;
    }

    public RecEnvDAO getRecEnvDAO() {
        return recEnvDAO;
    }

    public void setRecEnvDAO(RecEnvDAO recEnvDAO) {
        this.recEnvDAO = recEnvDAO;
    }

}
