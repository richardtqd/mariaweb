
package pe.marista.sigma.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.managedBean.SolicitudLogisticoMB;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

@FacesConverter("UsuarioConverter")
public class UsuarioConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string)
    {
        if(string != null && string.trim().length() > 0)
        {
            try 
            {
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                SolicitudLogisticoMB solicitudLogisticoMB = (SolicitudLogisticoMB) fc.getViewRoot().getViewMap().get("solicitudLogisticoMB");
                //return solicitudLogisticoMB.getListaTipoSolicitudBean().get(Integer.parseInt(string));
                //return solicitudLogisticoMB.getListaPersonalBean().get(Integer.parseInt(string));
                
                String usuario = string;
                UsuarioBean usuarioBean = usuarioService.buscarPorId(usuario);
                
                solicitudLogisticoMB.getSolicitudLogisticoBean().setUsuarioBean(usuarioBean);
                return solicitudLogisticoMB.getSolicitudLogisticoBean();
                //return usuarioBean;
                
            } catch (Exception ex) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ex);
            }
        } else 
        { 
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
         if(o != null)
         {
            return String.valueOf(((UsuarioBean) o).getUsuario());
         }
        else {
            return null;
        }
    }
    
}
