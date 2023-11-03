package WindowPackage.GamePackage;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import Utility.WordGenerator;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.MenuPackage.MenuState;
import WindowPackage.MenuPackage.ScoreboardPanels.ScoreTableSubPanel;
import WindowPackage.MenuPackage.ScoreboardPanels.ScoreboardMenu;
import WindowPackage.MenuPackage.WordInputPanels.WordInputMenu;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * <u>Container Class</u><p>
 * A JPanel container which has - Hangman, Lose/Win text, Guess Word, Alphabet Container.
 * This class handles logic of guessing the word and uses alphabet container for player input.
 */
public class GameContainer extends JPanel {

    /**
     * The Singleton for this class.
     */
    public static final GameContainer instance = new GameContainer();

    /**
     * States of a game when it ends. (game over)
     */
    private static final int WORD = 0;
    private static final int HINT = 1;
    private static final int WIN = 1;
    private static final int LOSE = -1;
    private static final int NONE = 0;

    /**
     * The word and hint that is to be guessed.<p>
     * Example - [<b>"HELLO", "Hint: Used as a greeting"</b>]
     */
    private String[] wordAndHint;

    /**
     * Word that has to be guessed.
     * This will be displayed differently from a normal label.
     * It has gaps in between for visibility and the alphabets that have not guessed
     * will be displayed as underscores ( _ ).<p>
     * Example word <b>HELLO</b> will be displayed as - <b>_ _ L L _</b>.
     *
     */
    private static final CLabel guessWord = new CLabel();
    private static final CLabel wordHint = new CLabel();

    /**
     * Contains the displaying letters of the {@code guessWord}.<p>
     * Example - {@code {"_", "_", "L", "L", "_"}}
     */
    private String[] fillers;

    /**
     * Text that displays the WIN or LOSE state of player.
     */
    private final CLabel endLabel = new CLabel();
    private final CButton nextRoundButton = new CButton("");
    private MenuState nextBtnState;

    private AlphabetContainer alphabetContainer;
    private HangmanHandler hangmanHandler;
    private final WordInputMenu wordInputMenu;
    private ScoreboardMenu scoreboardMenu;

    private int gameCounter;
    private boolean isMultiPlayer;

    /**
     * Sets Layout null by calling parent constructor - {@link JPanel#JPanel(LayoutManager)}.
     * Then Sets Opacity and Size of this panel.
     */
    public GameContainer() {
        super(null);
        setOpaque(false);
        setSize(Window.WIDTH, Window.HEIGHT);

        wordInputMenu = new WordInputMenu();
        scoreboardMenu = new ScoreboardMenu();
    }

    /**
     * Initializes the variables of this class and adds to this panel.
     * Then adds this panel to the {@code Window.Pane}.
     * And sets the visibility to false.
     */
    public void init() {
        alphabetContainer = new AlphabetContainer();
        hangmanHandler = new HangmanHandler(this);

        nextRoundButton.addActionListener((e) -> MenuContainer.instance.changeState(nextBtnState));

        add(endLabel);
        add(nextRoundButton);
        add(wordHint);
        add(guessWord);
        add(alphabetContainer);

        Window.PANE.add(this);

        setVisible(false);
    }

