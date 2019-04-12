/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrador
 */
public class MensajePrime {

    public void addErrorGeneralMessage() {
        FacesContext.getCurrentInstance().addMessage("errorGeneral",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorEtiqueta", null),
                        MensajesBackEnd.getValueOfKey("errorOperacion", null)));
    }

    public void addErrorMessage(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("errorGeneral",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorEtiqueta", null),
                        mensaje));
    }

    public void addInformativeMessage(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajesBackEnd.getValueOfKey("informacionEtiqueta", null),
                        mensaje));
    }

    public void addInformativeMessagePer(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajesBackEnd.getValueOfKey(mensaje, null),
                        mensaje));
    }

    public void addInformativeMessageSearch(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajesBackEnd.getValueOfKey("infBusquedaSinResEtiqueta", null),
                        mensaje));
    }

    public void addInformativeMessageFilterField(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajesBackEnd.getValueOfKey("infBusquedaFiltrarCampo", null),
                        mensaje));
    }

    public void addInformativeMessageSelectEst(String mensaje) {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajesBackEnd.getValueOfKey("infBusquedaErrorEst", null),
                        mensaje));
    }

    public void addDateMessage() {
        FacesContext.getCurrentInstance().addMessage("errorGeneral",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorEtiqueta", null),
                        MensajesBackEnd.getValueOfKey("errorFechas", null)));
    }

    /* MENSAJE DE NUMERO EXISTENTE */
    public void addMessageNumber() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorNroficha", null),
                        MensajesBackEnd.getValueOfKey("errorNroficha", null)));
    }

    /* MENSAJE DE NUMERO NO EXISTENTE */
    public void addMessageNumberNotExist() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorNrofichaExist", null),
                        MensajesBackEnd.getValueOfKey("errorNrofichaExist", null)));
    }

    /* MENSAJE PERSONA EXISTENTE */
    public void errorPersonExist() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("etiquetaAlertPersona", null),
                        MensajesBackEnd.getValueOfKey("etiquetaAlertPersona", null)));
    }

    /* MENSAJE ESTUDIANTE BLOQUEADO */
    public void errorEstudianteBloqueo() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("mensajeBloqueado", null),
                        MensajesBackEnd.getValueOfKey("mensajeBloqueado", null)));
    }

    /* MENSAJE DE MONTO DE DET_ACTIVIDAD ES MAYOR A LA ACTIVIDAD  */
    public void errorMontoDetActividad() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorDetAct", null),
                        MensajesBackEnd.getValueOfKey("errorDetAct", null)));
    }

    //CUENTA POR PRESUPUESTO Y CUENTA
    public void errorPresupuestoCuenta() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("errorPresCuenta", null),
                        MensajesBackEnd.getValueOfKey("errorPresCuenta", null)));
    }

    //SELECCIONAR TIPO DE SOLICITUD LOGISTICO
    public void selTipoSolicitud() {
        FacesContext.getCurrentInstance().addMessage("Informacion",
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MensajesBackEnd.getValueOfKey("selTipSolicitud", null),
                        MensajesBackEnd.getValueOfKey("selTipSolicitud", null)));
    }

}
