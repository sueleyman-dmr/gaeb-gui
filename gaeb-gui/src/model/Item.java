package model;

/**
 * Die Klasse "Item" umfasst die Eigenschaften der Elemente in einer x83-Datei (GAEB-Datei) .
 *
 * @author Sueleyman Demir - s0574402
 * @version 1.0
 * @since 17.07.2020
 */

public class Item {
    private final String oz;
    private final String menge;
    private final String einheit;
    private final String kurztext;


    /**
     * Der Konstruktor, der Klasse "Item", dient zur Erstellung eines Objekts (Item)
     * mit folgenden Attributen. Konstruktor mit den Parametern:
     *
     * @param oz       - Ordinalzahl
     * @param menge    - Menge, kann leer bleiben im Falle vom Gewerk bzw. Titel
     * @param einheit  - Einheit, kann leer bleiben im Falle vom Gewerk bzw. Titel
     * @param kurztext - kurze Beschreibung des jeweiligen Items
     */
    public Item(String oz, String menge, String einheit, String kurztext) {
        this.oz = oz;
        this.menge = menge;
        this.einheit = einheit;
        this.kurztext = kurztext;
    }


    /**
     * Eine Zugriffsmethode, die eine Eigenschaft eines Objekts abfragt.
     * In diesem Falle die Ordinalzahl der Item
     *
     * @return oz als String
     */
    public String getOz() {
        return oz;
    }

    /**
     * Eine Zugriffsmethode, die eine Eigenschaft eines Objekts abfragt.
     * In diesem Falle die Menge der Item
     *
     * @return menge als String
     */
    public String getMenge() {
        return menge;
    }

    /**
     * Eine Zugriffsmethode, die eine Eigenschaft eines Objekts abfragt.
     * In diesem Falle die Einheit der Item
     *
     * @return einheit als String
     */
    public String getEinheit() {
        return einheit;
    }

    /**
     * Eine Zugriffsmethode, die eine Eigenschaft eines Objekts abfragt.
     * In diesem Falle den Kurztext der Item
     *
     * @return kurztext als String
     */
    public String getKurztext() {
        return kurztext;
    }

}
