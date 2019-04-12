package pe.marista.sigma.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

@FacesConverter("programacionConvert")
public class ProgramacionConvert implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Object ret = null;
        try {
            if (uic instanceof PickList) {
                Object dualList = ((PickList) uic).getValue();
                DualListModel dl = (DualListModel) dualList;
                for (Object o : dl.getSource()) {
                    String id = "" + ((ProgramacionBean) o).getIdProgramacion();
                    if (string.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                if (ret == null) {
                    for (Object o : dl.getTarget()) {
                        String id = "" + ((ProgramacionBean) o).getIdProgramacion();
                        if (string.equals(id)) {
                            ret = o;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        try {
            if (o != null) {
                return String.valueOf(((ProgramacionBean) o).getIdProgramacion());
            } else {
                return null;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return null;
    }
}
