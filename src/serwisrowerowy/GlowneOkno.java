/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwisrowerowy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author PanterA
 */
public final class GlowneOkno extends JFrame implements Runnable {

    private static GlowneOkno INSTANCJA;
    
    
    //  PANEL PRAWY 
    JPanel panelPrawy = new JPanel();
    JButton dodajZlecenie = new JButton("Dodaj zlecenie");
    JButton usunZlecenie = new JButton("Usuń zlecenie");
    JButton cennik = new JButton("Cennik");
    JButton support = new JButton("Support");
    Color kolorSupport = support.getBackground();

    //  PANEL DOLNY
    JPanel panelDolny = new JPanel();

    //  PANEL LEWY
    String[] tytuly = {"Numer telefonu", "Rower", "Do zrobienia", "Cena"};
    DefaultTableModel tableModel = new DefaultTableModel(tytuly, 0);
    JTable tabela = new JTable(tableModel);

    KomunikatorSERWIS komunikator = new KomunikatorSERWIS(this);
    KomunikatorKLIENT klient;

    boolean stworzKlienta = false;

    Runnable komunikatorSerwer = () -> {
        while (true) {
            try {
                System.out.println("Komunikator SERWIS odbieranie");
                komunikator.odbierz();

            } finally {
                komunikator.ustawTekst("\n\tZAKOŃCZONO POŁĄCZENIE\n");
            }
        }
    };

    Runnable komunikatorKlient = () -> {
        while (true) {
            try {
                //bez czegokolwiek przed IFem nie chciało go wykonywać
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            if (stworzKlienta == true) {
                klient = new KomunikatorKLIENT(this);
                klient.odbierz();
                stworzKlienta = false;
            }
        }

    };

    Thread watekSERWER = new Thread(komunikatorSerwer);
    Thread watekKLIENT = new Thread(komunikatorKlient);

    private GlowneOkno(String nazwa) {
        super(nazwa);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024, 512));
        setMinimumSize(new Dimension(512, 256));

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    importZExcela();
                } catch (Exception blad) {
                    System.out.println(blad);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    exportDoExcela();
                } catch (Exception blad) {
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

//  PANEL 4     (środkowy)
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(500);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(64);

        add(new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

//  PANEL 1     (prawy)   
        GridLayout layoutGrid = new GridLayout(4, 1, 0, 5);
        panelPrawy.setLayout(layoutGrid);

        panelPrawy.add(dodajZlecenie);
        dodajZlecenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DodajNoweZlecenie zlecenie = new DodajNoweZlecenie(GlowneOkno.this);
                zlecenie.getPotwierdz().addActionListener((f) -> {
                    tableModel.addRow(new Object[]{zlecenie.getTekstTelefon(), zlecenie.getTekstRower(), zlecenie.getTekstDoZrobienia(), zlecenie.getTekstCena()});
                });

            }
        });
        panelPrawy.add(usunZlecenie);
        usunZlecenie.addActionListener((ActionEvent e) -> {
            try {
                int row = tabela.getSelectedRow();
                int n = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunac zlecenie " + tableModel.getValueAt(row, 1) + "?", "Potwierdzenie zakończenia zadania", JOptionPane.OK_CANCEL_OPTION);
                if (n == JOptionPane.OK_OPTION) {
                    tableModel.removeRow(row);

                }
            } catch (Exception blad) {
                System.out.println(blad);
            }

        });

        panelPrawy.add(cennik);
        cennik.addActionListener((e) -> {
            CennikPrac przyciskCennik = new CennikPrac(GlowneOkno.this);
        });

        panelPrawy.add(support);
        support.addActionListener((e) -> {
            support.setBackground(UIManager.getColor("Button.background"));
            komunikator.widoczne();
        });

        panelPrawy.setBackground(Color.LIGHT_GRAY);
        panelPrawy.setBorder(javax.swing.BorderFactory.createTitledBorder("Opcje"));
        add(panelPrawy, BorderLayout.EAST);

//  PANEL 3     (dolny)
        panelDolny.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDolny.setBackground(Color.GRAY);

        panelDolny.add(new JLabel("Autor: Krzysztof Kania"));

        add(panelDolny, BorderLayout.SOUTH);

//  MENU        (elementy)
        JMenuBar mojeMenu = new JMenuBar();

        JMenu opcja = new JMenu("Menu");

        mojeMenu.add(opcja);

        JMenuItem nowyKomunikator = new JMenuItem("Symulacja nowego klienta supportu");
        nowyKomunikator.addActionListener((e) -> {

            stworzKlienta = true;

        });
        opcja.add(nowyKomunikator);

//  FINALIZACJA OKNA
        setJMenuBar(mojeMenu);
        setVisible(true);

    }
    
    //SINGLETON
    public static GlowneOkno getInstance(){
        if(INSTANCJA == null){
            INSTANCJA = new GlowneOkno("Serwis \"Rowerem przez świat\"");
        }
        return INSTANCJA;
    }

    void startSerwera() {
        watekSERWER.start();
    }

    void startKlienta() {
        watekKLIENT.start();
    }
    
    void nowePolaczenie(){
        support.setBackground(Color.RED);
    }

    private void exportDoExcela() {
        // TODO add your handling code here:

        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;

        //Choose Location For Saving Excel File
        File plik = new File("tabela.xlsx");
        String path = plik.getAbsolutePath();

        try {
            //Import excel poi libraries to netbeans
            excelJTableExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
            //            Loop to get jtable columns and rows
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                XSSFRow excelRow = excelSheet.createRow(i);
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    XSSFCell excelCell = excelRow.createCell(j);
                    excelCell.setCellValue(tableModel.getValueAt(i, j).toString());

                }
            }   //Append xlsx file extensions to selected files. To create unique file names
            excelFOU = new FileOutputStream(path);
            excelBOU = new BufferedOutputStream(excelFOU);
            excelJTableExporter.write(excelBOU);
            //JOptionPane.showMessageDialog(null, "Zapisano pomyślnie!");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (excelBOU != null) {
                    excelBOU.close();
                }
                if (excelFOU != null) {
                    excelFOU.close();
                }
                if (excelJTableExporter != null) {
                    excelJTableExporter.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

    }

    private void importZExcela() {
        // TODO add your handling code here:

        File excelFile = new File("tabela.xlsx");
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;

        try {
            excelFIS = new FileInputStream(excelFile.getAbsolutePath());
            excelBIS = new BufferedInputStream(excelFIS);
            excelImportToJTable = new XSSFWorkbook(excelBIS);
            XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);

            for (int row = 0; row < excelSheet.getLastRowNum() + 1; row++) {
                XSSFRow excelRow = excelSheet.getRow(row);

                XSSFCell telefon = excelRow.getCell(0);
                XSSFCell rower = excelRow.getCell(1);
                XSSFCell zlecenie = excelRow.getCell(2);
                XSSFCell cena = excelRow.getCell(3);
                tableModel.addRow(new Object[]{telefon, rower, zlecenie, cena});
            }
            //JOptionPane.showMessageDialog(null, "Zaimportowano pomyślnie!");
        } catch (IOException iOException) {
            JOptionPane.showMessageDialog(null, iOException.getMessage());
        } finally {
            try {
                if (excelFIS != null) {
                    excelFIS.close();
                }
                if (excelBIS != null) {
                    excelBIS.close();
                }
                if (excelImportToJTable != null) {
                    excelImportToJTable.close();
                }
            } catch (IOException iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            }
        }

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
