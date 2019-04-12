/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;

/**
 *
 * @author MS002
 */
public class Impresora {

    Font fuente = new Font("Dialog", Font.PLAIN, 10);
    PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "SCAT", null);;
    Graphics pagina;

//    Impresora() {
//        pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "SCAT", null);
//    }

    public void imprimir(String Cadena) {
        try {
            pagina = pj.getGraphics();
            pagina.setFont(fuente);
            pagina.setColor(Color.black);
            pagina.drawString(Cadena, 60, 60);
            pagina.dispose();
            pj.end();
        } catch (Exception e) {
            System.out.println(">>>" + e.getMessage());
        }
    }

    
    
}
