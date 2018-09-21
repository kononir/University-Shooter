package bsuir.vlad.universityshooter.application;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WeaponsFile {
    private String filePath;
    private List<Weapon> weaponsList;

    WeaponsFile(String filePath) {
        this.filePath = filePath;
    }

    public final List<Weapon> loadWeapons() {
        DefaultHandler handler = new DefaultHandler() {
            String tagName;

            String type;
            int damage;
            int distance;
            int maxAmmo;

            @Override
            public void startDocument() {
                System.out.println("Start analysis");
                weaponsList = new ArrayList<>();
            }

            @Override
            public void startElement(
                    String uri,
                    String localName,
                    String qName,
                    Attributes attributes
            ) {
                tagName = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                String completeString = new String(ch, start, length);

                int firstIndex = 0, lastIndex = 1;
                String firstSymbol = completeString.substring(firstIndex, lastIndex);

                if(!firstSymbol.equals("\n")) {
                    switch (tagName) {
                        case "type":
                            type = completeString;
                            break;
                        case "damage":
                            damage = new Integer(completeString);
                            break;
                        case "distance":
                            distance = new Integer(completeString);
                            break;
                        case "maxAmmo":
                            maxAmmo = new Integer(completeString);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (qName.equals("weapon")) {
                    Weapon weapon = new Weapon(type, damage, distance, maxAmmo);
                    weaponsList.add(weapon);
                }
            }

            @Override
            public void endDocument() {
                System.out.println("Finish analysis");
            }
        };

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = null;

        try {
            parser = parserFactory.newSAXParser();
            parser.parse(filePath, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return weaponsList;
    }

}
