package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;

class BotScoreFile {
    private String filePath;
    private HashMap<String, Integer> scoreMap;

    BotScoreFile(String filePath) {
        this.filePath = filePath;
    }

    final HashMap<String, Integer> loadBotsScore() {
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
            parser.parse(filePath, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return scoreMap;
    }

}
