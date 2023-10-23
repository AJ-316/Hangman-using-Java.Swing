package WindowPackage.MenuPackage;

import WindowPackage.MenuPackage.ScoreboardPanels.ScoreTableSubPanel;

public class ScoreboardMenu extends AbstractMenuPanel {

    private final ScoreTableSubPanel scoreTable;

    public ScoreboardMenu() {
        super();
        addTitleText("Scoreboard");

        scoreTable = new ScoreTableSubPanel();
        addSubPanel(scoreTable, 100, 0, 0, 0, 0, 0, 0);
    }

    public void createScoreboard() {
        scoreTable.initTable(10, scoreTable.getPlayerLabels("P1", "P2", "P3"));
    }

}
