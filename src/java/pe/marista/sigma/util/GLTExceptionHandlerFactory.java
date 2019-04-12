/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author Administrador
 */
public class GLTExceptionHandlerFactory extends ExceptionHandlerFactory{
    private ExceptionHandlerFactory parent;
    // this injection handles jsf
   public GLTExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
   }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler handler = new 
                GLTExceptionHandler(parent.getExceptionHandler());
        return handler;
    }
}
