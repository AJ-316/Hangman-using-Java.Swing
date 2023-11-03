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
    private int currentRoundIndex;
    private int dividerPtr;

    private HashMap<String, CLabel[]> tableData;

    public ScoreTableSubPanel() {
        super();
    }

    public void calcTotalScore() {
        int i = 0;
        int[] totalScores = new int[tableData.size()-1];

        for(String column : tableData.keySet()) {
            if(column.equals("Rounds")) continue;
            for(CLabel scoreLabel : tableData.get(column)) {
                totalScores[i] += Integer.parseInt(scoreLabel.getText());
            }
            i++;
        }

        constraints.gridx = 0;
        constraints.gridy = currentRoundIndex+3;
        CLabel totalLabel = new CLabel("Total =", CLabel.YELLOW);
        add(totalLabel, constraints);

        i = 1;
        for(int score : totalScores) {
            constraints.gridx = i++ * 2;
            add(new CLabel(String.valueOf(score), CLabel.YELLOW), constraints);
        }
    }

    public void setRound(int round) {
        currentRoundIndex = round - 1;
        tableData.get("Rounds")[currentRoundIndex].setText(String.valueOf(round));
    }

    public void setPlayerScore(String playerName, String word, boolean isWinner) {
        System.out.printf("%s %s at round %d for word %s, index[%d]%n",
                playerName, isWinner ? "wins" : "loses", currentRoundIndex, word, currentRoundIndex);

        CLabel scoreLabel = tableData.get(playerName)[currentRoundIndex];
        scoreLabel.setToolTipText(word);
        scoreLabel.setRollover(null);

        scoreLabel.setText("0");

        if(!isWinner) return;
        scoreLabel.setText("1");
    }

    private void addHeader(CLabel... labels) {
        int totalRounds = ((CLabel[]) tableData.values().toArray()[0]).length + 3; // col count
        int totalPlayers = labels.length; // row count

        constraints.gridy++;
        constraints.gridx = -1;
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
        removeAll();
        tableData = new HashMap<>();

        playerLabels.add(0, new CLabel("Rounds"));

        tableData.put("Rounds", getEmptyLabelsArray(rounds));

        for(CLabel playerLabel : playerLabels) {
            tableData.put(playerLabel.getText(), getEmptyLabelsArray(rounds));
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
            addColData(i, playerLabels.get(i).getText());
    }

    private void addColData(int columnIndex, String columnName) {
        CLabel[] scores = tableData.get(columnName);
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0;

        for (CLabel score : scores) {
            constraints.gridx = columnIndex * 2;
            constraints.gridy++;
            add(score, constraints);
        }
    }

    private void addDivider(int cols) {
        constraints.gridx++;

        if(--dividerPtr < 0) return;

        constraints.gridheight = cols;
        constraints.weighty = 0;

        CLabel dividerLabel = new CLabel(DIVIDER_COL_IMG);
        add(dividerLabel, constraints);
    }

}
