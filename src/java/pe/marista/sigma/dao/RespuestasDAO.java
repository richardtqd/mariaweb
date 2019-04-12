/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.PreguntaBean;
import pe.marista.sigma.bean.RespuestasBean;

/**
 *
 * @author Administrador
 */
public interface RespuestasDAO {

    public List<RespuestasBean> obtenerRespuestas() throws Exception;
    
    public List<RespuestasBean> obtenerRespuestasPorOrden(Integer orden) throws Exception;

    public void insertarRespuestas(RespuestasBean respuestasBean) throws Exception;

    public void modificarRespuestas(RespuestasBean respuestasBean) throws Exception;

    public void eliminarRespuestas(RespuestasBean respuestasBean) throws Exception;

    public RespuestasBean obtenerRespuestasPorId(RespuestasBean respuestasBean) throws Exception;
    
    public RespuestasBean obtenerUltimoOrden() throws Exception;
    
    
    //preguntas
    
    public List<PreguntaBean> obtenerPregunta() throws Exception;

    public void insertarPregunta(PreguntaBean preguntaBean) throws Exception;

    public void modificarPregunta(PreguntaBean preguntaBean) throws Exception;

    public void eliminarPregunta(PreguntaBean preguntaBean) throws Exception;

    public PreguntaBean obtenerPreguntaPorId(PreguntaBean preguntaBean) throws Exception;
    

}
