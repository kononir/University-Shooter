package bsuir.vlad.universityshooter.activeobjects.characters;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class BotScoreFile {
    private InputStream fileStream;
    private HashMap<String, Integer> scoreMap;

    public BotScoreFile(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public final HashMap<String, Integer> loadBotsScore() {
        DefaultHandler handler = new DefaultHandler() {
            String tagName;

            String type;
            int score;

            @Override
            public void startDocument() {
                System.out.println("Start analysis file with bots score");
                scoreMap = new HashMap<>();
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

                if (!firstSymbol.equals("\n")) {
                    switch (tagName) {
                        case "type":
                            type = completeString;
                            break;
                        case "score":
                            score = new Integer(completeString);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if (qName.equals("bot")) {
                    scoreMap.put(type, score);
                }
            }

            @Override
            public void endDocument() {
                System.out.println("Finish analysis");
            }
        };

        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(fileStream, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return scoreMap;
    }

}
