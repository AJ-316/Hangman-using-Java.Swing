package WindowPackage.MenuPackage.ScoreboardPanels;

import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractSubPanel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ScoreTableSubPanel extends AbstractSubPanel {

    private static final ImageIcon DIVIDER_COL_IMG = Window.loadImage("Icons/dividerCol", Window.IMAGE_SCALE);
    private static final ImageIcon DIVIDER_ROW_IMG = Window.loadImage("Icons/dividerRow", Window.IMAGE_SCALE);
    private int roundsPlayed;
    private int dividerPtr;

    private HashMap<String, CLabel[]> rowData;
    private GridBagConstraints constraints;

    public ScoreTableSubPanel() {
        super();
    }

    public void addScore(String winnerPlayer, String word) {
        updateColumnData(winnerPlayer, roundsPlayed, "1");
        resetRowData(roundsPlayed, "0", winnerPlayer);
        progressRound(word);
    }

    private void progressRound(String word) {
        updateColumnData("Rounds", roundsPlayed, String.valueOf(roundsPlayed));
        System.out.println(rowData.get("Rounds")[roundsPlayed].getText());
        updateColumnData("Word", roundsPlayed, word);
        roundsPlayed++;
    }

    private void resetTable(String value) {
        for(String column : rowData.keySet()) {
            resetColumnData(column, value);
        }
    }

    private void resetRowData(int rowIndex, String value, String exceptColumn) {
        for(String column : rowData.keySet()) {
            if(exceptColumn != null && (column.equals(exceptColumn) || column.equals("Rounds") || column.equals("Word")))
                continue;
            rowData.get(column)[rowIndex].setText(value);
        }
    }

    private void resetColumnData(String column, String value) {
        for (CLabel label : rowData.get(column)) {
            label.setText(value);
        }
    }

    private void updateColumnData(String column, int rowIndex, String value) {
        rowData.get(column)[rowIndex].setText(value);
    }

    public ArrayList<CLabel> getPlayerLabels(String... playerNames) {
        ArrayList<CLabel> labels = new ArrayList<>();
        for(String name : playerNames)
            labels.add(new CLabel(name, CLabel.YELLOW));
        return labels;
    }

/*private void addRow(String... rowData) {
    int totalRounds = ((int[]) playersScore.values().toArray()[0]).length; // col count
    int totalPlayers = playersScore.size(); // row count

    constraints.gridy++;
    constraints.gridx = -1;
    System.out.println("new y: " + constraints.gridy);

    for(String cellData : rowData) {

        constraints.gridx++;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1f/totalPlayers;
        constraints.weighty = 1f/totalRounds;

        CLabel cellLabel = new CLabel(cellData);
        add(cellLabel, constraints);
        System.out.println("added label: " + constraints.gridx);
        addDivider(totalRounds);
    }
}*/

    private void addHeader(CLabel... labels) {
        int totalRounds = ((CLabel[]) rowData.values().toArray()[0]).length + 2; // col count
        int totalPlayers = labels.length; // row count

        constraints.gridy++;
        constraints.gridx = -1;
        System.out.println(Arrays.toString(labels));
        for(CLabel label : labels) {
            constraints.gridx++;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weightx = 1f/totalPlayers;
            constraints.weighty = 0;
            constraints.ipadx = 10;
            constraints.ipady = 10;
            add(label, constraints);
            addDivider(totalRounds);
        }
    }

    private CLabel[] getEmptyLabelsArray(int rounds) {
        CLabel[] scores = new CLabel[rounds];
        for(int i = 0; i < rounds; i++) {
            scores[i] = new CLabel("");
        }
        return scores;
    }

    public void initTable(int rounds, ArrayList<CLabel> playerLabels) {
        rowData = new HashMap<>();
        constraints = new GridBagConstraints();

        playerLabels.add(0, new CLabel("Rounds", CLabel.YELLOW));
        playerLabels.add(new CLabel("Word", CLabel.YELLOW));

        rowData.put("Rounds", getEmptyLabelsArray(rounds));
        rowData.put("Word", getEmptyLabelsArray(rounds));

        for(CLabel playerLabel : playerLabels) {
            rowData.put(playerLabel.getText(), getEmptyLabelsArray(rounds));
        }

        // Headers | Columns
        dividerPtr = playerLabels.size()-1;
        addHeader(playerLabels.toArray(new CLabel[] {}));

        CLabel[] rowDivider = new CLabel[playerLabels.size()];
        for(int i = 0; i < rowDivider.length; i++)
            rowDivider[i] = new CLabel(DIVIDER_ROW_IMG);
        addHeader(rowDivider);
        // Rows
        for (int i = 0; i < playerLabels.size(); i++)
            addRowData(i, playerLabels.get(i).getText());
    }

    private void addRowData(int columnIndex, String columnName) {
        CLabel[] scores = rowData.get(columnName);
        constraints.gridy = 1;
        constraints.weighty = 1;

        for (CLabel score : scores) {
            constraints.gridx = columnIndex*2;
            constraints.gridy++;
            add(score, constraints);
        }
    }

    private void addDivider(int cols) {
        constraints.gridx++;

        if(--dividerPtr < 0) return;

        constraints.gridheight = cols;
        constraints.weighty = 1;

        CLabel dividerLabel = new CLabel(DIVIDER_COL_IMG);
        add(dividerLabel, constraints);
    }

}
