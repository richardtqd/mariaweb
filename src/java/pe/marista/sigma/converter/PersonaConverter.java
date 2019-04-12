package pe.marista.sigma.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.PersonaDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

@FacesConverter("PersonaConverter")
public class PersonaConverter implements Converter {

    PersonaDAO personaDAO;

    public PersonaConverter() {
        personaDAO = BeanFactory.getPersonaDAO();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && string.trim().length() > 0) {
            try {
                String idPersona = string;
                UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonaBean persona = new PersonaBean();
                persona.setIdPersona(idPersona);
                persona.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());     
                PersonaBean personaBean = personaDAO.obtenerPersPorId(persona);
                return personaBean;
            } catch (Exception ex) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ex);
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return String.valueOf(((PersonaBean) o).getIdPersona());
        } else {
            return null;
        }
    }

}
