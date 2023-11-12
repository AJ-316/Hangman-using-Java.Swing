package WindowPackage.MenuPackage.WordInputPanels;

import CustomComponents.CLabel;
import CustomComponents.CTextField;
import DocumentListeners.TextLengthUpdateListener;
import WindowPackage.MenuPackage.AbstractSubPanel;

import java.awt.*;

public class WordInputSubPanel extends AbstractSubPanel {

    private final CLabel playerInputLabel;
    private final CTextField playerInputField;

    public WordInputSubPanel() {
        super();
        setLayout(new FlowLayout());

        playerInputLabel = new CLabel("Place Holder", CLabel.YELLOW);

        CLabel wordLengthLabel = new CLabel("Length: ");

        playerInputField = new CTextField("Enter Word...");
        playerInputField.setEchoChar('-');
        playerInputField.getDocument().addDocumentListener(new TextLengthUpdateListener(wordLengthLabel));

        addComponent(playerInputLabel, 0, 0, 1, 0, 0, 0, 20);
        addComponent(playerInputField, 1, 0, 1, 0, 0, 0, 20);
        addComponent(wordLengthLabel, 2, 0, 1, 0, 0, 0, 0);
    }

    protected void setPlayerName(String text) {
        playerInputLabel.setText(text);
    }

    protected String getWord() {
        return playerInputField.getInputText();
    }


}
