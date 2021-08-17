package parser;

import model.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse "GAEBParser" dient zum Parsen der x83-Datei (GAEB-Datei).
 *
 * @author Sueleyman Demir - s0574402
 * @version 1.0
 * @since 17.07.2020
 */
public class GAEBParser {

    static DocumentBuilderFactory factory;
    static DocumentBuilder builder;
    static Document document;
    private final List<Item> ITEM_LIST = new ArrayList<>();

    /**
     * Die Methode docInit erstellt ein Dokument mit den XML-Inhalten mit dem DocumentBuiler
     *
     * @param path - Pfad zu der Datei
     * @throws ParserConfigurationException - zeigt einen Konfigurationsfehler an
     * @throws SAXException                 - kapselt einen allgemeinen SAX Fehler oder Warnung
     * @throws IOException                  - signalisiert, dass ein IO Fehler aufgetreten ist
     */
    public void docInit(String path) throws ParserConfigurationException, SAXException, IOException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File(path));
    }

    /**
     * Die Methode fillList() fuellt eine ArrayList als Item mit den notwendigen Elementen anhand eines zuvor erstellten Dokuments.
     *
     * @see #docInit(String)
     */
    public void fillList() {
        NodeList nList = document.getElementsByTagName("*");
        for (int i = 0; i < nList.getLength(); i++) {
            Element e = (Element) nList.item(i);

            if (e.getNodeName().equals("BoQCtgy")) {
                Element gewerk = (Element) e.getParentNode().getParentNode();
                String ordinalZahl = e.getAttribute("RNoPart");
                String ordinalZahl2 = gewerk.getAttribute("RNoPart");
                String gewerkOrTitelName = e.getElementsByTagName("span").item(0).getTextContent();

                if (ordinalZahl2.isEmpty()) {
                    Item gewerkItem = new Item(ordinalZahl, "", "", gewerkOrTitelName);
                    ITEM_LIST.add(gewerkItem);

                } else {
                    String titelZahl = ordinalZahl2 + "." + ordinalZahl;
                    Item titelItem = new Item(titelZahl, "", "", gewerkOrTitelName);
                    ITEM_LIST.add(titelItem);
                }

            }
            if (e.getNodeName().equals("Item")) {
                Element gewerk = (Element) e.getParentNode().getParentNode().getParentNode().getParentNode()
                        .getParentNode();
                Element titel = (Element) e.getParentNode().getParentNode().getParentNode();

                String ozItem = gewerk.getAttribute("RNoPart") + "." + titel.getAttribute("RNoPart") + "."
                        + e.getAttribute("RNoPart");
                String mengeItem = e.getElementsByTagName("Qty").item(0).getTextContent();
                String einheitItem = e.getElementsByTagName("QU").item(0).getTextContent();
                String kurztext = e.getElementsByTagName("TextOutlTxt").item(0).getTextContent();

                Item iO = new Item(ozItem, mengeItem, einheitItem, kurztext);
                ITEM_LIST.add(iO);

            }
        }
    }


    /**
     * Eine Zugriffsmethode, die eine Eigenschaft eines Objekts abfragt.
     * In diesem Falle die List der Items
     *
     * @return itemList als List
     */

    public List<Item> getItems() {
        return ITEM_LIST;
    }

}
