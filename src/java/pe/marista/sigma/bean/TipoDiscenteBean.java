/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class TipoDiscenteBean implements Serializable{
    
    private Boolean flgAlu = Boolean.FALSE;
    private Boolean flgExa;
    private Boolean flgHem;
    private Boolean flgHex;
    private Boolean flgExt;
    
    

//    public boolean isFlgAlu() {
//        return flgAlu;
//    }
//
//    public void setFlgAlu(boolean flgAlu) {
//        this.flgAlu = flgAlu;
//    }
//
//    public boolean isFlgExa() {
//        return flgExa;
//    }
//
//    public void setFlgExa(boolean flgExa) {
//        this.flgExa = flgExa;
//    }
//
//    public boolean isFlgHem() {
//        return flgHem;
//    }
//
//    public void setFlgHem(boolean flgHem) {
//        this.flgHem = flgHem;
//    }
//
//    public boolean isFlgHex() {
//        return flgHex;
//    }
//
//    public void setFlgHex(boolean flgHex) {
//        this.flgHex = flgHex;
//    }
//
//    public boolean isFlgExt() {
//        return flgExt;
//    }
//
//    public void setFlgExt(boolean flgExt) {
//        this.flgExt = flgExt;
//    }

    public Boolean getFlgAlu() {
        return flgAlu;
    }

    public void setFlgAlu(Boolean flgAlu) {
        this.flgAlu = flgAlu;
    }

    public Boolean getFlgExa() {
        return flgExa;
    }

    public void setFlgExa(Boolean flgExa) {
        this.flgExa = flgExa;
    }

    public Boolean getFlgHem() {
        return flgHem;
    }

    public void setFlgHem(Boolean flgHem) {
        this.flgHem = flgHem;
    }

    public Boolean getFlgHex() {
        return flgHex;
    }

    public void setFlgHex(Boolean flgHex) {
        this.flgHex = flgHex;
    }

    public Boolean getFlgExt() {
        return flgExt;
    }

    public void setFlgExt(Boolean flgExt) {
        this.flgExt = flgExt;
    }
    
}
