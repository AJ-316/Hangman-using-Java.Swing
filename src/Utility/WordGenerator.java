package Utility;

import WindowPackage.Window;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.*;

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
     * Characters that will separate word and different hints
     */
    public static String WORD_SEPARATOR = "=";
    public static String HINTS_SEPARATOR = "\\+";

    /**
     * Static Hashmap of word:hints[] loaded from a file
     */
    private static HashMap<String, String[]> wordsAndHints;

    /**
     * Random class object used to generate a random index number
     */
    private static Random random;


    /**
     * Initializes the variables, Reads and Stores the words from a file.
     */
    public static void init() {
        wordsAndHints = new HashMap<>();
        random = new Random();

        try {
            String line;
            BufferedReader reader = new BufferedReader(
                    new FileReader("Resources/Files/englishWords.txt"));

            while((line = reader.readLine()) != null) {

                String word;

                try {
                    String[] wordAndHint = line.split(WORD_SEPARATOR);
                    word = wordAndHint[0].trim().toUpperCase();

                    String[] hints = wordAndHint[1].split(HINTS_SEPARATOR);

                    for(int i = 0; i < hints.length; i++)
                        hints[i] = hints[i].trim();

                    wordsAndHints.put(word, hints);

                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Error while reading word and hints for line: \"" + line + "\"");
                    e.printStackTrace();
                    continue;
                }

                if (MIN_WORD_LENGTH == 0)
                    MIN_WORD_LENGTH = word.length();

                MIN_WORD_LENGTH = Math.min(word.length(), MIN_WORD_LENGTH);
                MAX_WORD_LENGTH = Math.max(word.length(), MAX_WORD_LENGTH);
            }

            reader.close();

            createFonts();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a Random word and random hint using the {@code settings}.
     * @param settings Contains the filters for words to find
     */
    public static String[] getRandom(WordSettings settings) {
        ArrayList<String> words = new ArrayList<>(wordsAndHints.keySet());
        String word = words.get(random.nextInt(words.size()));

        if(word.length() > settings.getMaxLength() || word.length() < settings.getMinLength())
            return getRandom(settings);

        if(!word.contains(settings.getWordFilter()))
            return getRandom(settings);

        String[] wordAndHint = new String[] {word, ""};

        if(settings.isHintsEnabled()) {
            String[] hints = wordsAndHints.get(word);
            wordAndHint[1] = "Hint: " + hints[random.nextInt(hints.length)];
        }

        return wordAndHint;
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
