package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import WindowPackage.MenuPackage.GameModePanels.PlayerSettingsSubPanel;

import java.awt.*;

/**
 * <u>Menu class</u><p>
 * This class has components which are used to set multiplayer settings.
 */
public class GameModeMenu extends AbstractMenuPanel {

    private final PlayerSettingsSubPanel modeSelection;

    private final CButton startBtn;

    public GameModeMenu() {
        super();

        addTitleText("Select Game Mode");

        modeSelection = new PlayerSettingsSubPanel();
        addSubPanel(modeSelection, 100, 0, 0, 0, 0, 0, 1);

        GridBagConstraints constraints = new GridBagConstraints();
        startBtn = new CButton("Start");
        startBtn.addActionListener(new MenuContainer.StateChangeButtonEvent(MenuState.GAME_START, null));

        constraints.gridy = 1;
        constraints.insets.bottom = 200;
        add(startBtn, constraints);
    }
}
