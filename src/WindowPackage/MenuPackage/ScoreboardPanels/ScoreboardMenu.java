package WindowPackage.MenuPackage.ScoreboardPanels;

import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractMenuPanel;
import WindowPackage.MenuPackage.MenuContainer;
import WindowPackage.MenuPackage.PlayerSettingsPanels.PlayerSettingsMenu;
import WindowPackage.MenuPackage.ScoreboardPanels.ScoreTableSubPanel;

import java.util.ArrayList;

public class ScoreboardMenu extends AbstractMenuPanel {

    private final ScoreTableSubPanel scoreTable;

    public ScoreboardMenu() {
        super();
        addTitleText("Scoreboard");

        scoreTable = new ScoreTableSubPanel();
        addSubPanel(scoreTable, 100, 0, 0, 0, 0, 0, 0);

        /*scoreTable.initTable(3, getPlayerLabels(new String[] {"P1", "P2"}));
        scoreTable.setRound(1);
        scoreTable.setPlayerScore("P1", "", true);
        scoreTable.setPlayerScore("P2", "", false);
        scoreTable.setRound(2);
        scoreTable.setPlayerScore("P1", "", false);
        scoreTable.setPlayerScore("P2", "", true);
        scoreTable.setRound(3);
        scoreTable.setPlayerScore("P1", "", false);
        scoreTable.setPlayerScore("P2", "", true);

        scoreTable.calcTotalScore();*/
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

        for (String name : names) {
            CLabel label = new CLabel(name, CLabel.YELLOW);
            playerLabels.add(label);
        }

        return playerLabels;
    }

}
