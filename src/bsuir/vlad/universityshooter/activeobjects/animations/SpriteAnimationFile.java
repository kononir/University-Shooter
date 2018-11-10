package bsuir.vlad.universityshooter.activeobjects.animations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;

public class SpriteAnimationFile {
    private String filePath;
    private HashMap<String, SpriteAnimation> spriteAnimationMap;

    public SpriteAnimationFile(String filePath) {
        this.filePath = filePath;
    }

    public final HashMap<String, SpriteAnimation> loadAnimations(String characterName) {
        DefaultHandler handler = new DefaultHandler() {
            String tagName;

            String animationName;
            int cycleDuration;
            int countOfAnimationFrames;
            int animationFrameHeight;
            int animationFrameWidth;

            @Override
            public void startDocument() {
                System.out.println("Start analysis file with sprite animations characteristics");
                spriteAnimationMap = new HashMap<>();
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
                        case "animationName":
                            animationName = completeString;
                            break;
                        case "cycleDuration":
                            cycleDuration = new Integer(completeString);
                            break;
                        case "countOfAnimationFrames":
                            countOfAnimationFrames = new Integer(completeString);
                            break;
                        case "animationFrameHeight":
                            animationFrameHeight = new Integer(completeString);
                            break;
                        case "animationFrameWidth":
                            animationFrameWidth = new Integer(completeString);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if (qName.equals("animation")) {
                    String playersImagePath = "animations/" + characterName + "/" + animationName + ".png";

                    Image playersImage = new Image(playersImagePath);
                    ImageView playersImageView = new ImageView(playersImage);
                    SpriteAnimation spriteAnimation = new SpriteAnimation(
                            playersImageView,
                            Duration.millis(cycleDuration),
                            countOfAnimationFrames,
                            animationFrameHeight,
                            animationFrameWidth
                    );
                    spriteAnimationMap.put(animationName, spriteAnimation);
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

        return spriteAnimationMap;
    }
}
