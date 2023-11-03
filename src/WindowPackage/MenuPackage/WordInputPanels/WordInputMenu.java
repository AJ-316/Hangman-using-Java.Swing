package WindowPackage.MenuPackage.WordInputPanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import CustomComponents.CTextField;
import DocumentListeners.TextLengthUpdateListener;
import WindowPackage.MenuPackage.AbstractMenuPanel;
import WindowPackage.MenuPackage.MenuContainer.StateChangeButtonEvent;
import WindowPackage.MenuPackage.MenuState;

import javax.swing.*;

public class WordInputMenu extends AbstractMenuPanel {

    private String[] playerNames;

    private final WordInputSubPanel wordInputPanel;

    public WordInputMenu() {
        super();
        addTitleText("Round 1");
        setTitlePadding(130);

        CLabel hintLabel = new CLabel("Type the Word to be Guessed");
        constraints.insets.top = 50;
        add(hintLabel, constraints);

        wordInputPanel = new WordInputSubPanel();
        addSubPanel(wordInputPanel, 40, 20, 40, 40, 0, 1, 1);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.insets.top = 20;
        CButton startGameBtn = new CButton("Start Guessing");
        startGameBtn.addActionListener(new StateChangeButtonEvent(MenuState.GAME_START));
        add(startGameBtn, constraints);
    }

    public void init(String[] names) {
        playerNames = names;
    }

    public void setRound(int playerIndex, int round) {
        wordInputPanel.setPlayerName(playerNames[playerIndex] + ":");
        setTitleText("Round " + round);
    }

    public String getWord() {
        return wordInputPanel.getWord().toUpperCase().trim();
    }

    public int getPlayerCount() {
        return playerNames.length;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public void reset() {
        playerNames = null;
    }

}
