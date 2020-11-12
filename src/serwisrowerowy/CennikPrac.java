/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author PanterA
 */
public class CennikPrac implements ItemListener {

    JDialog cennik;

    JPanel elementy = new JPanel(new GridLayout(0, 2));
    JPanel wyniki = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    Collection<czynnoscCheckBox> uslugi;
    
    JLabel widelki = new JLabel("Wycena: 0");
    JButton ok = new JButton("OK");

    int cenaMin = 0, cenaMax = 0;

    public CennikPrac(Frame owner) {

        cennik = new JDialog(owner, "Wycena");
        cennik.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        uslugi = new ArrayList<>();
        uslugi.add(new czynnoscCheckBox("Duży przegląd roweru", 100, 250));
        uslugi.add(new czynnoscCheckBox("Mały przegląd roweru", 50, 100));
        uslugi.add(new czynnoscCheckBox("Regulacja przerzutki", 10, 20));
        uslugi.add(new czynnoscCheckBox("Regulacja hamulca", 10, 30));
        uslugi.add(new czynnoscCheckBox("Centrowanie koła", 10, 30));
        uslugi.add(new czynnoscCheckBox("Wymiana ramy", 100, 200));
        uslugi.add(new czynnoscCheckBox("Wymiana widelca", 20, 50));
        uslugi.add(new czynnoscCheckBox("Wymiana łańcucha", 15, 25));
        uslugi.add(new czynnoscCheckBox("Wymiana przerzutki", 10, 30));
        uslugi.add(new czynnoscCheckBox("Wymiana obręczy koła", 25, 60));
        uslugi.add(new czynnoscCheckBox("Wymiana piasty koła", 25, 60));
        uslugi.add(new czynnoscCheckBox("Wymiana elementów suportu (korbowodu)", 20, 60));
        uslugi.add(new czynnoscCheckBox("Wymiana sterów", 20, 30));
        uslugi.add(new czynnoscCheckBox("Wymiana wolnobiegu", 10, 35));
        uslugi.add(new czynnoscCheckBox("Wymiana kasety", 10, 35));
        uslugi.add(new czynnoscCheckBox("Wymiana pedałów", 10, 30));
        uslugi.add(new czynnoscCheckBox("Wymiana tarczy napędowej z przodu", 20, 40));
        uslugi.add(new czynnoscCheckBox("Wymiana linki hamulca", 10, 15));
        uslugi.add(new czynnoscCheckBox("Wymiana linki przerzutki", 10, 15));
        uslugi.add(new czynnoscCheckBox("Wymiana kierownicy", 10, 25));
        uslugi.add(new czynnoscCheckBox("Wymiana mostu kierownicy", 10, 25));
        uslugi.add(new czynnoscCheckBox("Wymiana płynu w hamulcu hydraulicznym", 30));
        uslugi.add(new czynnoscCheckBox("Odpowietrzanie hamulca hydraulicznego", 25));
        uslugi.add(new czynnoscCheckBox("Naprawa torpeda", 20, 30));
        uslugi.add(new czynnoscCheckBox("Naprawa gwintu w korbie pod pedał", 30, 40));
        uslugi.add(new czynnoscCheckBox("Naprawa gwintu korby pod ściągacz", 25, 35));
        uslugi.add(new czynnoscCheckBox("Naprawa gwintu haka ramy", 20, 40));
        uslugi.add(new czynnoscCheckBox("Naprawa wolnobiegu", 15, 20));
        uslugi.add(new czynnoscCheckBox("Naprawa bębenka kasety", 20, 25));
        uslugi.add(new czynnoscCheckBox("Naprawa manetki", 15, 40));
        uslugi.add(new czynnoscCheckBox("Naprawa gwintu suportu", 30, 60));
        uslugi.add(new czynnoscCheckBox("Sprawdzanie geometrii ramy", 100, 150));
        uslugi.add(new czynnoscCheckBox("Prostowanie tarczy napędowej z przodu", 10, 30));
        uslugi.add(new czynnoscCheckBox("Pompowanie amortyzatora", 5, 10));
        uslugi.add(new czynnoscCheckBox("Mycie i smarowanie napędu", 15, 25));
        uslugi.add(new czynnoscCheckBox("Smarowanie wkładu suportu (korbowodu)", 15, 35));
        uslugi.add(new czynnoscCheckBox("Przygotowanie główki ramy pod montaż sterów (frezowanie)", 20, 35));
        uslugi.add(new czynnoscCheckBox("Przygotowanie mufy suportowej z przegwintowaniem pod montaż misek", 25, 40));
        uslugi.add(new czynnoscCheckBox("Demontaż zapieczonej rury wspornika kierownicy lub siodła", 50, 200));
        uslugi.add(new czynnoscCheckBox("Demontaż rączek", 5, 15));
        uslugi.add(new czynnoscCheckBox("Montaż roweru", 100));
        uslugi.add(new czynnoscCheckBox("Montaż bagażnika", 10, 25));
        uslugi.add(new czynnoscCheckBox("Montaż błotników", 15, 40));
        uslugi.add(new czynnoscCheckBox("Montaż oświetlenia", 10, 40));
        uslugi.add(new czynnoscCheckBox("Montaż licznika", 10, 30));
        uslugi.add(new czynnoscCheckBox("Wymiana opony, dętki", 10, 40));
        uslugi.add(new czynnoscCheckBox("Łatanie dętki", 5, 10));

        for (czynnoscCheckBox el : uslugi) {
            elementy.add(el);
            el.addItemListener(this);
        }

        cennik.add(new JScrollPane(elementy, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        
        
        wyniki.add(widelki);
        wyniki.add(ok);
        ok.addActionListener((e) -> {
            cennik.dispose();
        });
        
        cennik.setMinimumSize(new Dimension(512, 256));
        cennik.add(wyniki, BorderLayout.SOUTH);
        cennik.pack();
        cennik.setVisible(true);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        czynnoscCheckBox badany = (czynnoscCheckBox)e.getItem();
        if (badany.isSelected()) {
            cenaMin += badany.getCenaMin();
            cenaMax += badany.getCenaMax();
        } else {
            cenaMin -= badany.getCenaMin();
            cenaMax -= badany.getCenaMax();
        }
        if(cenaMin == cenaMax){
            widelki.setText("Wycena: " + cenaMin );
        } else {
            widelki.setText("Wycena: " + cenaMin + "-" + cenaMax);
        }
        
    }

}
