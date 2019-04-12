/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Administrador
 */
public class InitSystemResources extends HttpServlet implements Servlet{
    private static Logger log = Logger.getLogger(InitSystemResources.class);
    @Override
    public void init() throws ServletException {
        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

            SpringWebApplicationContext.getInstance().setWebApplicationContext(wac);

            //GCPLog.write(GCPLog.CATEGORIA_INFO, this.getClass(), "Iniciando repositorio Sislahde!");

            // Propiedades.inicializaConObjeto(SpringWebApplicationContext.getInstance().getBean("propiedades"));
            // PropertyConfigurator.configure(GeneralUtil.getPropertyFile(GeneralUtil.getProperty("LogConfigFile")));
            //Log.write(Log.CategoriaINFO, Log.MODUTIL, null,
            // "*** sistema levantado");
            log.info("*** sistema SIGMA levantado *** (debug activado?=" + log.isDebugEnabled() + ")");

        } catch (Exception ex) {
            log.info("*** sistema SIGMA CON ERROR AL CARGARSE !! ***");
        }
        super.init();
    }
}
