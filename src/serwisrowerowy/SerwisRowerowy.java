/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

/**
 *
 * @author PanterA
 */
public class SerwisRowerowy {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        GlowneOkno okno = GlowneOkno.getInstance();
        okno.startSerwera();
        okno.startKlienta();
    }
    
    
}
