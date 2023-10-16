package WindowPackage.MenuPackage;

import CustomComponents.CButton;
import WindowPackage.GamePackage.GameContainer;
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

    /**
     * Main Menu Panel
     */
    private MainMenu mainMenu;

    /**
     * Settings Menu Panel.
     */
    private SettingsMenu settingsMenu;

    /**
     * Back button to switch back to the {@code MainMenu} from any other {@code MenuState}.
     */
    private CButton back;

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

        back = new CButton("Main Menu", Color.WHITE, "small");

        back.setIcon(Window.loadImage("ButtonIcons/back0", Window.IMAGE_SCALE));
        back.setRolloverIcon(Window.loadImage("ButtonIcons/back1", Window.IMAGE_SCALE));
        back.setPressedIcon(Window.loadImage("ButtonIcons/back2", Window.IMAGE_SCALE));
        back.setSize(back.getPreferredSize());
        back.addActionListener(new BackButtonEvent());

        add(mainMenu);
        add(settingsMenu);
        add(back);

        Window.PANE.add(this);

        changeState(MenuState.MAIN);
    }

    /**
     * Changes the current state to specified {@code MenuState}
     * and sets the visibilities of different panels according to the state.
     */
    public void changeState(MenuState state) {

        boolean isStateGame = state.equals(MenuState.GAME);
        boolean isStateMain = state.equals(MenuState.MAIN);

        GameContainer.instance.setVisible(isStateGame);
        GameContainer.instance.startWordGuessing(instance.settingsMenu.getWordSettings());
        GameContainer.instance.setInitialLives(instance.settingsMenu.getLives());

        instance.settingsMenu.setVisible(state.equals(MenuState.SETTINGS));
        instance.mainMenu.setVisible(isStateMain);
        instance.back.setVisible(!isStateMain);
    }

    /**
     * Action Listener for the {@code back} button.
     */
    private static class BackButtonEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuContainer.instance.changeState(MenuState.MAIN);
            ((CButton)e.getSource()).setVisible(false);
        }
    }
}