package WindowPackage.MenuPackage.ScoreboardPanels;

import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractMenuPanel;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.MenuPackage.PlayerSettingsPanels.PlayerSettingsMenu;
import WindowPackage.MenuPackage.ScoreboardPanels.ScoreTableSubPanel;

import java.awt.*;
import java.util.ArrayList;

public class ScoreboardMenu extends AbstractMenuPanel {

    private final ScoreTableSubPanel scoreTable;

    public static final Color[] PLAYER_COLORS = new Color[] {CLabel.PURPLE, CLabel.PINK, CLabel.ORANGE, CLabel.CYAN};

    public ScoreboardMenu() {
        super();
        addTitleText("Scoreboard");

        scoreTable = new ScoreTableSubPanel();
        addSubPanel(scoreTable, 100, 0, 0, 0, 0, 0, 0);
    }

    public ScoreTableSubPanel getScoreTable() {
        return scoreTable;
    }

    public void createScoreboard() {
        PlayerSettingsMenu settingsMenu = MenuContainer.instance.getPlayerSettingsMenu();
        scoreTable.initTable(settingsMenu.getTotalRounds(), getPlayerLabels(settingsMenu.getPlayerNames()));
    }

    public ArrayList<CLabel> getPlayerLabels(String[] names) {
        ArrayList<CLabel> playerLabels = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            CLabel label = new CLabel(names[i], PLAYER_COLORS[i]);
            playerLabels.add(label);
        }

        return playerLabels;
    }

}