    private void setNextRoundButton(String text, MenuState state) {
        nextRoundButton.setText(text);
        nextRoundButton.setSize(nextRoundButton.getPreferredSize());
        nextRoundButton.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - nextRoundButton.getWidth())/2,
                Window.HEIGHT - nextRoundButton.getHeight()*2);

        nextBtnState = state;

        if(nextRoundButton.getActionListeners().length == 0) {
            nextRoundButton.addActionListener((e) -> MenuContainer.instance.changeState(nextBtnState));
        }

        nextRoundButton.setVisible(true);
    }

    private void startWordGuessing(String[] wordAndHint) {
        if(!isVisible()) return;
        nextRoundButton.setVisible(false);
        this.wordAndHint = wordAndHint;
        fillers = null;
        updateText();
        System.out.println(Arrays.toString(wordAndHint));
    }


    /**
     * Finds a word through {@code WordGenerator} class and {@code WordSettings} class
     * and assigns it to {@code word} variable. Then {@code guessWord} label's text is updated.
     */
    public void start(String[] playerNames, boolean isMultiPlayer) {
        wordInputMenu.init(playerNames);
        this.isMultiPlayer = isMultiPlayer;

        if(!isMultiPlayer) {
            startWordGuessing(WordGenerator.getRandom(MenuContainer.instance.getWordSettings()));
            return;
        }

        if(gameCounter == -1) {
            gameCounter = 0;
            scoreboardMenu.createScoreboard();
            MenuContainer.instance.changeState(MenuState.WORD_INPUT_MENU);
            return;
        }

        startWordGuessing(new String[] {wordInputMenu.getWord(), "Word Selector: " + playerNames[getPlayerIndex()]});
    }

    /**
     * If {@code fillers} is null, it is initialized with same length of {@code word} variable and filled with underscores ("_").
     * Sets the {@code guessWord} label's text using {@code fillers} through the overridden method {@link CLabel#setText(String)}.
     * And updates the position of {@code guessWord} to center it.
     */
    private void updateText() {
        if(fillers == null) {
            // This will create an array which contains alphabets which
            // have been guessed and underscore for which have not been guessed.
            fillers = new String[wordAndHint[WORD].length()];
            Arrays.fill(fillers, "_");
        }

        // This will set the text of label adding space between the letters.
        guessWord.setText(String.join(" ", fillers));

        // Set hint for the word
        wordHint.setText(wordAndHint[HINT] == null ? "" : wordAndHint[HINT]);
        wordHint.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - wordHint.getWidth())/2 - 20, 160);

        // To center the position relative to Alphabet Container
        guessWord.setLocation(
                alphabetContainer.getX() + (alphabetContainer.getWidth() - guessWord.getWidth())/2,
                alphabetContainer.getY() - 100);
    }

    /**
     * If the {@code word} contains {@code alphabet} then {@code fillers} and label text is updated
     */
    public void guessAlphabet(String alphabet) {

        // index of the alphabet in the word variable
        int index = wordAndHint[WORD].indexOf(alphabet);

        // If the alphabet is not present the index will be -1 and
        // so the hangman will move to next stage and method is exited.
        if(index == -1) {

            // This moves the hangman to next stage and updates its face
            // if the player still has more chances.
            boolean lost = looseLive();
            if(!lost)
                hangmanHandler.setFace(HangmanHandler.HangmanFace.FROWN_LOOK);
            return;
        }

        // Loops and replaces underscores at the positions
        // of the same alphabet in word variable
        while (index != -1) {
            fillers[index] = alphabet;
            index = wordAndHint[WORD].indexOf(alphabet, index + 1);
        }

        // Updates the displayed text (guessWord label)
        updateText();

        // Checks if the player has won.
        // If not then Hangman's face is updated else
        // gameOver is called
        if(!guessWord.getText().contains("_")) {
            gameOver(WIN);
        } else
            hangmanHandler.setFace(HangmanHandler.HangmanFace.SMILE_LOOK);
    }

    /**
     * @return true if {@code guessWord} has an empty text
     */
    public boolean isEmpty() {
        return guessWord.getText().isEmpty();
    }

    /**
     * The Initial Hangman stage is set
     * @param lives stage index
     */
    public void setInitialLives(int lives) {
        if(!isVisible()) return;
        hangmanHandler.setStage(lives);
    }

    /**
     * Moves the hangman to next stage.
     * If hangman is already on last stage then the game is ended with {@code LOSE} state.
     * @return true if the game ends
     */
    private boolean looseLive() {

        int currentHangStage = hangmanHandler.nextStage();

        if(currentHangStage < 0) {
            gameOver(LOSE);
            return true;
        }

        return false;
    }

    /**
     * Reveals the word in {@code guessWord} label
     */
    private void showWord() {
        System.arraycopy(wordAndHint[WORD].split(""), 0, fillers, 0, wordAndHint[WORD].length());
        updateText();
    }

    /**
     * Overridden method that enables and disables {@code GameContainer}'s components
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if(!visible) return;
        alphabetContainer.reset();
        gameOver(NONE);
    }

    /**
     * This is called whenever the game is ended.
     * There are 3 ways in which game ends - {@code WIN}, {@code LOSE} or just exit ({@code NONE}).
     * Depending on these - {@code guessWord} label, {@code endText} label and
     * hangman's face is updated.
     */
    private void gameOver(int gameOverState) {

        String endText = "";

        setNextRoundButton("Next Word", MenuState.GAME_START);

        switch (gameOverState) {
            case WIN -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.LAUGH);
                guessWord.setForeground(CLabel.GREEN);
                endText = updateScoreboard(false);
            }
            case LOSE -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.DIE);
                guessWord.setForeground(CLabel.RED);
                showWord();
                endText = updateScoreboard(true);
            }
            case NONE -> {
                hangmanHandler.setFace(HangmanHandler.HangmanFace.NULL);
                guessWord.setForeground(Color.white);
            }
        }

        endLabel.setText(endText);
        endLabel.setLocation(alphabetContainer.getX() + (alphabetContainer.getWidth() - endLabel.getWidth())/2, 100);
        alphabetContainer.setEnabled(gameOverState == NONE);
    }

    private String updateScoreboard(boolean isWinner) {
        if(!isMultiPlayer) return isWinner ? "BETTER LUCK NEXT TIME!" : "GUESSED IT RIGHT!";

        ScoreTableSubPanel scoreTable = scoreboardMenu.getScoreTable();
        String playerName = wordInputMenu.getPlayerNames()[getPlayerIndex()];

        scoreTable.setRound(getRound());
        scoreTable.setPlayerScore(playerName, wordAndHint[WORD], isWinner);

        setNextRoundButton("Next Round", MenuState.WORD_INPUT_MENU);

        if(gameCounter >= MenuContainer.instance.getPlayerSettingsMenu().getTotalRounds() * wordInputMenu.getPlayerCount() - 1) {
            setNextRoundButton("Scoreboard", MenuState.SCOREBOARD_MENU);
            scoreTable.calcTotalScore();
        }

        gameCounter++;
        return String.format("%s %s", playerName, isWinner ? "Won!" : "Lost!");
    }

    public int getRound() {
        return (gameCounter + wordInputMenu.getPlayerCount()) / wordInputMenu.getPlayerCount();
    }

    public int getPlayerIndex() {
        System.out.println("Getting player Index: " + ((gameCounter - (getRound() - 1) * wordInputMenu.getPlayerCount()) - 1) + ", round: " + getRound() + ", counter i: " + gameCounter);
        System.out.println("half: " + (gameCounter - (getRound() - 1) * wordInputMenu.getPlayerCount()) + ", round: " + getRound() + ", counter i: " + gameCounter);
        return gameCounter - (getRound() - 1) * wordInputMenu.getPlayerCount();
    }

    public void resetGameCounter() {
        gameCounter = -1;
    }

    public WordInputMenu getWordInputMenu() {
        return wordInputMenu;
    }

    public ScoreboardMenu getScoreboardMenu() {
        return scoreboardMenu;
    }
}
