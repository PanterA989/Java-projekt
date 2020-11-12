/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author PanterA
 */
public class KomunikatorSERWIS implements ActionListener{

    JDialog okno;
    JTextArea obszar = new JTextArea(20, 54);   //CHAT
    JScrollPane scrollObszaru = new JScrollPane(obszar, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JTextField pole = new JTextField("", 45);   //WIADOMOŚĆ
    JButton przycisk = new JButton("Wyślij");
    SpringLayout layout = new SpringLayout();

    Socket gniazdo = null;
    ServerSocket serwer = null;
    BufferedReader we = null;
    PrintWriter wy = null;
    InetAddress adres = null;

    public KomunikatorSERWIS(Frame owner) {
        okno = new JDialog(owner, "Komunikator");
        okno.setPreferredSize(new Dimension(629, 565));
        okno.setSize(new Dimension(629, 480));
        okno.setLayout(layout);
        okno.setTitle("Komunikator - SERWIS");

        okno.add(scrollObszaru);
        layout.putConstraint(SpringLayout.NORTH, scrollObszaru, 5, SpringLayout.NORTH, okno);
        layout.putConstraint(SpringLayout.WEST, scrollObszaru, 5, SpringLayout.WEST, okno);
        scrollObszaru.setPreferredSize(new Dimension(605, 400));
        scrollObszaru.setSize(new Dimension(605, 400));

        okno.add(pole);
        layout.putConstraint(SpringLayout.NORTH, pole, 5, SpringLayout.SOUTH, scrollObszaru);
        layout.putConstraint(SpringLayout.WEST, pole, 5, SpringLayout.WEST, okno);
        pole.setPreferredSize(new Dimension(300, 27));
        pole.setSize(new Dimension(300, 27));

        okno.add(przycisk);
        layout.putConstraint(SpringLayout.NORTH, przycisk, 5, SpringLayout.SOUTH, scrollObszaru);
        layout.putConstraint(SpringLayout.EAST, przycisk, -20, SpringLayout.EAST, okno);
        przycisk.setPreferredSize(new Dimension(100, 27));
        przycisk.setSize(new Dimension(100, 27));
        przycisk.addActionListener(this);

        okno.setResizable(false);
        okno.setVisible(false);

    }

    public void odbierz() {

        try {
            try {
                serwer = new ServerSocket(8080);
                obszar.append("\n\tCZEKAM NA NOWEGO UŻYTOWNIKA\n\n");
                System.out.println("SERWIS - WŁĄCZONO SOCKET - " + serwer);
            } catch (IOException ex) {
                obszar.append("BŁĄD PODCZAS STARTOWANIA SERWERA\n");
                System.out.println("SERWER - BŁĄD TWORZENIA SOCKETU - " + ex);
            }

            try {
                gniazdo = serwer.accept();
                obszar.append("POŁĄCZYŁ SIĘ NOWY UŻYTKOWNIK\n\n");
                
                if(!okno.isVisible()){
                    GlowneOkno.getInstance().nowePolaczenie();
                }
                
                System.out.println("SERWIS - UTWORZONO POŁĄCZENIE - " + gniazdo);
            } catch (IOException ex) {
                obszar.append("BŁĄD PODCZAS ŁĄCZENIA UŻYTKOWNIKA\n");
                System.out.println("SERWER - BŁĄD ŁĄCZENIA SOCKET\n");
            }

            try {
                we = new BufferedReader(new InputStreamReader(gniazdo.getInputStream()));
                while (we.readLine() != null) {
                    obszar.append("KLIENT: " + we.readLine() + "\n");
                }
            } catch (IOException ex) {
                System.out.println("bład TWORZENIA BUFORA");
            }

        } finally {
            System.out.println("Zamknięcie SOCKETU " + gniazdo);
            try {
                gniazdo.close();
            } catch (IOException ex) {
                System.out.println("Błąd zamknięcia SOCKETU");
            }
            try {
                serwer.close();
                System.out.println("Wyłączenie serwera" + serwer);
            } catch (IOException ex) {
                System.out.println("błąd ZAKOŃCZENIA SERWER");
            }
        }

    }

    public void widoczne() {
        okno.setVisible(true);
    }

    public void ustawTekst(String tekst) {
        obszar.append(tekst);
    }

    public void wyslij() {

        try {

            wy = new PrintWriter(new OutputStreamWriter(gniazdo.getOutputStream()));
            wy.write(pole.getText() + "\r\n");
            wy.write(pole.getText() + "\r\n");
            wy.flush();
            obszar.append("WYSŁANO: " + pole.getText() + "\n");

        } catch (IOException ex) {
            obszar.append("błąd TWORZENIA BUFORU WYSYŁANIA " + ex + "\n");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            wyslij();
        } catch (NullPointerException ex) {
            obszar.append("BRAK POŁĄCZONEGO UŻYTKOWNIKA");
            System.out.println("SERWIS - BRAK UŻYTKOWNIKA - " + ex);
        }
        
        pole.setText("");
    }

}
