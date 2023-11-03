package WindowPackage.MenuPackage.PlayerSettingsPanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import CustomComponents.CTextField;
import WindowPackage.MenuPackage.AbstractMenuPanel;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.MenuPackage.MenuState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <u>Menu class</u><p>
 * This class has components which are used to set multiplayer settings.
 */
public class PlayerSettingsMenu extends AbstractMenuPanel {

    private final PlayerSettingsSubPanel settingsSubPanel;

    private final CTextField roundsField;

    public PlayerSettingsMenu() {
        super();

        addTitleText("Select Game Mode");
        setTitlePadding(50);

        settingsSubPanel = new PlayerSettingsSubPanel();
        addSubPanel(settingsSubPanel, 50, 0, 0, 0, 0, 0, 1);

        CButton startBtn = new CButton("Start");
        startBtn.addActionListener(new MenuContainer.StateChangeButtonEvent(MenuState.GAME_START));

        JPanel roundsPanel = new JPanel();
        roundsPanel.setOpaque(false);
        CLabel roundsLabel = new CLabel("Number Of Rounds: ", CLabel.LIGHT_GRAY);
        roundsPanel.add(roundsLabel);

        roundsField = new CTextField("Total Rounds...");
        roundsField.getInsets().set(0,0,0,0);
        roundsPanel.add(roundsField);

        constraints.gridy = 2;
        add(roundsPanel, constraints);

        constraints.gridy = 3;
        add(startBtn, constraints);
    }

    public int getTotalRounds() {
        String rounds = roundsField.getText();
        if(rounds.isEmpty()) rounds = "1";
        return Integer.parseInt(rounds);
    }

    public boolean isMultiPlayer() {
        return settingsSubPanel.isMultiPlayer;
    }

    public void setMultiPlayer(boolean isMultiPlayer) {
        settingsSubPanel.isMultiPlayer = isMultiPlayer;
    }

    public String[] getPlayerNames() {
        ArrayList<String> names = new ArrayList<>();

        for(JPanel panel : settingsSubPanel.playerNamePanels) {
            CTextField field = (CTextField) panel.getComponent(1);
            if(field.isEnabled()) {
                names.add(field.getText());
            }
        }

        return names.toArray(new String[] {});
    }
}
