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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
public class KomunikatorKLIENT {

    JDialog okno;
    JTextArea obszar = new JTextArea(20, 54);   //CHAT
    JScrollPane scrollObszaru = new JScrollPane(obszar, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JTextField pole = new JTextField("", 45);   //WIADOMOŚĆ
    JButton przycisk = new JButton("Wyślij");
    SpringLayout layout = new SpringLayout();

    Socket gniazdo = null;
    PrintWriter wy = null;
    InetAddress adres = null;
    ServerSocket serwer = null;
    BufferedReader we = null;
    MojListener listener = new MojListener();

    public KomunikatorKLIENT(Frame owner) {
        okno = new JDialog(owner, "Komunikator KLIENT");
        okno.setPreferredSize(new Dimension(629, 565));
        okno.setSize(new Dimension(629, 480));
        okno.setLayout(layout);
        okno.setTitle("Komunikator - KLIENT");

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
        przycisk.addActionListener(listener);

        okno.setResizable(false);
        okno.setVisible(true);
        okno.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    przycisk.removeActionListener(listener);
                    gniazdo.close();
                    obszar.setText("");
                    okno.dispose();
                } catch (IOException blad) {
                    System.out.println(blad);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }

    public void wyslij(Socket gniazdo) {

        try {
            wy = new PrintWriter(new OutputStreamWriter(gniazdo.getOutputStream()));
            wy.write(pole.getText() + "\r\n");
            wy.write(pole.getText() + "\r\n");
            wy.flush();
            obszar.append("WYSYŁANO: " + pole.getText() + "\n");
        } catch (IOException ex) {
            obszar.append("błąd TWORZENIA BUFORU WYSYŁANIA " + ex + "\n");
        }

    }

    public void odbierz() {

        try {
            adres = InetAddress.getByName("localhost");
            System.out.println("KLIENT - POŁĄCZONO Z - " + adres);
            try {
                gniazdo = new Socket(adres, 8080);
                obszar.append("\tPOŁĄCZONO Z SUPPORTEM\n\n");
                System.out.println("KLIENT - POŁĄCZONO Z - " + gniazdo);
                
                try {
                    we = new BufferedReader(new InputStreamReader(gniazdo.getInputStream()));
                    while (we.readLine() != null) {
                        obszar.append("SERWIS: " + we.readLine() + "\n");
                    }
                } catch (IOException ex) {
                    System.out.println("KLIENT BŁĄD TWORZENIA BUFORA - " + ex);
                }
            } catch (IOException ex) {
                obszar.append("BŁĄD ŁĄCZENIA Z SUPPORTEM\n");
                System.out.println("KLIENT BŁĄD ŁĄCZENIA Z SOCKETM - " + ex);
            }
        } catch (UnknownHostException ex) {
            obszar.append("BŁĄD ŁĄCZENIA Z SERWEREM SUPPORTU\n");
            System.out.println("KLIENT - " + ex);
        }

    }
    

    private class MojListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            wyslij(gniazdo);
            pole.setText("");
        }

    }

}
