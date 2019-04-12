package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PreguntaBean;
import pe.marista.sigma.bean.RespuestasBean;
import pe.marista.sigma.dao.RespuestasDAO;


/**
 *
 * @author Administrador
 */
public class RespuestasService {

    private RespuestasDAO respuestasDAO;

    //Logica de Negocio
    
    //respuestas
    @Transactional
    public void insertarRespuestas(RespuestasBean respuestasBean,List<PreguntaBean> listaPreguntaBean,RespuestasBean resp) throws Exception {
        for (PreguntaBean preg : listaPreguntaBean) { 
            RespuestasBean respuestas = new RespuestasBean();
            respuestas.setOrden(resp.getOrden()+1);
            respuestas.getPreguntaBean().setIdPregunta(preg.getIdPregunta()); 
            respuestasDAO.insertarRespuestas(respuestas);
        }
    }

    @Transactional
    public void eliminarRespuestas(RespuestasBean respuestasBean) throws Exception {
        respuestasDAO.eliminarRespuestas(respuestasBean);
    }

    @Transactional
    public void modificarRespuestas(RespuestasBean respuestasBean) throws Exception {
        respuestasDAO.modificarRespuestas(respuestasBean);
    }
    
     public RespuestasBean obtenerRespuestasPorId(RespuestasBean respuestasBean) throws Exception {
        return respuestasDAO.obtenerRespuestasPorId(respuestasBean);
    }
    
     public RespuestasBean obtenerUltimoOrden() throws Exception {
        return respuestasDAO.obtenerUltimoOrden();
    }

    public List<RespuestasBean> obtenerRespuestasPorOrden(Integer orden) throws Exception{
       return respuestasDAO.obtenerRespuestasPorOrden(orden);
    }
    
    
    //pregunta
    @Transactional
    public void insertarPregunta(PreguntaBean preguntaBean) throws Exception {
        respuestasDAO.insertarPregunta(preguntaBean);
    }

    @Transactional
    public void eliminarPregunta(PreguntaBean preguntaBean) throws Exception {
        respuestasDAO.eliminarPregunta(preguntaBean);
    }

    @Transactional
    public void modificarPregunta(PreguntaBean preguntaBean) throws Exception {
        respuestasDAO.modificarPregunta(preguntaBean);
    }
    
     public PreguntaBean obtenerPreguntaPorId(PreguntaBean preguntaBean) throws Exception {
        return respuestasDAO.obtenerPreguntaPorId(preguntaBean);
    }

    public List<PreguntaBean> obtenerPregunta() throws Exception{
       return respuestasDAO.obtenerPregunta();
    }
    
    //Getter y Setter

    public RespuestasDAO getRespuestasDAO() {
        return respuestasDAO;
    }

    public void setRespuestasDAO(RespuestasDAO respuestasDAO) {
        this.respuestasDAO = respuestasDAO;
    }
   

}
