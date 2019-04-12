/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import pe.marista.sigma.bean.UsuarioBean;

/**
 *
 * @author Administrador
 */
public class MaristaUtils {

    public void sesionColocarObjeto(String nombre, Object objeto) {
        ((HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest()).getSession().
                setAttribute(nombre, objeto);
    }

    public Object sesionObtenerObjeto(String nombre) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest()).getSession().
                getAttribute(nombre);
    }

    public Object requestObtenerObjeto(String nombre) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest()).getParameter(nombre);
    }

    public void requestColocarObjeto(String nombre, Object objeto) {
        ((HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest()).
                setAttribute(nombre, objeto);

    }

    /**
     * Permite verificar si un usuario se encuentra en sesión. Si no está, se
     * redirecciona al Login
     *
     * @return - Pantalla de Login
     */
    public String validaUsuarioSesion() {
        String pagina = null;
        UsuarioBean beanUsuarioSesion = (UsuarioBean) sesionObtenerObjeto("usuarioLogin");
        if (beanUsuarioSesion == null || beanUsuarioSesion.getUsuario() == null
                || beanUsuarioSesion.getUsuario().equals("")) {
            pagina = "/index";
        }
//        System.out.println("ruta1: " + pagina);
        return pagina;
    }

    public static String obtenerRealPath() {
        String archivo = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                getRequest()).getServletContext().getRealPath("");
        return archivo;
    }
}
