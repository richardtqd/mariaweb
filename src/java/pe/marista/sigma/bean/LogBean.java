/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import pe.marista.sigma.util.GLTLog;

/**
 *
 * @author Administrador
 */
public class LogBean implements Serializable {

    private Integer idItem = 0;
    private Timestamp fecLog;
    private UsuarioBean usuarioBean;
    private String tabAfectada;
    private Integer tipOpeLog = 0;
    private String obsLog;
    private String usuario;

   

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Timestamp getFecLog() {
        return fecLog;
    }

    public void setFecLog(Timestamp fecLog) {
        this.fecLog = fecLog;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public String getTabAfectada() {
        return tabAfectada;
    }

    public void setTabAfectada(String tabAfectada) {
        this.tabAfectada = tabAfectada;
    }

    public int getTipOpeLog() {
        return tipOpeLog;
    }

    public void setTipOpeLog(Integer tipOpeLog) {
        this.tipOpeLog = tipOpeLog;
    }

    public String getObsLog() {
        return obsLog;
    }

    public void setObsLog(String obsLog) {
        this.obsLog = obsLog;
    }

    public String getDescripcionOperacion() {
        if (tipOpeLog == GLTLog.ACCION_CONSULTAR) {
            return GLTLog.ESTADO_CONSULTA;
        } else if (tipOpeLog == GLTLog.ACCION_ELIMINAR) {
            return GLTLog.ESTADO_ELIMINACION;
        } else if (tipOpeLog == GLTLog.ACCION_ERROR) {
            return GLTLog.ESTADO_ERROR;
        } else if (tipOpeLog == GLTLog.ACCION_INSERTAR) {
            return GLTLog.ESTADO_INSERCION;
        } else if (tipOpeLog == GLTLog.ACCION_MODIFICAR) {
            return GLTLog.ESTADO_MODIFICACION;
        }
        return "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
