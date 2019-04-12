/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import pe.marista.sigma.bean.LogBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class GLTLog {

    // Categorias
    public static final int CATEGORIA_DEBUG = 0;
    public static final int CATEGORIA_INFO = 1;
    public static final int CATEGORIA_ERROR = 2;
    // tablas
    public static final String TABLA_USUARIO = "Usuario";
    // Acciones
    public static final int ACCION_INSERTAR = 1;
    public static final int ACCION_MODIFICAR = 2;
    public static final int ACCION_ELIMINAR = 3;
    public static final int ACCION_CONSULTAR = 4;
    public static final int ACCION_ERROR = 5;
    //Estados Acciones
    public static final String ESTADO_INSERCION = "Inserción";
    public static final String ESTADO_MODIFICACION = "Modificación";
    public static final String ESTADO_ELIMINACION = "Eliminación";
    public static final String ESTADO_ERROR = "Error";
    public static final String ESTADO_CONSULTA = "Consulta";
    static Logger logger;

    private static void write(int categoria, Class clase, String message, Exception err) {
        logger = Logger.getLogger(clase);

        switch (categoria) {
            case CATEGORIA_DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(message);
                }
                break;
            case CATEGORIA_INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(message);
                }
                break;
            case CATEGORIA_ERROR:
                StringWriter errors = new StringWriter();
                err.printStackTrace(new PrintWriter(errors));
                message = message + " El error es: " + errors.toString();
                logger.error(message);
                break;
        }
    }

    public static void write(int categoria, Class clase, String message) {
        UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        message = "User: " + user.getUsuario() + " - " + message;
        Exception err = null;
        write(categoria, clase, message, err);
    }

    /**
     * Almacena la accion realizada en la Base de Datos.
     *
     * @param clase
     * @param accion
     * @param tabla
     * @param idRegistroAfectado
     * @param descripcion
     * @throws java.lang.Exception
     */
    public static void writeAction(Class clase, Integer accion, String tabla,
            Integer idRegistroAfectado, String descripcion)
            throws Exception {
        LogBean bean = new LogBean();
        bean.setTipOpeLog(accion);
//		if (idRegistroAfectado != null)
//			bean.set(idRegistroAfectado.toString());
        bean.setTabAfectada(tabla);
        UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        bean.setUsuario(user.getUsuario());
        bean.setFecLog(new java.sql.Timestamp(new java.util.Date().getTime()));
        bean.setObsLog(descripcion);
        write(GLTLog.CATEGORIA_INFO, clase, descripcion); // Para pantalla o archivo
        BeanFactory.getLogService().insertarLog(bean); // Para BD
    }

    public static void writeActionString(Class clase, Integer accion, String tabla,
            String idRegistroAfectado, String descripcion)
            throws Exception {
        LogBean bean = new LogBean();
        bean.setTipOpeLog(accion);
//		if (idRegistroAfectado != null)
//			bean.set(idRegistroAfectado.toString());
        bean.setTabAfectada(tabla);
        UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        bean.setUsuario(user.getUsuario());
        bean.setFecLog(new java.sql.Timestamp(new java.util.Date().getTime()));
        bean.setObsLog(descripcion);
        write(GLTLog.CATEGORIA_INFO, clase, descripcion); // Para pantalla o archivo
//        BeanFactory.getLogService().insertarLog(bean); // Para BD
    } 

    /**
     * Almacena el error capturado en la Base de Datos
     *
     * @param clase
     * @param err 
     */
    public static void writeError(Class clase, Exception err) {
        LogBean bean = new LogBean();
        UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        if (user != null) {
            bean.setUsuario(user.getUsuario());
        } 
        bean.setTipOpeLog(ACCION_ERROR);
        bean.setTabAfectada("---");
        bean.setFecLog(new java.sql.Timestamp(new java.util.Date().getTime()));
        StringWriter errors = new StringWriter();
        err.printStackTrace();
        String message = "";
        if (user != null) {
            message = "User: " + user.getUsuario() + " - " + errors.toString();
        } else {
            message = "User: SIN USUARIO - " + errors.toString();
        }
        if (message.length() > 4000) {
            message = message.substring(0, 3999);
        }
        bean.setObsLog(message);
        try {
            write(GLTLog.CATEGORIA_ERROR, clase, message, err);  //Para la pantalla o archivo
            BeanFactory.getLogService().insertarLog(bean);  // Para la BD
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
