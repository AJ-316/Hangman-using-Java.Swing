package WindowPackage.MenuPackage.GameModePanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.MenuPackage.AbstractSubPanel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerSettingsSubPanel extends AbstractSubPanel {

    private static final float ICON_SCALE = 0.1f;
    private static final ImageIcon[] ADD_BTN_ICONS = new ImageIcon[] {
            Window.loadImage("Icons/addBtn", ICON_SCALE),
            Window.loadImage("Icons/addBtnPressed", ICON_SCALE),
            Window.loadImage("Icons/addBtnRollover", ICON_SCALE),
            Window.loadImage("Icons/addBtnDisabled", ICON_SCALE)
    };

    private static final ImageIcon[] REMOVE_BTN_ICONS = new ImageIcon[] {
            Window.loadImage("Icons/removeBtn", ICON_SCALE),
            Window.loadImage("Icons/removeBtnPressed", ICON_SCALE),
            Window.loadImage("Icons/removeBtnRollover", ICON_SCALE),
            Window.loadImage("Icons/removeBtnDisabled", ICON_SCALE)
    };

    private static final int MAX_PLAYERS = 4;
    private final ArrayList<JPanel> playerNamePanels;
    private final GridBagConstraints constraints;

    private final CButton addBtn;
    private final CButton removeBtn;

    private int currentPlayerIndex;

    public PlayerSettingsSubPanel() {
        super();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        playerNamePanels = new ArrayList<>();

        addBtn = createButton(this::addPlayerPanel, ADD_BTN_ICONS);
        removeBtn = createButton(this::removePlayerPanel, REMOVE_BTN_ICONS);

        createPlayerPanels();

        addPlayerPanel(null);
    }

    private CButton createButton(ActionListener listener, ImageIcon... icons) {
        CButton button = new CButton("");
        button.setIcon(icons[0]);
        button.setPressedIcon(icons[1]);
        button.setRolloverIcon(icons[2]);
        button.setDisabledIcon(icons[3]);
        button.addActionListener(listener);
        return button;
    }

    private void addPlayerPanel(ActionEvent e) {
        if(currentPlayerIndex > MAX_PLAYERS-1) {
            currentPlayerIndex = MAX_PLAYERS-1;
            return;
        }

        constraints.gridx = 0;
        constraints.gridy = currentPlayerIndex;
        add(playerNamePanels.get(currentPlayerIndex), constraints);
        setButtons(currentPlayerIndex);

        repaint();
        revalidate();
        currentPlayerIndex++;
    }

    private void removePlayerPanel(ActionEvent e) {
        currentPlayerIndex--;
        if(currentPlayerIndex < 0) {
            currentPlayerIndex = 0;
            return;
        }

        remove(playerNamePanels.get(currentPlayerIndex));
        setButtons(currentPlayerIndex-1);

        repaint();
        revalidate();
    }

    private void createPlayerPanels() {
        for(int i = 0; i < MAX_PLAYERS; i++) {
            JPanel panel = new JPanel();
            panel.setOpaque(false);

            CLabel label = new CLabel("Player " + (playerNamePanels.size() + 1));
            label.setVerticalAlignment(CLabel.BOTTOM);
            label.setHorizontalAlignment(CLabel.CENTER);
            JTextField field = new JTextField(label.getText());

            panel.add(label);
            panel.add(field);
            playerNamePanels.add(panel);
        }

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(playerNamePanels.get(0), constraints);
    }

    private void setButtons(int playerIndex) {
        constraints.gridy = playerIndex;
        removeBtn.setEnabled(playerIndex != 0);
        addBtn.setEnabled(playerIndex != MAX_PLAYERS - 1);

        constraints.gridx = 1;
        add(addBtn, constraints);
        constraints.gridx = 2;
        add(removeBtn, constraints);
    }
}
