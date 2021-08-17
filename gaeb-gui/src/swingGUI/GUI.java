package swingGUI;

import model.Item;
import org.xml.sax.SAXException;
import parser.GAEBParser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Die Klasse "GUI" dient zum Erstellen der grafischen Benutzeroberflaeche mit
 * seinen Funktionen
 *
 * @author Sueleyman Demir - s0574402
 * @version 1.0
 * @since 18.07.2020
 */

public class GUI extends JFrame {
    /**
     * def Serial
     */
    private static final long serialVersionUID = 1L;

    private final String[] COLNAMES = {"Ordinalzahl", "Menge", "Einheit", "Kurztext"};
    private Container pane;
    private JMenuItem neu;
    private JMenuItem oeffnen;
    //private JMenuItem speichern;
    private JMenuItem beenden;
    private JTable table;
    private JFileChooser jfc;
    private DefaultTableModel tableModel;
    private final GAEBParser gaebData = new GAEBParser();

    /**
     * Der Konstruktor erstellt einen Frame mit seinen Komponenten, Tabellen und
     * ActionListener.
     *
     * @see #initFrame()
     * @see #initTable()
     * @see #initListener()
     */
    public GUI() {
        initFrame();
        initTable();
        initListener();

    }

    /**
     * Die Methode initialisiert die Attribute der Klasse GUI
     *
     * @see #GUI()
     */
    public void initFrame() {
        // GUI
        setTitle("\t\t\tAngewandte Programmierung - GAEB Analyzer");
        setSize(650, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("gaeb.png");
        setIconImage(img.getImage());

        pane = getContentPane();

        // Menu
        JMenuBar mb = new JMenuBar();
        JMenu datei = new JMenu("Datei");
        neu = new JMenuItem("Neu");
        oeffnen = new JMenuItem("Öffnen");
        beenden = new JMenuItem("Beenden");

        datei.add(neu);
        datei.add(oeffnen);
        datei.addSeparator();
        datei.add(beenden);
        mb.add(datei);
        setJMenuBar(mb);
    }

    /**
     * Die Methode initialisiert die Tabelle von JTable, womit spaeter weiter
     * gearbeitet wird, falls z.B. eine x83-Datei geoeffnet werden soll.
     *
     * @see #GUI()
     */
    public void initTable() {
        tableModel = new DefaultTableModel(COLNAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        pane.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Die Methode ist eine Bestätigungsabfrage, ob der Benutzer bspw. eine neue
     * Datei öffnen möchte oder nicht, aber auch ob der Benutzer das Programm
     * beenden möchte oder nicht.
     *
     * @param msg - Die Nachricht die im OptionPane auftaucht.
     * @return response - Nutzerauswahl
     */
    public int initOption(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Bestätigung", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Die Methode fuegt den MenuItems die ActionListener hinzu.
     *
     * @see #GUI()
     */
    public void initListener() {

        neuAbfrage();

        oeffnenAbfrage();

        programmBeendenAbfrage();
    }

    /**
     * Die Methode passt die Breite der Spalten an den Inhalt der Dateien
     * automatisch an.
     *
     * @param table - die Tabelle deren Spalten automatisch angepasst werden sollen
     */
    public void initColumnSizes(JTable table) {
        TableColumn column;
        Component comp;
        int headWidth;
        int cellWidth;

        TableCellRenderer render = table.getTableHeader().getDefaultRenderer();

        int colNum = table.getColumnCount();
        int rowNum = table.getRowCount();

        for (int i = 0; i < (colNum); i++) {

            column = table.getColumnModel().getColumn(i);
            comp = render.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headWidth = comp.getPreferredSize().width;

            cellWidth = 50;

            for (int j = 0; j < (rowNum); j++) {

                comp = render.getTableCellRendererComponent(table, table.getValueAt(j, i), false, false, j, i);
                cellWidth = Math.max(cellWidth, comp.getPreferredSize().width);
            }
            column.setPreferredWidth(Math.max(headWidth, cellWidth) + 20);
        }
    }

    private void neuAbfrage() {
        /*
         * Falls der JMenuItem "Neu" geklickt wird, wird die Tabelle entleert, falls sie
         * voll ist.
         *
         */
        neu.addActionListener(e -> {
            int response = initOption("Möchtest du eine neue Datei öffnen?");
            if (response == JOptionPane.YES_OPTION) {
                tableModel.setRowCount(0);
            }
        });
    }

    private void oeffnenAbfrage() {
        /*
         * Falls der JMenuItem "Oeffnen" geklickt wird, wird der Benutzer aufgefordert
         * anhand eines JFileChoosers eine x83-Datei zu waehlen.
         */
        oeffnen.addActionListener(e -> {
            gaebData.getItems().clear();
            jfc = new JFileChooser("Datei waehlen");
            jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter x83datei = new FileNameExtensionFilter("X83-Datei - (*.x83)", "x83");
            jfc.setFileFilter(x83datei);
            //entfernt Option ("alle Dateien")
            jfc.removeChoosableFileFilter(jfc.getChoosableFileFilters()[0]);
            int result = jfc.showOpenDialog(pane);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                try {
                    gaebData.docInit(path);
                } catch (ParserConfigurationException | IOException | SAXException e1) {
                    e1.printStackTrace();
                }
                gaebData.fillList();
                tableModel.setRowCount(0);
                for (Item item : gaebData.getItems()) {
                    tableModel.addRow(
                            new Object[]{item.getOz(), item.getMenge(), item.getEinheit(), item.getKurztext()});
                }
                initColumnSizes(table);

            }

        });
    }

    private void programmBeendenAbfrage() {
        /*
         * Beim Klicken des JMenuItems "Beenden" wird das Programm beendet.
         *
         */
        beenden.addActionListener(e -> {
            int response = initOption("Möchtest du das Programm beenden?");
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        });
    }

}
