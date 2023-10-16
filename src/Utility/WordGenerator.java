package Utility;

import WindowPackage.Window;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * <u>Word Generator class:</u><br/>
 * This class is used to get random words from a preloaded list
 * of words. {@code WordSettings} class is used to control random search of a word.
 * It also creates custom fonts and stores them in {@code UIManager} class.
 */
public class WordGenerator {

    /**
     * Max and Min length of word from the list
     */
    public static int MAX_WORD_LENGTH;
    public static int MIN_WORD_LENGTH;

    /**
     * Static ArrayList of words loaded from a file
     */
    private static ArrayList<String> words;

    /**
     * Random class object used to generate a random index number
     */
    private static Random random;


    /**
     * Initializes the variables, Reads and Stores the words from a file.
     */
    public static void init() {
        words = new ArrayList<>();
        random = new Random();

        try {
            String line;
            BufferedReader reader = new BufferedReader(
                    new FileReader("Resources/Files/englishWords.txt"));

            while((line = reader.readLine()) != null) {
                words.add(line.toUpperCase());
                if (MIN_WORD_LENGTH == 0)
                    MIN_WORD_LENGTH = line.length();

                MIN_WORD_LENGTH = Math.min(line.length(), MIN_WORD_LENGTH);
                MAX_WORD_LENGTH = Math.max(line.length(), MAX_WORD_LENGTH);
            }

            reader.close();

            createFonts();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a Random word using the {@code settings}.
     * @param settings Contains the filters for words to find
     */
    public static String getRandom(WordSettings settings) {
        String word = words.get(random.nextInt(words.size()));

        if(word.length() > settings.getMaxLength() || word.length() < settings.getMinLength())
            return getRandom(settings);

        if(!word.contains(settings.getWordFilter()))
            return getRandom(settings);

        return word;
    }

    /**
     * Creates and adds custom fonts to {@link UIManager}.
     */
    public static void createFonts() throws IOException, FontFormatException {
        Font large = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
                Window.class.getResourceAsStream("/Files/CrayonCrumble.ttf"))).deriveFont(45f);

        Map attributes = large.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);

        UIManager.put("underlineLarge", large.deriveFont(attributes));

        attributes.remove(TextAttribute.UNDERLINE);
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

        UIManager.put("large", large);
        UIManager.put("small", large.deriveFont(19f));
        UIManager.put("regular", large.deriveFont(30f));
        UIManager.put("strikethrough", large.deriveFont(attributes));

        UIManager.put("Button.disabledText", new ColorUIResource(Color.GRAY));
    }

}
