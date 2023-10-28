package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import CustomComponents.CTextField;
import WindowPackage.GamePackage.GameContainer;
import WindowPackage.MenuPackage.MenuContainer.StateChangeButtonEvent;

public class WordInputMenu extends AbstractMenuPanel {

    private String[] playerNames;

    private final CLabel playerInputLabel;
    private final CTextField playerInputField;

    public WordInputMenu() {
        super();
        addTitleText("Round 1");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets.bottom = 50;
        CLabel hintLabel = new CLabel("Type the Word to be Guessed");
        add(hintLabel, constraints);
        constraints.insets.bottom = 0;

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        playerInputLabel = new CLabel("PLACE HOLDER", CLabel.YELLOW);
        add(playerInputLabel, constraints);

        constraints.gridx = 1;
        playerInputField = new CTextField("Enter Word...");
        add(playerInputField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets.top = 50;
        CButton startGameBtn = new CButton("Start Guessing");
        startGameBtn.addActionListener(new StateChangeButtonEvent(MenuState.GAME_START));
        add(startGameBtn, constraints);
    }

    public void init(String[] names) {
        playerNames = names;
    }

    public void setRound(int playerIndex, int round) {
        playerInputLabel.setText(playerNames[playerIndex] + ": ");
        addTitleText("Round " + round);
    }

    public String getWord() {
        return playerInputField.getText().toUpperCase().trim();
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
