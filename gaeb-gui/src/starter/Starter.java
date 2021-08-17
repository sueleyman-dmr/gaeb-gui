package starter;

import swingGUI.GUI;

import java.awt.*;


/**
 * Die Klasse "Starter" dient zum Starten des Programms.
 *
 * @author Sueleyman Demir - s0574402
 * @version 1.0
 * @since 17.07.2020
 */
public class Starter {

    /**
     * Main-Methode zum Aufrufen der grafischen Benutzeroberflaeche
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GUI f = new GUI();
            f.setVisible(true);
        });
    }
}
