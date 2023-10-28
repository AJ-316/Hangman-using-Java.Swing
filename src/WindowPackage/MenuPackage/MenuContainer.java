package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import Utility.WordSettings;
import WindowPackage.GamePackage.GameContainer;
import WindowPackage.MenuPackage.PlayerSettingsPanels.PlayerSettingsMenu;
import WindowPackage.MenuPackage.SettingsPanels.SettingsMenu;
import WindowPackage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <u>Container class</u><p>
 * Used to store Main and Settings menu.
 * It has {@code MenuState}s which is can be changed in order to
 * display the required menu panels or game panel.
 */
public class MenuContainer extends JPanel {

    /**
     * Singleton of this class.
     */
    public static final MenuContainer instance = new MenuContainer();

    private MainMenu mainMenu;
    private SettingsMenu settingsMenu;
    private PlayerSettingsMenu playerSettingsMenu;

    /**
     * Back button to switch back to the {@code MainMenu} from any other {@code MenuState}.
     */
    private CButton backBtn;

    /**
     * Sets layout, size and opacity.
     * Initializes {@code MaineMenu}, {@code SettingsMenu} and {@code back} button.
     */
    public void init() {
        setLayout(null);
        setSize(Window.WIDTH, Window.HEIGHT);
        setOpaque(false);

        mainMenu = new MainMenu();
        settingsMenu = new SettingsMenu();
        playerSettingsMenu = new PlayerSettingsMenu();

        backBtn = new CButton("Main Menu", Color.WHITE, "small");

        backBtn.setIcon(Window.loadImage("Icons/backBtn", Window.IMAGE_SCALE));
        backBtn.setRolloverIcon(Window.loadImage("Icons/backBtn_rollover", Window.IMAGE_SCALE));
        backBtn.setPressedIcon(Window.loadImage("Icons/backBtn_pressed", Window.IMAGE_SCALE));
        backBtn.setSize(backBtn.getPreferredSize());

        StateChangeButtonEvent backBtnEvent = new StateChangeButtonEvent(MenuState.MAIN_MENU);
        backBtnEvent.setPostStateChangeAction(() -> backBtn.setVisible(false));
        backBtn.addActionListener(backBtnEvent);

        add(mainMenu);
        add(settingsMenu);
        add(playerSettingsMenu);
        add(GameContainer.instance.getWordInputMenu());
        add(GameContainer.instance.getScoreboardMenu());
        add(backBtn);

        Window.PANE.add(this);

        changeState(MenuState.MAIN_MENU);
    }

    public WordSettings getWordSettings() {
        return settingsMenu.getWordSettings();
    }

    public PlayerSettingsMenu getPlayerSettingsMenu() {
        return playerSettingsMenu;
    }

    /**
     * Changes the current state to specified {@code MenuState}
     * and sets the visibilities of different panels according to the state.
     */
    public void changeState(MenuState state) {

        boolean isStateGameStart = state.equals(MenuState.GAME_START);
        boolean isStateWordInputMenu = state.equals(MenuState.WORD_INPUT_MENU);
        boolean isStatePlayerSettingsMenu = state.equals(MenuState.PLAYER_SETTINGS_MENU);

        // Pane Visibility
        GameContainer.instance.setVisible(isStateGameStart);
        mainMenu.setVisible(state.equals(MenuState.MAIN_MENU));
        settingsMenu.setVisible(state.equals(MenuState.SETTINGS_MENU));
        playerSettingsMenu.setVisible(isStatePlayerSettingsMenu);
        GameContainer.instance.getScoreboardMenu().setVisible(state.equals(MenuState.SCOREBOARD_MENU));
        GameContainer.instance.getWordInputMenu().setVisible(isStateWordInputMenu);

        // Back button visibility
        backBtn.setVisible(!mainMenu.isVisible());

        // Other Tasks
        if(isStateGameStart) {
            GameContainer.instance.start(playerSettingsMenu.getPlayerNames(), playerSettingsMenu.isMultiPlayer());
            GameContainer.instance.setInitialLives(settingsMenu.getLives());
        }

        if(isStateWordInputMenu)
            GameContainer.instance.getWordInputMenu().setRound(GameContainer.instance.getPlayerIndex(), GameContainer.instance.getRound());

        if(mainMenu.isVisible())
            GameContainer.instance.resetGameCounter();
    }

    /**
     * Action Listener for the buttons that will change the {@code MenuState} to the specified state.
     * postStateChangeAction a Runnable interface to run an action after the state has changed.
     */
    public static class StateChangeButtonEvent implements ActionListener {

        private Runnable preStateChangeAction;
        private Runnable postStateChangeAction;
        private final MenuState state;

        public StateChangeButtonEvent(MenuState state) {
            this.state = state;
        }

        public void setPreStateChangeAction(Runnable action) {
            preStateChangeAction = action;
        }

        public void setPostStateChangeAction(Runnable action) {
            postStateChangeAction = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(preStateChangeAction != null)
                preStateChangeAction.run();

            MenuContainer.instance.changeState(state);

            if(postStateChangeAction != null)
                postStateChangeAction.run();
        }
    }

    /**
     * Action Listener to exit the application.
     */
    public static class ExitWindowEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}