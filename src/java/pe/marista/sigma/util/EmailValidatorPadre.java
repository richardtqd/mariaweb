package pe.marista.sigma.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pe.marista.sigma.managedBean.EstudianteMB;

//@FacesValidator("pe.marista.sigma.util.EmailValidator")
@FacesValidator("emailValidatorPadre")
public class EmailValidatorPadre implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public EmailValidatorPadre() {   
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!value.toString().trim().equals("")) {
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage("Formato de E-mail del padre no válido.", "Formato de E-mail del padre no válido.");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                throw new ValidatorException(msg);
            } 
        }

    }
}
