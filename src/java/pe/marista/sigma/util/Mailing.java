package pe.marista.sigma.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetEsquelaBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.DetEsquelaService;
import pe.marista.sigma.service.ProcesoEnvioService;

public class Mailing {
  
//     public static String username = "ramirezbarinagamanuel@gmail.com";//falta crear un correo gmail
//    public static String password = "meylinsuyin";//password del correo por crear
 
    public static String username = "sigma.sanjose@maristas.edu.pe";//falta crear un correo gmail
    public static String password = "Godzilla2008";//password del correo por crear
  
    //nuevos correos
    //a) Callao sigma.sanjose@maristas.edu.pe
    //b) Barranco sigma.sanluis@maristas.edu.pe
    //c) Surco sigma.champaganat@maristas.edu.pe
    //d) San Juan sigma.barinaga@maristas.edu.pe
    //
    //el password es montane2015
    public static String username_CHAMPS = "sigma.champaganat@maristas.edu.pe";
    public static String password_new = "montane2015"
            + "";//password del correo por crear

    String mensaje = "";
    String to = "julio.teruya@gmail.com";//destino
    String subject = "Prueba de JavaMail";

    public void enviarCorreoCita(AdmisionBean admisionBean, EstudianteBean estudianteBean, EntidadBean entidadBean, String nom) {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm");
        to = estudianteBean.getPersonaBean().getCorreo();
        subject = "Confirmaci�n de Inscripci�n de Postulante";
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body>").
                append("<left><h2>Proceso de Admisi�n " + admisionBean.getAnio() + " del colegio " + usuarioBean.getPersonalBean().getUnidadNegocioBean().getNombreUniNeg()+ "</h2></left>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Se confirma la inscripci�n del postulante <span><b>" + estudianteBean.getPersonaBean().getNombreCompletoDesdeApellidos() + "</b></span> al <span style='text-decoration:underline;' ><b>" + estudianteBean.getPersonaBean().getGradoAcademicoBean().getNombre() + "</b></span>,<br/>"
                        + "</p>").
                append("<br/>").
                append("<p>Datos del Padre/Apoderado: <span><b>" + estudianteBean.getRespPagoBean().getNombreCompletoDesdeApellidos() + "</b></span></p>").
//                append("<p>Fecha y Hora Programada para la entrevista: " + date.format(admisionBean.getFecExamen()) + " y " + date.format(admisionBean.getHoraExamen().getTime()) + " </p>").
                append("<br/>").
//                append("<h3><B>Indicaciones para seguir el proceso de Admisi�n: </B></h3>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Sr. Padre de familia o apoderado, se comunica que tiene una cita el D�a:"+ date.format(admisionBean.getFecExamen()) + " hora: " + hora.format(admisionBean.getHoraExamen().getTime()) +"<br/> "
                        + "para lo cual agradeceremos seguir la siguiente indicaci�n:   \n"
                        + "</p>"). 
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Asistir 15 minutos antes de la hora programada..</li>\n"
                        + "</ul>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        +"Gracias por su preferencia con nuestra instituci�n..</li>\n"
                        + "</ul>").
                append("<p>\n"
                        + "Oficina de Admisi�n: Imagen Institucional<br/>\n"
                        + "Contacto: " + entidadBean.getContacto() + " <br/>\n"
                        + "Correo de Admisi�n: " + entidadBean.getCorreo() + "<br/>\n"
                        + "Tel�fono de Admisi�n: " + entidadBean.getTelefono() + "<br/>\n"
                        + "</p>").  
                append("</body>").
                append("</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoAuto(AdmisionBean admisionBean, EstudianteBean estudianteBean, EntidadBean entidadBean, String nom) {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        to = estudianteBean.getPersonaBean().getCorreo();
        subject = "Confirmaci�n de Inscripci�n de Postulante";
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body>").
                append("<left><h2>Proceso de Admisi�n " + "2015" + " del colegio " + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "</h2></left>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Se confirma la inscripci�n del postulante <span><b>" + estudianteBean.getPersonaBean().getNombreCompletoDesdeApellidos() + "</b></span> al <span style='text-decoration:underline;' ><b>" + estudianteBean.getPersonaBean().getGradoAcademicoBean().getNombre() + "</b></span>,<br/>"
                        + "</p>").
                append("<br/>").
                append("<p>Datos del Padre/Apoderado: <span><b>" + estudianteBean.getRespPagoBean().getNombreCompleto() + "</b></span></p>").
                append("<p>Fecha y Hora Programada para la entrevista: " + date.format(admisionBean.getFecExamen()) + " y " + admisionBean.getHoraExamen() + " </p>").
                append("<br/>").
                append("<h3><B>Indicaciones para seguir el proceso de Admisi�n: </B></h3>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Sr. Padre de familia o apoderado, se comunica que las matr�culas se realizar�n del " + date.format(admisionBean.getProgramacionBean().getFecIni()) + " al " + date.format(admisionBean.getProgramacionBean().getFecFin()) + ",<br/> "
                        + "para lo cual deber� seguir los siguientes pasos:   \n"
                        + "</p>").
                append("<ul><b>Indicaciones para la Entrevista: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Asistir 15 minutos antes de la hora programada..</li>\n"
                        + "</ul>").
                append("<p>\n"
                        + "Oficina de Admisi�n: Imagen Institucional<br/>\n"
                        + "Contacto: " + entidadBean.getContacto() + " <br/>\n"
                        + "Correo de Admisi�n: " + entidadBean.getCorreo() + "<br/>\n"
                        + "Tel�fono de Admisi�n: " + entidadBean.getTelefono() + "<br/>\n"
                        + "</p>").
                append("</body>").
                append("</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoEstudiante(EstudianteBean estudianteBean, EntidadBean entidadBean) {
        to = estudianteBean.getPersonaBean().getCorreo();
        subject = "Bienvenido al colegio " + estudianteBean.getUnidadNegocioBean().getNombreUniNeg();
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body> ").
                append("<h1><b>Bienvenido al colegio ").append(estudianteBean.getUnidadNegocioBean().getNombreUniNeg()).append("</b></h1>").
                append("<left><p><b>Hemos registrado la siguiente informaci�n: </b><p></left>").
                append("<ul>"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">Prospecto: " + estudianteBean.getPersonaBean().getNombreCompletoDesdeApellidos() + "</li>"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">Grado Acad�mico: " + estudianteBean.getPersonaBean().getGradoAcademicoBean().getNombre() + "</li>"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">A�o: " + "2015" + " </li>"
                        + "<ul>").
                append("<h1><B>Indicaciones para seguir el proceso de Admisi�n: </B></h1>").
                append("<p style=\"text-align:justify;\">\n"
                        + "Sr. Padre de familia o apoderado, para participar en el proceso de Admisi�n -  " + "2015" + ".<br/>\n"
                        + "Ud. deber� completar los siguientes pasos:</p>").
                append("<ul><span><b>Pre-Inscripci�n: </b></span>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Pagar derecho de inscripci�n</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Recoger carpeta o prospecto</li>\n"
                        + "</ul>").
                append("<ul><b>Inscripci�n: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Realizar Inscripci�n del postulante en la oficina de Admisi�n presentando <br/>los documentos que se indican en el prospecto o la carpeta del postulante.</li>\n"
                        + "</ul>").
                append("<ul><b>Entrevista y evaluaci�n de documentos: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Asistir a la entrevista programada en fecha y hora.</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "El colegio evaluar� documentos presentados.</li>\n"
                        + "</ul>").
                append("<ul><b>Resultados: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "El colegio comunicar� el resultado al correo y/o tel�fono proporcionado..</li>\n"
                        + "</ul>").
                append("<p>\n"
                        + "Oficina de Admisi�n: Imagen Institucional<br/>\n"
                        + "Contacto: " + entidadBean.getContacto() + " <br/>\n"
                        + "Correo de Admisi�n: " + entidadBean.getCorreo() + "<br/>\n"
                        + "Tel�fono de Admisi�n: " + entidadBean.getTelefono() + "<br/>\n"
                        + "</p>").
                append("</body>\n"
                        + "</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoAdmision(AdmisionBean admisionBean, EstudianteBean estudianteBean, EntidadBean entidadBean, String nom) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        to = estudianteBean.getPersonaBean().getCorreo();
        subject = "Postulante Admitido";
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body>").
                append("<left><h2>Proceso de Admisi�n " + admisionBean.getAnio().toString() + " del colegio " + admisionBean.getUnidadNegocioBean().getNombreUniNeg() + "</h2></left>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Se comunica que el postulante <span><b>" + estudianteBean.getPersonaBean().getNombreCompletoDesdeApellidos() + "</b></span> ha sido admitido al <span style='text-decoration:underline;' ><b>" + estudianteBean.getPersonaBean().getGradoAcademicoBean().getNombre() + "</b></span>,<br/>"
                        + "ha sido admitido por el Colegio " + admisionBean.getUnidadNegocioBean().getNombreUniNeg() + ".    \n"
                        + "</p>").
                append("<br/>").
                append("<h3><B>Indicaciones para seguir el proceso de Admisi�n: </B></h3>").
                append("<p style=\"text-align:justify;font-size:15px;\">\n"
                        + "Sr. Padre de familia o apoderado, se comunica que las matr�culas se realizar�n del " + date.format(admisionBean.getProgramacionBean().getFecIni()) + " al " + date.format(admisionBean.getProgramacionBean().getFecFin()) + ",<br/> "
                        + "para lo cual deber� seguir los siguientes pasos:   \n"
                        + "</p>").
                append("<ul><b>Pre-Matr�cula: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Pagar Derecho de Admisi�n en Caja del Colegio (DONACION).</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Pagar Matr�cula (en el Banco " + nom + ").</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Pagar Cuota APAFA.</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Completar datos de Nuevo Estudiante (v�a web).</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Imprimir y firmar Ficha del Estudiante.</li>\n"
                        + "</ul>").
                append("<ul><b>Matr�cula: </b>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Presentar documentos que se indican en la web o la gu�a de matr�cula.</li>\n"
                        + "<li style=\"padding-left:20px;list-style-type:none;\">\n"
                        + "Registrar matr�cula en la Oficina de Matr�culas del Colegio.</li>\n"
                        + "</ul>").
                append("<p>\n"
                        + "Oficina de Admisi�n: Imagen Institucional<br/>\n"
                        + "Contacto: " + entidadBean.getContacto() + " <br/>\n"
                        + "Correo de Admisi�n: " + entidadBean.getCorreo() + "<br/>\n"
                        + "Tel�fono de Admisi�n: " + entidadBean.getTelefono() + "<br/>\n"
                        + "</p>").
                append("</body>").
                append("</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoConfirmacion(EstudianteBean estudianteBean) {
        to = estudianteBean.getPersonaBean().getCorreo();
        subject = "Bienvenido al colegio " + estudianteBean.getUnidadNegocioBean().getNombreUniNeg();
        StringBuilder msj = new StringBuilder();
        msj.append("<p>Sr. Padre de familia o apoderado, para participar en el proceso de Admisión</p>").
                append("<p>Ud. deberá completar los siguientes pasos:<p><br/><br/>").
                append("<p>Pre - Inscripción:</p>").
                append("<p>- Pagar derecho de inscripción</p>").
                append("<p>- Recoger carpeta o prospecto (que incluye usuario y clave para registro vía web</p>").
                append("<p>- Completar datos de la ficha del postulante vìa web</p>").
                append("<p>- Imprimir y firmar ficha del postulante </p>").
                append("<p> </p>").
                append("<p>Inscripción</p>").
                append("<p>- Realizar Inscripción del postulante en la oficina de Admisión presentando</p>").
                append("<p> los documentos que se indican en el prospecto o la carpeta del postulante.</p>").
                append("<p> </p>").
                append("<p>Entrevista y evaluación de documentos:</p>").
                append("<p>- Asistir a la entrevista programada en fecha y hora.</p>").
                append("<p>- El colegio evaluará documentos presentados.</p>").
                append("<p> </p>").
                append("<p>Resultados</p>").
                append("<p>- El colegio comunicará el resultado al correo y/o teléfono proporcionado.</p>").
                append("<p>Oficina de Admisión</p>").
                append("<p>Correo de Admisión</p>").
                append("<p>Teléfono de Admisión: 247-5436</p>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoCumple(PersonalBean personalBean) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        to = personalBean.getCorreoCor().toString();
        subject = "Feliz Cumplea�os" + personalBean.getNombreCompleto().toString();
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body>").
                append("<h1>Feliz Cumplea�os " + personalBean.getNombreCompleto().toString() + "</h1>").
                append("<p style='text-align:justify;padding:5px;' >Hoy d�a nos acordamos de ti, esperamos que sea un d�a maravilloso. <br/>"
                        + "<span style='text-align:rigth;'>Ten un Feliz cumplea�os tus amigos del </span><span style='color:red;' >Sistema SIGMA</span></p>").
                append("</body>").
                append("</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoMensaje(SolicitudCajaCHBean solicitudCajaCHBean) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        to = solicitudCajaCHBean.getIdAutorizaPer1Bean().getCorreoCor().toString();
        subject = "SOLICITUD PARA APROBAR: " + solicitudCajaCHBean.getTipoSolicitudBean().getNombre();
        StringBuilder msj = new StringBuilder();
        msj.append("<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "	<meta charset=\"utf-8\">\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "	<title></title>\n"
                + "	<link rel=\"stylesheet\" href=\"\">\n"
                + "</head>\n"
                + "<body>").
                append("<h1>" + solicitudCajaCHBean.getTipoSolicitudBean().getNombre() + "</h1>").
                append("</body>").
                append("</html>");
        mensaje = msj.toString();
        sendMail();
    }

    public void enviarCorreoMasivo(String email, DetEsquelaBean detEsquelaBean, EsquelaBean esquelaBean, List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) throws Exception {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        Integer b = 0;
        Integer a = 0;
        List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
        ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
        listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentaEstudiante(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), detEsquelaBean.getEstudianteBean().getIdEstudiante());
        to = email;
        subject = esquelaBean.getTitulo();
        StringBuilder msj = new StringBuilder();
        msj.append(esquelaBean.getMensaje());
        DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
        b = detEsquelaService.obtenerMaxId(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        detEsquelaBean.setMensaje(msj.toString());
        detEsquelaBean.setIdDetEsquela(b);
        detEsquelaService.modificarDetMensaje(detEsquelaBean);
        System.out.println(b);
        mensaje = msj.toString();
        sendMail();
    }

    //Primero a probar
    public void enviarMensajeAutoriza(String email, SolicitudLogisticoBean solicitudLogisticoBean) {
        to = email;
        subject = "SOLICITUD LOGISTICA";
        mensaje = "<b><h1>M�dulo Log�stico del " + solicitudLogisticoBean.getUnidadNegocioBean().getNombreUniNeg() + "</h1></b><br/>\n"
                + "Buenos d�as.</b><br/>\n"
                + "Se tiene la siguiente solicitud del �rea de Log�stica por AUTORIZAR.</b><br/>\n"
                + "Solicitud N�: " + solicitudLogisticoBean.getNroSolicitud() + "</b><br/>\n"
                + "Descripci�n:  " + solicitudLogisticoBean.getTitulo() + "</b><br/>\n"
                + "Importe:    " + solicitudLogisticoBean.getImportePropuesto() + "</b><br/>\n"
                + "Nota: Porfavor ingresar al sistema SIGMA</b></b>";
        sendMail();
    }

    //Mensaje que ya a sido aprobado a logistica
    public void enviarMensajeAutorizado(String email, SolicitudLogisticoBean solicitudLogisticoBean) {
        to = email;
        subject = "SOLICITUDES APROBADAS";
        mensaje = "<b><h1>M�dulo Log�stico del " + solicitudLogisticoBean.getUnidadNegocioBean().getNombreUniNeg() + "</h1></b><br/>\n"
                + "Buenos d�as.</b><br/>\n"
                + "Se aprob� la siguiente solicitud:</b><br/>\n"
                + "Solicitud N�: " + solicitudLogisticoBean.getNroSolicitud() + "</b><br/>\n"
                + "Descripci�n:  " + solicitudLogisticoBean.getTitulo() + "</b><br/>\n"
                + "Importe:    " + solicitudLogisticoBean.getImportePropuesto() + "</b><br/>\n"
                + "Nota: Porfavor ingresar al sistema SIGMA para realizar la Orden de Compra</b></b>";
        if (MaristaConstantes.UNI_NEG_CHAMPS.equals(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg())) {
            sendMailUniNeg(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
        } else {
            sendMail();
        }
    }

    //Mensaje que ya a sido aprobado a logistica
    public void enviarMensajeDesAutorizado(String email, SolicitudLogisticoBean solicitudLogisticoBean) {
        to = email;
        subject = "SOLICITUDES NO APROBADAS";
        mensaje = "<b><h1>M�dulo Log�stico del " + solicitudLogisticoBean.getUnidadNegocioBean().getNombreUniNeg() + "</h1></b><br/>\n"
                + "Buenos d�as.</b><br/>\n"
                + "No se aprob� la siguiente solicitud:</b><br/>\n"
                + "Solicitud N�: " + solicitudLogisticoBean.getNroSolicitud() + "</b><br/>\n"
                + "Descripci�n:  " + solicitudLogisticoBean.getTitulo() + "</b><br/>\n"
                + "Importe:    " + solicitudLogisticoBean.getImportePropuesto() + "</b><br/>\n";
        if (MaristaConstantes.UNI_NEG_CHAMPS.equals(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg())) {
            sendMailUniNeg(solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
        } else {
            sendMail();
        }

    }

    //MENSAJE DE ENVIOS MASIVOS NEW
    public void envioMasivoObjeto(DetEsquelaBean detEsquelaBean) throws Exception {
        to = detEsquelaBean.getCorreo();
        subject = detEsquelaBean.getAsunto();
        String pie="";
        pie="<br/> <br/>\n"+
                "Alumno(a): " + detEsquelaBean.getEstudianteBean().getPersonaBean().getNombreCompletoDesdeApellidos() + "</b><br/>\n"+
                "C�digo: " + detEsquelaBean.getEstudianteBean().getCodigo() + "</b><br/>\n";
        mensaje = pie+detEsquelaBean.getMensaje();
        sendMail();
    }
    //MENSAJE DE ENVIOS MASIVOS NEW
    public void envioMasivoObjetoConf(DetEsquelaBean detEsquelaBean) throws Exception {
        to = detEsquelaBean.getCorreo();
        subject = detEsquelaBean.getAsunto(); 
        mensaje = detEsquelaBean.getMensaje();
        sendMail();
    }

    //    public void enviarCorreoRecuperacionPassword(String email)
    //    {
    //        to = email;
    //        subject = "Recuperación de contraseña en Proadalid.com";
    //        mensaje = "<p>Hola, recibimos una solicitud de recuperación de contraseña.</p>"
    //                +"<p>Para ingresar tu nueva contraseña ingresa al siguiente .</p>"
    //                +"<p>Si no ordenaste el reestablecimiento de contraseña de tu cuenta, ignora este correo.</p>"+"<br/><br/>"
    //                +"<p>Atentamente</p>"
    //                +"<p>El equipo de Proadalid</p>"
    //                +"<a href=\"http://localhost:8080/Siap\">http://localhost8080/Siap</a>";
    //        sendMail();
    //    }
    private void sendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
//         props.put("mail.smtp.host", "smtp.live.com");
//        props.put("mail.smtp.port", "25");
 
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress(Username));
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            //message.setText(Mensaje);
            message.setContent(mensaje, "text/html");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMailUniNeg(String uniNeg) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        System.out.println(uniNeg);
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress(Username));
            message.setFrom(new InternetAddress("robot@proadalid.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            //message.setText(Mensaje);
            message.setContent(mensaje, "text/html");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
