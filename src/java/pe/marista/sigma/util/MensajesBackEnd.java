/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class MensajesBackEnd {
    public static ResourceBundle bundle;
    public static String getValueOfKey(String key, List<String> params) 
    {
        String text;
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        bundle = ResourceBundle.getBundle(MaristaConstantes.ARCHIVO_MENSAJES_NOMBRE,locale);

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "" + key + "";
        }

        if (params != null && !params.isEmpty()) {
            MessageFormat mf = new MessageFormat(text, locale);
            text = mf.format(params.toArray(), new StringBuffer(), null).toString();
        }

        return text;

    } 
}
