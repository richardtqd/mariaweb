package pe.marista.sigma.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBloqueoBean;
import pe.marista.sigma.bean.reporte.EstudianteBloqueoRepBean;
import pe.marista.sigma.dao.EstudianteBloqueoDAO;
import pe.marista.sigma.factory.BeanFactory;

public class EstudianteBloqueoService {

    private EstudianteBloqueoDAO estudianteBloqueoDAO;

    public EstudianteBloqueoDAO getEstudianteBloqueoDAO() {
        return estudianteBloqueoDAO;
    }

    public void setEstudianteBloqueoDAO(EstudianteBloqueoDAO estudianteBloqueoDAO) {
        this.estudianteBloqueoDAO = estudianteBloqueoDAO;
    }

    public void insertar(EstudianteBloqueoBean estudianteBloqueoBean, List<EstudianteBloqueoBean> listaEstudianteBloqueoBean, EstudianteBean estudianteBean) throws Exception {
        EstudianteService estudianteService = BeanFactory.getEstudianteService();
        estudianteBloqueoBean.getTipoStatusBloqueoBean().setIdCodigo(MaristaConstantes.Id_Bloqueo_Activo);
        Calendar fechaBloq;
        fechaBloq = new GregorianCalendar();
        estudianteBloqueoBean.setFechaBloqueo(fechaBloq.getTime());
        estudianteBloqueoBean.setFechaBloqueo(estudianteBloqueoBean.getFechaBloqueo());
        estudianteBloqueoDAO.insertar(estudianteBloqueoBean);

        listaEstudianteBloqueoBean = estudianteBloqueoDAO.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
        if (!listaEstudianteBloqueoBean.isEmpty()) {
            Integer estado = 0;//0=bloqueado;1=activo idstatusestudiante MX_estudiante
            for (int i = 0; i < listaEstudianteBloqueoBean.size(); i++) {
                System.out.println("entró");
                System.out.println(listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo());
                System.out.println("fin----------");
                if (listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo() == 20902) { //Solucionado 
                    estado = 1;
                } else {
                    if (listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo() == 20901) {//Activo
                        estado = 0;
                        break;
                    }
                }
            }
            if (estado.equals(1)) {
                estudianteBean.setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
                estudianteBean.getTipoStatusEst().setIdCodigo(MaristaConstantes.COD_ESTUDIANTE_ACTIVO);
                estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                estudianteService.modificarTipoStatusEst(estudianteBean);
            } else {
                estudianteBean.setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
                estudianteBean.getTipoStatusEst().setIdCodigo(MaristaConstantes.COD_EST_BLO);
                estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                estudianteService.modificarTipoStatusEst(estudianteBean);
            }
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoBean = new CodigoBean();
            codigoBean.setIdCodigo(estudianteBean.getTipoStatusEst().getIdCodigo());
            codigoBean = codigoService.obtenerPorId(codigoBean);

            if (!codigoBean.equals(null)) {
                estudianteBean.setTipoStatusEst(codigoBean);
            }
        }
    }

