package bsuir.vlad.universityshooter.game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ProfileFile {
    private String filePath;
    private List<Profile> profileList;

    public ProfileFile(String filePath) {
        this.filePath = filePath;
    }

    public final void modify(Profile profile) {
        try {
            final File file = new File(filePath);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.normalize();

            Node firstNode = doc.getFirstChild();

            Node profileNode = doc.createElement("profile");
            firstNode.appendChild(profileNode);

            Element name = doc.createElement("name");
            name.setTextContent(profile.getName());
            profileNode.appendChild(name);

            Element difficulty = doc.createElement("difficulty");
            difficulty.setTextContent(profile.getDifficulty());
            profileNode.appendChild(difficulty);

            Element score = doc.createElement("score");
            score.setTextContent(String.valueOf(profile.getScore()));
            profileNode.appendChild(score);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public final List<Profile> load() {
        DefaultHandler handler = new DefaultHandler() {
            String tagName;

            String name;
            String difficulty;
            long score;

            @Override
            public void startDocument() {
                System.out.println("Start analysis file with profiles");
                profileList = new LinkedList<>();
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
                        case "name":
                            name = completeString;
                            break;
                        case "difficulty":
                            difficulty = completeString;
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
                if (qName.equals("profile")) {
                    Profile profile = new Profile(name, difficulty, score);

                    profileList.add(profile);
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

        Comparator<Profile> scoreComparator = (profile1, profile2) -> {
            Long score1 = profile1.getScore();
            Long score2 = profile2.getScore();

            return score2.compareTo(score1);
        };

        profileList.sort(scoreComparator);

        return profileList;
    }

}
