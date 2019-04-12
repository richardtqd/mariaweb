/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
@FacesConverter("familiaConverter")
public class FamiliaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Object ret = null;
        try {
            if (uic instanceof PickList) {
                Object dualList = ((PickList) uic).getValue();
                DualListModel dl = (DualListModel) dualList;
                for (Object o : dl.getSource()) {
                    String id = "" + ((FamiliaBean) o).getIdFamilia();
                    if (string.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                if (ret == null) {
                    for (Object o : dl.getTarget()) {
                        String id = "" + ((FamiliaBean) o).getIdFamilia();
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
                return String.valueOf(((FamiliaBean) o).getIdFamilia());
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
