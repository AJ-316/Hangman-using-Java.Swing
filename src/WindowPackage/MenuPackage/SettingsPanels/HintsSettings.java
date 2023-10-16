package WindowPackage.MenuPackage.SettingsPanels;

import CustomComponents.CButton;
import CustomComponents.CLabel;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Settings class</u><p>
 * This panel will have components that will take player input and
 * Have the option to show hints is Enabled or not.
 */
public class HintsSettings extends AbstractSettings {

    private boolean isHintsEnabled = true;
    private static final float ICON_SCALE = 0.15f;
    private static final ImageIcon ROLLOVER_ENABLED = Window.loadImage("ButtonIcons/check_rollover", ICON_SCALE);
    private static final ImageIcon ROLLOVER_DISABLED = Window.loadImage("ButtonIcons/uncheck_rollover", ICON_SCALE);
    private static final ImageIcon ENABLED = Window.loadImage("ButtonIcons/check", ICON_SCALE);
    private static final ImageIcon DISABLED = Window.loadImage("ButtonIcons/uncheck", ICON_SCALE);

    /**
     * Calls Parent constructor and adds the necessary components.
     */
    public HintsSettings() {
        super();

        // Title
        CLabel title = new CLabel("Hints");
        addComponent(title, 0, 0, 3, 0, 0, 0, 0);

        // Hint Button

        CButton hintsEnableButton = new CButton("Enable: ", CLabel.LIGHT_GRAY, "large");
        hintsEnableButton.setHorizontalTextPosition(SwingConstants.LEFT);
        hintsEnableButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        hintsEnableButton.setIcon(ENABLED);
        hintsEnableButton.setRolloverIcon(ROLLOVER_ENABLED);

        hintsEnableButton.addActionListener(new HintButtonEvent(this));
        addComponent(hintsEnableButton, 0, 1, 1, 30, 0, 0, 0);
    }

    /**
     * @return true if Hints are enabled else false.
     */
    public boolean isHintsEnabled() {
        return isHintsEnabled;
    }

    /**
         * Action Listener to enable and disable hint button
         */
    private record HintButtonEvent(HintsSettings settings) implements ActionListener {

        @Override
            public void actionPerformed(ActionEvent e) {
                CButton clicked = (CButton) e.getSource();
                if (settings.isHintsEnabled) {
                    settings.isHintsEnabled = false;
                    clicked.setIcon(DISABLED);
                    clicked.setRolloverIcon(ROLLOVER_DISABLED);
                    settings.repaint();
                    return;
                }
                settings.isHintsEnabled = true;
                clicked.setIcon(ENABLED);
                clicked.setRolloverIcon(ROLLOVER_ENABLED);
                settings.repaint();
            }
        }
}