    public List<EstudianteBloqueoBean> obtenerBloqueoPorEstudiantes(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception {
        return estudianteBloqueoDAO.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
    }

    public void actualizar(EstudianteBloqueoBean estudianteBloqueoBean, List<EstudianteBloqueoBean> listaEstudianteBloqueoBean, EstudianteBean estudianteBean) throws Exception {

        EstudianteService estudianteService = BeanFactory.getEstudianteService();

//        for (EstudianteBloqueoBean lista : listaEstudianteBloqueoBean) {
        Calendar fechaSolucion;
        Integer codEstBloq = 0;
        EstudianteBloqueoBean est = new EstudianteBloqueoBean();
        est.setIdEstudianteBloqueo(estudianteBloqueoBean.getIdEstudianteBloqueo());
        est.getUnidadNegocioBean().setUniNeg(estudianteBloqueoBean.getUnidadNegocioBean().getUniNeg());
        est.getTipoStatusBloqueoBean().setIdCodigo(estudianteBloqueoBean.getTipoStatusBloqueoBean().getIdCodigo());
        est.getTipoStatusBloqueoBean().setCodigo(estudianteBloqueoBean.getTipoStatusBloqueoBean().getCodigo());
        est.getEstudianteBean().setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
        est.setModiPor(estudianteBloqueoBean.getModiPor());
        if (est.getTipoStatusBloqueoBean().getIdCodigo().equals(MaristaConstantes.Id_Bloqueo_Activo)) {
            fechaSolucion = new GregorianCalendar();
            est.setFechaSolucion(fechaSolucion.getTime());
        } else {
            est.setFechaSolucion(null);
        }
        if (est.getTipoStatusBloqueoBean().getIdCodigo().equals(MaristaConstantes.Id_Bloqueo_Resuelto)) {
            fechaSolucion = new GregorianCalendar();
            est.setFechaSolucion(fechaSolucion.getTime());
        } else {
            est.setFechaSolucion(null);
        }

        estudianteBloqueoDAO.actualizar(est);
        estudianteBloqueoBean.setFechaSolucion(est.getFechaSolucion());
//            System.out.print("Aqui");
//            System.out.print(estudianteBloqueoBean.getFechaSolucion());
//            System.out.print(est.getFechaSolucion());

//        }
        Integer estado = 0;//0=bloqueado;1=activo idstatusestudiante MX_estudiante
        for (int i = 0; i < listaEstudianteBloqueoBean.size(); i++) {
            System.out.println("entró");
            System.out.println(listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo());
            System.out.println("fin----------");
            if (listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo() == 20902) { //Solucionado 
                estado = 1;
            } else {
                if (listaEstudianteBloqueoBean.get(i).getTipoStatusBloqueoBean().getIdCodigo() == 20901) {//Activo
                    estado = 0;
                    break;
                }
            }
        }
        if (estado.equals(1)) {
            estudianteBean.setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
            estudianteBean.getTipoStatusEst().setIdCodigo(MaristaConstantes.COD_ESTUDIANTE_ACTIVO);
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteService.modificarTipoStatusEst(estudianteBean);
        } else {
            estudianteBean.setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
            estudianteBean.getTipoStatusEst().setIdCodigo(MaristaConstantes.COD_EST_BLO);
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteService.modificarTipoStatusEst(estudianteBean);
        }
        CodigoService codigoService = BeanFactory.getCodigoService();
        CodigoBean codigoBean = new CodigoBean();
        codigoBean.setIdCodigo(estudianteBean.getTipoStatusEst().getIdCodigo());
        codigoBean = codigoService.obtenerPorId(codigoBean);

        if (!codigoBean.equals(null)) {
            estudianteBean.setTipoStatusEst(codigoBean);
        }

    }

    public EstudianteBloqueoBean obtenerEstudianteBloqueo(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception {
        return estudianteBloqueoDAO.obtenerEstudianteBloqueo(estudianteBloqueoBean);
    }

    public List<EstudianteBloqueoBean> obtenerBloqueoFiltro(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception {
        return estudianteBloqueoDAO.obtenerBloqueoFiltro(estudianteBloqueoBean);
    }

    public List<EstudianteBloqueoBean> obtenerFiltroEstudianteMasivo(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception {
        return estudianteBloqueoDAO.obtenerFiltroEstudianteMasivo(estudianteBloqueoBean);
    }

    public List<EstudianteBloqueoRepBean> obtenerCabecera(String uniNeg, Integer anio) throws Exception {
        return estudianteBloqueoDAO.obtenerCabecera(uniNeg, anio);
    }

    public List<EstudianteBloqueoRepBean> obtenerDetalle(String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return estudianteBloqueoDAO.obtenerDetalle(uniNeg, anio, idEstudiante);
    }

   

}
