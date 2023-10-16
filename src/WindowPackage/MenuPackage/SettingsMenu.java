package WindowPackage.MenuPackage;

import CustomComponents.CLabel;
import Utility.WordSettings;
import WindowPackage.MenuPackage.SettingsPanels.*;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;

/**
 * <u>Menu class</u><p>
 * This class has components which are used to set the word settings.
 */
public class SettingsMenu extends JPanel {

    /**
     * {@code WordLengthSettings} panel containing the components to set
     * settings for word's length.
     */
    private final WordLengthSettings wordLengthSettings;

    /**
     * {@code LivesSettings} panel containing the components to set
     * settings for player's chances to guess the word.
     */
    private final LivesSettings livesSettings;
    private final VowelContainSettings vowelContainSettings;
    private final VowelContainSettings vowelContainSetting2s;

    /**
     * Background Image for this menu.
     */
    private final CLabel menuBackground;

    /**
     * Title of this menu.
     */
    private final CLabel title;

    /**
     * Calls Parent constructor - {@link JPanel#JPanel(LayoutManager)}
     * to set layout manager as GridBagLayout and sets size and opacity.
     * Then adds necessary components and {@code AbstractSettings} classes.
     */
    public SettingsMenu() {
        super(new GridBagLayout());
        setSize(Window.WIDTH, Window.HEIGHT);
        setOpaque(false);

        // Background Image
        menuBackground = new CLabel(Window.loadImage("Backgrounds/menuSeparator"));
        menuBackground.setSize(Window.WIDTH, Window.HEIGHT);
        Window.PANE.add(menuBackground);

        // Title
        title = new CLabel();
        title.setFont(UIManager.getFont("underlineLarge"));

        Window.PANE.add(title);

        title.setText("Settings");
        title.setLocation(Window.WIDTH/2 - title.getPreferredSize().width/2, 50);

        // Setting Panels
        wordLengthSettings = new WordLengthSettings();
        livesSettings = new LivesSettings();
        vowelContainSettings = new VowelContainSettings();
        vowelContainSetting2s = new VowelContainSettings();

        addSettingsPanel(wordLengthSettings,   125,  0,   0, 0, 0, 1);
        addSettingsPanel(livesSettings,        125,  0, 100, 1, 0, 1);
        addSettingsPanel(vowelContainSettings,   0,  50,  0, 0, 1, 2);
        addSettingsPanel(vowelContainSetting2s,   0,  50,  0, 0, 1, 2);
    }

    /**
     * Adds a Setting panel with specified constraints.
     * @param settings Setting panel to be added
     * @param top Top padding of the panel
     * @param bottom bottom padding of the panel
     * @param right right padding of the panel
     * @param gridX column of panel
     * @param gridY row of panel
     * @param gridW The number of columns panel will fit in.
     */
    private void addSettingsPanel(AbstractSettings settings, int top, int bottom, int right, int gridX, int gridY, int gridW) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5f;
        constraints.weighty = 0.5f;
        constraints.insets.set(top, 0, bottom, right);
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridW;
        add(settings, constraints);
    }

    /**
     * @return {@code WordSettings} with values got from {@code AbstractSettings} panels.
     */
    public WordSettings getWordSettings() {
        return new WordSettings(
                wordLengthSettings.getValue(0),
                wordLengthSettings.getValue(1),
                vowelContainSettings.getValue());
    }

    /**
     * @return The initial hangman stage index got from {@code LivesSettings} panel.
     */
    public int getLives() {
        return livesSettings.getValue();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        menuBackground.setVisible(visible);
        title.setVisible(visible);
    }

}
