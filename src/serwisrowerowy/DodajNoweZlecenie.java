/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author PanterA
 */
public class DodajNoweZlecenie {

    JDialog dodawanie;
    
    JPanel dane = new JPanel();
    JPanel sterowanie = new JPanel(new FlowLayout());

    JLabel labelTelefon = new JLabel("Numer telefonu");
    JLabel labelRower = new JLabel("Model roweru");
    JLabel labelDoZrobienia = new JLabel("Do zrobienia");
    JLabel labelCena = new JLabel("Przewidywana cena");

    JTextField tekstTelefon = new JTextField(30);
    JTextField tekstRower = new JTextField(30);
    JTextField tekstDoZrobienia = new JTextField(30);
    JTextField tekstCena = new JTextField(30);

    JButton potwierdz = new JButton("OK");
    JButton anuluj = new JButton("Anuluj");
    

    public DodajNoweZlecenie(Frame owner) {

        dodawanie = new JDialog(owner, "Formularz dodawania zlecenia");

        dane.setLayout(new SpringLayout());

        dane.add(labelTelefon);
        labelTelefon.setLabelFor(tekstTelefon);
        dane.add(tekstTelefon);

        dane.add(labelRower);
        labelTelefon.setLabelFor(tekstRower);
        dane.add(tekstRower);

        dane.add(labelDoZrobienia);
        labelTelefon.setLabelFor(tekstDoZrobienia);
        dane.add(tekstDoZrobienia);

        dane.add(labelCena);
        labelTelefon.setLabelFor(tekstCena);
        dane.add(tekstCena);

        SpringUtilities.makeCompactGrid(dane,
                4, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);//xPad, yPad

        sterowanie.setLayout(new FlowLayout(FlowLayout.CENTER));
        sterowanie.setBackground(Color.LIGHT_GRAY);
        sterowanie.add(potwierdz);
        potwierdz.addActionListener((e) -> {
                    dodawanie.dispose();
        });
        sterowanie.add(anuluj);
        anuluj.addActionListener((e) -> {
            dodawanie.dispose();
        });

        dodawanie.add(dane, BorderLayout.CENTER);
        dodawanie.add(sterowanie, BorderLayout.SOUTH);

        //ZMIENIŁEM Z JFrame na JDialog \|/
        dodawanie.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dodawanie.pack();
        dodawanie.setResizable(false);
        dodawanie.setVisible(true);

    }

    public JButton getPotwierdz() {
        return potwierdz;
    }

    public String getTekstTelefon() {
        return tekstTelefon.getText();
    }

    public String getTekstRower() {
        return tekstRower.getText();
    }

    public String getTekstDoZrobienia() {
        return tekstDoZrobienia.getText();
    }

    public String getTekstCena() {
        return tekstCena.getText();
    }
    
    
}
