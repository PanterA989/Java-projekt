/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

import javax.swing.JCheckBox;

/**
 *
 * @author PanterA
 */
public class czynnoscCheckBox extends JCheckBox{

    int cenaMin, cenaMax;
    
    public czynnoscCheckBox(String tytul, int cena) {
        super();
        String nazwa = tytul + "     " + Integer.toString(cena);
        this.setText(nazwa);
        this.cenaMin = cena;
        this.cenaMax = cena;
    }
    
    public czynnoscCheckBox(String tytul, int cenaMin, int cenaMax) {
        super();
        String nazwa = tytul + "     " + Integer.toString(cenaMin) + "-" + Integer.toString(cenaMax);
        this.setText(nazwa);
        this.cenaMin = cenaMin;
        this.cenaMax = cenaMax;
    }


    public int getCenaMin() {
        return cenaMin;
    }

    public int getCenaMax() {
        return cenaMax;
    }
    
    
    
}
